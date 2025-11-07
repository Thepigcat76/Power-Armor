package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.capabilities.PAComponentEnergyStorage;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class EnergyArmorModule implements ArmorModule {
    public static final Set<ArmorItem.Type> TYPES = Set.of(ArmorItem.Type.values());

    @Override
    public Item getItem() {
        return PAItems.ENERGY_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<ArmorItem.Type> getArmorTypes() {
        return TYPES;
    }

    @Override
    public void onAdded(ItemStack armorItem) {
        if (armorItem.has(PAComponents.ENERGY_CAPACITY)) {
            armorItem.set(PAComponents.ENERGY_CAPACITY, PowerArmorConfig.powerArmorEnergyCapacity * PowerArmorConfig.powerArmorEnergyModuleMultiplier);
        }
    }

    @Override
    public void onRemoved(ItemStack armorItem) {
        if (armorItem.has(PAComponents.ENERGY_CAPACITY)) {
            IEnergyStorage energyStorage = armorItem.getCapability(Capabilities.EnergyStorage.ITEM);
            if (energyStorage instanceof PAComponentEnergyStorage energyStorage1) {
                energyStorage1.setEnergy(Math.min(PowerArmorConfig.powerArmorEnergyCapacity, energyStorage.getEnergyStored()));
            }
            armorItem.set(PAComponents.ENERGY_CAPACITY, PowerArmorConfig.powerArmorEnergyCapacity);
        }
    }
}
