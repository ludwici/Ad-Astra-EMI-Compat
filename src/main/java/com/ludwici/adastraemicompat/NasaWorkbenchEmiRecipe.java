package com.ludwici.adastraemicompat;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import earth.terrarium.adastra.common.recipes.machines.NasaWorkbenchRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class NasaWorkbenchEmiRecipe implements EmiRecipe {
    private static final int[][] INPUT_POSITIONS = {
        {56, 10},
        {47, 28}, {65, 28},
        {47, 46}, {65, 46},
        {47, 64}, {65, 64},
        {29, 82}, {47, 82}, {65, 82}, {83, 82},
        {29, 100}, {56, 100}, {83, 100}
    };

    private final ResourceLocation id;
    private final EmiRecipeCategory category;
    private final List<EmiIngredient> inputs;
    private final List<EmiStack> outputs;

    public NasaWorkbenchEmiRecipe(ResourceLocation id, NasaWorkbenchRecipe recipe, EmiRecipeCategory category) {
        this.id = id;
        this.category = category;
        this.inputs = recipe.ingredients().stream().map(EmiIngredient::of).toList();
        this.outputs = List.of(EmiStack.of(recipe.result()));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return category;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 152;
    }

    @Override
    public int getDisplayHeight() {
        return 120;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        for (int i = 0; i < INPUT_POSITIONS.length; i++) {
            int[] position = INPUT_POSITIONS[i];
            widgets.addSlot(i < inputs.size() ? inputs.get(i) : EmiStack.EMPTY, position[0], position[1]);
        }
        widgets.addSlot(outputs.get(0), 127, 52).recipeContext(this);
    }
}
