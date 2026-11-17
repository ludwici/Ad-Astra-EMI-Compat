package com.ludwici.adastraemicompat;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class ProcessingEmiRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiRecipeCategory category;
    private final List<EmiIngredient> inputs;
    private final List<EmiStack> outputs;
    private final int cookingTime;
    private final int energy;
    private final boolean alloying;

    public ProcessingEmiRecipe(ResourceLocation id, EmiRecipeCategory category, List<EmiIngredient> inputs,
                              List<EmiStack> outputs, int cookingTime, int energy, boolean alloying) {
        this.id = id;
        this.category = category;
        this.inputs = inputs;
        this.outputs = outputs;
        this.cookingTime = cookingTime;
        this.energy = energy;
        this.alloying = alloying;
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
        return 164;
    }

    @Override
    public int getDisplayHeight() {
        return 92;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        if (alloying) {
            int[][] positions = {{28, 27}, {46, 27}, {28, 45}, {46, 45}};
            for (int i = 0; i < positions.length; i++) {
                widgets.addSlot(i < inputs.size() ? inputs.get(i) : EmiStack.EMPTY, positions[i][0], positions[i][1]);
            }
        } else {
            widgets.addSlot(inputs.getFirst(), 38, 36);
        }

        widgets.addFillingArrow(78, 36, Math.max(50, cookingTime * 50));
        widgets.addSlot(outputs.getFirst(), 120, 36).recipeContext(this);
        widgets.addText(Component.translatable("emi.adastraemicompat.time", cookingTime), 7, 72, 0x777777, false);
        widgets.addText(Component.translatable("emi.adastraemicompat.energy", energy), 7, 82, 0x777777, false);
    }
}
