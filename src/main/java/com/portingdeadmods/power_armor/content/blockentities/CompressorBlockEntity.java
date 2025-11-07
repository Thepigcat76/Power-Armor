package com.portingdeadmods.power_armor.content.blockentities;

import com.portingdeadmods.portingdeadlibs.api.blockentities.RedstoneBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLBlockStateProperties;
import com.portingdeadmods.portingdeadlibs.utils.capabilities.HandlerUtils;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.content.menus.CompressorMenu;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import com.portingdeadmods.power_armor.registries.PATranslations;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.utils.IOAction;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CompressorBlockEntity extends ContainerBlockEntity implements MenuProvider, RedstoneBlockEntity {
    private CompressingRecipe currentRecipe;
    private int progress;
    private RedstoneSignalType redstoneSignalType;

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PABlockEntityTypes.COMPRESSOR.get(), blockPos, blockState);
        // 0 - Input, 1 - Output
        addItemHandler(HandlerUtils::newItemStackHandler, builder -> builder
                .slots(2)
                .validator((slot, stack) -> slot == 0)
                .onChange(this::onItemsChanged));
        addEnergyStorage(HandlerUtils::newEnergystorage, builder -> builder
                .maxTransfer(PowerArmorConfig.compressorEnergyTransfer)
                .onChange(this::updateData)
                .capacity(PowerArmorConfig.compressorEnergyCapacity));

        this.redstoneSignalType = RedstoneSignalType.IGNORED;
    }

    protected void onItemsChanged(int slot) {
        this.updateData();

        if (slot == 0 || slot == 1) {
            this.checkRecipe();
        }

    }

    private void checkRecipe() {
        ItemStack stackInSlot = this.getItemHandler().getStackInSlot(0);
        CompressingRecipe recipe = level.getRecipeManager().getRecipeFor(CompressingRecipe.TYPE, new SingleRecipeInput(stackInSlot), level)
                .map(RecipeHolder::value)
                .orElse(null);
        if (recipe != null) {
            ItemStack resultStack = this.getItemHandler().getStackInSlot(1);
            if (resultStack.getCount() + recipe.result().getCount() <= this.getItemHandler().getSlotLimit(1) && (resultStack.is(recipe.result().getItem()) || resultStack.isEmpty())) {
                this.currentRecipe = recipe;
            } else {
                this.currentRecipe = null;
                this.progress = 0;
            }
        } else {
            this.currentRecipe = null;
            this.progress = 0;
        }
    }

    @Override
    public void tick() {
        if (this.currentRecipe != null && this.getEnergyStorage().getEnergyStored() >= PowerArmorConfig.compressorEnergyUsage) {
            if (this.progress >= this.currentRecipe.duration()) {
                ItemStack result = this.currentRecipe.result().copy();
                this.getItemHandler().extractItem(0, 1, false);
                this.forceInsertItem(((IItemHandlerModifiable) this.getItemHandler()), 1, result, false, this::onItemsChanged);
                this.progress = 0;
            } else {
                this.getEnergyStorage().extractEnergy(PowerArmorConfig.compressorEnergyUsage, false);
                this.progress++;
                setActive(!isActive(), true);
            }
        } else {
            this.progress = 0;
            setActive(isActive(), false);
        }
    }

    private @NotNull Boolean isActive() {
        return this.getBlockState().getValue(PDLBlockStateProperties.ACTIVE);
    }

    private void setActive(boolean active, boolean value) {
        if (active) {
            this.level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(PDLBlockStateProperties.ACTIVE, value));
        }
    }

    public int getMaxProgress() {
        return this.currentRecipe != null ? this.currentRecipe.duration() : 0;
    }

    public int getProgress() {
        return this.progress;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return PATranslations.COMPRESSOR_SCREEN_TITLE.component();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CompressorMenu(i, inventory, this);
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.progress = tag.getInt("progress");
        this.redstoneSignalType = RedstoneSignalType.values()[tag.getInt("redstone_signal_type")];
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putInt("progress", this.progress);
        tag.putInt("redstone_signal_type", this.redstoneSignalType.ordinal());
    }

    @Override
    public void onLoad() {
        super.onLoad();

        this.checkRecipe();
    }

    @Override
    public int emitRedstoneLevel() {
        return ItemHandlerHelper.calcRedstoneFromInventory(this.getItemHandler());
    }

    @Override
    public void setRedstoneSignalType(RedstoneSignalType redstoneSignalType) {
        this.redstoneSignalType = redstoneSignalType;
        this.updateData();
    }

    @Override
    public RedstoneSignalType getRedstoneSignalType() {
        return this.redstoneSignalType;
    }

}
