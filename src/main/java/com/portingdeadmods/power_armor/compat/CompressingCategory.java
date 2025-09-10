package com.portingdeadmods.power_armor.compat;

import com.portingdeadmods.portingdeadlibs.api.recipes.IngredientWithCount;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PATranslations;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CompressingCategory extends AbstractRecipeCategory<CompressingRecipe> {
    public static final RecipeType<CompressingRecipe> TYPE = RecipeType.create(PowerArmor.MODID, "compressing", CompressingRecipe.class);

    public CompressingCategory(IGuiHelper guiHelper) {
        super(TYPE, PATranslations.COMPRESSING_JEI_CATEGORY.component(), guiHelper.createDrawableItemLike(PABlocks.COMPRESSOR), 82, 54);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(1, 19)
                .setStandardSlotBackground()
                .addIngredients(iWCToIngredientSaveCount(recipe.ingredient()));

        builder.addOutputSlot(61, 19)
                .setOutputSlotBackground()
                .addItemStack(recipe.result());
    }


    public static @NotNull Ingredient iWCToIngredientSaveCount(IngredientWithCount ingredientWithCount) {
        Ingredient ingredient = ingredientWithCount.ingredient();
        for (ItemStack itemStack : ingredient.getItems()) {
            itemStack.setCount(ingredientWithCount.count());
        }
        return ingredient;
    }

    // TODO: Add duration text
    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
        builder.addAnimatedRecipeArrow(recipe.duration())
                .setPosition(26, 17);
    }
}
