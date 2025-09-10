package com.portingdeadmods.power_armor.utils;

import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public final class ItemBarUtils {
    private static final int COLOR = FastColor.ARGB32.color(255, 215, 0, 0);

    public static int energyBarWidth(ItemStack stack) {
        IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        float ratio = (float) energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored();
        return Math.round(13.0F - ((1 - ratio) * 13.0F));
    }

    public static int energyBarColor(ItemStack stack) {
        return COLOR;
    }

}