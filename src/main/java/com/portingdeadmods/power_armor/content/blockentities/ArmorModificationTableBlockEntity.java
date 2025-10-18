package com.portingdeadmods.power_armor.content.blockentities;

import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.utils.IOAction;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.content.items.PowerArmorItem;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import com.portingdeadmods.power_armor.data.ArmorModulesItemHandlerWrapper;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import com.portingdeadmods.power_armor.registries.PATranslations;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ArmorModificationTableBlockEntity extends ContainerBlockEntity implements MenuProvider {
    private final ArmorModulesItemHandlerWrapper itemHandler;

    public ArmorModificationTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PABlockEntityTypes.ARMOR_MODIFICATION_TABLE.get(), blockPos, blockState);
        addEnergyStorage(PowerArmorConfig.POWER_ARMOR_CAPACITY.getAsInt());
        this.itemHandler = new ArmorModulesItemHandlerWrapper(ItemStack.EMPTY);
    }

    @Override
    public void commonTick() {
        IEnergyStorage energyStorage = this.itemHandler.itemStack().getCapability(Capabilities.EnergyStorage.ITEM);
        if (energyStorage != null) {
            IEnergyStorage beEnergyStorage = this.getEnergyStorage();
            int extracted = beEnergyStorage.extractEnergy(PowerArmorConfig.POWER_ARMOR_TRANSFER.getAsInt(), false);
            energyStorage.receiveEnergy(extracted, false);
        }
    }

    @Override
    public ArmorModulesItemHandlerWrapper getItemHandler() {
        return this.itemHandler;
    }

    @Override
    public <T> Map<Direction, Pair<IOAction, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> blockCapability) {
        return Map.of();
    }

    @Override
    public Component getDisplayName() {
        return PATranslations.ARMOR_MODIFICATION_SCREEN_TITLE.component();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ArmorModificationTableMenu(i, inventory, this);
    }

    @Override
    public void drop() {
        Containers.dropContents(this.level, this.worldPosition, NonNullList.of(ItemStack.EMPTY, this.itemHandler.getStackInSlot(0)));
    }

}
