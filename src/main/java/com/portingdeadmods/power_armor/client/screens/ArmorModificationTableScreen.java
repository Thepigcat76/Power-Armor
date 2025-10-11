package com.portingdeadmods.power_armor.client.screens;

import com.portingdeadmods.portingdeadlibs.api.client.screens.PanelContainerScreen;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.client.screens.widgets.ArmorPanelWidget;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import com.portingdeadmods.power_armor.content.menus.ArmorSlot;
import com.portingdeadmods.power_armor.networking.ArmorWidgetSetSlotPositionsPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ArmorModificationTableScreen extends PanelContainerScreen<ArmorModificationTableMenu> {
    public static final ResourceLocation BACKGROUND = PowerArmor.rl("textures/gui/armor_modification_table.png");

    public ArmorModificationTableScreen(ArmorModificationTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 212;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();

        addPanelWidget(new ArmorPanelWidget(this.leftPos + this.imageWidth, this.topPos + 2 + 2));

        PacketDistributor.sendToServer(new ArmorWidgetSetSlotPositionsPayload(27));
        this.menu.setArmorSlotPositions(27);

        for (ArmorSlot armorSlot : this.menu.getArmorSlots()) {
            armorSlot.setActive(false);
        }

    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return BACKGROUND;
    }
}
