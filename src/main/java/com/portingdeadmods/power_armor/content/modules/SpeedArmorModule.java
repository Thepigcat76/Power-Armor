package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SpeedArmorModule implements ArmorModule {
    public static final Set<EquipmentSlot> SLOTS = Set.of(EquipmentSlot.LEGS);

    @Override
    public Item getItem() {
        return PAItems.SPEED_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<EquipmentSlot> getArmorSlots() {
        return SLOTS;
    }

    @Override
    public int getEnergyUsage(ItemStack stack) {
        return PowerArmorConfig.powerArmorSpeedModuleEnergyUsage;
    }

    @Override
    public void tick(ItemStack armorItem, Player player) {
        if (!this.isActive(armorItem)) {
            player.removeEffect(MobEffects.SPEED);
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 400, 2, false, false, false));

        if (player.isSprinting()) {
            this.extractEnergy(armorItem);
        }

    }

    @Override
    public void onArmorUnequipped(Player player, ItemStack armorItem) {
        player.removeEffect(MobEffects.SPEED);
    }

}
