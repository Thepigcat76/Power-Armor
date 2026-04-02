package com.portingdeadmods.power_armor.content.blockentities;

import com.portingdeadmods.portingdeadlibs.api.blockentities.SimpleContainerBlockEntity;
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
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ArmorModificationTableBlockEntity extends SimpleContainerBlockEntity implements MenuProvider {
    private final ArmorModulesItemHandlerWrapper itemHandler;
    private final SimpleEnergyHandler energyHandler;

    public ArmorModificationTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PABlockEntityTypes.ARMOR_MODIFICATION_TABLE.get(), blockPos, blockState);

        this.energyHandler = addHandler(Capabilities.Energy.BLOCK, new SimpleEnergyHandler(PowerArmorConfig.armorModificationTableEnergyCapacity, PowerArmorConfig.compressorEnergyTransfer, 0));
        this.itemHandler = new ArmorModulesItemHandlerWrapper(ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (!this.itemHandler.itemStack().isEmpty()) {
            EnergyHandler energyHandler = this.itemHandler.itemStack().getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(this.itemHandler.itemStack()));
            if (energyHandler != null) {
                try (Transaction tx = Transaction.openRoot()) {
                    int extracted = this.energyHandler.extract(PowerArmorConfig.powerArmorEnergyTransfer, tx);
                    energyHandler.insert(extracted, tx);
                    tx.commit();
                }
            }
        }
    }

    public ArmorModulesItemHandlerWrapper getItemHandler() {
        return this.itemHandler;
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
    protected void dropItems() {
        Containers.dropContents(this.level, this.worldPosition, NonNullList.of(ItemStack.EMPTY, this.itemHandler.itemStack()));
    }
}
