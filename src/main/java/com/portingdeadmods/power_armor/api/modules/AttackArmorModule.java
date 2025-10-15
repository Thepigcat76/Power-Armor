package com.portingdeadmods.power_armor.api.modules;

import com.portingdeadmods.power_armor.api.AttackType;
import net.minecraft.resources.ResourceLocation;

public interface AttackArmorModule extends ArmorModule {
    AttackType getAttackType();
}
