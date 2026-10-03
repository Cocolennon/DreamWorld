package me.cocolennon.dreamworld;

import me.cocolennon.dreamworld.datagen.*;
import me.cocolennon.dreamworld.worldgen.ModBiomes;
import me.cocolennon.dreamworld.worldgen.ModDimensions;
import me.cocolennon.dreamworld.worldgen.ModNoiseSettings;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DreamWorldDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(DimensionProvider::new);
		pack.addProvider(BiomeProvider::new);
		pack.addProvider(NoiseSettingsProvider::new);
		pack.addProvider(FeaturesProvider::new);
		pack.addProvider(AdvancementsProvider::new);
		pack.addProvider(RecipesProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.LEVEL_STEM, ModDimensions::bootstrapDimension);
		registryBuilder.add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType);
		registryBuilder.add(Registries.BIOME, ModBiomes::bootstrap);
		registryBuilder.add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrapNoiseSettings);
		registryBuilder.add(Registries.FEATURE, FeaturesProvider::bootstrapFeatures);
		registryBuilder.add(Registries.PLACED_FEATURE, FeaturesProvider::bootstrapPlacedFeatures);
	}
}
