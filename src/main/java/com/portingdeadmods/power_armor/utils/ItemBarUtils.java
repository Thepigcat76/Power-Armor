package com.portingdeadmods.power_armor.utils;

import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public final class ItemBarUtils {
    private static final int COLOR = ARGB.color(255, 215, 0, 0);

    public static int energyBarWidth(ItemStack stack) {
        EnergyHandler energyHandler = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
        float ratio = (float) energyHandler.getAmountAsInt() / energyHandler.getCapacityAsInt();
        return Math.round(13.0F - ((1 - ratio) * 13.0F));
    }

    public static int energyBarColor(ItemStack stack) {
        return COLOR;
    }

}