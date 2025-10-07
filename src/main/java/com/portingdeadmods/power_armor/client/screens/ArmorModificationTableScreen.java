package com.portingdeadmods.power_armor.client.screens;

import com.portingdeadmods.portingdeadlibs.api.client.screens.PanelContainerScreen;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ArmorModificationTableScreen extends PanelContainerScreen<ArmorModificationTableMenu> {
    public static final ResourceLocation BACKGROUND = PowerArmor.rl("textures/gui/armor_modification_table.png");

    public ArmorModificationTableScreen(ArmorModificationTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 212;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return BACKGROUND;
    }
}
