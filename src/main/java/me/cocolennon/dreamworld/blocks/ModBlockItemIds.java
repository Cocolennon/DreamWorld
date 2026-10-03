package me.cocolennon.dreamworld.blocks;

import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

public class ModBlockItemIds {
    public static final BlockItemId CLOUD_BLOCK = create("cloud");

    private static BlockItemId create(String name) {
        Identifier id = DreamWorld.id(name);
        return BlockItemId.create(id, id);
    }
}
