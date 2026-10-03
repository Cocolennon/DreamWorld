package me.cocolennon.dreamworld.worldgen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;

import java.util.Optional;

public class FlatDryStructure extends Structure {
    public static final MapCodec<FlatDryStructure> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Structure.settingsCodec(i), Structure.DIRECT_CODEC.fieldOf("inner").forGetter(s -> s.inner),
            Codec.INT.fieldOf("radius").forGetter(s -> s.radius),
            Codec.INT.fieldOf("max_slope").forGetter(s -> s.maxSlope)
    ).apply(i, FlatDryStructure::new));

    private final Structure inner;
    private final int radius, maxSlope;

    public FlatDryStructure(StructureSettings settings, Structure inner, int radius, int maxSlope) {
        super(settings);
        this.inner = inner;
        this.radius = radius;
        this.maxSlope = maxSlope;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        int cx = context.chunkPos().getMiddleBlockX(), cz = context.chunkPos().getMiddleBlockZ();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[] offsets = { -radius, 0, radius };
        for (int dx : offsets) for (int dz : offsets) {
                int surface = context.chunkGenerator().getBaseHeight(cx + dx, cz + dz, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                int floor = context.chunkGenerator().getBaseHeight(cx + dx, cz + dz, Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState());
                if (surface != floor) return Optional.empty();
                min = Math.min(min, floor);
                max = Math.max(max, floor);
        }
        if (max - min > maxSlope) return Optional.empty();
        return inner.findValidGenerationPoint(context);
    }

    @Override
    public void afterPlace(WorldGenLevel level, StructureManager sm, ChunkGenerator gen, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, PiecesContainer pieces) {
        BoundingBox full = pieces.calculateBoundingBox();
        int topY = full.minY() - 1;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = Math.max(full.minX(), chunkBox.minX()); x <= Math.min(full.maxX(), chunkBox.maxX()); x++) for (int z = Math.max(full.minZ(), chunkBox.minZ()); z <= Math.min(full.maxZ(), chunkBox.maxZ()); z++) for (int y = topY; y > level.getMinY(); y--) {
                    pos.set(x, y, z);
                    if (level.getBlockState(pos).isSolid()) break;
                    int depth = topY - y;
                    level.setBlock(pos, depth < 4 ? Blocks.DIRT.defaultBlockState() : Blocks.STONE.defaultBlockState(), 2);
        }
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.FLAT_DRY;
    }
}
