package com.portingdeadmods.power_armor.datagen.data;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.portingdeadlibs.api.fluids.PDLFluid;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.registries.PATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EMTagsProvider {
    public static void createTagProviders(DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        generator.addProvider(true, new BlocksProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ItemsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new FluidsProvider(packOutput, lookupProvider));
    }

    protected static class ItemsProvider extends net.neoforged.neoforge.common.data.ItemTagsProvider {
        public ItemsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, PowerArmor.MODID);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(PATags.ItemTags.PLATES, PATags.ItemTags.PLATES_COPPER, PATags.ItemTags.PLATES_IRON);
            tag(PATags.ItemTags.WIRES, PATags.ItemTags.WIRES_COPPER);

            tag(PATags.ItemTags.PLATES_COPPER, PAItems.COPPER_PLATE.get());
            tag(PATags.ItemTags.PLATES_IRON, PAItems.IRON_PLATE.get());
            tag(PATags.ItemTags.WIRES_COPPER, PAItems.COPPER_WIRE.get());
        }

        private void tag(TagKey<Item> itemTagKey, ItemLike... items) {
            TagAppender<Item, Item> tag = tag(itemTagKey);
            for (ItemLike item : items) {
                tag.add(item.asItem());
            }
        }

        @SafeVarargs
        private void tag(TagKey<Item> itemTagKey, TagKey<Item>... items) {
            TagAppender<Item, Item> tag = tag(itemTagKey);
            for (TagKey<Item> item : items) {
                tag.addTag(item);
            }
        }
    }

    protected static class BlocksProvider extends BlockTagsProvider {
        public BlocksProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, PowerArmor.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(BlockTags.NEEDS_STONE_TOOL, PABlocks.MACHINE_FRAME.get());
            tag(BlockTags.NEEDS_STONE_TOOL, PABlocks.COMPRESSOR.get());
            tag(BlockTags.NEEDS_STONE_TOOL, PABlocks.ARMOR_MODIFICATION_TABLE.get());


            tag(BlockTags.MINEABLE_WITH_PICKAXE, PABlocks.MACHINE_FRAME.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE, PABlocks.COMPRESSOR.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE, PABlocks.ARMOR_MODIFICATION_TABLE.get());

        }

        private void tag(TagKey<Block> itemTagKey, Block... blocks) {
            TagAppender<Block, Block> tag = tag(itemTagKey);
            for (Block block : blocks) {
                tag.add(block);
            }
        }

        @SafeVarargs
        private void tag(TagKey<Block> itemTagKey, TagKey<Block>... blocks) {
            TagAppender<Block, Block> tag = tag(itemTagKey);
            for (TagKey<Block> block : blocks) {
                tag.addTag(block);
            }
        }
    }

    public static class FluidsProvider extends FluidTagsProvider {
        public FluidsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider, PowerArmor.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
        }

        private void tag(TagKey<Fluid> fluidTagKey, PDLFluid... fluids) {
            TagAppender<Fluid, Fluid> tag = tag(fluidTagKey);
            for (PDLFluid fluid : fluids) {
                tag.add(fluid.getStillFluid());
            }
        }

        private void tag(TagKey<Fluid> fluidTagKey, Fluid... fluids) {
            TagAppender<Fluid, Fluid> tag = tag(fluidTagKey);
            for (Fluid fluid : fluids) {
                tag.add(fluid);
            }
        }

        @SafeVarargs
        private void tag(TagKey<Fluid> fluidTagKey, TagKey<Fluid>... fluids) {
            TagAppender<Fluid, Fluid> tag = tag(fluidTagKey);
            for (TagKey<Fluid> fluid : fluids) {
                tag.addTag(fluid);
            }
        }
    }
}
