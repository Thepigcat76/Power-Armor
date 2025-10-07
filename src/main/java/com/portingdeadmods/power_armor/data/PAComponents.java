package com.portingdeadmods.power_armor.data;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.data.components.ArmorModuleComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PAComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PowerArmor.MODID);

    public static final Supplier<DataComponentType<ArmorModuleComponent>> ARMOR_MODULE = COMPONENTS.registerComponentType("armor_module", builder -> builder
            .persistent(ArmorModuleComponent.CODEC)
            .networkSynchronized(ArmorModuleComponent.STREAM_CODEC));
}
