package com.portingdeadmods.power_armor.datagen.assets;

import com.portingdeadmods.portingdeadlibs.api.misc.PDLBlockStateProperties;
import com.portingdeadmods.power_armor.registries.PABlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.function.TriFunction;

public class PABlockModelSubProvider {
    private final String modid;

    public PABlockModelSubProvider(String modid) {
        this.modid = modid;
    }

    public static TextureMapping compressor(Block compressor, Block machineFrame, boolean active) {
        return (new TextureMapping())
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(machineFrame))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(machineFrame))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(machineFrame))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(compressor, "_front" + (active ? "_active" : "")))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(machineFrame))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(machineFrame))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(machineFrame));
    }

    public void registerModels(ItemModelGenerators itemModelGenerators, BlockModelGenerators blockModelGenerators) {
        createCompressor(blockModelGenerators, PABlocks.COMPRESSOR.get(), PABlocks.MACHINE_FRAME.get(), PABlockModelSubProvider::compressor);
        blockModelGenerators.createTrivialCube(PABlocks.MACHINE_FRAME.get());
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(PABlocks.ARMOR_MODIFICATION_TABLE.get(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(PABlocks.ARMOR_MODIFICATION_TABLE.get()))));

        emitSimpleBlockItem(itemModelGenerators, PABlocks.COMPRESSOR.get(), PABlocks.COMPRESSOR.asItem());
        emitSimpleBlockItem(itemModelGenerators, PABlocks.ARMOR_MODIFICATION_TABLE.get(), PABlocks.ARMOR_MODIFICATION_TABLE.asItem());
        emitSimpleBlockItem(itemModelGenerators, PABlocks.MACHINE_FRAME.get(), PABlocks.MACHINE_FRAME.asItem());
    }

    private static void emitSimpleBlockItem(ItemModelGenerators itemModelGenerators, Block compressorBlock, Item compressorItem) {
        itemModelGenerators.itemModelOutput.accept(compressorItem, ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(compressorBlock)));
    }

    public void createCompressor(BlockModelGenerators blockModelGenerators, Block compressor, Block machineFrame, TriFunction<Block, Block, Boolean, TextureMapping> mappingFunction) {
        MultiVariant normalModel = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(compressor, mappingFunction.apply(compressor, machineFrame, false), blockModelGenerators.modelOutput));
        MultiVariant activeModel = BlockModelGenerators.plainVariant(
                ModelTemplates.CUBE.createWithSuffix(compressor, "_active", mappingFunction.apply(compressor, machineFrame, true), blockModelGenerators.modelOutput)
        );
        blockModelGenerators.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(compressor)
                        .with(BlockModelGenerators.createBooleanModelDispatch(PDLBlockStateProperties.ACTIVE, activeModel, normalModel))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        );
    }
}
