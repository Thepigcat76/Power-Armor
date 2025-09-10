package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.items.BatteryItem;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLDeferredRegisterItems;
import com.portingdeadmods.power_armor.content.items.PowerArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public final class PAItems {
    public static final PDLDeferredRegisterItems ITEMS = PDLDeferredRegisterItems.createItemsRegister(PowerArmor.MODID);

    public static final DeferredItem<Item> COPPER_PLATE = ITEMS.registerSimpleItem("copper_plate");
    public static final DeferredItem<Item> COPPER_WIRE = ITEMS.registerSimpleItem("copper_wire");
    public static final DeferredItem<Item> IRON_PLATE = ITEMS.registerSimpleItem("iron_plate");

    public static final DeferredItem<Item> BATTERY = ITEMS.register("battery", () -> new BatteryItem(new Item.Properties()
            .component(PDLDataComponents.ENERGY, 0)
            .stacksTo(1)));

    private static final Supplier<Item.Properties> POWER_ARMOR_PROPS = () -> new Item.Properties()
            .component(PDLDataComponents.ENERGY.get(), 0)
            .stacksTo(1);

    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_HELMET = ITEMS.register("power_armor_helmet", () -> new PowerArmorItem(ArmorItem.Type.HELMET, POWER_ARMOR_PROPS.get()));
    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_CHESTPLATE = ITEMS.register("power_armor_chestplate", () -> new PowerArmorItem(ArmorItem.Type.CHESTPLATE, POWER_ARMOR_PROPS.get()));
    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_LEGGINGS = ITEMS.register("power_armor_leggings", () -> new PowerArmorItem(ArmorItem.Type.LEGGINGS, POWER_ARMOR_PROPS.get()));
    public static final DeferredItem<PowerArmorItem> POWER_ARMOR_BOOTS = ITEMS.register("power_armor_boots", () -> new PowerArmorItem(ArmorItem.Type.BOOTS, POWER_ARMOR_PROPS.get()));

}
