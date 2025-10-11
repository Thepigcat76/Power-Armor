package com.portingdeadmods.power_armor.utils;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModuleComponent;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public final class ArmorModuleUtils {
    public static boolean hasModule(ItemStack stack, Supplier<? extends ArmorModule> module) {
        ArmorModuleComponent armorModuleComponent = stack.getOrDefault(PAComponents.ARMOR_MODULE, ArmorModuleComponent.EMPTY);
        return armorModuleComponent.modulesUnsafe().contains(module.get());
    }
}
