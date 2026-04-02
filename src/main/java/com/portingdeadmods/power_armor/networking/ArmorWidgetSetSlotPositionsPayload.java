package com.portingdeadmods.power_armor.networking;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ArmorWidgetSetSlotPositionsPayload(int startY) implements CustomPacketPayload {
    public static final Type<ArmorWidgetSetSlotPositionsPayload> TYPE = new Type<>(PowerArmor.id("armor_widget_set_slot_positions"));
    public static final StreamCodec<ByteBuf, ArmorWidgetSetSlotPositionsPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ArmorWidgetSetSlotPositionsPayload::startY,
            ArmorWidgetSetSlotPositionsPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof ArmorModificationTableMenu menu) {
                menu.setArmorSlotPositions(this.startY);
            }
        }).exceptionally(err -> {
            PowerArmor.LOGGER.error("Failed to handle ArmorWidgetSetSlotPositionsPayload payload", err);
            return null;
        });
    }
}