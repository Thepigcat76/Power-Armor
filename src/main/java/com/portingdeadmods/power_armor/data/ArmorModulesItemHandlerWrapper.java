package com.portingdeadmods.power_armor.data;

import com.portingdeadmods.portingdeadlibs.utils.UniqueArray;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.components.ArmorModuleComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Objects;

public class ArmorModulesItemHandlerWrapper implements IItemHandlerModifiable, INBTSerializable<CompoundTag> {
    private ItemStack itemStack;

    public ArmorModulesItemHandlerWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public int getSlots() {
        return this.getArmorModules().modulesAmount() + 1;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (i == 0) return this.itemStack.copy();

        NonNullList<ArmorModule> modules = this.getArmorModules().modulesUnsafe();
        if (modules.size() > i - 1) {
            return modules.get(i - 1).getItem().getDefaultInstance();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack itemStack, boolean simulate) {
        if (slot == 0) {
            if (this.getStackInSlot(0).isEmpty()) {
                int count = itemStack.getCount();
                if (!simulate) {
                    this.setStackInSlot(0, itemStack.copyWithCount(1));
                }
                return count - 1 <= 0 ? ItemStack.EMPTY : itemStack.copyWithCount(count - 1);
            } else {

            }
        }
        if (!simulate) {
            this.setStackInSlot(slot, itemStack);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int count, boolean simulate) {
        if (slot == 0) {
            ItemStack stack = itemStack.copy();
            if (!simulate) {
                this.setStackInSlot(slot, ItemStack.EMPTY);
            }
            return stack;
        }
        ItemStack stack = this.getStackInSlot(slot);
        if (!simulate) {
            this.setStackInSlot(slot, ItemStack.EMPTY);
        }
        return stack;
    }

    @Override
    public int getSlotLimit(int i) {
        return 1;
    }

    @Override
    public boolean isItemValid(int i, ItemStack itemStack) {
        if (i == 0) return itemStack.has(PAComponents.ARMOR_MODULE);

        ArmorModule armorModule = ArmorModule.byItem(itemStack.getItem());
        return armorModule != null && !this.getArmorModules().modulesUnsafe().contains(armorModule);
    }

    @Override
    public void setStackInSlot(int i, ItemStack itemStack) {
        if (i == 0) {
            this.itemStack = itemStack.copy();
            return;
        }
        ArmorModule armorModule = ArmorModule.byItem(itemStack.getItem());
        NonNullList<ArmorModule> modules = this.getArmorModules().modules();
        if (modules.size() > i - 1) {
            if (armorModule != null) {
                modules.set(i - 1, armorModule);
            } else if (itemStack.isEmpty()) {
                modules.remove(i - 1);
            }
        }
        this.itemStack.set(PAComponents.ARMOR_MODULE.get(), new ArmorModuleComponent(modules, this.getArmorModules().modulesAmount()));
    }

    private ArmorModuleComponent getArmorModules() {
        return this.itemStack.getOrDefault(PAComponents.ARMOR_MODULE, ArmorModuleComponent.EMPTY);
    }

    public ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ArmorModulesItemHandlerWrapper) obj;
        return Objects.equals(this.itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemStack);
    }

    @Override
    public String toString() {
        return "ArmorModulesItemHandlerWrapper[" +
                "itemStack=" + itemStack + ']';
    }


    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {

    }
}
