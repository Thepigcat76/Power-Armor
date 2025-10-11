package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class SolarArmorModule implements ArmorModule {
    @Override
    public Item getItem() {
        return PAItems.SOLAR_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable ArmorItem.Type getArmorType() {
        return ArmorItem.Type.HELMET;
    }

    @Override
    public void tick(ItemStack armorItem, Player player) {
        Iterable<ItemStack> armorStacks = player.getArmorSlots();

        if (!player.level().isNight() && player.level().getGameTime() % 5 == 0 && !player.level().isClientSide()) {
            for (ItemStack stack : armorStacks) {
                IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
                if (energyStorage != null) {
                    energyStorage.receiveEnergy(20, false);
                }
            }
        }
    }

}
