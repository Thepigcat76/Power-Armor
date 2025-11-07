package com.portingdeadmods.power_armor.api;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.AttackArmorModule;
import com.portingdeadmods.power_armor.content.modules.LaserArmorModule;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public record AttackType(ResourceLocation sprite, Component displayName, boolean vanilla) {
    public static final ResourceLocation VANILLA_ATTACK_SPRITE = PowerArmor.rl("vanilla_attack");
    public static final AttackType VANILLA = new AttackType(VANILLA_ATTACK_SPRITE, Component.literal("Vanilla"));

    public AttackType(ResourceLocation sprite, Component displayName) {
        this(sprite, displayName, false);
    }

    public void handle(Player player, @Nullable Entity target, InteractionHand hand) {
        if (this == LaserArmorModule.LASER) {
            Vec3 viewVector = player.getLookAngle();
            Vec3 position = player.position().add(0, 1.25, 0);
            RandomSource random = player.getRandom();
            for (int i = 0; i < random.nextInt(3, 20); i++) {
                player.level().addParticle(
                        ParticleTypes.END_ROD,
                        position.x + (random.nextFloat() - 0.5f) / 8,
                        position.y + (random.nextFloat() - 0.5f) / 8,
                        position.z + (random.nextFloat() - 0.5f) / 8,
                        viewVector.x, viewVector.y, viewVector.z
                );
            }
            Vec3 vec3 = player.getEyePosition();
            Vec3 vec31 = player.getViewVector(1.0F);
            Vec3 vec32 = vec3.add(vec31.x * 100.0, vec31.y * 100.0, vec31.z * 100.0);
            EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(
                    player.level(), player, vec3, vec32, new AABB(vec3, vec32).inflate(1.0), p_156765_ -> !p_156765_.isSpectator(), 0.0F
            );
            if (entityhitresult != null) {
                Entity entity = entityhitresult.getEntity();
                entity.hurt(player.level().damageSources().onFire(), 5);
            }
            //player.sendSystemMessage(Component.literal("Handling laser attack"));
        }
    }

}
