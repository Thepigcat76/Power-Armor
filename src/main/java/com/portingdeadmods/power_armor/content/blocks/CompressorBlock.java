package com.portingdeadmods.power_armor.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.portingdeadlibs.api.blockentities.PDLBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blocks.PDLEntityBlock;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import com.portingdeadmods.portingdeadlibs.api.misc.PDLBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class CompressorBlock extends PDLEntityBlock {
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
    protected RotationType getRotationType() {
        return RotationType.HORIZONTAL_FACING;
    }

    @Override
    public BlockEntityType<? extends PDLBlockEntity> getBlockEntityType() {
        return PABlockEntityTypes.COMPRESSOR.get();
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof MenuProvider menuProvider) {
            player.openMenu(menuProvider, pos);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

}
