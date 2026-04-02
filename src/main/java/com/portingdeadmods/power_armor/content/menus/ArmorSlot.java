package com.portingdeadmods.power_armor.content.menus;

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;

import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ArmorSlot extends Slot {
    private final LivingEntity owner;
    private final EquipmentSlot slot;
    @Nullable
    private final Identifier emptyIcon;
    private boolean active;

    public ArmorSlot(Container container, LivingEntity owner, EquipmentSlot slot, int slotIndex, int x, int y, @Nullable Identifier emptyIcon) {
        super(container, slotIndex, x, y);
        this.owner = owner;
        this.slot = slot;
        this.emptyIcon = emptyIcon;
    }

    @Override
    public void setByPlayer(ItemStack newStack, ItemStack oldStack) {
        this.owner.onEquipItem(this.slot, oldStack, newStack);
        super.setByPlayer(newStack, oldStack);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.canEquip(this.slot, this.owner);
    }

    @Override
    public boolean mayPickup(Player player) {
        ItemStack itemstack = this.getItem();
        return !itemstack.isEmpty() && !player.isCreative() && EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) ? false : super.mayPickup(player);
    }

    @Override
    public @org.jspecify.annotations.Nullable Identifier getNoItemIcon() {
        return this.emptyIcon != null ? this.emptyIcon : super.getNoItemIcon();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
