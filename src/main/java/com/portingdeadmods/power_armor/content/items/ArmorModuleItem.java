package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import com.portingdeadmods.power_armor.utils.ArmorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ArmorModuleItem extends Item {
    public ArmorModuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        ArmorModule armorModule = ArmorModule.byItem(this);

        if (armorModule == PAArmorModules.LASER.get()) {
            builder.accept(Component.literal("WIP").withStyle(ChatFormatting.RED));
        }

        builder.accept(Component.literal("Can be applied to:").withStyle(ChatFormatting.GRAY));
        if (armorModule != null && armorModule.getArmorSlots() != null) {
            List<EquipmentSlot> slots = armorModule.getArmorSlots().stream().sorted(Comparator.comparingInt(EquipmentSlot::ordinal)).toList();
            for (EquipmentSlot slot : slots) {
                if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;

                ArmorType armorType = ArmorUtils.getArmorTypeBySlot(slot);
                String name = armorType.getSlot().getName();
                char[] charArray = name.toCharArray();
                charArray[0] = Character.toUpperCase(name.charAt(0));
                String newName = new String(charArray);
                builder.accept(Component.literal("- " + newName).withStyle(ChatFormatting.GRAY));
            }
        }
    }

}
