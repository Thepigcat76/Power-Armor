package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class EnergyArmorModule implements ArmorModule {
    public static final Set<ArmorItem.Type> TYPES = Set.of(ArmorItem.Type.values());

    @Override
    public Item getItem() {
        return PAItems.ENERGY_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<ArmorItem.Type> getArmorTypes() {
        return TYPES;
    }
}
