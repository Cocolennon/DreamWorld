package me.cocolennon.dreamworld.worldgen;

import me.cocolennon.dreamworld.DreamWorld;
import me.cocolennon.dreamworld.datagen.FeaturesProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    public static final ResourceKey<Biome> DREAM_PLAINS = ResourceKey.create(Registries.BIOME, DreamWorld.id("dream_plains"));
    public static final ResourceKey<Biome> DREAM_FOREST = ResourceKey.create(Registries.BIOME, DreamWorld.id("dream_forest"));
    public static final ResourceKey<Biome> DREAM_HILLS = ResourceKey.create(Registries.BIOME, DreamWorld.id("dream_hills"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> featureLookup = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<WorldCarver> carverLookup = context.lookup(Registries.CARVER);
        context.register(DREAM_PLAINS, dreamPlains(featureLookup, carverLookup));
        context.register(DREAM_FOREST, dreamForest(featureLookup, carverLookup));
        context.register(DREAM_HILLS, dreamHills(featureLookup, carverLookup));
    }

    private static Biome dreamPlains(HolderGetter<PlacedFeature> featureLookup, HolderGetter<WorldCarver> carverLookup) {
        BiomeGenerationSettings DREAM_PLAINS_GEN_SETTINGS = new BiomeGenerationSettings.Builder(featureLookup, carverLookup)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_MEADOW)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeaturesProvider.CLOUD_PLACED_KEY)
                .build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.7F).downfall(0.5f)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(0x7008E7).grassColorOverride(0x00d103).build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings(DREAM_PLAINS_GEN_SETTINGS).build();
    }

    private static Biome dreamForest(HolderGetter<PlacedFeature> featureLookup, HolderGetter<WorldCarver> carverLookup) {
        BiomeGenerationSettings DREAM_FOREST_GEN_SETTINGS = new BiomeGenerationSettings.Builder(featureLookup, carverLookup)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_FLOWER_FOREST)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_MEADOW)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeaturesProvider.CLOUD_PLACED_KEY)
                .addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.SPRING_WATER)
                .build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.55F).downfall(0.55F)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(0x7008E7).grassColorOverride(0x00d103).build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings(DREAM_FOREST_GEN_SETTINGS).build();
    }

    private static Biome dreamHills(HolderGetter<PlacedFeature> featureLookup, HolderGetter<WorldCarver> carverLookup) {
        BiomeGenerationSettings DREAM_HILLS_GEN_SETTINGS = new BiomeGenerationSettings.Builder(featureLookup, carverLookup)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_HILLS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeaturesProvider.CLOUD_PLACED_KEY)
                .build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.5F).downfall(0.55F)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(0x7008E7).grassColorOverride(0x00d103).build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings(DREAM_HILLS_GEN_SETTINGS).build();
    }
}
