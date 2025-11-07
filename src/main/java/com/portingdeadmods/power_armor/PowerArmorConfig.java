package com.portingdeadmods.power_armor;

import com.portingdeadmods.portingdeadlibs.api.config.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.OptionalInt;

public final class PowerArmorConfig {
    @ConfigValue(name = "Battery Energy Capacity", category = "energy.capacity", comment = "Energy Capacity of the Battery Item")
    public static int batteryEnergyCapacity = 64_000;

    @ConfigValue(name = "Battery Energy Transfer", category = "energy.transfer", comment = "Energy Transfer of the Battery Item")
    public static int batteryEnergyTransfer = 32;

    @ConfigValue(name = "Power Armor Energy Capacity", category = "energy.capacity", comment = "Energy Capacity of the Power Armor")
    public static int powerArmorEnergyCapacity = 64_000;

    @ConfigValue(name = "Power Armor Energy Transfer", category = "energy.transfer", comment = "Energy Transfer of the Power Armor")
    public static int powerArmorEnergyTransfer = 32;

    @ConfigValue(name = "Power Armor Plating Energy Usage", category = "module.energy.usage", comment = "Energy Usage of the Power Armor Plating Module")
    public static int powerArmorPlatingEnergyUsage = 200;
    @ConfigValue(name = "Power Armor Night Vision Energy Usage", category = "module.energy.usage", comment = "Energy Usage of the Power Armor Night Vision Module")
    public static int powerArmorNightVisionEnergyUsage = 1;
    @ConfigValue(name = "Power Armor Energy Module Multiplier", category = "module.energy", comment = "Energy Multiplier of the Power Armor Energy Module")
    public static int powerArmorEnergyModuleMultiplier = 4;
    @ConfigValue(name = "Power Armor Speed Module Usage", category = "module.energy.usage", comment = "Energy Usage of the Power Armor Speed Module")
    public static int powerArmorSpeedModuleEnergyUsage = 2;
    @ConfigValue(name = "Power Armor Jetpack Module Usage", category = "module.energy.usage", comment = "Energy Usage of the Power Armor Jetpack Module")
    public static int powerArmorJetpackModuleEnergyUsage = 2;
    @ConfigValue(name = "Power Armor Solar Module Production", category = "module.energy.production", comment = "Energy Production of the Power Armor Solar Module")
    public static int powerArmorSolarModuleEnergyProduction = 4;

    @ConfigValue(name = "Compressor Energy Capacity", category = "energy.capacity", comment = "Energy Capacity of the Compressor")
    public static int compressorEnergyCapacity = 16_000;
    @ConfigValue(name = "Compressor Energy Usage", category = "energy.usage", comment = "Energy usage of the Compressor per tick")
    public static int compressorEnergyUsage = 8;
    @ConfigValue(name = "Compressor Energy Transfer", category = "energy.transfer", comment = "Energy Transfer of the Compressor")
    public static int compressorEnergyTransfer = 32;

    @ConfigValue(name = "Armor Modification Table Energy Capacity", category = "energy.capacity", comment = "Energy Capacity of the Armor Modification Table")
    public static int armorModificationTableEnergyCapacity = 256_000;
    @ConfigValue(name = "Armor Modification Table Energy Transfer", category = "energy.transfer", comment = "Energy Transfer of the Armor Modification Table")
    public static int armorModificationTableEnergyTransfer = 128;
}
