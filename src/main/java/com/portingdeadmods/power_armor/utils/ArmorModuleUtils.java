package com.portingdeadmods.power_armor.utils;

import com.portingdeadmods.power_armor.api.AttackType;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.api.modules.AttackArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import com.portingdeadmods.power_armor.registries.PAAttachments;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ArmorModuleUtils {
    public static final Set<EquipmentSlot> ARMOR_SLOTS = Set.copyOf(EquipmentSlotGroup.ARMOR.slots());

    public static boolean hasModule(ItemStack stack, Supplier<? extends ArmorModule> module) {
        ArmorModulesComponent armorModulesComponent = stack.getOrDefault(PAComponents.ARMOR_MODULES, ArmorModulesComponent.EMPTY);
        return armorModulesComponent.modulesUnsafe().contains(module.get());
    }

    public static boolean hasMultipleAttacks(Player player) {
        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            ItemStack stack = player.getItemBySlot(slot);
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
        return EquipmentSlotGroup.ARMOR.slots().stream()
                .map(player::getItemBySlot)
                .filter(item -> item.has(PAComponents.ARMOR_MODULES))
                .flatMap(item -> item.getOrDefault(PAComponents.ARMOR_MODULES, ArmorModulesComponent.EMPTY).modulesUnsafe().stream());
    }

    public static <M> Stream<M> getModules(Player player, Class<M> clazz) {
        return getModules(player).filter(clazz::isInstance).map(clazz::cast);
    }

    public static AttackType getAttackType(Player player) {
        int index = player.getData(PAAttachments.ATTACK_TYPE);
        if (index == 0) return AttackType.VANILLA;
        List<AttackArmorModule> modules = ArmorModuleUtils.getModules(player, AttackArmorModule.class).toList();
        return modules.stream().map(AttackArmorModule::getAttackType).toList().get(index - 1);
    }

}
