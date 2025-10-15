package com.portingdeadmods.power_armor.api.modules;

import com.portingdeadmods.portingdeadlibs.utils.Utils;
import com.portingdeadmods.power_armor.PARegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

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
        public Set<ArmorItem.Type> getArmorTypes() {
            return Set.of();
        }
    };

    Item getItem();

    default Component getDisplayName() {
        return Utils.registryTranslation(PARegistries.ARMOR_MODULE, this);
    }

    @Nullable
    Set<ArmorItem.Type> getArmorTypes();

    default void addTooltip(ItemStack stack, List<Component> tooltipComponents) {
    }

    /**
     * Only called when the module is
     * installed on armor module which is worn
     */
    default void tick(ItemStack armorItem, Player player) {
    }

    default void onAdded(ItemStack armorItem) {
    }

    default void onRemoved(ItemStack armorItem) {
    }

    default void onArmorUnequipped(Player player, ItemStack armorItem) {
    }

    default boolean isActive(ItemStack armorItem) {
        IEnergyStorage energyStorage = armorItem.getCapability(Capabilities.EnergyStorage.ITEM);
        return energyStorage.getEnergyStored() > 0;
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
