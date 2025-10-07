package com.portingdeadmods.power_armor.client.items;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.content.items.PowerArmorTooltipComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record ClientPowerArmorTooltip(PowerArmorTooltipComponent component, List<ItemStack> moduleItems) implements ClientTooltipComponent {
    public ClientPowerArmorTooltip(PowerArmorTooltipComponent component) {
        this(component, new ArrayList<>());
        component.component().modules().stream().map(ArmorModule::getItem).map(ItemStack::new).forEach(this.moduleItems()::add);
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        List<ItemStack> items = moduleItems();
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            guiGraphics.renderFakeItem(item, x + i * 16, y);
        }
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(Font font) {
        return this.moduleItems().size() * 16;
    }

}
