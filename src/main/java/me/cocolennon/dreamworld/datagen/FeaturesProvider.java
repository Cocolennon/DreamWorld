package me.cocolennon.dreamworld.datagen;

import me.cocolennon.dreamworld.DreamWorld;
import me.cocolennon.dreamworld.worldgen.features.CloudFeature;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FeaturesProvider extends FabricDynamicRegistryProvider {
    public static final ResourceKey<Feature> CLOUD_FEATURE_KEY = ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(DreamWorld.MOD_ID, "cloud"));
    public static final ResourceKey<PlacedFeature> CLOUD_PLACED_KEY = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(DreamWorld.MOD_ID, "cloud"));

    public FeaturesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
    }

    public static void bootstrapFeatures(BootstrapContext<Feature> context) {
        context.register(CLOUD_FEATURE_KEY, new CloudFeature());
    }

    public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> featureLookup = context.lookup(Registries.FEATURE);

        context.register(CLOUD_PLACED_KEY, new PlacedFeature(
                featureLookup.getOrThrow(CLOUD_FEATURE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), BiomeFilter.biome())
        ));
    }

    @Override
    public String getName() {
        return "Dream World Features";
    }
}
