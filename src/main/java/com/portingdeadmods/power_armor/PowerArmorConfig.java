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

    public static final ModConfigSpec.IntValue COMPRESSOR_CAPACITY = BUILDER
            .comment("Energy capacity of the compressor")
            .defineInRange("compressor_capacity", 16_000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMPRESSOR_USAGE = BUILDER
            .comment("Energy usage of the compressor per tick")
            .defineInRange("compressor_usage", 8, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
