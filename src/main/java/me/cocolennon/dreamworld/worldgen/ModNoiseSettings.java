package me.cocolennon.dreamworld.worldgen;

import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Optional;

public class ModNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> DREAM_WORLD_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, Identifier.fromNamespaceAndPath(DreamWorld.MOD_ID, "dreamworlddim_noise_settings"));

    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityLookup = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise> noiseLookup = context.lookup(Registries.NOISE);
        NoiseGeneratorSettings base = NoiseGeneratorSettings.overworld(context, false, false);
        NoiseRouter vanillaRouter = base.noiseRouter();
        DensityFunction factor = new DensityFunctions.HolderHolder(densityLookup.getOrThrow(vanillaDf("overworld/factor")));
        DensityFunction hillness = DensityFunctions.add(DensityFunctions.constant(-1.8f), DensityFunctions.mul(DensityFunctions.constant(-4.0f), vanillaRouter.erosion())).clamp(0.0F, 1.0F);
        DensityFunction rolling = DensityFunctions.mul(DensityFunctions.constant(0.06f), DensityFunctions.noise(noiseLookup.getOrThrow(Noises.SURFACE), 0.3, 0.0));
        DensityFunction wobble = DensityFunctions.mul(vanillaRouter.ridges(), DensityFunctions.add(DensityFunctions.constant(0.08F), DensityFunctions.mul(DensityFunctions.constant(0.07F), hillness)));
        DensityFunction offset = DensityFunctions.add(DensityFunctions.add(DensityFunctions.add(DensityFunctions.constant(offsetForY(72)), DensityFunctions.mul(DensityFunctions.constant(offsetForY(190) - offsetForY(72)), hillness)), wobble), rolling);
        DensityFunction depth = DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 1.5F, -1.5F), offset);
        DensityFunction terrain = DensityFunctions.mul(DensityFunctions.constant(4.0f), DensityFunctions.mul(depth, factor).quarterNegative());
        DensityFunction finalDensity = DensityFunctions.mul(DensityFunctions.constant(0.64f), DensityFunctions.interpolated(DensityFunctions.blendDensity(terrain), 4, 8)).squeeze();
        NoiseRouter solidNoiseRouter = new NoiseRouter(
                vanillaRouter.temperature(),
                vanillaRouter.vegetation(),
                vanillaRouter.continents(),
                vanillaRouter.erosion(),
                depth,
                vanillaRouter.ridges(),
                terrain,
                finalDensity
        );
        NoiseGeneratorSettings customSettings = new NoiseGeneratorSettings(
                base.noiseSettings(),
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                solidNoiseRouter,
                base.materialRule(),
                base.spawnTarget(),
                base.seaLevel() - 3,
                false,
                Optional.empty(),
                false,
                base.debugFunctions()
        );

        context.register(DREAM_WORLD_NOISE_SETTINGS, customSettings);
    }

    private static float offsetForY(int y) {
        return -1.5f + 3.0f * (y + 64) / 384.0f;
    }

    private static ResourceKey<DensityFunction> vanillaDf(String path) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace(path));
    }
}
