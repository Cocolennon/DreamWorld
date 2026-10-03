package me.cocolennon.dreamworld.items;

import me.cocolennon.dreamworld.DreamWorld;
import me.cocolennon.dreamworld.effects.ModEffects;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.function.Function;

public class ModItems {
    public static final Item SLEEP_PILL = register(ModItemIds.SLEEP_PILL, Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.6f).alwaysEdible().build(), Consumable.builder().consumeSeconds(1.6f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.DREAMY, 600))).build()));
    public static final Item CLOUD_PUFF = register(ModItemIds.CLOUD_PUFF, Item::new, new Item.Properties().stacksTo(16));

    public static final ResourceKey<JukeboxSong> OCHAME_KINOU_SONG = ResourceKey.create(Registries.JUKEBOX_SONG, DreamWorld.id("ochame_kinou"));
    public static final Item OCHAME_KINOU_DISC = register(ModItemIds.OCHAME_KINOU_DISC, Item::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).jukeboxPlayable(OCHAME_KINOU_SONG));

    public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void initialize() {
        DreamWorld.LOGGER.info("Initializing Items");
        CreativeModeTabEvents.modifyOutputEvent(ModCreativeTabs.DREAM_WORLD_TAB_KEY).register((dreamWorldTab) -> {
            dreamWorldTab.accept(CLOUD_PUFF);
            dreamWorldTab.accept(SLEEP_PILL);
            dreamWorldTab.accept(OCHAME_KINOU_DISC);
        });
    }
}
