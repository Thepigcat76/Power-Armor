package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record PowerArmorTooltipComponent(ArmorModulesComponent component) implements TooltipComponent {
}
