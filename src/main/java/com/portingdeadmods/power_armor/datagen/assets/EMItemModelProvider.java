package com.portingdeadmods.power_armor.datagen.assets;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.client.items.PAItemProperties;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class EMItemModelProvider extends ItemModelProvider {
    public EMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PowerArmor.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        PABlocks.BLOCKS.getBlockItems().stream().map(Supplier::get).map(BlockItem::getBlock).forEach(this::simpleBlockItem);

        basicItem(PAItems.COPPER_PLATE.get());
        basicItem(PAItems.IRON_PLATE.get());
        basicItem(PAItems.COPPER_WIRE.get());

        overrideItemModel(6, basicItem(PAItems.BATTERY.get(), extend(itemTexture(PAItems.BATTERY.get()), "_0")), PAItemProperties.BATTERY_STAGE,
                i -> basicItem(PAItems.BATTERY.get(), "_" + i));

        basicItem(PAItems.POWER_ARMOR_HELMET.get());
        basicItem(PAItems.POWER_ARMOR_CHESTPLATE.get());
        basicItem(PAItems.POWER_ARMOR_LEGGINGS.get());
        basicItem(PAItems.POWER_ARMOR_BOOTS.get());

    }

    private void overrideItemModel(int variants, ItemModelBuilder defaultModel, ResourceLocation key, Function<Integer, ItemModelBuilder> overrideFunction) {
        for (int i = 0; i < variants; i++) {
            ItemModelBuilder model = overrideFunction.apply(i);
            defaultModel.override()
                    .model(model)
                    .predicate(key, i)
                    .end();
        }
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    private static @NotNull ResourceLocation key(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    public ResourceLocation itemTexture(ItemLike item) {
        ResourceLocation name = key(item);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
    }

    public ItemModelBuilder basicItem(ItemLike itemLike) {
        return basicItem(itemLike, "");
    }

    public ItemModelBuilder basicItem(ItemLike item, String suffix) {
        ResourceLocation location = key(item);
        return getBuilder(location + suffix)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath() + suffix));
    }

    public String name(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    public ItemModelBuilder basicItem(ItemLike item, ResourceLocation texture) {
        return getBuilder(name(item))
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(texture.getNamespace(), texture.getPath()));
    }
}
