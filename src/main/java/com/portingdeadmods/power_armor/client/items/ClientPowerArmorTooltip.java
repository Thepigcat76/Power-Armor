package com.portingdeadmods.power_armor.client.items;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.content.items.PowerArmorTooltipComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record ClientPowerArmorTooltip(PowerArmorTooltipComponent component,
                                      List<ItemStack> moduleItems) implements ClientTooltipComponent {
    public static final Identifier MODULE_BACKGROUND_SPRITE = PowerArmor.id("armor_module_background");

    public ClientPowerArmorTooltip(PowerArmorTooltipComponent component) {
        this(component, new ArrayList<>());
        component.component().modules().stream().map(ArmorModule::getItem).map(ItemStack::new).forEach(this.moduleItems()::add);
    }

    @Override
    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor guiGraphics) {
        List<ItemStack> items = this.moduleItems();
        if (!Minecraft.getInstance().hasShiftDown()) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack item = items.get(i);
                if (item.isEmpty()) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, MODULE_BACKGROUND_SPRITE, x + i * 16, y, 16, 16);
                } else {
                    guiGraphics.fakeItem(item, x + i * 16, y);
                }
            }
        } else {
            for (int i = 0; i < items.size(); i++) {
                ItemStack item = items.get(i);
                if (item.isEmpty()) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, MODULE_BACKGROUND_SPRITE, x, y + i * 16, 16, 16);
                } else {
                    guiGraphics.fakeItem(item, x, y + i * 16);
                }
            }
        }
    }

    @Override
    public void extractText(GuiGraphicsExtractor guiGraphics, Font font, int x, int y) {
        List<ItemStack> items = this.moduleItems();
        if (Minecraft.getInstance().hasShiftDown()) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack item = items.get(i);

                MutableComponent text = Component.literal(" - ").withStyle(ChatFormatting.GRAY);
                guiGraphics.text(font, text, x + 15, y + 4 + i * 16, -1);
                ActiveTextCollector activeTextCollector = guiGraphics.textRenderer();
                Component displayName = component.component().modulesUnsafe().get(i).getDisplayName().copy().withStyle(ChatFormatting.GRAY);
                guiGraphics.drawScrollingString(activeTextCollector, font, displayName, x + 15 + font.width(text), x + this.getWidth(font), y + 4 + i * 16);
            }
        }
    }

    @Override
    public int getHeight(Font font) {
        return 18 + (Minecraft.getInstance().hasShiftDown() ? (this.component.component().modulesAmount() - 1) * 16 : 0);
    }

    @Override
    public int getWidth(Font font) {
        return this.moduleItems().size() * 16;
    }

}
