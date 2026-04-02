package com.portingdeadmods.power_armor.data;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class ArmorModulesItemHandlerWrapper implements ResourceHandler<ItemResource> {
    private ItemStack itemStack;
    private final ArrayList<StackJournal> snapshotJournals;

    public ArmorModulesItemHandlerWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.snapshotJournals = new ArrayList<>(this.size());
        this.updateStacksSize();
    }

    private void updateStacksSize() {
        snapshotJournals.ensureCapacity(this.size());
        // Add missing entries
        while (snapshotJournals.size() < this.size()) {
            snapshotJournals.add(new StackJournal(snapshotJournals.size()));
        }
        // Remove superfluous entries
        if (snapshotJournals.size() > this.size()) {
            snapshotJournals.subList(this.size(), snapshotJournals.size()).clear();
        }
    }

    private ArmorModulesComponent getArmorModules() {
        return this.itemStack.getOrDefault(PAComponents.ARMOR_MODULES, ArmorModulesComponent.EMPTY);
    }

    public ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public int size() {
        return this.getArmorModules().modulesAmount() + 1;
    }

    @Override
    public @NonNull ItemResource getResource(int i) {
        return ItemResource.of(this.getStack(i));
    }

    public ItemStack getStack(int i) {
        if (i == 0) return this.itemStack.copy();

        NonNullList<ArmorModule> modules = this.getArmorModules().modulesUnsafe();
        if (modules.size() > i - 1) {
            return new ItemStack(modules.get(i - 1).getItem());
        }
        return ItemStack.EMPTY;
    }

    @Override
    public long getAmountAsLong(int index) {
        return this.getStack(index).count();
    }

    @Override
    public long getCapacityAsLong(int index, ItemResource resource) {
        return 1;
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        if (index == 0) return resource.has(PAComponents.ARMOR_MODULES);

        if (!this.itemStack.has(PAComponents.ARMOR_MODULES)) return false;

        Equippable equippable = this.itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable != null) {
            EquipmentSlot slot = equippable.slot();

            ArmorModule armorModule = ArmorModule.byItem(resource.getItem());
            return armorModule != null
                    && !this.getArmorModules().modulesUnsafe().contains(armorModule)
                    && (armorModule.getArmorSlots() == null || armorModule.getArmorSlots().contains(slot));
        }

        return false;
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        Objects.checkIndex(index, size());
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);

        ItemStack currentStack = this.getStack(index);
        int currentAmount = currentStack.count();

        if ((currentAmount == 0 || resource.matches(currentStack) || isValid(index, resource))) {
            if (index == 0) {
                if (this.getStack(0).isEmpty()) {
                    this.snapshotJournals.get(index).updateSnapshots(transaction);
                    this.set(0, resource, 1);

                    return 1;
                }
            }
            this.snapshotJournals.get(index).updateSnapshots(transaction);
            this.set(index, resource.toStack(1), null);

            return 1;
        }

        return 0;
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        Objects.checkIndex(index, this.size());
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);

        if (amount > 0 && resource.matches(getStack(index))) {
            if (index == 0) {
                int curAmount = itemStack().count();
                this.snapshotJournals.get(index).updateSnapshots(transaction);
                this.set(index, ItemStack.EMPTY, transaction);
                return curAmount;
            }
            int moduleAmount = this.getAmountAsInt(index);
            this.snapshotJournals.get(index).updateSnapshots(transaction);
            this.set(index, ItemStack.EMPTY, transaction);
            return moduleAmount;
        }

        return 0;
    }

    protected void onContentsChanged(int index, ItemStack previousStack) {
    }

    public void set(int index, ItemStack stack, TransactionContext transaction) {
        if (index == 0) {
            this.itemStack = stack.copy();
            return;
        }

        if (!stack.isEmpty() && !this.itemStack.has(PAComponents.ARMOR_MODULES)) return;

        ArmorModule armorModule = ArmorModule.byItem(stack.getItem());
        NonNullList<ArmorModule> modules = this.getArmorModules().modules();
        if (modules.size() > index - 1) {
            if (armorModule != null && armorModule != ArmorModule.EMPTY) {
                modules.set(index - 1, armorModule);
                armorModule.onAdded(this.itemStack, transaction);
            } else if (stack.isEmpty()) {
                ArmorModule prevModule = modules.get(index - 1);
                modules.set(index - 1, ArmorModule.EMPTY);
                prevModule.onRemoved(this.itemStack, transaction);
            }
        }
        this.itemStack.set(PAComponents.ARMOR_MODULES.get(), new ArmorModulesComponent(modules, this.getArmorModules().modulesAmount()));
    }

    public void set(int index, ItemResource resource, int amount) {
        this.set(index, resource.toStack(amount), null);
    }

    private class StackJournal extends SnapshotJournal<ItemStack> {
        private final int index;

        private StackJournal(int index) {
            this.index = index;
        }

        @Override
        protected ItemStack createSnapshot() {
            return ArmorModulesItemHandlerWrapper.this.getStack(this.index);
        }

        @Override
        protected void revertToSnapshot(ItemStack snapshot) {
            ArmorModulesItemHandlerWrapper.this.set(this.index, snapshot, null);
        }

        @Override
        protected void onRootCommit(ItemStack originalState) {
            ArmorModulesItemHandlerWrapper.this.onContentsChanged(this.index, originalState);
        }
    }

}
