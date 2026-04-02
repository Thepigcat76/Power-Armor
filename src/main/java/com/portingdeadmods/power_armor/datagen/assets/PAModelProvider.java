package com.portingdeadmods.power_armor.datagen.assets;

import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public final class PAModelProvider extends ModelProvider {
    private final PAItemModelSubProvider itemModelSubProvider;
    private final PABlockModelSubProvider blockModelSubProvider;

    public PAModelProvider(PackOutput output) {
        super(output, PowerArmor.MODID);
        this.itemModelSubProvider = new PAItemModelSubProvider(PowerArmor.MODID);
        this.blockModelSubProvider = new PABlockModelSubProvider(PowerArmor.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.itemModelSubProvider.registerModels(itemModels);
        this.blockModelSubProvider.registerModels(itemModels, blockModels);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.empty();
    }
}
