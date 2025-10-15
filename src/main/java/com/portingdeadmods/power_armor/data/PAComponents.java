package com.portingdeadmods.power_armor.data;

import com.mojang.serialization.Codec;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PAComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PowerArmor.MODID);

    public static final Supplier<DataComponentType<ArmorModulesComponent>> ARMOR_MODULES = COMPONENTS.registerComponentType("armor_modules", builder -> builder
            .persistent(ArmorModulesComponent.CODEC)
            .networkSynchronized(ArmorModulesComponent.STREAM_CODEC));
    public static final Supplier<DataComponentType<ItemAttributeModifiers>> DEFAULT_ATTRIBUTES = COMPONENTS.registerComponentType("default_attributes", builder -> builder
            .persistent(ItemAttributeModifiers.CODEC)
            .networkSynchronized(ItemAttributeModifiers.STREAM_CODEC));
    public static final Supplier<DataComponentType<Integer>> ENERGY_CAPACITY = COMPONENTS.registerComponentType("energy_capacity", builder -> builder
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT));

}
