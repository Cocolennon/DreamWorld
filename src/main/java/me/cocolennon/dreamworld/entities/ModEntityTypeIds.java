package me.cocolennon.dreamworld.entities;

import me.cocolennon.dreamworld.DreamWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeIds {
    public static ResourceKey<EntityType<?>> PIWWO = create("piwwo");

    private static ResourceKey<EntityType<?>> create(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, DreamWorld.id(name));
    }
}
