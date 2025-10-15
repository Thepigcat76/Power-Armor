package com.portingdeadmods.power_armor.api;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record AttackType(ResourceLocation sprite, Component displayName) {
}
