package com.portingdeadmods.power_armor.data;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.data.components.ArmorModuleComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PAComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PowerArmor.MODID);

    public static final Supplier<DataComponentType<ArmorModuleComponent>> ARMOR_MODULE = COMPONENTS.registerComponentType("armor_module", builder -> builder
            .persistent(ArmorModuleComponent.CODEC)
            .networkSynchronized(ArmorModuleComponent.STREAM_CODEC));
    public static final Supplier<DataComponentType<ItemAttributeModifiers>> DEFAULT_ATTRIBUTES = COMPONENTS.registerComponentType("default_attributes", builder -> builder
            .persistent(ItemAttributeModifiers.CODEC)
            .networkSynchronized(ItemAttributeModifiers.STREAM_CODEC));

}
