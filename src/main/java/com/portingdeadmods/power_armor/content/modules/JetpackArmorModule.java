package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class JetpackArmorModule implements ArmorModule {
    @Override
    public Item getItem() {
        return PAItems.JETPACK_ARMOR_MODULE.get();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Jetpack");
    }

    @Override
    public ArmorItem.Type getArmorType() {
        return ArmorItem.Type.CHESTPLATE;
    }

}
