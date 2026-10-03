package me.cocolennon.dreamworld.worldgen.structures;

import com.mojang.serialization.MapCodec;
import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ModStructures {
    public static final StructureType<FlatDryStructure> FLAT_DRY = register("flat_dry", FlatDryStructure.CODEC);

    public static <T extends Structure> StructureType<T>  register(String path, MapCodec<T> codec) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, DreamWorld.id(path), () -> codec);
    }

    public static void initialize() {
        DreamWorld.LOGGER.info("Initializing Structure Types");
    }
}
