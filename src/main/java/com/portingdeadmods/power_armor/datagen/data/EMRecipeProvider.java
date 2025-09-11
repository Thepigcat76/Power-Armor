package com.portingdeadmods.power_armor.datagen.data;

import com.portingdeadmods.portingdeadlibs.api.recipes.IngredientWithCount;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.registries.PATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
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
                .pattern("PRP")
                .pattern("RSR")
                .pattern("PRP")
                .define('P', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('S', Tags.Items.STONES)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PABlocks.COMPRESSOR)
                .pattern("P")
                .pattern("M")
                .pattern("B")
                .define('P', Items.PISTON)
                .define('M', PABlocks.MACHINE_FRAME)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PABlocks.ARMOR_MODIFICATION_TABLE)
                .pattern("PPP")
                .pattern(" B ")
                .pattern(" F ")
                .define('P', PATags.ItemTags.PLATES_IRON)
                .define('B', PAItems.BATTERY)
                .define('F', PABlocks.MACHINE_FRAME)
                .unlockedBy("has_iron_plates", has(PATags.ItemTags.PLATES_IRON))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PAItems.BATTERY)
                .pattern(" W ")
                .pattern("IRI")
                .pattern("IRI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', PATags.ItemTags.WIRES_COPPER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PAItems.ARMOR_PLATING, 2)
                .pattern("P")
                .pattern("W")
                .pattern("P")
                .define('P', PATags.ItemTags.PLATES_IRON)
                .define('W', PATags.ItemTags.WIRES_COPPER)
                .unlockedBy("has_iron_plate", has(PATags.ItemTags.PLATES_IRON))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PAItems.COPPER_WIRE, 6)
                .pattern("CCC")
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_HELMET)
                .pattern("PBP")
                .pattern("PGP")
                .pattern(" P ")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_CHESTPLATE)
                .pattern("P P")
                .pattern("PBP")
                .pattern("PPP")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_LEGGINGS)
                .pattern("PBP")
                .pattern("P P")
                .pattern("P P")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_BOOTS)
                .pattern("P P")
                .pattern("PBP")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
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
