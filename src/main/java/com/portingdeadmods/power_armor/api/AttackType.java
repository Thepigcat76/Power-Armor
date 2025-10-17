package com.portingdeadmods.power_armor.api;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.AttackArmorModule;
import com.portingdeadmods.power_armor.content.modules.LaserArmorModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public record AttackType(ResourceLocation sprite, Component displayName, boolean vanilla) {
    public static final ResourceLocation VANILLA_ATTACK_SPRITE = PowerArmor.rl("vanilla_attack");
    public static final AttackType VANILLA = new AttackType(VANILLA_ATTACK_SPRITE, Component.literal("Vanilla"));

    public AttackType(ResourceLocation sprite, Component displayName) {
        this(sprite, displayName, false);
    }

    public void handle(Player player, @Nullable Entity target, InteractionHand hand) {
        if (this == LaserArmorModule.LASER) {
            player.sendSystemMessage(Component.literal("Handling laser attack"));
        }
    }

}
