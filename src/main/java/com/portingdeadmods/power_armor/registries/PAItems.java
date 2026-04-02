package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.content.items.ArmorModuleItem;
import com.portingdeadmods.power_armor.content.items.BatteryItem;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.misc.PDLDeferredRegisterItems;
import com.portingdeadmods.power_armor.content.items.PowerArmorItem;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Function;
import java.util.function.Supplier;

public final class PAItems {
    public static final PDLDeferredRegisterItems ITEMS = PDLDeferredRegisterItems.createItemsRegister(PowerArmor.MODID);

    public static final DeferredItem<Item> COPPER_PLATE = ITEMS.registerSimpleItem("copper_plate");
    public static final DeferredItem<Item> COPPER_WIRE = ITEMS.registerSimpleItem("copper_wire");
    public static final DeferredItem<Item> IRON_PLATE = ITEMS.registerSimpleItem("iron_plate");
    public static final DeferredItem<Item> ARMOR_PLATING = ITEMS.registerSimpleItem("armor_plating");

    public static final DeferredItem<BatteryItem> BATTERY = ITEMS.registerItem("battery", BatteryItem::new, () -> new Item.Properties()
            .component(PDLDataComponents.ENERGY, 0)
            .stacksTo(1));

    private static final Function<ArmorType, Item.Properties> POWER_ARMOR_PROPS = armorType -> new Item.Properties()
            .component(PAComponents.ARMOR_MODULES.get(), ArmorModulesComponent.EMPTY)
            .component(PDLDataComponents.ENERGY.get(), 0)
            .component(PAComponents.ENERGY_CAPACITY.get(), 64_000)
            .attributes(ItemAttributeModifiers.EMPTY)
            .component(DataComponents.EQUIPPABLE, Equippable.builder(armorType.getSlot())
                    .setEquipSound(PAArmorMaterials.POWER_ARMOR.equipSound())
                    .setAsset(PAArmorMaterials.POWER_ARMOR.assetId())
                    .build())
            .stacksTo(1);

    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_HELMET = ITEMS.registerItem("power_armor_helmet", PowerArmorItem::new, () -> POWER_ARMOR_PROPS.apply(ArmorType.HELMET));
    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_CHESTPLATE = ITEMS.registerItem("power_armor_chestplate", PowerArmorItem::new, () -> POWER_ARMOR_PROPS.apply(ArmorType.CHESTPLATE));
    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_LEGGINGS = ITEMS.registerItem("power_armor_leggings", PowerArmorItem::new, () -> POWER_ARMOR_PROPS.apply(ArmorType.LEGGINGS));
    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_BOOTS = ITEMS.registerItem("power_armor_boots", PowerArmorItem::new, () -> POWER_ARMOR_PROPS.apply(ArmorType.BOOTS));

    public static final DeferredItem<ArmorModuleItem> BLANK_ARMOR_MODULE = ITEMS.registerItem("armor_module_blank", ArmorModuleItem::new);
    public static final DeferredItem<ArmorModuleItem> JETPACK_ARMOR_MODULE = ITEMS.registerItem("armor_module_jetpack", ArmorModuleItem::new);
    public static final DeferredItem<ArmorModuleItem> LASER_ARMOR_MODULE = ITEMS.registerItem("armor_module_laser", ArmorModuleItem::new);
    public static final DeferredItem<ArmorModuleItem> SOLAR_ARMOR_MODULE = ITEMS.registerItem("armor_module_solar", ArmorModuleItem::new);
    public static final DeferredItem<ArmorModuleItem> PLATING_ARMOR_MODULE = ITEMS.registerItem("armor_module_plating", ArmorModuleItem::new);
    public static final DeferredItem<ArmorModuleItem> NIGHT_VISION_ARMOR_MODULE = ITEMS.registerItem("armor_module_night_vision", ArmorModuleItem::new);
    public static final DeferredItem<ArmorModuleItem> ENERGY_ARMOR_MODULE = ITEMS.registerItem("armor_module_energy", ArmorModuleItem::new);
    public static final DeferredItem<ArmorModuleItem> SPEED_ARMOR_MODULE = ITEMS.registerItem("armor_module_speed", ArmorModuleItem::new);

}
