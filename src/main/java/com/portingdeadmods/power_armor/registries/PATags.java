package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public final class PATags {
    public static final class ItemTags {
        public static final TagKey<Item> WIRES = create("wires");
        public static final TagKey<Item> PLATES = create("plates");

        public static final TagKey<Item> WIRES_COPPER = create("wires/copper");
        public static final TagKey<Item> PLATES_IRON = create("plates/iron");
        public static final TagKey<Item> PLATES_COPPER = create("plates/copper");

        private static @NotNull TagKey<Item> create(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
        }

    }
}
