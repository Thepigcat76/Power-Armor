package com.portingdeadmods.power_armor.content.modules;

import com.google.common.base.Suppliers;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class PlatingArmorModule implements ArmorModule {
    public static final Set<ArmorItem.Type> TYPES = Set.of(ArmorItem.Type.values());
    private static final Function<ItemStack, ItemAttributeModifiers> ATTRIBUTE_MODIFIERS = armorItem -> {
        ResourceLocation id = PowerArmor.rl("armor." + ((ArmorItem) armorItem.getItem()).getType().getName());
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
    public @Nullable Set<ArmorItem.Type> getArmorTypes() {
        return TYPES;
    }

    @Override
    public int getEnergyUsage(ItemStack stack) {
        return PowerArmorConfig.powerArmorPlatingEnergyUsage;
    }

    @Override
    public void onAdded(ItemStack armorItem) {
        if (armorItem.has(PAComponents.DEFAULT_ATTRIBUTES)) {
            ItemAttributeModifiers attributeModifiers = armorItem.get(PAComponents.DEFAULT_ATTRIBUTES);
            Set<ItemAttributeModifiers.Entry> modifiers = new HashSet<>(attributeModifiers.modifiers());
            ItemAttributeModifiers modifiers1 = ATTRIBUTE_MODIFIERS.apply(armorItem);
            modifiers.addAll(modifiers1.modifiers());
            armorItem.set(PAComponents.DEFAULT_ATTRIBUTES, new ItemAttributeModifiers(List.copyOf(modifiers), attributeModifiers.showInTooltip()));
        }
    }

    @Override
    public void onRemoved(ItemStack armorItem) {
        ItemAttributeModifiers attributeModifiers = armorItem.get(PAComponents.DEFAULT_ATTRIBUTES);
        List<ItemAttributeModifiers.Entry> modifiers = new ArrayList<>(attributeModifiers.modifiers());
        ItemAttributeModifiers modifiers1 = ATTRIBUTE_MODIFIERS.apply(armorItem);
        modifiers.removeAll(modifiers1.modifiers());
        armorItem.set(PAComponents.DEFAULT_ATTRIBUTES, new ItemAttributeModifiers(modifiers, attributeModifiers.showInTooltip()));
    }

    @Override
    public void onPlayerAttacked(Player player, ItemStack armorItem) {
        this.extractEnergy(armorItem);
    }

}
