package com.portingdeadmods.power_armor.api.modules;

import com.portingdeadmods.power_armor.PARegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public interface ArmorModule {
    ArmorModule EMPTY = new ArmorModule() {
        @Override
        public Item getItem() {
            return Items.AIR;
        }

        @Override
        public Component getDisplayName() {
            return Component.literal("Empty");
        }

        @Override
        public @org.jetbrains.annotations.Nullable ArmorItem.Type getArmorType() {
            return null;
        }
    };

    Item getItem();

    Component getDisplayName();

    @Nullable ArmorItem.Type getArmorType();

    default void addTooltip(ItemStack stack, List<Component> tooltipComponents) {
    }

    default void tick(ItemStack armorItem, Player player) {

    }

    static @Nullable ArmorModule byItem(Item item) {
        for (ArmorModule armorModule : PARegistries.ARMOR_MODULE) {
            if (armorModule.getItem().equals(item)) {
                return armorModule;
            }
        }
        return null;
    }

}
