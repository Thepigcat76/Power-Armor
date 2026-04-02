package com.portingdeadmods.power_armor.registries;

import com.google.common.collect.Maps;
import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public interface PAArmorMaterials {
    ResourceKey<EquipmentAsset> POWER_ARMOR_ASSET = PAArmorMaterials.createId("power_armor");
    ResourceKey<EquipmentAsset> POWER_ARMOR_SOLAR_ASSET = PAArmorMaterials.createId("power_armor_solar");
    ArmorMaterial POWER_ARMOR = new ArmorMaterial(
            0,
            makeDefense(0, 0, 0, 0, 0),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            null,
            POWER_ARMOR_ASSET
    );

    static ResourceKey<EquipmentAsset> createId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, PowerArmor.id(name));
    }

    private static Map<ArmorType, Integer> makeDefense(int boots, int legs, int chest, int helm, int body) {
        return Maps.newEnumMap(Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, legs, ArmorType.CHESTPLATE, chest, ArmorType.HELMET, helm, ArmorType.BODY, body));
    }

}
