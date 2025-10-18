package com.portingdeadmods.power_armor;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.OptionalInt;

public final class PowerArmorConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue BATTERY_CAPACITY = BUILDER
            .comment("Energy capacity of the battery item")
            .defineInRange("battery_capacity", 64_000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue BATTERY_TRANSFER = BUILDER
            .comment("Amount of energy the battery item can transfer per tick")
            .defineInRange("battery_transfer", 32, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue POWER_ARMOR_CAPACITY = BUILDER
            .comment("Energy capacity of the power armor item")
            .defineInRange("power_armor_capacity", 64_000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue POWER_ARMOR_TRANSFER = BUILDER
            .comment("Amount of energy the power armor item can transfer per tick")
            .defineInRange("power_armor_transfer", 32, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue POWER_ARMOR_PLATING_USAGE = BUILDER
            .comment("Amount of energy used by the Power Armor Plating Module when the player takes damage")
            .defineInRange("power_armor_plating_usage", 200, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue POWER_ARMOR_NIGHT_VISION_USAGE = BUILDER
            .comment("Amount of energy used by the Power Armor Night Vision Module")
            .defineInRange("power_armor_night_vision_usage", 1, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue POWER_ARMOR_ENERGY_MODULE_MULTIPLIER = BUILDER
            .comment("Multiplier of energy capacity, when Energy Armor Module is installed")
            .defineInRange("power_armor_energy_module_multiplier", 4, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue POWER_ARMOR_SPEED_USAGE = BUILDER
            .comment("Amount of energy used by the Power Armor Speed Module when the player is running")
            .defineInRange("power_armor_speed_usage", 2, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue POWER_ARMOR_JETPACK_USAGE = BUILDER
            .comment("Amount of energy used by the Power Armor Jetpack Module when the player is flying")
            .defineInRange("power_armor_jetpack_usage", 2, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue POWER_ARMOR_SOLAR_PRODUCTION = BUILDER
            .comment("Amount of energy produced by the Power Armor Solar Module when the sun is shining")
            .defineInRange("power_armor_solar_production", 4, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue COMPRESSOR_CAPACITY = BUILDER
            .comment("Energy capacity of the compressor")
            .defineInRange("compressor_capacity", 16_000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMPRESSOR_USAGE = BUILDER
            .comment("Energy usage of the compressor per tick")
            .defineInRange("compressor_usage", 8, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue ARMOR_MODIFICATION_TABLE_CAPACITY = BUILDER
            .comment("Energy capacity of the armor modification table")
            .defineInRange("armor_modification_table_capacity", 512_000, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
