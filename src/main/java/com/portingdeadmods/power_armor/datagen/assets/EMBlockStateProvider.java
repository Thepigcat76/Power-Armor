package com.portingdeadmods.power_armor.datagen.assets;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.portingdeadlibs.api.datagen.ModelBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

public class EMBlockStateProvider extends BlockStateProvider {
    public EMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PowerArmor.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(PABlocks.MACHINE_FRAME.get());

        modelBuilder(PABlocks.COMPRESSOR.get())
                .front(this::blockTexture, "_front")
                .horizontalFacing()
                .active()
                .create();
    }

    private @NotNull ModelBuilder modelBuilder(Block block) {
        return new ModelBuilder(block, this).defaultTexture(blockTexture(PABlocks.MACHINE_FRAME.get()));
    }

    private ResourceLocation machineTexture(DeferredBlock<?> block, String suffix) {
        return blockTexture(block.get(), "machine", suffix);
    }

    private ResourceLocation machineTexture(DeferredBlock<?> block) {
        return blockTexture(block.get(), "machine", "");
    }

    private ResourceLocation machineTexture(Block block, String suffix) {
        return blockTexture(block, "machine", suffix);
    }

    private ResourceLocation machineTexture(Block block) {
        return blockTexture(block, "machine", "");
    }

    public ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private ResourceLocation blockTexture(Block block, String textureFolder, String suffix) {
        ResourceLocation name = key(block);
        if (textureFolder == null || textureFolder.trim().isEmpty())
            return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + suffix);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + textureFolder + "/" + name.getPath() + suffix);
    }

    private ResourceLocation blockTexture(Block block, String suffix) {
        return this.blockTexture(block, "", suffix);
    }
}
