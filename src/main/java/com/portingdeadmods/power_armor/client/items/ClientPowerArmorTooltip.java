package com.portingdeadmods.power_armor.client.items;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.content.items.PowerArmorTooltipComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record ClientPowerArmorTooltip(PowerArmorTooltipComponent component,
                                      List<ItemStack> moduleItems) implements ClientTooltipComponent {
    public static final ResourceLocation MODULE_BACKGROUND_SPRITE = PowerArmor.rl("armor_module_background");

    public ClientPowerArmorTooltip(PowerArmorTooltipComponent component) {
        this(component, new ArrayList<>());
        component.component().modules().stream().map(ArmorModule::getItem).map(ItemStack::new).forEach(this.moduleItems()::add);
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        List<ItemStack> items = this.moduleItems();
        if (!Screen.hasShiftDown()) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack item = items.get(i);
                if (item.isEmpty()) {
                    guiGraphics.blitSprite(MODULE_BACKGROUND_SPRITE, x + i * 16, y, 16, 16);
                } else {
                    guiGraphics.renderFakeItem(item, x + i * 16, y);
                }
            }
        } else {
            for (int i = 0; i < items.size(); i++) {
                ItemStack item = items.get(i);
                if (item.isEmpty()) {
                    guiGraphics.blitSprite(MODULE_BACKGROUND_SPRITE, x, y + i * 16, 16, 16);
                } else {
                    guiGraphics.renderFakeItem(item, x, y + i * 16);
                }

                MutableComponent text = Component.literal(" - ");
                guiGraphics.drawString(font, text, x + 15, y + 4 + i * 16, ChatFormatting.GRAY.getColor());
                guiGraphics.drawScrollingString(font, component.component().modulesUnsafe().get(i).getDisplayName(), x + 15 + font.width(text), x + this.getWidth(font), y + 4 + i * 16, ChatFormatting.GRAY.getColor());
            }
        }
    }

    @Override
    public int getHeight() {
        return 18 + (Screen.hasShiftDown() ? (this.component.component().modulesAmount() - 1) * 16 : 0);
    }

    @Override
    public int getWidth(Font font) {
        return this.moduleItems().size() * 16;
    }

}
