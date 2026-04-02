package com.portingdeadmods.power_armor.datagen.assets;

import com.portingdeadmods.power_armor.client.items.Energy;
import com.portingdeadmods.power_armor.content.items.BatteryItem;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class PAItemModelSubProvider {
    private final String modid;

    public PAItemModelSubProvider(String modid) {
        this.modid = modid;
    }

    public void registerModels(ItemModelGenerators itemModelGenerators) {
        emitFlatItem(itemModelGenerators, PAItems.COPPER_PLATE);
        emitFlatItem(itemModelGenerators, PAItems.COPPER_WIRE);
        emitFlatItem(itemModelGenerators, PAItems.IRON_PLATE);
        emitFlatItem(itemModelGenerators, PAItems.ARMOR_PLATING);

        emitFlatItem(itemModelGenerators, PAItems.POWER_ARMOR_HELMET);
        emitFlatItem(itemModelGenerators, PAItems.POWER_ARMOR_CHESTPLATE);
        emitFlatItem(itemModelGenerators, PAItems.POWER_ARMOR_LEGGINGS);
        emitFlatItem(itemModelGenerators, PAItems.POWER_ARMOR_BOOTS);

        emitFlatItem(itemModelGenerators, PAItems.BLANK_ARMOR_MODULE);
        emitFlatItem(itemModelGenerators, PAItems.ENERGY_ARMOR_MODULE);
        emitFlatItem(itemModelGenerators, PAItems.JETPACK_ARMOR_MODULE);
        emitFlatItem(itemModelGenerators, PAItems.LASER_ARMOR_MODULE);
        emitFlatItem(itemModelGenerators, PAItems.NIGHT_VISION_ARMOR_MODULE);
        emitFlatItem(itemModelGenerators, PAItems.PLATING_ARMOR_MODULE);
        emitFlatItem(itemModelGenerators, PAItems.SOLAR_ARMOR_MODULE);
        emitFlatItem(itemModelGenerators, PAItems.SPEED_ARMOR_MODULE);

        emitRangeSelectItem(itemModelGenerators, PAItems.BATTERY, new Energy(BatteryItem.STAGES), id -> ItemModelUtils.plainModel(id.withSuffix("_0")), (item, builder) -> {
            for (int i = 0; i < BatteryItem.STAGES; i++) {
                builder.accept(entry(i, ItemModelUtils.plainModel(
                        ModelTemplates.FLAT_ITEM.create(itemModel(item).withSuffix("_" + i), TextureMapping.layer0(new Material(itemModel(item).withSuffix("_" + i))), itemModelGenerators.modelOutput)
                )));
            }
        });

    }

    public void emitFlatItem(ItemModelGenerators generators, ItemLike item) {
        Identifier identifier = ModelTemplates.FLAT_ITEM.create(item.asItem(), TextureMapping.layer0(item.asItem()), generators.modelOutput);
        generators.itemModelOutput.accept(item.asItem(), ItemModelUtils.plainModel(identifier));
    }

    public void emitRangeSelectItem(ItemModelGenerators generators, ItemLike item, RangeSelectItemModelProperty itemProperty, ItemModel.Unbaked baseModel, BiConsumer<Item, Consumer<RangeSelectItemModel.Entry>> entryConsumer) {
        List<RangeSelectItemModel.Entry> entryList = new ArrayList<>();
        entryConsumer.accept(item.asItem(), entryList::add);

        generators.itemModelOutput.accept(item.asItem(), ItemModelUtils.rangeSelect(itemProperty, baseModel, entryList));
    }

    public void emitRangeSelectItem(ItemModelGenerators generators, ItemLike item, RangeSelectItemModelProperty itemProperty, Function<Identifier, ItemModel.Unbaked> baseModel, BiConsumer<Item, Consumer<RangeSelectItemModel.Entry>> entryConsumer) {
        emitRangeSelectItem(generators, item, itemProperty, baseModel.apply(itemModel(item)), entryConsumer);
    }

    public RangeSelectItemModel.Entry entry(float threshold, ItemModel.Unbaked model) {
        return new RangeSelectItemModel.Entry(threshold, model);
    }

    public Identifier itemModel(ItemLike item) {
        return this.id(item).withPrefix("item/");
    }

    public Identifier id(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

}
