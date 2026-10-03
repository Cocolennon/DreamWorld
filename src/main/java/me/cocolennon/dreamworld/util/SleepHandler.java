package me.cocolennon.dreamworld.util;

import me.cocolennon.dreamworld.worldgen.ModDimensions;
import me.cocolennon.dreamworld.blocks.ModBlocks;
import me.cocolennon.dreamworld.effects.ModEffects;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class SleepHandler {
    private static final Set<UUID> PENDING = new HashSet<>();

    public static void initialize() {
        EntitySleepEvents.START_SLEEPING.register((entity, pos) -> {
            if (!(entity instanceof ServerPlayer player)) return;
            ResourceKey<Level> dimension = player.level().dimension();
            if (dimension == ModDimensions.DREAMWORLD || (dimension == Level.OVERWORLD && player.hasEffect(ModEffects.DREAMY))) PENDING.add(player.getUUID());
        });
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity,damageSource, damageAmount) -> {
            if(!(entity instanceof ServerPlayer player) || player.level().dimension() != ModDimensions.DREAMWORLD) return true;
            PENDING.remove(player.getUUID());
            player.setHealth(player.getMaxHealth());
            player.clearFire();
            player.resetFallDistance();
            player.setAirSupply(player.getMaxAirSupply());
            player.getFoodData().setFoodLevel(20);
            toOverworld(player);
            return false;
        });
        EntitySleepEvents.STOP_SLEEPING.register((entity, pos) -> PENDING.remove(entity.getUUID()));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (PENDING.isEmpty()) return;
            for (ServerPlayer player : new ArrayList<>(server.getPlayerList().getPlayers())) {
                if (!PENDING.contains(player.getUUID()) || !player.isSleepingLongEnough()) continue;
                PENDING.remove(player.getUUID());
                if (player.level().dimension() == Level.OVERWORLD) {
                    ServerLevel target = server.getLevel(ModDimensions.DREAMWORLD);
                    if (target != null) toDreamworld(player, target);
                } else toOverworld(player);
            }
        });
    }

    private static void toDreamworld(ServerPlayer player, ServerLevel target) {
        double x = player.getX(), z = player.getZ();
        int bx = Mth.floor(x), bz = Mth.floor(z);
        int top = target.getMinY() + target.getLogicalHeight() - 1;
        BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos();
        int y = Integer.MIN_VALUE;
        for (int cy = top; cy > target.getMinY() + 1; cy--) {
            p.set(bx, cy, bz);
            BlockState feet = target.getBlockState(p), head = target.getBlockState(p.above()), below = target.getBlockState(p.below());
            boolean ground = (below.isSolid() || below.getFluidState().is(FluidTags.WATER)) && !below.is(ModBlocks.CLOUD_BLOCK);
            if (feet.isAir() && head.isAir() && ground && !below.getFluidState().is(FluidTags.LAVA)) {
                y = cy;
                break;
            }
        }
        if (y == Integer.MIN_VALUE) return;
        player.removeEffect(ModEffects.DREAMY);
        player.stopSleeping();
        player.teleport(new TeleportTransition(target, new Vec3(x, y, z), Vec3.ZERO, player.getYRot(), player.getXRot(), TeleportTransition.DO_NOTHING));
    }

    private static void toOverworld(ServerPlayer player) {
        player.stopSleeping();
        player.teleport(player.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING));
    }
}
