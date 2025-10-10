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
    public Component getDisplayName() {
        return Component.literal("Solar");
    }

    @Override
    public @Nullable ArmorItem.Type getArmorType() {
        return ArmorItem.Type.HELMET;
    }

    @Override
    public void tick(ItemStack armorItem, Player player) {
        ArmorModule.super.tick(armorItem, player);

        if (!player.level().isNight() && player.level().getGameTime() % 10 == 0 && !player.level().isClientSide()) {
            IEnergyStorage energyStorage = armorItem.getCapability(Capabilities.EnergyStorage.ITEM);
            energyStorage.receiveEnergy(1, false);
        }
    }

}
