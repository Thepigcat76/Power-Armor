package com.portingdeadmods.power_armor.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blocks.RotatableContainerBlock;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLBlockStateProperties;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class CompressorBlock extends RotatableContainerBlock {
    public CompressorBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState()
                .setValue(PDLBlockStateProperties.ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(PDLBlockStateProperties.ACTIVE));
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return PABlockEntityTypes.COMPRESSOR.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CompressorBlock::new);
    }
}
