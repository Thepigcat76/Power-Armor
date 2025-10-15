package com.portingdeadmods.power_armor.content.modules;

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

public class NightVisionArmorModule implements ArmorModule {
    public static final Set<ArmorItem.Type> TYPES = Set.of(ArmorItem.Type.HELMET);

    @Override
    public Item getItem() {
        return PAItems.NIGHT_VISION_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<ArmorItem.Type> getArmorTypes() {
        return TYPES;
    }

    @Override
    public void tick(ItemStack armorItem, Player player) {
        if (!this.isActive(armorItem)) return;

        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false));
    }

    @Override
    public void onArmorUnequipped(Player player, ItemStack armorItem) {
        player.removeEffect(MobEffects.NIGHT_VISION);
    }

}
