package com.portingdeadmods.power_armor.mixins;

import com.portingdeadmods.power_armor.content.menus.SlotAccessor;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Slot.class)
public class SlotMixin implements SlotAccessor {
    @Final
    @Mutable
    @Shadow
    public int x;

    @Final
    @Mutable
    @Shadow public int y;

    @Override
    public void powerArmor$setX(int x) {
        this.x = x;
    }

    @Override
    public void powerArmor$setY(int y) {
        this.y = y;
    }
}