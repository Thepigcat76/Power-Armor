package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PARegistries;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PAArmorModules {
    public static final DeferredRegister<ArmorModule> ARMOR_MODULES = DeferredRegister.create(PARegistries.ARMOR_MODULE, PowerArmor.MODID);


}
