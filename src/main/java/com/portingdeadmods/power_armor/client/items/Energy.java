package com.portingdeadmods.power_armor.client.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record Energy(int stages) implements RangeSelectItemModelProperty {
    public static final MapCodec<Energy> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.INT.fieldOf("stages").forGetter(Energy::stages)
    ).apply(inst, Energy::new));

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner itemOwner, int i) {
        EnergyHandler energyHandler = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        float ratio = (float) energyHandler.getAmountAsInt() / energyHandler.getCapacityAsInt();
        return ratio * this.stages();
    }

    @Override
    public @NonNull MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}
