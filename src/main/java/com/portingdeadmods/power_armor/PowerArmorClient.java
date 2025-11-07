package com.portingdeadmods.power_armor;

import com.portingdeadmods.power_armor.client.items.ClientPowerArmorTooltip;
import com.portingdeadmods.power_armor.client.overlays.AttackSelectorOverlay;
import com.portingdeadmods.power_armor.client.overlays.EnergyBarOverlay;
import com.portingdeadmods.power_armor.client.screens.ArmorModificationTableScreen;
import com.portingdeadmods.power_armor.client.screens.CompressorScreen;
import com.portingdeadmods.power_armor.content.items.PowerArmorTooltipComponent;
import com.portingdeadmods.power_armor.content.menus.ArmorModificationTableMenu;
import com.portingdeadmods.power_armor.registries.PAMenuTypes;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.Nullable;

@Mod(value = PowerArmor.MODID, dist = Dist.CLIENT)
public final class PowerArmorClient {
    public PowerArmorClient(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::registerMenuScreens);
        modEventBus.addListener(this::registerClientTooltips);
        modEventBus.addListener(this::registerClientOverlays);

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(PAMenuTypes.COMPRESSOR.get(), CompressorScreen::new);
        event.register(PAMenuTypes.ARMOR_MODIFICATION_TABLE.get(), ArmorModificationTableScreen::new);
    }

    private void registerClientTooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(PowerArmorTooltipComponent.class, ClientPowerArmorTooltip::new);
    }

    private void registerClientOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(PowerArmor.rl("attack_selector"), new AttackSelectorOverlay());
        event.registerAboveAll(PowerArmor.rl("energy_bar"), new EnergyBarOverlay());
    }

}
