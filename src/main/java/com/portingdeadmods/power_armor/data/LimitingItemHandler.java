package com.portingdeadmods.power_armor.data;

import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.Set;

public class LimitingItemHandler implements ResourceHandler<ItemResource> {
    private final ResourceHandler<ItemResource> inner;
    private final Set<Integer> insertSlots;
    private final Set<Integer> extractSlots;

    public LimitingItemHandler(ResourceHandler<ItemResource> inner, Set<Integer> insertSlots, Set<Integer> extractSlots) {
        this.inner = inner;
        this.insertSlots = insertSlots;
        this.extractSlots = extractSlots;
    }

    @Override
    public int size() {
        return this.inner.size();
    }

    @Override
    public ItemResource getResource(int index) {
        return this.inner.getResource(index);
    }

    @Override
    public long getAmountAsLong(int index) {
        return this.inner.getAmountAsLong(index);
    }

    @Override
    public long getCapacityAsLong(int index, ItemResource resource) {
        return this.inner.getCapacityAsLong(index, resource);
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return this.inner.isValid(index, resource);
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (this.insertSlots.contains(index)) {
            return this.inner.insert(index, resource, amount, transaction);
        }
        return 0;
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (this.extractSlots.contains(index)) {
            return this.inner.extract(index, resource, amount, transaction);
        }
        return 0;
    }
}
