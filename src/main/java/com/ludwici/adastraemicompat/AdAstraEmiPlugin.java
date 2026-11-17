package com.ludwici.adastraemicompat;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import earth.terrarium.adastra.common.recipes.machines.AlloyingRecipe;
import earth.terrarium.adastra.common.recipes.machines.CompressingRecipe;
import earth.terrarium.adastra.common.recipes.machines.CryoFreezingRecipe;
import earth.terrarium.adastra.common.recipes.machines.NasaWorkbenchRecipe;
import earth.terrarium.adastra.common.recipes.machines.OxygenLoadingRecipe;
import earth.terrarium.adastra.common.recipes.machines.RefiningRecipe;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.ingredient.SizedFluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@EmiEntrypoint
public final class AdAstraEmiPlugin implements EmiPlugin {
    private static final EmiRecipeCategory NASA_WORKBENCH = category("nasa_workbench", ModItems.NASA_WORKBENCH.get());
    private static final EmiRecipeCategory COMPRESSING = category("compressing", ModItems.COMPRESSOR.get());
    private static final EmiRecipeCategory ALLOYING = category("alloying", ModItems.ETRIONIC_BLAST_FURNACE.get());
    private static final EmiRecipeCategory CRYO_FREEZING = category("cryo_freezing", ModItems.CRYO_FREEZER.get());
    private static final EmiRecipeCategory OXYGEN_LOADING = category("oxygen_loading", ModItems.OXYGEN_LOADER.get());
    private static final EmiRecipeCategory REFINING = category("refining", ModItems.FUEL_REFINERY.get());

    @Override
    public void register(EmiRegistry registry) {
        workstation(registry, NASA_WORKBENCH, ModItems.NASA_WORKBENCH.get());
        workstation(registry, COMPRESSING, ModItems.COMPRESSOR.get());
        workstation(registry, ALLOYING, ModItems.ETRIONIC_BLAST_FURNACE.get());
        workstation(registry, CRYO_FREEZING, ModItems.CRYO_FREEZER.get());
        workstation(registry, OXYGEN_LOADING, ModItems.OXYGEN_LOADER.get());
        workstation(registry, REFINING, ModItems.FUEL_REFINERY.get());

        RecipeManager manager = registry.getRecipeManager();

        for (RecipeHolder<NasaWorkbenchRecipe> holder : manager.getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH.get())) {
            registry.addRecipe(new NasaWorkbenchEmiRecipe(holder.id(), holder.value(), NASA_WORKBENCH));
        }

        for (RecipeHolder<CompressingRecipe> holder : manager.getAllRecipesFor(ModRecipeTypes.COMPRESSING.get())) {
            CompressingRecipe recipe = holder.value();
            registry.addRecipe(new ProcessingEmiRecipe(holder.id(), COMPRESSING,
                List.of(EmiIngredient.of(recipe.ingredient())), List.of(EmiStack.of(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }

        for (RecipeHolder<AlloyingRecipe> holder : manager.getAllRecipesFor(ModRecipeTypes.ALLOYING.get())) {
            AlloyingRecipe recipe = holder.value();
            registry.addRecipe(new ProcessingEmiRecipe(holder.id(), ALLOYING,
                recipe.ingredients().stream().map(EmiIngredient::of).toList(), List.of(EmiStack.of(recipe.result())),
                recipe.cookingTime(), recipe.energy(), true));
        }

        for (RecipeHolder<CryoFreezingRecipe> holder : manager.getAllRecipesFor(ModRecipeTypes.CRYO_FREEZING.get())) {
            CryoFreezingRecipe recipe = holder.value();
            registry.addRecipe(new ProcessingEmiRecipe(holder.id(), CRYO_FREEZING,
                List.of(EmiIngredient.of(recipe.input())), List.of(fluid(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }

        for (RecipeHolder<OxygenLoadingRecipe> holder : manager.getAllRecipesFor(ModRecipeTypes.OXYGEN_LOADING.get())) {
            OxygenLoadingRecipe recipe = holder.value();
            registry.addRecipe(new ProcessingEmiRecipe(holder.id(), OXYGEN_LOADING,
                List.of(fluidIngredient(recipe.input())), List.of(fluid(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }

        for (RecipeHolder<RefiningRecipe> holder : manager.getAllRecipesFor(ModRecipeTypes.REFINING.get())) {
            RefiningRecipe recipe = holder.value();
            registry.addRecipe(new ProcessingEmiRecipe(holder.id(), REFINING,
                List.of(fluidIngredient(recipe.input())), List.of(fluid(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }
    }

    private static EmiRecipeCategory category(String name, Item workstation) {
        return new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath("ad_astra", name), EmiStack.of(workstation));
    }

    private static void workstation(EmiRegistry registry, EmiRecipeCategory category, Item item) {
        registry.addCategory(category);
        registry.addWorkstation(category, EmiStack.of(item));
    }

    private static EmiStack fluid(ResourceStack<FluidResource> stack) {
        return EmiStack.of(stack.resource().getType(), stack.amount());
    }

    private static EmiIngredient fluidIngredient(SizedFluidIngredient ingredient) {
        return EmiIngredient.of(ingredient.getFluids().stream().map(AdAstraEmiPlugin::fluid).toList());
    }
}
