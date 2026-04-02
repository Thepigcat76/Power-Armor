package com.portingdeadmods.power_armor.mixins;

import com.portingdeadmods.power_armor.content.items.PowerArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onEquipItem", at = @At("HEAD"), cancellable = true)
    public void suppressItemReequipSound(EquipmentSlot slot, ItemStack oldStack, ItemStack stack, CallbackInfo ci) {
        if (stack.getItem() instanceof PowerArmorItem && ItemStack.isSameItem(oldStack, stack)) {
            ci.cancel();
        }
    }
}
