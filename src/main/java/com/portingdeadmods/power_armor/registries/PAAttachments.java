package com.portingdeadmods.power_armor.registries;

import com.mojang.serialization.Codec;
import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class PAAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PowerArmor.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ATTACK_TYPE = ATTACHMENTS.register("attack_type", () -> AttachmentType.builder(() -> 0)
            .serialize(Codec.INT.fieldOf("attack_type"))
            //.sync(ByteBufCodecs.INT)
            .copyOnDeath()
            .build());

}
