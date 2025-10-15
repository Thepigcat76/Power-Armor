package com.portingdeadmods.power_armor.content.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ArmorRemoveHandler {
    void onArmorRemoved(Player player, ItemStack armorItem);
}
