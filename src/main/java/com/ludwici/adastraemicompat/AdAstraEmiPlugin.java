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
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.QuantifiedFluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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

        for (NasaWorkbenchRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH.get())) {
            registry.addRecipe(new NasaWorkbenchEmiRecipe(recipe.id(), recipe, NASA_WORKBENCH));
        }

        for (CompressingRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.COMPRESSING.get())) {
            registry.addRecipe(new ProcessingEmiRecipe(recipe.id(), COMPRESSING,
                List.of(EmiIngredient.of(recipe.ingredient())), List.of(EmiStack.of(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }

        for (AlloyingRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.ALLOYING.get())) {
            registry.addRecipe(new ProcessingEmiRecipe(recipe.id(), ALLOYING,
                recipe.ingredients().stream().map(EmiIngredient::of).toList(), List.of(EmiStack.of(recipe.result())),
                recipe.cookingTime(), recipe.energy(), true));
        }

        for (CryoFreezingRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.CRYO_FREEZING.get())) {
            registry.addRecipe(new ProcessingEmiRecipe(recipe.id(), CRYO_FREEZING,
                List.of(EmiIngredient.of(recipe.input())), List.of(fluid(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }

        for (OxygenLoadingRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.OXYGEN_LOADING.get())) {
            registry.addRecipe(new ProcessingEmiRecipe(recipe.id(), OXYGEN_LOADING,
                List.of(fluidIngredient(recipe.input())), List.of(fluid(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }

        for (RefiningRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.REFINING.get())) {
            registry.addRecipe(new ProcessingEmiRecipe(recipe.id(), REFINING,
                List.of(fluidIngredient(recipe.input())), List.of(fluid(recipe.result())),
                recipe.cookingTime(), recipe.energy(), false));
        }
    }

    private static EmiRecipeCategory category(String name, Item workstation) {
        return new EmiRecipeCategory(new ResourceLocation("ad_astra", name), EmiStack.of(workstation));
    }

    private static void workstation(EmiRegistry registry, EmiRecipeCategory category, Item item) {
        registry.addCategory(category);
        registry.addWorkstation(category, EmiStack.of(item));
    }

    private static EmiStack fluid(FluidHolder holder) {
        return EmiStack.of(holder.getFluid(), holder.getFluidAmount());
    }

    private static EmiIngredient fluidIngredient(QuantifiedFluidIngredient ingredient) {
        return EmiIngredient.of(ingredient.getFluids().stream()
            .map(holder -> EmiStack.of(holder.getFluid(), ingredient.getFluidAmount())).toList());
    }
}
