package com.portingdeadmods.power_armor.content.blocks;

import com.portingdeadmods.portingdeadlibs.api.blockentities.PDLBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blocks.PDLEntityBlock;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CreativeBatteryBlock extends PDLEntityBlock {
    public CreativeBatteryBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected BlockEntityType<? extends PDLBlockEntity> getBlockEntityType() {
        return PABlockEntityTypes.CREATIVE_BATTERY.get();
    }

    @Override
    protected boolean tickingEnabled() {
        return true;
    }

    @Override
    protected RotationType getRotationType() {
        return RotationType.NONE;
    }
}
