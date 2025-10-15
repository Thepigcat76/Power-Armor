package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SolarArmorModule implements ArmorModule {

    public static final Set<ArmorItem.Type> TYPES = Set.of(ArmorItem.Type.HELMET);

    @Override
    public Item getItem() {
        return PAItems.SOLAR_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<ArmorItem.Type> getArmorTypes() {
        return TYPES;
    }

    @Override
    public void tick(ItemStack armorItem, Player player) {
        if (!this.isActive(armorItem)) return;

        Iterable<ItemStack> armorStacks = player.getArmorSlots();

        Level level = player.level();
        if (!level.isNight() && level.getGameTime() % 5 == 0 && !level.isClientSide()) {
            for (ItemStack stack : armorStacks) {
                IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
                if (energyStorage != null) {
                    energyStorage.receiveEnergy(20, false);
                }
            }
        }

    }

}
