package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.menus.CompressorMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class PAMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, PowerArmor.MODID);

    public static final Supplier<MenuType<CompressorMenu>> COMPRESSOR = MENU_TYPES.register("compressor", () -> IMenuTypeExtension.create(CompressorMenu::new));
}
