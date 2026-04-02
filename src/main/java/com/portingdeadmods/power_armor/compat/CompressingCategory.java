package com.portingdeadmods.power_armor.compat;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PATranslations;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CompressingCategory extends AbstractRecipeCategory<CompressingRecipe> {
    public static final IRecipeType<CompressingRecipe> TYPE = IRecipeType.create(PowerArmor.MODID, "compressing", CompressingRecipe.class);

    public CompressingCategory(IGuiHelper guiHelper) {
        super(TYPE, PATranslations.COMPRESSING_JEI_CATEGORY.component(), guiHelper.createDrawableItemLike(PABlocks.COMPRESSOR), 82, 54);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
        HolderSet<Item> values = recipe.ingredient().ingredient().getValues();
        List<ItemStack> stacks = new ArrayList<>();
        for (Holder<Item> value : values) {
            ItemStack stack = new ItemStack(value, recipe.ingredient().count());
            stacks.add(stack);
        }
        builder.addInputSlot(1, 19)
                .setStandardSlotBackground()
                .addIngredients(VanillaTypes.ITEM_STACK, stacks);

        builder.addOutputSlot(61, 19)
                .setOutputSlotBackground()
                .add(recipe.assemble(null));
    }


//    public static @NotNull Ingredient iWCToIngredientSaveCount(SizedIngredient ingredientWithCount) {
//        Ingredient ingredient = ingredientWithCount.ingredient();
//        for (ItemStack itemStack : ingredient.getItems()) {
//            itemStack.setCount(ingredientWithCount.count());
//        }
//        return ingredient;
//    }

    // TODO: Add duration text
    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
        builder.addAnimatedRecipeArrow(recipe.duration())
                .setPosition(26, 17);
    }
}
