package com.portingdeadmods.power_armor;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class PowerArmorClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

}
