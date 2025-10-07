package com.portingdeadmods.power_armor.content.menus;

import com.portingdeadmods.portingdeadlibs.api.gui.menus.PDLAbstractContainerMenu;
import com.portingdeadmods.power_armor.content.blockentities.ArmorModificationTableBlockEntity;
import com.portingdeadmods.power_armor.registries.PAMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ArmorModificationTableMenu extends PDLAbstractContainerMenu<ArmorModificationTableBlockEntity> {
    public ArmorModificationTableMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, (ArmorModificationTableBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

    public ArmorModificationTableMenu(int containerId, @NotNull Inventory inv, @NotNull ArmorModificationTableBlockEntity blockEntity) {
        super(PAMenuTypes.ARMOR_MODIFICATION_TABLE.get(), containerId, inv, blockEntity);

        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 80, 57));

        for (int i = 0; i < 4; i++) {
            int x = 22 + (i == 0 || i == 3 ? 4 : 0);
            addSlot(new SlotItemHandler(blockEntity.getItemHandler(), i + 1, x, 18 + i * 26));
        }

        for (int i = 0; i < 4; i++) {
            int x = 138 - (i == 0 || i == 3 ? 4 : 0);
            addSlot(new SlotItemHandler(blockEntity.getItemHandler(), i + 5, x, 18 + i * 26));
        }

        addPlayerInventory(inv, 132);
        addPlayerHotbar(inv, 190);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 9;
    }
}
