package com.portingdeadmods.power_armor.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.portingdeadlibs.utils.UniqueArray;
import com.portingdeadmods.portingdeadlibs.utils.codec.CodecUtils;
import com.portingdeadmods.power_armor.PARegistries;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public record ArmorModuleComponent(NonNullList<ArmorModule> modules, int modulesAmount) {
    public static final ArmorModuleComponent EMPTY = new ArmorModuleComponent(NonNullList.withSize(8, PAArmorModules.EMPTY.get()), 8);
    public static final Codec<ArmorModuleComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            NonNullList.codecOf(CodecUtils.registryCodec(PARegistries.ARMOR_MODULE)).fieldOf("modules").forGetter(ArmorModuleComponent::modules),
            Codec.INT.fieldOf("modules_amount").forGetter(ArmorModuleComponent::modulesAmount)
    ).apply(inst, ArmorModuleComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorModuleComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(NonNullList::createWithCapacity, CodecUtils.registryStreamCodec(PARegistries.ARMOR_MODULE)),
            ArmorModuleComponent::modules,
            ByteBufCodecs.INT,
            ArmorModuleComponent::modulesAmount,
            ArmorModuleComponent::new
    );

    public ArmorModuleComponent(NonNullList<ArmorModule> modules) {
        this(modules, 8);
    }

    public static ArmorModuleComponent of(ArmorModule ...modules) {
        return new ArmorModuleComponent(NonNullList.of(PAArmorModules.EMPTY.get(), modules));
    }

    @Override
    public NonNullList<ArmorModule> modules() {
        return NonNullList.copyOf(this.modules);
    }

    public void addTooltip(ItemStack stack, List<Component> tooltipComponents) {
        for (ArmorModule module : this.modules()) {
            module.addTooltip(stack, tooltipComponents);
        }
    }
}
