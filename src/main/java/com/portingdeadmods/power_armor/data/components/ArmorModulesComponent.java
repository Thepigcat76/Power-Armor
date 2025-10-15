package com.portingdeadmods.power_armor.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

public record ArmorModulesComponent(NonNullList<ArmorModule> modules, int modulesAmount) {
    public static final ArmorModulesComponent EMPTY = new ArmorModulesComponent(NonNullList.withSize(8, ArmorModule.EMPTY), 8);
    public static final Codec<ArmorModulesComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            NonNullList.codecOf(CodecUtils.registryCodec(PARegistries.ARMOR_MODULE)).fieldOf("modules").forGetter(ArmorModulesComponent::modules),
            Codec.INT.fieldOf("modules_amount").forGetter(ArmorModulesComponent::modulesAmount)
    ).apply(inst, ArmorModulesComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorModulesComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(NonNullList::createWithCapacity, CodecUtils.registryStreamCodec(PARegistries.ARMOR_MODULE)),
            ArmorModulesComponent::modules,
            ByteBufCodecs.INT,
            ArmorModulesComponent::modulesAmount,
            ArmorModulesComponent::new
    );

    public ArmorModulesComponent(NonNullList<ArmorModule> modules) {
        this(modules, 8);
    }

    public static ArmorModulesComponent of(ArmorModule ...modules) {
        return new ArmorModulesComponent(NonNullList.of(PAArmorModules.EMPTY.get(), modules));
    }

    @Override
    public NonNullList<ArmorModule> modules() {
        NonNullList<ArmorModule> modules = NonNullList.withSize(this.modules.size(), ArmorModule.EMPTY);
        for (int i = 0; i < this.modules.size(); i++) {
            ArmorModule module = this.modules.get(i);
            modules.set(i, module);
        }
        return modules;
    }

    public NonNullList<ArmorModule> modulesUnsafe() {
        return this.modules;
    }

    public void addTooltip(ItemStack stack, List<Component> tooltipComponents) {
        for (ArmorModule module : this.modules()) {
            module.addTooltip(stack, tooltipComponents);
        }
    }
}
