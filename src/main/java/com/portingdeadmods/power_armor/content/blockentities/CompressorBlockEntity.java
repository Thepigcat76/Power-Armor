package com.portingdeadmods.power_armor.content.blockentities;

import com.portingdeadmods.portingdeadlibs.api.blockentities.RedstoneBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blockentities.SimpleContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.data.transfer.PDLItemStacksHandler;
import com.portingdeadmods.portingdeadlibs.api.data.transfer.PDLSimpleEnergyHandler;
import com.portingdeadmods.portingdeadlibs.api.misc.PDLBlockStateProperties;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.content.blocks.CompressorBlock;
import com.portingdeadmods.power_armor.content.menus.CompressorMenu;
import com.portingdeadmods.power_armor.content.recipes.CompressingRecipe;
import com.portingdeadmods.power_armor.data.LimitingItemHandler;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import com.portingdeadmods.power_armor.registries.PATranslations;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
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
import net.neoforged.neoforge.transfer.energy.LimitingEnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CompressorBlockEntity extends SimpleContainerBlockEntity implements MenuProvider, RedstoneBlockEntity {
    private final ContainerData dataAccess;
    private CompressingRecipe currentRecipe;
    private int progress;
    private int maxProgress;
    private RedstoneSignalType redstoneSignalType;
    private final PDLItemStacksHandler itemHandler;
    private final ResourceHandler<ItemResource> exposedItemHandler;
    private final EnergyHandler exposedEnergyHandler;

    private final RecipeManager.CachedCheck<SingleRecipeInput, CompressingRecipe> quickCheck;

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PABlockEntityTypes.COMPRESSOR.get(), blockPos, blockState);

        this.quickCheck = RecipeManager.createCheck(CompressingRecipe.TYPE);

        // 0 - Input, 1 - Output
        this.itemHandler = addHandler(Capabilities.Item.BLOCK, new PDLItemStacksHandler(2) {
            @Override
            public long getCapacityAsLong(int index, ItemResource resource) {
                Objects.checkIndex(index, size());
                return resource.isEmpty() || isValid(index, resource) || index == 1 ? getCapacity(index, resource) : 0;
            }
        });
        //this.itemHandler.setOnChangeFunction((_, _) -> this.updateData());
        this.itemHandler.setValidator((i, _) -> i == 0);

        this.exposedItemHandler = new LimitingItemHandler(this.itemHandler, IntSet.of(0), IntSet.of(1));

        PDLSimpleEnergyHandler simpleEnergyHandler = addHandler(Capabilities.Energy.BLOCK, new PDLSimpleEnergyHandler(PowerArmorConfig.compressorEnergyCapacity, PowerArmorConfig.compressorEnergyTransfer));
        simpleEnergyHandler.setOnChangeFunction(_ -> {
            //this.updateData();
        });

        this.exposedEnergyHandler = new LimitingEnergyHandler(simpleEnergyHandler, PowerArmorConfig.compressorEnergyTransfer, 0);

        this.redstoneSignalType = RedstoneSignalType.IGNORED;

        this.dataAccess = new ContainerData() {
            @Override
            public int get(int dataId) {
                return switch (dataId) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int i1) {
                switch (dataId) {
                    case 0 -> progress = i1;
                    case 1 -> maxProgress = i1;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private boolean checkRecipe() {
        boolean changed = false;
        if (level instanceof ServerLevel serverLevel) {
            ResourceHandler<ItemResource> itemHandler = this.getHandler(Capabilities.Item.BLOCK);
            ItemStack stackInSlot = itemHandler.getResource(0).toStack();
            CompressingRecipe recipe = this.quickCheck.getRecipeFor(new SingleRecipeInput(stackInSlot), serverLevel)
                    .map(RecipeHolder::value)
                    .orElse(null);
            if (recipe != null) {
                ItemStack resultItem = recipe.result().create();
                ItemResource result = this.getExposedItemHandler().getResource(1);
                int resultAmount = this.getExposedItemHandler().getAmountAsInt(1);
                if (resultAmount + resultItem.getCount() <= this.getExposedItemHandler().getCapacityAsInt(1, result) && (result.is(resultItem.getItem()) || result.isEmpty())) {
                    changed = this.currentRecipe != recipe;
                    this.currentRecipe = recipe;
                    this.maxProgress = recipe.duration();
                } else {
                    changed = this.currentRecipe == recipe;
                    this.currentRecipe = null;
                    this.progress = 0;
                    this.maxProgress = 0;
                }
            } else {
                changed = this.currentRecipe != null;
                this.currentRecipe = null;
                this.progress = 0;
                this.maxProgress = 0;
            }
        }
        return changed;
    }

    @Override
    public void tick() {
        EnergyHandler energyHandler = this.getHandler(Capabilities.Energy.BLOCK);
        ResourceHandler<ItemResource> itemHandler = this.getItemHandler();

        boolean changed = this.checkRecipe();

        if (level instanceof ServerLevel) {
            if (this.currentRecipe != null && energyHandler.getAmountAsInt() >= PowerArmorConfig.compressorEnergyUsage) {
                if (this.progress >= this.currentRecipe.duration()) {
                    ItemStack resultItem = this.currentRecipe.result().create();
                    try (Transaction tx = Transaction.openRoot()) {
                        itemHandler.extract(0, itemHandler.getResource(0), 1, tx);
                        tx.commit();
                    }
                    ItemResource resultResource = ItemResource.of(resultItem);
                    boolean resultSlotEmpty = this.itemHandler.getResource(1).isEmpty() && this.itemHandler.getAmountAsInt(1) == 0;
                    boolean resultMatches = this.itemHandler.getResource(1).matches(resultItem);
                    boolean resultFits = this.itemHandler.getAmountAsInt(1) + resultItem.count() <= this.itemHandler.getCapacityAsInt(1, this.itemHandler.getResource(1));
                    if ((resultMatches && resultFits) || resultSlotEmpty) {
                        this.itemHandler.set(1, resultResource, this.itemHandler.getAmountAsInt(1) + resultItem.count());
                    }
                    this.progress = 0;
                    changed = true;
                } else {
                    try (Transaction tx = Transaction.openRoot()) {
                        energyHandler.extract(PowerArmorConfig.compressorEnergyUsage, tx);
                        tx.commit();
                    }
                    this.progress++;
                    changed = true;
                    setActive(!isActive(), true);
                }
            } else {
                if (isActive()) {
                    changed = true;
                }
                this.progress = 0;
                setActive(isActive(), false);
            }
        }

        if (changed) {
            setChanged();
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
        return this.maxProgress;
    }

    public int getProgress() {
        return this.progress;
    }

    public ResourceHandler<ItemResource> getExposedItemHandler() {
        return this.exposedItemHandler;
    }

    public PDLItemStacksHandler getItemHandler() {
        return itemHandler;
    }

    public EnergyHandler getExposedEnergyHandler() {
        return this.exposedEnergyHandler;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return PATranslations.COMPRESSOR_SCREEN_TITLE.component();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CompressorMenu(i, inventory, this, dataAccess);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.progress = input.getIntOr("progress", 0);
        this.maxProgress = input.getIntOr("max_progress", 0);
        this.redstoneSignalType = RedstoneSignalType.values()[input.getIntOr("redstone_signal_type", 0)];
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("progress", this.progress);
        output.putInt("max_progress", this.maxProgress);
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

    public void updateData() {
        this.setChanged();
        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
    }

}
