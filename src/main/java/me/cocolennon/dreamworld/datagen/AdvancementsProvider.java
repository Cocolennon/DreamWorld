package me.cocolennon.dreamworld.datagen;

import me.cocolennon.dreamworld.DreamWorld;
import me.cocolennon.dreamworld.items.ModItems;
import me.cocolennon.dreamworld.worldgen.ModDimensions;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.triggers.ChangeDimensionTrigger;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementsProvider extends FabricAdvancementProvider {
    public AdvancementsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider wrapperLookup, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStackTemplate(ModItems.CLOUD_PUFF), Component.literal("Dream World"), Component.literal("Time passes slower here"), Optional.of(new ClientAsset.ResourceTexture(DreamWorld.id("gui/advancements/backgrounds/cloud"))), AdvancementType.GOAL, false, false, false))
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick()).save(consumer, DreamWorld.id("root"));
        AdvancementHolder startDreaming = Advancement.Builder.advancement().parent(root)
                .display(ModItems.SLEEP_PILL, Component.literal("Start Dreaming"), Component.literal("Enter the Dream World"), AdvancementType.GOAL, true, true, false)
                .addCriterion("entered_dreamworld", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ModDimensions.DREAMWORLD)).save(consumer, DreamWorld.id("entered_dreamworld"));
        AdvancementHolder gatherClouds = Advancement.Builder.advancement().parent(startDreaming)
                .display(ModItems.CLOUD_PUFF, Component.literal("Gather the clouds"), Component.literal("My mom used to tell me you couldn't"), AdvancementType.TASK, true, true, false)
                .addCriterion("gathered_clouds", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLOUD_PUFF)).save(consumer, DreamWorld.id("gathered_clouds"));
    }
}
