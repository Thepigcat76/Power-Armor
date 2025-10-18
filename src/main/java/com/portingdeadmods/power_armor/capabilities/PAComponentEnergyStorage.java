package com.portingdeadmods.power_armor.capabilities;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;

public class PAComponentEnergyStorage extends ComponentEnergyStorage {
    public PAComponentEnergyStorage(MutableDataComponentHolder parent, DataComponentType<Integer> energyComponent, int capacity, int maxReceive, int maxExtract) {
        super(parent, energyComponent, capacity, maxReceive, maxExtract);
    }

    public PAComponentEnergyStorage(MutableDataComponentHolder parent, DataComponentType<Integer> energyComponent, int capacity, int maxTransfer) {
        super(parent, energyComponent, capacity, maxTransfer);
    }

    public PAComponentEnergyStorage(MutableDataComponentHolder parent, DataComponentType<Integer> energyComponent, int capacity) {
        super(parent, energyComponent, capacity);
    }

    @Override
    public void setEnergy(int energy) {
        super.setEnergy(energy);
    }
}
