package me.cocolennon.dreamworld.items;

import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {
    public static final ResourceKey<Item> SLEEP_PILL = create("sleep_pill");
    public static final ResourceKey<Item> OCHAME_KINOU_DISC = create("ochame_kinou_disc");

    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, DreamWorld.id(name));
    }
}
