package com.portingdeadmods.power_armor.utils;

import com.portingdeadmods.portingdeadlibs.api.wrappers.EnergyHandlerWrapper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public final class ArmorUtils {
    public static ArmorType getArmorTypeBySlot(EquipmentSlot slot) {
        return switch (slot) {
            case FEET -> ArmorType.BOOTS;
            case LEGS -> ArmorType.LEGGINGS;
            case CHEST -> ArmorType.CHESTPLATE;
            case HEAD -> ArmorType.HELMET;
            default -> null;
        };
    }

    public static EnergyHandlerWrapper getEnergy(Player player) {
        int energy = 0;
        int capacity = 0;

        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            ItemStack stack = player.getItemBySlot(slot);
            // TODO: Replace with player slot index
            if (!stack.isEmpty()) {
                EnergyHandler energyHandler = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
                if (energyHandler != null) {
                    energy += energyHandler.getAmountAsInt();
                    capacity += energyHandler.getCapacityAsInt();
                }
            }
        }

        return new Wrapper(energy, capacity);
    }

    private record Wrapper(int energy, int capacity) implements EnergyHandlerWrapper {
        @Override
        public int getEnergyStored() {
            return this.energy;
        }

        @Override
        public int getEnergyCapacity() {
            return this.capacity;
        }
    }
}
