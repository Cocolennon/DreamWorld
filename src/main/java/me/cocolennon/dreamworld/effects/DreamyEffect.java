package me.cocolennon.dreamworld.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DreamyEffect extends MobEffect {
    protected DreamyEffect() {
        super(MobEffectCategory.NEUTRAL, 0x87CDFF);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }
}
