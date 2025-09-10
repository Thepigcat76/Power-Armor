package com.portingdeadmods.power_armor.compat;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;

import java.util.List;

@JeiPlugin
public final class PAJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = PowerArmor.rl("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);

        registration.addRecipeCategories(new CompressingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);

        registration.addRecipeCatalyst(PABlocks.COMPRESSOR, CompressingCategory.TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);

        Level level = Minecraft.getInstance().level;
        RecipeManager recipeManager = level.getRecipeManager();
        List<CompressingRecipe> recipes = recipeManager.getAllRecipesFor(CompressingRecipe.TYPE).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(CompressingCategory.TYPE, recipes);
    }
}
