package com.portingdeadmods.power_armor.content.menus;

import com.portingdeadmods.power_armor.content.blockentities.CompressorBlockEntity;
import com.portingdeadmods.power_armor.registries.PAMenuTypes;
import com.portingdeadmods.portingdeadlibs.api.gui.menus.PDLAbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jetbrains.annotations.NotNull;

public class CompressorMenu extends PDLAbstractContainerMenu<CompressorBlockEntity> {
    public static final int DATA_SLOTS = 3;
    private final ContainerData dataAccess;

    public CompressorMenu(int containerId, @NotNull Inventory inv, @NotNull FriendlyByteBuf byteBuf) {
        this(containerId, inv, (CompressorBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()), new SimpleContainerData(DATA_SLOTS));
    }

    public CompressorMenu(int containerId, @NotNull Inventory inv, @NotNull CompressorBlockEntity blockEntity, ContainerData dataAccess) {
        super(PAMenuTypes.COMPRESSOR.get(), containerId, inv, blockEntity);
        this.dataAccess = dataAccess;

        int startX = 56;
        int startY = 35;

        addSlot(new ResourceHandlerSlot(blockEntity.getItemHandler(), blockEntity.getItemHandler()::set, 0, startX, startY));
        addSlot(new ResourceHandlerSlot(blockEntity.getItemHandler(), blockEntity.getItemHandler()::set, 1, startX + 60, startY));

        addPlayerInventory(inv, 83 + 1);
        addPlayerHotbar(inv, 141 + 1);

        addDataSlots(dataAccess);

    }

    public int getProgress() {
        return this.dataAccess.get(0);
    }

    public int getMaxProgress() {
        return this.dataAccess.get(1);
    }

    public int getEnergyStored() {
        return this.dataAccess.get(2);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 1;
    }
}
