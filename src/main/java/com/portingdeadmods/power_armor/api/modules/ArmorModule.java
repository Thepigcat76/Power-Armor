package com.portingdeadmods.power_armor.api.modules;

import com.portingdeadmods.portingdeadlibs.utils.Utils;
import com.portingdeadmods.power_armor.PARegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public interface ArmorModule {
    ArmorModule EMPTY = new ArmorModule() {
        @Override
        public Item getItem() {
            return Items.AIR;
        }

        @Override
        public Component getDisplayName() {
            return Component.literal("Empty");
        }

        @Override
        public Set<EquipmentSlot> getArmorSlots() {
            return Set.of();
        }
    };

    Item getItem();

    default Component getDisplayName() {
        return Utils.registryTranslation(PARegistries.ARMOR_MODULE, this);
    }

    @Nullable
    Set<EquipmentSlot> getArmorSlots();

    default int getEnergyUsage(ItemStack stack) {
        return 0;
    }

    default void addTooltip(ItemStack stack, List<Component> tooltipComponents) {
    }

    /**
     * Only called when the module is
     * installed on armor module which is worn
     */
    default void tick(ItemStack armorItem, Player player) {
    }

    default void onAdded(ItemStack armorItem, TransactionContext transaction) {
    }

    default void onRemoved(ItemStack armorItem, TransactionContext transaction) {
    }

    default void onArmorUnequipped(Player player, ItemStack armorItem) {
    }

    default void onPlayerAttacked(Player player, ItemStack armorItem) {

    }

    default boolean isActive(ItemStack armorItem) {
        EnergyHandler energyHandler = armorItem.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(armorItem));
        return energyHandler.getAmountAsInt() >= this.getEnergyUsage(armorItem);
    }

    default void extractEnergy(ItemStack armorItem) {
        this.extractEnergy(armorItem, this.getEnergyUsage(armorItem));
    }

    default void extractEnergy(ItemStack armorItem, int usage) {
        EnergyHandler energyHandler = armorItem.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(armorItem));
        try (Transaction tx = Transaction.openRoot()) {
            energyHandler.extract(usage, tx);
            tx.commit();
        }
    }

    static @Nullable ArmorModule byItem(Item item) {
        for (ArmorModule armorModule : PARegistries.ARMOR_MODULE) {
            if (armorModule.getItem().equals(item)) {
                return armorModule;
            }
        }
        return null;
    }

}
