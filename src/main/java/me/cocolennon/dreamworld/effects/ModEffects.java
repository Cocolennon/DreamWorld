package me.cocolennon.dreamworld.effects;

import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    public static final Holder<MobEffect> DREAMY = register("dreamy", new DreamyEffect());

    public static Holder<MobEffect> register(String path, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, DreamWorld.id(path), effect);
    }

    public static void initialize() {
        DreamWorld.LOGGER.info("Initializing Effects");
    }
}
