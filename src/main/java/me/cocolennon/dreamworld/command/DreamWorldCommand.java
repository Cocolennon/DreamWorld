package me.cocolennon.dreamworld.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.cocolennon.dreamworld.worldgen.ModDimensions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

public class DreamWorldCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("dreamworld").executes(DreamWorldCommand::run);
        dispatcher.register(command);
    }

    private static int run(CommandContext<CommandSourceStack> context) {
        if(!(context.getSource().getEntity() instanceof ServerPlayer player)) return 0;
        ServerLevel destination = context.getSource().getServer().getLevel(ModDimensions.DREAMWORLD);
        if(destination == null) return 0;
        Vec3 position = new Vec3(player.getX(), destination.getHeight(Heightmap.Types.MOTION_BLOCKING, 0, 0) + 1, player.getZ());
        player.teleport(new TeleportTransition(destination, position, Vec3.ZERO, player.getYRot(), player.getXRot(), TeleportTransition.DO_NOTHING));
        return Command.SINGLE_SUCCESS;
    }
}
