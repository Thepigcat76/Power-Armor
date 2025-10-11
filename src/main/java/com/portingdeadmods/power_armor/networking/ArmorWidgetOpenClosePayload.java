package com.portingdeadmods.power_armor.networking;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import com.portingdeadmods.power_armor.content.menus.ArmorSlot;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ArmorWidgetOpenClosePayload(boolean open) implements CustomPacketPayload {
    public static final Type<ArmorWidgetOpenClosePayload> TYPE = new Type<>(PowerArmor.rl("armor_widget_open_close"));
    public static final StreamCodec<ByteBuf, ArmorWidgetOpenClosePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ArmorWidgetOpenClosePayload::open,
            ArmorWidgetOpenClosePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof ArmorModificationTableMenu menu) {
                for (ArmorSlot upgradeSlot : menu.getArmorSlots()) {
                    upgradeSlot.setActive(this.open);
                }
            }
        }).exceptionally(err -> {
            PowerArmor.LOGGER.error("Failed to handle ArmorWidgetOpenClosePayload payload", err);
            return null;
        });
    }
}