package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.AttackType;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.api.modules.AttackArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class LaserArmorModule implements AttackArmorModule {
    public static final ResourceLocation LASER_ATTACK_SPRITE = PowerArmor.rl("laser_attack");
    public static final Set<ArmorItem.Type> TYPES = Set.of(ArmorItem.Type.CHESTPLATE);

    @Override
    public Item getItem() {
        return PAItems.LASER_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<ArmorItem.Type> getArmorTypes() {
        return TYPES;
    }

    @Override
    public AttackType getAttackType() {
        return new AttackType(LASER_ATTACK_SPRITE, Component.literal("Laser"));
    }

}
