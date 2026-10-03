package me.cocolennon.dreamworld.worldgen.features;

import com.mojang.serialization.MapCodec;
import me.cocolennon.dreamworld.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public class CloudFeature implements Feature {
    public static final MapCodec<CloudFeature> CODEC = MapCodec.unit(new CloudFeature());

    private static final int BASE_Y = 202;
    private static final int MIN_Y = 200;
    private static final int LAYERS = 7;
    private static final int SPAN = 31;

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        final BlockState cloud = ModBlocks.CLOUD_BLOCK.defaultBlockState();
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        final boolean[] placed = new boolean[SPAN * SPAN * LAYERS];

        boolean stretchX = random.nextBoolean();
        float stretch = 1.0f + random.nextFloat() * 0.8f;
        int puffs = 4 + random.nextInt(5);

        for (int i = 0; i < puffs; i++) {
            int ox = (int) ((random.nextInt(17) - 8) * (stretchX ? stretch : 1.0f));
            int oz = (int) ((random.nextInt(17) - 8) * (stretchX ? 1.0f : stretch));
            int oy = random.nextInt(3) - 1;
            int pr = 3 + random.nextInt(4);
            int py = 2 + random.nextInt(2);
            placePuff(level, random, cloud, pos, placed, origin.getX(), origin.getZ(), ox, BASE_Y + oy, oz, pr, py);
        }
        return true;
    }

    private void placePuff(WorldGenLevel level, RandomSource random, BlockState cloud, BlockPos.MutableBlockPos pos, boolean[] placed, int originX, int originZ, int ox, int cy, int oz, int pr, int py) {
        final int cx = originX + ox, cz = originZ + oz, size = pr * 2 + 1;
        final double prSq = (double) (pr * pr);
        final double[] horiz = new double[size];
        for (int i = 0; i < size; i++) {
            int v = i - pr;
            horiz[i] = (v * v) / prSq;
        }
        final double pySq = (py * py);
        final int minY = -py / 2;
        for (int x = -pr; x <= pr; x++) {
            final double dx = horiz[x + pr];
            final int wx = cx + x;
            final int lx = wx - originX;
            final boolean xOk = Math.abs(lx) <= 15;
            for (int y = minY; y <= py; y++) {
                final double dxy = dx + (y * y) / pySq;
                final int wy = cy + y;
                final int ly = wy - MIN_Y;
                for (int z = -pr; z <= pr; z++) {
                    double d = dxy + horiz[z + pr];
                    if (d > 1.0 || random.nextFloat() < d * d * 0.5f) continue;
                    if (!xOk) continue;
                    final int wz = cz + z;
                    final int lz = wz - originZ;
                    if (Math.abs(lz) > 15) continue;
                    int idx = ((lx + 15) * SPAN + (lz + 15)) * LAYERS + ly;
                    if (placed[idx]) continue;
                    placed[idx] = true;
                    level.setBlock(pos.set(wx, wy, wz), cloud, 2);
                }
            }
        }
    }
}
