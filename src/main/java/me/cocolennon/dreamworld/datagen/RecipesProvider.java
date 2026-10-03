package me.cocolennon.dreamworld.datagen;

import me.cocolennon.dreamworld.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends FabricRecipeProvider {
    public RecipesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
                shaped(RecipeCategory.DECORATIONS, Items.CRAFTING_TABLE, 1)
                        .pattern("cc")
                        .pattern("cc")
                        .define('c', ModItems.CLOUD_PUFF)
                        .unlockedBy(getHasName(ModItems.CLOUD_PUFF), has(ModItems.CLOUD_PUFF))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "Dream World Recipes";
    }
}
