package com.portingdeadmods.power_armor;

import com.portingdeadmods.power_armor.registries.*;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(PowerArmor.MODID)
public final class PowerArmor {
    public static final String MODID = "power_armor";
    public static final String MODNAME = "Power Armor";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PowerArmor(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerRegistries);

        PAItems.ITEMS.register(modEventBus);
        PABlocks.BLOCKS.register(modEventBus);
        PATranslations.TRANSLATIONS.register(modEventBus);
        PACreativeTabs.TABS.register(modEventBus);
        PABlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        PAMenuTypes.MENU_TYPES.register(modEventBus);
        PARecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        PAArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        PAArmorModules.ARMOR_MODULES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, PowerArmorConfig.SPEC);
    }

    private void registerRegistries(NewRegistryEvent event) {
        event.register(PARegistries.ARMOR_MODULE);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.BATTERY_CAPACITY, PowerArmorConfig.BATTERY_TRANSFER),
                PAItems.BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_CAPACITY, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_HELMET.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_CAPACITY, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_CHESTPLATE.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_CAPACITY, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_LEGGINGS.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_CAPACITY, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_BOOTS.get());

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, PABlockEntityTypes.COMPRESSOR.get(), (be, ctx) -> be.getEnergyStorage());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PABlockEntityTypes.COMPRESSOR.get(), (be, ctx) -> be.getItemHandler());

    }

    private static @NotNull ComponentEnergyStorage getComponentEnergyStorage(ItemStack stack, ModConfigSpec.IntValue capacity, ModConfigSpec.IntValue transfer) {
        return new ComponentEnergyStorage(stack, PDLDataComponents.ENERGY.get(), capacity.getAsInt(), transfer.getAsInt());
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
