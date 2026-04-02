package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.AttackType;
import com.portingdeadmods.power_armor.api.modules.AttackArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class LaserArmorModule implements AttackArmorModule {
    public static final Identifier LASER_ATTACK_SPRITE = PowerArmor.id("laser_attack");
    public static final AttackType LASER = new AttackType(LASER_ATTACK_SPRITE, Component.literal("Laser"));
    public static final Set<EquipmentSlot> SLOTS = Set.of(EquipmentSlot.CHEST);

    @Override
    public Item getItem() {
        return PAItems.LASER_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<EquipmentSlot> getArmorSlots() {
        return SLOTS;
    }

    @Override
    public AttackType getAttackType() {
        return LASER;
    }

}
