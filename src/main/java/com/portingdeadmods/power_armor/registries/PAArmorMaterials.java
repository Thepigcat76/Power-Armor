package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public final class PAArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, PowerArmor.MODID);

    public static final Supplier<ArmorMaterial> POWER_ARMOR = ARMOR_MATERIALS.register("power_armor", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 3);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.EMPTY,
            List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace("power_armor"))),
            2.0F,
            0
    ));

}
