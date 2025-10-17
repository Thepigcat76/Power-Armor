package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PARegistries;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.content.modules.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PAArmorModules {
    public static final DeferredRegister<ArmorModule> ARMOR_MODULES = DeferredRegister.create(PARegistries.ARMOR_MODULE, PowerArmor.MODID);

    public static final Supplier<ArmorModule> EMPTY = ARMOR_MODULES.register("empty", () -> ArmorModule.EMPTY);
    public static final Supplier<JetpackArmorModule> JETPACK = ARMOR_MODULES.register("jetpack", JetpackArmorModule::new);
    public static final Supplier<LaserArmorModule> LASER = ARMOR_MODULES.register("laser", LaserArmorModule::new);
    public static final Supplier<SolarArmorModule> SOLAR = ARMOR_MODULES.register("solar", SolarArmorModule::new);
    public static final Supplier<PlatingArmorModule> PLATING = ARMOR_MODULES.register("plating", PlatingArmorModule::new);
    public static final Supplier<NightVisionArmorModule> NIGHT_VISION = ARMOR_MODULES.register("night_vision", NightVisionArmorModule::new);
    public static final Supplier<EnergyArmorModule> ENERGY = ARMOR_MODULES.register("energy", EnergyArmorModule::new);
    public static final Supplier<SpeedArmorModule> SPEED = ARMOR_MODULES.register("speed", SpeedArmorModule::new);

}
