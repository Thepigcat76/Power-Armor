package com.portingdeadmods.power_armor.client.screens;

import com.portingdeadmods.portingdeadlibs.api.client.screens.PanelContainerScreen;
import com.portingdeadmods.portingdeadlibs.client.screens.widgets.EnergyBarWidget;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.client.screens.widgets.ArmorPanelWidget;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import com.portingdeadmods.power_armor.content.menus.ArmorSlot;
import com.portingdeadmods.power_armor.networking.ArmorWidgetSetSlotPositionsPayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ArmorModificationTableScreen extends PanelContainerScreen<ArmorModificationTableMenu> {
    public static final Identifier BACKGROUND = PowerArmor.id("textures/gui/armor_modification_table.png");

    private ArmorPanelWidget armorPanelWidget;

    public ArmorModificationTableScreen(ArmorModificationTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 186, 212);
        this.titleLabelX = 18;
        this.inventoryLabelX = 18;
        this.inventoryLabelY = this.imageHeight - 90;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(EnergyBarWidget.forgeEnergy(this.leftPos + 4, this.topPos + 10, this.menu.blockEntity, true));

        this.armorPanelWidget = new ArmorPanelWidget(this.leftPos + this.imageWidth, this.topPos + 2 + 2);
        addPanelWidget(this.armorPanelWidget);

        ClientPacketDistributor.sendToServer(new ArmorWidgetSetSlotPositionsPayload(27));
        this.menu.setArmorSlotPositions(27);

        for (ArmorSlot armorSlot : this.menu.getArmorSlots()) {
            armorSlot.setActive(false);
        }

    }

    @Override
    public Optional<GuiEventListener> getChildAt(double x, double y) {
        boolean isArmorPanelNotHovered = x >= this.armorPanelWidget.getX()
                && y <= this.armorPanelWidget.getY() + this.armorPanelWidget.getOpenHeight()
                && x < this.armorPanelWidget.getX() + this.armorPanelWidget.getClosedWidth()
                && y > this.armorPanelWidget.getY() + (this.armorPanelWidget.getClosedHeight() - 4);
        if (isArmorPanelNotHovered) {
            return Optional.empty();
        }
        return super.getChildAt(x, y);
    }

    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.getBackgroundTexture(), this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    public @NotNull Identifier getBackgroundTexture() {
        return BACKGROUND;
    }
}
