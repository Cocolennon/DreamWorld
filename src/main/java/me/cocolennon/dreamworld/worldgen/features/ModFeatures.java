package me.cocolennon.dreamworld.worldgen.features;

import com.mojang.serialization.MapCodec;
import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ModFeatures {
    public static final MapCodec<CloudFeature> CLOUD_CODEC = register("cloud", CloudFeature.CODEC);

    public static void initialize() {
        DreamWorld.LOGGER.info("Initializing Features");
    }

    private static <T extends Feature> MapCodec<T> register(String path, MapCodec<T> codec) {
        return Registry.register(BuiltInRegistries.FEATURE_TYPE, Identifier.fromNamespaceAndPath(DreamWorld.MOD_ID, path), codec);
    }
}
