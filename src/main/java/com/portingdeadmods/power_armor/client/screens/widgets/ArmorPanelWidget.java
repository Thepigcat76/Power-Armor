package com.portingdeadmods.power_armor.client.screens.widgets;

import com.portingdeadmods.portingdeadlibs.PortingDeadLibs;
import com.portingdeadmods.portingdeadlibs.api.client.screens.widgets.MenuWidgetContext;
import com.portingdeadmods.portingdeadlibs.api.client.screens.widgets.PanelWidget;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import com.portingdeadmods.power_armor.content.menus.ArmorSlot;
import com.portingdeadmods.power_armor.networking.ArmorWidgetOpenClosePayload;
import com.portingdeadmods.power_armor.networking.ArmorWidgetSetSlotPositionsPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class ArmorPanelWidget extends PanelWidget {
    public static final ResourceLocation WIDGET_SPRITE = PowerArmor.rl("widget/widget_armor_right");
    public static final ResourceLocation WIDGET_OPEN_SPRITE = PowerArmor.rl("widget/widget_armor_open");
    public static final int WIDGET_WIDTH = 32, WIDGET_HEIGHT = 32;
    public static final int WIDGET_OPEN_WIDTH = 32, WIDGET_OPEN_HEIGHT = 112;

    public ArmorPanelWidget(int x, int y) {
        super(x, y, WIDGET_OPEN_WIDTH, WIDGET_OPEN_HEIGHT, WIDGET_WIDTH, WIDGET_HEIGHT);
        this.open = false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean isHovered = mouseX >= this.getX()
                && mouseY >= this.getY()
                && mouseX < this.getX() + this.getClosedWidth()
                && mouseY < this.getY() + (this.getClosedHeight() - 4);

        if (isHovered) {
            this.open = !this.open;

            PacketDistributor.sendToServer(new ArmorWidgetOpenClosePayload(open));
            if (this.context.menu() instanceof ArmorModificationTableMenu menu) {
                for (ArmorSlot armorslot : menu.getArmorSlots()) {
                    armorslot.setActive(this.open);
                }
            }

            if (open) {
                this.setSize(WIDGET_OPEN_WIDTH, WIDGET_OPEN_HEIGHT);
            } else {
                this.setSize(WIDGET_WIDTH, WIDGET_HEIGHT);
            }
            this.context.onWidgetResizeFunc().accept(this);
            return super.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        if (open) {
//            Font font = Minecraft.getInstance().font;
//
            guiGraphics.blitSprite(WIDGET_OPEN_SPRITE, getX(), getY(), WIDGET_OPEN_WIDTH, WIDGET_OPEN_HEIGHT);
//
//            guiGraphics.renderFakeItem(REDSTONE_STACK, getX() + 3, getY() + 8);
//            guiGraphics.drawString(font, Component.literal("Redstone").withStyle(ChatFormatting.WHITE), getX() + 20, getY() + 13, -1);
//
//            guiGraphics.drawString(font, Component.literal("Signal").withStyle(ChatFormatting.GRAY), getX() + 5, getY() + 54, -1);
//            RedstoneBlockEntity.RedstoneSignalType signalType = this.upgradeBlockEntity.getRedstoneSignalType();
//            if (signalType == null) signalType = RedstoneBlockEntity.RedstoneSignalType.IGNORED;
//            guiGraphics.drawString(font, Component.translatable("redstone_signal_type." + IndustrialReforged.MODID + "." + signalType.getSerializedName()).withStyle(ChatFormatting.WHITE), getX() + 5, getY() + 54 + font.lineHeight + 2, -1);
        } else {
            guiGraphics.blitSprite(WIDGET_SPRITE, getX(), getY(), WIDGET_WIDTH, WIDGET_HEIGHT);
        }
    }

    @Override
    public void onWidgetResized(PanelWidget resizedWidget) {
        super.onWidgetResized(resizedWidget);

        if (this.context.menu() instanceof ArmorModificationTableMenu menu) {
            if (resizedWidget.isOpen()) {
                PacketDistributor.sendToServer(new ArmorWidgetSetSlotPositionsPayload(27 + resizedWidget.getOpenHeight()));
                menu.setArmorSlotPositions(27 + resizedWidget.getOpenHeight());
            } else {
                PacketDistributor.sendToServer(new ArmorWidgetSetSlotPositionsPayload(46));
                menu.setArmorSlotPositions(46);
            }
        }

    }

}
