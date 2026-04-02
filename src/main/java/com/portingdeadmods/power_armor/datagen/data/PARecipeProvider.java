package com.portingdeadmods.power_armor.datagen.data;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.registries.PATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.concurrent.CompletableFuture;

public class PARecipeProvider extends RecipeProvider {
    protected PARecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        shaped(RecipeCategory.BUILDING_BLOCKS, PABlocks.MACHINE_FRAME)
                .pattern("PRP")
                .pattern("RSR")
                .pattern("PRP")
                .define('P', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('S', Tags.Items.STONES)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(output);

        shaped(RecipeCategory.DECORATIONS, PABlocks.COMPRESSOR)
                .pattern("P")
                .pattern("M")
                .pattern("B")
                .define('P', Items.PISTON)
                .define('M', PABlocks.MACHINE_FRAME)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        shaped(RecipeCategory.DECORATIONS, PABlocks.ARMOR_MODIFICATION_TABLE)
                .pattern("PPP")
                .pattern(" B ")
                .pattern(" F ")
                .define('P', PATags.ItemTags.PLATES_IRON)
                .define('B', PAItems.BATTERY)
                .define('F', PABlocks.MACHINE_FRAME)
                .unlockedBy("has_iron_plates", has(PATags.ItemTags.PLATES_IRON))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.BATTERY)
                .pattern(" W ")
                .pattern("IRI")
                .pattern("IRI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', PATags.ItemTags.WIRES_COPPER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.ARMOR_PLATING, 2)
                .pattern("P")
                .pattern("W")
                .pattern("P")
                .define('P', PATags.ItemTags.PLATES_IRON)
                .define('W', PATags.ItemTags.WIRES_COPPER)
                .unlockedBy("has_iron_plate", has(PATags.ItemTags.PLATES_IRON))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.COPPER_WIRE, 6)
                .pattern("CCC")
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
                .save(output);

        shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_HELMET)
                .pattern("PBP")
                .pattern("PGP")
                .pattern(" P ")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
                .save(output);

        shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_CHESTPLATE)
                .pattern("P P")
                .pattern("PBP")
                .pattern("PPP")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
                .save(output);

        shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_LEGGINGS)
                .pattern("PBP")
                .pattern("P P")
                .pattern("P P")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
                .save(output);

        shaped(RecipeCategory.COMBAT, PAItems.POWER_ARMOR_BOOTS)
                .pattern("P P")
                .pattern("PBP")
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BATTERY)
                .unlockedBy("has_armor_plating", has(PAItems.ARMOR_PLATING))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.BLANK_ARMOR_MODULE, 3)
                .pattern(" I ")
                .pattern("ICI")
                .pattern(" I ")
                .define('I', PATags.ItemTags.PLATES_IRON)
                .define('C', PATags.ItemTags.PLATES_COPPER)
                .unlockedBy("has_iron_plates", has(PATags.ItemTags.PLATES_IRON))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.SOLAR_ARMOR_MODULE)
                .pattern("GGG")
                .pattern("LBL")
                .pattern("PPP")
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('B', PAItems.BLANK_ARMOR_MODULE)
                .define('L', Tags.Items.GEMS_LAPIS)
                .define('P', PATags.ItemTags.PLATES_COPPER)
                .unlockedBy("has_armor_module", has(PAItems.BLANK_ARMOR_MODULE))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.PLATING_ARMOR_MODULE)
                .pattern("IPI")
                .pattern("PBP")
                .pattern("IPI")
                .define('I', PATags.ItemTags.PLATES_IRON)
                .define('P', PAItems.ARMOR_PLATING)
                .define('B', PAItems.BLANK_ARMOR_MODULE)
                .unlockedBy("has_armor_module", has(PAItems.BLANK_ARMOR_MODULE))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.JETPACK_ARMOR_MODULE)
                .pattern("P P")
                .pattern("PBP")
                .pattern("R R")
                .define('P', PATags.ItemTags.PLATES_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('B', PAItems.BLANK_ARMOR_MODULE)
                .unlockedBy("has_armor_module", has(PAItems.BLANK_ARMOR_MODULE))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.LASER_ARMOR_MODULE)
                .pattern("  R")
                .pattern("PB ")
                .pattern("IP ")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('P', PATags.ItemTags.PLATES_IRON)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', PAItems.BLANK_ARMOR_MODULE)
                .unlockedBy("has_armor_module", has(PAItems.BLANK_ARMOR_MODULE))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.ENERGY_ARMOR_MODULE)
                .pattern(" B ")
                .pattern("RMR")
                .pattern(" B ")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('B', PAItems.BATTERY)
                .define('M', PAItems.BLANK_ARMOR_MODULE)
                .unlockedBy("has_armor_module", has(PAItems.BLANK_ARMOR_MODULE))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.NIGHT_VISION_ARMOR_MODULE)
                .pattern(" D ")
                .pattern("GMG")
                .pattern(" D ")
                .define('D', Tags.Items.DUSTS_GLOWSTONE)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('M', PAItems.BLANK_ARMOR_MODULE)
                .unlockedBy("has_armor_module", has(PAItems.BLANK_ARMOR_MODULE))
                .save(output);

        shaped(RecipeCategory.MISC, PAItems.SPEED_ARMOR_MODULE)
                .pattern(" C ")
                .pattern("SMS")
                .pattern(" C ")
                .define('S', Items.SUGAR)
                .define('C', PATags.ItemTags.PLATES_COPPER)
                .define('M', PAItems.BLANK_ARMOR_MODULE)
                .unlockedBy("has_armor_module", has(PAItems.BLANK_ARMOR_MODULE))
                .save(output);

        output.accept(
                recipeId("copper_plate"),
                new CompressingRecipe(new SizedIngredient(tag(Tags.Items.INGOTS_COPPER), 1), 200, new ItemStackTemplate(PAItems.COPPER_PLATE)),
                null
        );
        output.accept(
                recipeId("iron_plate"),
                new CompressingRecipe(new SizedIngredient(tag(Tags.Items.INGOTS_IRON), 1), 200, new ItemStackTemplate(PAItems.IRON_PLATE)),
                null
        );
    }

    public static ResourceKey<Recipe<?>> recipeId(String path) {
        return ResourceKey.create(Registries.RECIPE, PowerArmor.id(path));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new PARecipeProvider(registries, output);
        }

        public String getName() {
            return "Power Armor Recipes";
        }
    }

}
