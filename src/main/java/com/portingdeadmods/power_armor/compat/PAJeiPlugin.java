package com.portingdeadmods.power_armor.compat;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.client.screens.ArmorModificationTableScreen;
import com.portingdeadmods.power_armor.client.screens.CompressorScreen;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;

import java.util.List;

@JeiPlugin
public final class PAJeiPlugin implements IModPlugin {
    public static final Identifier UID = PowerArmor.id("jei_plugin");

    @Override
    public Identifier getPluginUid() {
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

        registration.addCraftingStation(CompressingCategory.TYPE, PABlocks.COMPRESSOR);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);

        ClientLevel level = Minecraft.getInstance().level;
        RecipeManager recipeManager = (RecipeManager) level.recipeAccess();

        List<CompressingRecipe> recipes = getRecipesByType(recipeManager, CompressingRecipe.TYPE).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(CompressingCategory.TYPE, recipes);
    }

    public static <C extends net.minecraft.world.item.crafting.RecipeInput, T extends net.minecraft.world.item.crafting.Recipe<C>> java.util.List<net.minecraft.world.item.crafting.RecipeHolder<T>> getRecipesByType(net.minecraft.world.item.crafting.RecipeManager manager, net.minecraft.world.item.crafting.RecipeType<T> type) {
        return manager.getRecipes().stream()
                .filter(recipe -> recipe.value().getType() == type)
                .map(recipe -> (net.minecraft.world.item.crafting.RecipeHolder<T>) recipe)
                .toList();
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGenericGuiContainerHandler(CompressorScreen.class, new JeiWidgetBounds());
        registration.addGenericGuiContainerHandler(ArmorModificationTableScreen.class, new JeiWidgetBounds());
    }

}
