package com.portingdeadmods.power_armor.content.blockentities;

import com.portingdeadmods.portingdeadlibs.api.blockentities.RedstoneBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blockentities.SimpleContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.misc.PDLBlockStateProperties;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.content.menus.CompressorMenu;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import com.portingdeadmods.power_armor.registries.PATranslations;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompressorBlockEntity extends SimpleContainerBlockEntity implements MenuProvider, RedstoneBlockEntity {
    private CompressingRecipe currentRecipe;
    private int progress;
    private RedstoneSignalType redstoneSignalType;
    private final ItemStacksResourceHandler itemHandler;
    private final RecipeManager.CachedCheck<SingleRecipeInput, CompressingRecipe> quickCheck;

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PABlockEntityTypes.COMPRESSOR.get(), blockPos, blockState);

        this.quickCheck = RecipeManager.createCheck(CompressingRecipe.TYPE);

        // 0 - Input, 1 - Output
        this.itemHandler = addHandler(Capabilities.Item.BLOCK, new ItemStacksResourceHandler(2));
        addHandler(Capabilities.Energy.BLOCK, new SimpleEnergyHandler(PowerArmorConfig.compressorEnergyCapacity, PowerArmorConfig.compressorEnergyTransfer));
//        addItemHandler(HandlerUtils::newItemStackHandler, builder -> builder
//                .slots(2)
//                .validator((slot, stack) -> slot == 0)
//                .onChange(this::onItemsChanged));
//        addEnergyStorage(HandlerUtils::newEnergystorage, builder -> builder
//                .maxTransfer(PowerArmorConfig.compressorEnergyTransfer)
//                .onChange(this::updateData)
//                .capacity(PowerArmorConfig.compressorEnergyCapacity));

        this.redstoneSignalType = RedstoneSignalType.IGNORED;
    }

    private void checkRecipe() {
        ResourceHandler<ItemResource> itemHandler = this.getHandler(Capabilities.Item.BLOCK);

        ItemStack stackInSlot = itemHandler.getResource(0).toStack();
        if (level instanceof ServerLevel serverLevel) {
            CompressingRecipe recipe = this.quickCheck.getRecipeFor(new SingleRecipeInput(stackInSlot), serverLevel)
                    .map(RecipeHolder::value)
                    .orElse(null);
            if (recipe != null) {
                ItemStack resultItem = recipe.result().create();
                ItemResource result = this.getItemHandler().getResource(1);
                int resultAmount = this.getItemHandler().getAmountAsInt(1);
                if (resultAmount + resultItem.getCount() <= this.getItemHandler().getCapacityAsInt(1, result) && (result.is(resultItem.getItem()) || result.isEmpty())) {
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
    }

    @Override
    public void tick() {
        EnergyHandler energyHandler = this.getHandler(Capabilities.Energy.BLOCK);
        ResourceHandler<ItemResource> itemHandler = this.getItemHandler();

        if (this.currentRecipe != null && energyHandler.getAmountAsInt() >= PowerArmorConfig.compressorEnergyUsage) {
            if (this.progress >= this.currentRecipe.duration()) {
                ItemStack resultItem = this.currentRecipe.result().create();
                try (Transaction tx = Transaction.openRoot()) {
                    itemHandler.extract(0, itemHandler.getResource(0), 1, tx);
                    tx.commit();
                }
                ItemResource resultResource = ItemResource.of(resultItem);
                boolean resultSlotEmpty = this.itemHandler.getResource(1).isEmpty() && this.itemHandler.getAmountAsInt(1) == 0;
                boolean resultValid = this.itemHandler.isValid(1, resultResource);
                boolean resultMatches = this.itemHandler.getResource(1).matches(resultItem);
                boolean resultFits = this.itemHandler.getAmountAsInt(1) + resultItem.count() <= this.itemHandler.getCapacityAsInt(1, this.itemHandler.getResource(1));
                if (resultValid && ((resultMatches && resultFits) || resultSlotEmpty)) {
                    this.itemHandler.set(1, resultResource, resultItem.count());
                }
                this.progress = 0;
            } else {
                try (Transaction tx = Transaction.openRoot()) {
                    energyHandler.extract(PowerArmorConfig.compressorEnergyUsage, tx);
                    tx.commit();
                }
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

    public ItemStacksResourceHandler getItemHandler() {
        return itemHandler;
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
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.progress = input.getIntOr("progress", 0);
        this.redstoneSignalType = RedstoneSignalType.values()[input.getIntOr("redstone_signal_type", 0)];
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("progress", this.progress);
        output.putInt("redstone_signal_type", this.redstoneSignalType.ordinal());
    }

    @Override
    public void onLoad() {
        super.onLoad();

        this.checkRecipe();
    }

    @Override
    public int emitRedstoneLevel() {
        return ResourceHandlerUtil.getRedstoneSignalFromResourceHandler(this.getHandler(Capabilities.Item.BLOCK));
    }

    @Override
    public void setRedstoneSignalType(RedstoneSignalType redstoneSignalType) {
        this.redstoneSignalType = redstoneSignalType;
        this.setChanged();
    }

    @Override
    public RedstoneSignalType getRedstoneSignalType() {
        return this.redstoneSignalType;
    }

}
