package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ArmorModuleItem extends Item {
    public ArmorModuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ArmorModule armorModule = ArmorModule.byItem(this);
        tooltipComponents.add(Component.literal("Can be applied to:").withStyle(ChatFormatting.GRAY));
        for (ArmorItem.Type armorType : armorModule.getArmorTypes()) {
            String name = armorType.getSlot().getName();
            char[] charArray = name.toCharArray();
            charArray[0] = Character.toUpperCase(name.charAt(0));
            String newName = new String(charArray);
            tooltipComponents.add(Component.literal("- " + newName).withStyle(ChatFormatting.GRAY));
        }
    }
}
