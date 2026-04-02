package com.portingdeadmods.power_armor.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.portingdeadlibs.api.blockentities.PDLBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blocks.PDLEntityBlock;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArmorModificationTableBlock extends PDLEntityBlock {
    public static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 14, 0, 16, 16, 16),
            Block.box(4, 0, 4, 12, 14, 12)
    );

    public ArmorModificationTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected RotationType getRotationType() {
        return RotationType.NONE;
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

    @Override
    public BlockEntityType<? extends PDLBlockEntity> getBlockEntityType() {
        return PABlockEntityTypes.ARMOR_MODIFICATION_TABLE.get();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
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
