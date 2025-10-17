package com.portingdeadmods.power_armor.utils;

import com.portingdeadmods.portingdeadlibs.api.capabilities.EnergyStorageWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public final class ArmorUtils {
    public static EnergyStorageWrapper getEnergy(Player player) {
        int energy = 0;
        int capacity = 0;

        for (ItemStack stack : player.getArmorSlots()) {
            IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
            if (energyStorage != null) {
                energy += energyStorage.getEnergyStored();
                capacity += energyStorage.getMaxEnergyStored();
            }
        }

        return new Wrapper(energy, capacity);
    }

    private record Wrapper(int energy, int capacity) implements EnergyStorageWrapper {
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
