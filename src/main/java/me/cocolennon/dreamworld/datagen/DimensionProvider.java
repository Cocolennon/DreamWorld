package me.cocolennon.dreamworld.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class DimensionProvider extends FabricDynamicRegistryProvider {
    public DimensionProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.LEVEL_STEM));
        entries.addAll(registries.lookupOrThrow(Registries.DIMENSION_TYPE));
    }

    @Override
    public String getName() {
        return "Dream World Dimensions";
    }
}
