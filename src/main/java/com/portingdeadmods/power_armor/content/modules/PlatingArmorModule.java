package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.utils.ArmorModuleUtils;
import com.portingdeadmods.power_armor.utils.ArmorUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class PlatingArmorModule implements ArmorModule {
    private static final Function<ItemStack, ItemAttributeModifiers> ATTRIBUTE_MODIFIERS = armorItem -> {
        Equippable equippable = armorItem.get(DataComponents.EQUIPPABLE);

        Identifier id = PowerArmor.id("armor." + ArmorUtils.getArmorTypeBySlot(equippable.slot()).getName());
        return ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR, new AttributeModifier(id, 5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ARMOR)
                .add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, 0.1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ARMOR)
                .build();
    };

    @Override
    public Item getItem() {
        return PAItems.PLATING_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<EquipmentSlot> getArmorSlots() {
        return ArmorModuleUtils.ARMOR_SLOTS;
    }

    @Override
    public int getEnergyUsage(ItemStack stack) {
        return PowerArmorConfig.powerArmorPlatingEnergyUsage;
    }

    @Override
    public void onAdded(ItemStack armorItem, TransactionContext transaction) {
        if (armorItem.has(DataComponents.ATTRIBUTE_MODIFIERS)) {
            ItemAttributeModifiers attributeModifiers = armorItem.get(DataComponents.ATTRIBUTE_MODIFIERS);
            Set<ItemAttributeModifiers.Entry> modifiers = new HashSet<>(attributeModifiers.modifiers());
            ItemAttributeModifiers modifiers1 = ATTRIBUTE_MODIFIERS.apply(armorItem);
            modifiers.addAll(modifiers1.modifiers());
            armorItem.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(List.copyOf(modifiers)));
        }
    }

    @Override
    public void onRemoved(ItemStack armorItem, TransactionContext transaction) {
        ItemAttributeModifiers attributeModifiers = armorItem.get(DataComponents.ATTRIBUTE_MODIFIERS);
        List<ItemAttributeModifiers.Entry> modifiers = new ArrayList<>(attributeModifiers.modifiers());
        ItemAttributeModifiers modifiers1 = ATTRIBUTE_MODIFIERS.apply(armorItem);
        modifiers.removeAll(modifiers1.modifiers());
        armorItem.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers));
    }

    @Override
    public void onPlayerAttacked(Player player, ItemStack armorItem) {
        this.extractEnergy(armorItem);
    }

}
