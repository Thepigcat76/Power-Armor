package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.blockentities.ArmorModificationTableBlockEntity;
import com.portingdeadmods.power_armor.content.blockentities.CompressorBlockEntity;
import com.portingdeadmods.power_armor.content.blockentities.CreativeBatteryBE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PABlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PowerArmor.MODID);

    public static final Supplier<BlockEntityType<CompressorBlockEntity>> COMPRESSOR = BLOCK_ENTITY_TYPES.register("compressor",
            () -> new BlockEntityType<>(CompressorBlockEntity::new, PABlocks.COMPRESSOR.get()));
    public static final Supplier<BlockEntityType<ArmorModificationTableBlockEntity>> ARMOR_MODIFICATION_TABLE = BLOCK_ENTITY_TYPES.register("armor_modification_table",
            () -> new BlockEntityType<>(ArmorModificationTableBlockEntity::new, PABlocks.ARMOR_MODIFICATION_TABLE.get()));
//    public static final Supplier<BlockEntityType<CreativeBatteryBE>> CREATIVE_BATTERY = BLOCK_ENTITY_TYPES.register("creative_battery",
//            () -> new BlockEntityType<>(CreativeBatteryBE::new, PABlocks.CREATIVE_BATTERY.get()));
}
