package com.portingdeadmods.power_armor;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class PARegistries {
    public static final ResourceKey<Registry<ArmorModule>> ARMOR_MODULE_KEY = ResourceKey.createRegistryKey(PowerArmor.rl("armor_module"));
    public static final Registry<ArmorModule> ARMOR_MODULE = new RegistryBuilder<>(ARMOR_MODULE_KEY).create();
    
}
