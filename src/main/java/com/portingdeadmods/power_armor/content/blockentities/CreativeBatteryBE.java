package com.portingdeadmods.power_armor.content.blockentities;

import com.portingdeadmods.portingdeadlibs.api.blockentities.PDLBlockEntity;
import com.portingdeadmods.power_armor.registries.PABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class CreativeBatteryBE extends PDLBlockEntity {
    public CreativeBatteryBE(BlockPos worldPosition, BlockState blockState) {
        super(PABlockEntityTypes.CREATIVE_BATTERY.get(), worldPosition, blockState);
    }

    @Override
    public void tick() {
        for (Direction direction : Direction.values()) {
            BlockPos pos = this.worldPosition.relative(direction);
            EnergyHandler capability = level.getCapability(Capabilities.Energy.BLOCK, pos, direction.getOpposite());
            if (capability != null) {
                try (Transaction tx = Transaction.openRoot()) {
                    capability.insert(100, tx);
                    tx.commit();
                }
            }
        }
    }

}
