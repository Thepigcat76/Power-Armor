package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SpeedArmorModule implements ArmorModule {
    public static final Set<ArmorItem.Type> TYPES = Set.of(ArmorItem.Type.LEGGINGS);

    @Override
    public Item getItem() {
        return PAItems.SPEED_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<ArmorItem.Type> getArmorTypes() {
        return TYPES;
    }

    @Override
    public int getEnergyUsage(ItemStack stack) {
        return PowerArmorConfig.POWER_ARMOR_SPEED_USAGE.getAsInt();
    }

    @Override
    public void tick(ItemStack armorItem, Player player) {
        if (!this.isActive(armorItem)) {
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2, false, false, false));

        if (player.isSprinting()) {
            this.extractEnergy(armorItem);
        }

    }

    @Override
    public void onArmorUnequipped(Player player, ItemStack armorItem) {
        player.removeEffect(MobEffects.MOVEMENT_SPEED);
    }

}
