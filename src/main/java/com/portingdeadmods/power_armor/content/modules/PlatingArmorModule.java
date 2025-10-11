package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlatingArmorModule implements ArmorModule {
    @Override
    public Item getItem() {
        return PAItems.PLATING_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable ArmorItem.Type getArmorType() {
        return null;
    }

    @Override
    public void onAdded(ItemStack armorItem) {
        ItemAttributeModifiers attributeModifiers = armorItem.get(PAComponents.DEFAULT_ATTRIBUTES);
        List<ItemAttributeModifiers.Entry> modifiers = new ArrayList<>(attributeModifiers.modifiers());
        ResourceLocation id = PowerArmor.rl("armor." + ((ArmorItem) armorItem.getItem()).getType().getName());
        ItemAttributeModifiers modifiers1 = ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR, new AttributeModifier(id, 5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ARMOR)
                .add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, 0.1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ARMOR)
                .build();
        modifiers.addAll(modifiers1.modifiers());
        armorItem.set(PAComponents.DEFAULT_ATTRIBUTES, new ItemAttributeModifiers(modifiers, attributeModifiers.showInTooltip()));
    }

    @Override
    public void onRemoved(ItemStack armorItem) {
        ItemAttributeModifiers attributeModifiers = armorItem.get(PAComponents.DEFAULT_ATTRIBUTES);
        List<ItemAttributeModifiers.Entry> modifiers = new ArrayList<>(attributeModifiers.modifiers());
        for (int i = 0; i < modifiers.size(); i++) {
            ItemAttributeModifiers.Entry modifier = modifiers.get(i);
            if (modifier.attribute() == Attributes.ARMOR) {
                modifiers.remove(modifier);
                break;
            }
        }
        armorItem.set(PAComponents.DEFAULT_ATTRIBUTES, new ItemAttributeModifiers(modifiers, attributeModifiers.showInTooltip()));
    }
}
