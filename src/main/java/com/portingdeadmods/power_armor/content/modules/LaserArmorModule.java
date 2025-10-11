package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class LaserArmorModule implements ArmorModule {
    @Override
    public Item getItem() {
        return PAItems.LASER_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable ArmorItem.Type getArmorType() {
        return ArmorItem.Type.CHESTPLATE;
    }
}
