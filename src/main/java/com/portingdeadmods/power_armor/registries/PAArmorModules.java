package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PARegistries;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.content.modules.JetpackArmorModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PAArmorModules {
    public static final DeferredRegister<ArmorModule> ARMOR_MODULES = DeferredRegister.create(PARegistries.ARMOR_MODULE, PowerArmor.MODID);

    public static final Supplier<ArmorModule> EMPTY = ARMOR_MODULES.register("empty", () -> new ArmorModule() {
        @Override
        public Item getItem() {
            return Items.AIR;
        }

        @Override
        public Component getDisplayName() {
            return Component.literal("Empty");
        }

        @Override
        public ArmorItem.Type getArmorType() {
            return null;
        }
    });
    public static final Supplier<JetpackArmorModule> JETPACK = ARMOR_MODULES.register("jetpack", JetpackArmorModule::new);

}
