package com.portingdeadmods.power_armor.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.portingdeadlibs.utils.codec.CodecUtils;
import com.portingdeadmods.power_armor.PARegistries;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record ArmorModuleComponent(List<ArmorModule> modules) {
    public static final Codec<ArmorModuleComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecUtils.registryCodec(PARegistries.ARMOR_MODULE).listOf().fieldOf("modules").forGetter(ArmorModuleComponent::modules)
    ).apply(inst, ArmorModuleComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorModuleComponent> STREAM_CODEC = StreamCodec.composite(
            CodecUtils.registryStreamCodec(PARegistries.ARMOR_MODULE).apply(ByteBufCodecs.list()),
            ArmorModuleComponent::modules,
            ArmorModuleComponent::new
    );
}
