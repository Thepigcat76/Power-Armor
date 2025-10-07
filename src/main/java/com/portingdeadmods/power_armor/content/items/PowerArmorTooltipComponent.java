package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.power_armor.data.components.ArmorModuleComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record PowerArmorTooltipComponent(ArmorModuleComponent component) implements TooltipComponent {
}
