package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PACreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, PowerArmor.MODID);

    public static final Supplier<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .icon(PAItems.IRON_PLATE::toStack)
            .title(Component.translatable("creative_tabs.power_armor.main"))
            .displayItems((params, out) -> {
                PAItems.ITEMS.getCreativeTabItems().stream().map(Supplier::get).forEach(out::accept);
            })
            .build());
}
