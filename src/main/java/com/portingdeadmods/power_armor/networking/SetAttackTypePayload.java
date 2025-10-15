package com.portingdeadmods.power_armor.networking;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.registries.PAAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetAttackTypePayload(int attackType) implements CustomPacketPayload {
    public static final Type<SetAttackTypePayload> TYPE = new Type<>(PowerArmor.rl("set_attack_type"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, SetAttackTypePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetAttackTypePayload::attackType,
            SetAttackTypePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
           context.player().setData(PAAttachments.ATTACK_TYPE.get(), this.attackType);
        }).exceptionally(err -> {
            PowerArmor.LOGGER.error("Failed to handle SetAttackTypePayload", err);
            return null;
        });
    }

}
