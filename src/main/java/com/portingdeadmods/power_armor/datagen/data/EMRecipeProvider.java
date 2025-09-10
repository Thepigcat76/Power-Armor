package com.portingdeadmods.power_armor.datagen.data;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.utils.IngredientWithCount;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class EMRecipeProvider extends RecipeProvider {
    public EMRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        super.buildRecipes(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PABlocks.MACHINE_FRAME, 2)
                .pattern("PPP")
                .pattern("PSP")
                .pattern("PPP")
                .define('P', Items.IRON_INGOT)
                .define('S', Items.STONE)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PABlocks.COMPRESSOR)
                .pattern("P")
                .pattern("M")
                .pattern("B")
                .define('P', Items.PISTON)
                .define('M', PABlocks.MACHINE_FRAME)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        output.accept(
                PowerArmor.rl("copper_plate"),
                new CompressingRecipe(IngredientWithCount.of(Tags.Items.INGOTS_COPPER), 200, PAItems.COPPER_PLATE.toStack()),
                null
        );
        output.accept(
                PowerArmor.rl("iron_plate"),
                new CompressingRecipe(IngredientWithCount.of(Tags.Items.INGOTS_IRON), 200, PAItems.IRON_PLATE.toStack()),
                null
        );

    }
}
