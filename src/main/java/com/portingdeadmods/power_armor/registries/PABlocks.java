package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.blocks.ArmorModificationTableBlock;
import com.portingdeadmods.power_armor.content.blocks.CompressorBlock;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLDeferredRegisterBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class PABlocks {
    public static final PDLDeferredRegisterBlocks BLOCKS = PDLDeferredRegisterBlocks.createBlocksRegister(PowerArmor.MODID, PAItems.ITEMS);

    public static final DeferredBlock<Block> MACHINE_FRAME = BLOCKS.registerSimpleBlockWithItem("machine_frame", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<CompressorBlock> COMPRESSOR = BLOCKS.registerBlockWithItem("compressor", CompressorBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<ArmorModificationTableBlock> ARMOR_MODIFICATION_TABLE = BLOCKS.registerBlockWithItem("armor_modification_table", ArmorModificationTableBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

}
