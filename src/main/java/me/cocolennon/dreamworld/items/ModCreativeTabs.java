package me.cocolennon.dreamworld.items;

import me.cocolennon.dreamworld.DreamWorld;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    public static final ResourceKey<CreativeModeTab> DREAM_WORLD_TAB_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), DreamWorld.id("dream_world_tab"));
    public static final CreativeModeTab DREAM_WORLD_TAB = register(DREAM_WORLD_TAB_KEY, FabricCreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CLOUD_PUFF)).title(Component.translatable("creativeTab.dreamworld")).build());

    public static CreativeModeTab register(ResourceKey<CreativeModeTab> key, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
    }

    public static void initialize() {
        DreamWorld.LOGGER.info("Initializing Creative Tabs");
    }
}
