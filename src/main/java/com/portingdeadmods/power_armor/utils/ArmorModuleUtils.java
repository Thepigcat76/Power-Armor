package com.portingdeadmods.power_armor.utils;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.api.modules.AttackArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class ArmorModuleUtils {
    public static boolean hasModule(ItemStack stack, Supplier<? extends ArmorModule> module) {
        ArmorModulesComponent armorModulesComponent = stack.getOrDefault(PAComponents.ARMOR_MODULES, ArmorModulesComponent.EMPTY);
        return armorModulesComponent.modulesUnsafe().contains(module.get());
    }

    public static boolean hasMultipleAttacks(Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.has(PAComponents.ARMOR_MODULES)) {
                NonNullList<ArmorModule> armorModules = stack.get(PAComponents.ARMOR_MODULES).modulesUnsafe();
                for (ArmorModule armorModule : armorModules) {
                    if (armorModule instanceof AttackArmorModule) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Stream<ArmorModule> getModules(Player player) {
        return player.getInventory().armor.stream()
                .filter(item -> item.has(PAComponents.ARMOR_MODULES))
                .flatMap(item -> item.getOrDefault(PAComponents.ARMOR_MODULES, ArmorModulesComponent.EMPTY).modulesUnsafe().stream());
    }

    public static <M> Stream<M> getModules(Player player, Class<M> clazz) {
        return getModules(player).filter(clazz::isInstance).map(module -> (M) module);
    }


}
