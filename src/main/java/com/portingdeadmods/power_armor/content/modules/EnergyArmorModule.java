package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.PDLItemAccessEnergyHandler;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.utils.ArmorModuleUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class EnergyArmorModule implements ArmorModule {
    @Override
    public Item getItem() {
        return PAItems.ENERGY_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<EquipmentSlot> getArmorSlots() {
        return ArmorModuleUtils.ARMOR_SLOTS;
    }

    @Override
    public void onAdded(ItemStack armorItem, TransactionContext transaction) {
        if (armorItem.has(PAComponents.ENERGY_CAPACITY)) {
            armorItem.set(PAComponents.ENERGY_CAPACITY, PowerArmorConfig.powerArmorEnergyCapacity * PowerArmorConfig.powerArmorEnergyModuleMultiplier);
        }
    }

    @Override
    public void onRemoved(ItemStack armorItem, TransactionContext transaction) {
        if (armorItem.has(PAComponents.ENERGY_CAPACITY)) {
            EnergyHandler energyHandler = armorItem.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(armorItem));
            if (energyHandler instanceof PDLItemAccessEnergyHandler itemAccessEnergyHandler) {
                itemAccessEnergyHandler.set(Math.min(PowerArmorConfig.powerArmorEnergyCapacity, energyHandler.getAmountAsInt()), transaction);
            }
            armorItem.set(PAComponents.ENERGY_CAPACITY, PowerArmorConfig.powerArmorEnergyCapacity);
        }
    }
}
