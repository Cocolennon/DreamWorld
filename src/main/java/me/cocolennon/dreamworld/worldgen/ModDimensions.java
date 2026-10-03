package me.cocolennon.dreamworld.worldgen;

import com.mojang.datafixers.util.Pair;
import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.biome.Climate.Parameter.point;
import static net.minecraft.world.level.biome.Climate.Parameter.span;

public class ModDimensions {
    public static final ResourceKey<DimensionType> DREAMWORLD_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath(DreamWorld.MOD_ID, "dreamworlddim"));
    public static final ResourceKey<LevelStem> DREAMWORLD_OPTIONS = ResourceKey.create(Registries.LEVEL_STEM, Identifier.fromNamespaceAndPath(DreamWorld.MOD_ID, "dreamworlddim"));
    public static final ResourceKey<Level> DREAMWORLD = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(DreamWorld.MOD_ID, "dreamworlddim"));

    private static final EnvironmentAttributeMap DREAMWORLD_ATTRIBUTES = EnvironmentAttributeMap.builder()
            .set(EnvironmentAttributes.BED_RULE, new BedRule(BedRule.Rule.ALWAYS, BedRule.Rule.NEVER, false, false, Optional.empty()))
            .set(EnvironmentAttributes.STRAW_BED_RULE, new BedRule(BedRule.Rule.ALWAYS, BedRule.Rule.NEVER, false, false, Optional.empty()))
            .set(EnvironmentAttributes.CLOUD_COLOR, new Vector4f(1.0f, 1.0f, 1.0f, 0.8f))
            .set(EnvironmentAttributes.SKY_COLOR, new Vector3f(0.0f, 1.0f, 1.0f))
            .set(EnvironmentAttributes.FOG_COLOR, new Vector3f(0.0f, 1.0f, 1.0f))
            .set(EnvironmentAttributes.CLOUD_HEIGHT, 312.0f)
            .set(EnvironmentAttributes.CAN_PILLAGER_PATROL_SPAWN, false)
            .set(EnvironmentAttributes.CAN_START_RAID, false)
            .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
            .build();

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        HolderGetter<Timeline> timelines = context.lookup(Registries.TIMELINE);
        HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
        context.register(DREAMWORLD_TYPE, new DimensionType(
                false, true, false, false, 1.0, -64,
                384, 384, blocks.getOrThrow(BlockTags.INFINIBURN_OVERWORLD), 0.0f,
                new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0), DimensionType.Skybox.OVERWORLD,
                CardinalLighting.Type.DEFAULT, DREAMWORLD_ATTRIBUTES, HolderSet.direct(timelines.getOrThrow(Timelines.OVERWORLD_DAY)),
                clocks.get(WorldClocks.OVERWORLD).map(holder -> (Holder<WorldClock>) holder)
        ));
    }

    public static void bootstrapDimension(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettingsRegistry = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<DimensionType> dimTypeRegistry = context.lookup(Registries.DIMENSION_TYPE);

        List<Pair<Climate.ParameterPoint, Holder<Biome>>> biomesList = List.of(
                Pair.of(Climate.parameters(span(-1.0F, 1.0F), span(-1.0F, 0.0F), span(-1.0F, 1.0F), span(-0.45F, 1.0F), point(0.0F), span(-1.0F, 1.0F), 0.0F), biomeRegistry.getOrThrow(ModBiomes.DREAM_PLAINS)),
                Pair.of(Climate.parameters(span(-1.0F, 1.0F), span(0.0F, 1.0F), span(-1.0F, 1.0F), span(-0.45F, 1.0F), point(0.0F), span(-1.0F, 1.0F), 0.0F), biomeRegistry.getOrThrow(ModBiomes.DREAM_FOREST)),
                Pair.of(Climate.parameters(span(-1.0F, 1.0F), span(-1.0F, 1.0F), span(-1.0F, 1.0F), span(-1.0F, -0.45F), point(0.0F), span(-1.0F, 1.0F), 0.0F), biomeRegistry.getOrThrow(ModBiomes.DREAM_HILLS))
        );

        Climate.ParameterList<net.minecraft.core.Holder<Biome>> parameters = new Climate.ParameterList<>(biomesList);
        MultiNoiseBiomeSource multiNoiseSource = MultiNoiseBiomeSource.createFromList(parameters);

        ChunkGenerator vanillaNoiseGenerator = new NoiseBasedChunkGenerator(multiNoiseSource, noiseSettingsRegistry.getOrThrow(ModNoiseSettings.DREAM_WORLD_NOISE_SETTINGS));
        context.register(DREAMWORLD_OPTIONS, new LevelStem(dimTypeRegistry.getOrThrow(ModDimensions.DREAMWORLD_TYPE), vanillaNoiseGenerator));
    }
}
