package com.portingdeadmods.power_armor;

import com.portingdeadmods.power_armor.client.screens.CompressorScreen;
import com.portingdeadmods.power_armor.registries.PAMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = PowerArmor.MODID, dist = Dist.CLIENT)
public final class PowerArmorClient {
    public PowerArmorClient(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::registerMenuScreens);

        container.registerConfig(ModConfig.Type.CLIENT, PowerArmorClientConfig.SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(PAMenuTypes.COMPRESSOR.get(), CompressorScreen::new);
    }
}
