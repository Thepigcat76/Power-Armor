package com.portingdeadmods.power_armor.networking;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.registries.PAAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetDeltaMovementPayload(Vec3 deltaMovement) implements CustomPacketPayload {
    public static final Type<SetDeltaMovementPayload> TYPE = new Type<>(PowerArmor.id("set_delta_movement"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, SetDeltaMovementPayload> STREAM_CODEC = StreamCodec.composite(
            Vec3.STREAM_CODEC,
            SetDeltaMovementPayload::deltaMovement,
            SetDeltaMovementPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().setDeltaMovement(this.deltaMovement);
        }).exceptionally(err -> {
            PowerArmor.LOGGER.error("Failed to handle SetDeltaMovementPayload", err);
            return null;
        });
    }

}

