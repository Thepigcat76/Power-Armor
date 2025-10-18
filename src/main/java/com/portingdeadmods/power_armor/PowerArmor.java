package com.portingdeadmods.power_armor;

import com.portingdeadmods.power_armor.capabilities.PAComponentEnergyStorage;
import com.portingdeadmods.power_armor.client.InputHandler;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.networking.ArmorWidgetOpenClosePayload;
import com.portingdeadmods.power_armor.networking.ArmorWidgetSetSlotPositionsPayload;
import com.portingdeadmods.power_armor.networking.SetAttackTypePayload;
import com.portingdeadmods.power_armor.networking.UpdateInputPayload;
import com.portingdeadmods.power_armor.registries.*;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
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

        PAAttachments.ATTACHMENTS.register(modEventBus);
        PAArmorModules.ARMOR_MODULES.register(modEventBus);
        PAItems.ITEMS.register(modEventBus);
        PABlocks.BLOCKS.register(modEventBus);
        PAComponents.COMPONENTS.register(modEventBus);
        PATranslations.TRANSLATIONS.register(modEventBus);
        PACreativeTabs.TABS.register(modEventBus);
        PABlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        PAMenuTypes.MENU_TYPES.register(modEventBus);
        PARecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        PAArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        PASoundEvents.SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(new InputHandler());

        modContainer.registerConfig(ModConfig.Type.COMMON, PowerArmorConfig.SPEC);
    }

    private void registerRegistries(NewRegistryEvent event) {
        event.register(PARegistries.ARMOR_MODULE);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID);

        registrar.playToServer(UpdateInputPayload.TYPE, UpdateInputPayload.STREAM_CODEC, UpdateInputPayload::handle);
        registrar.playToServer(ArmorWidgetOpenClosePayload.TYPE, ArmorWidgetOpenClosePayload.STREAM_CODEC, ArmorWidgetOpenClosePayload::handle);
        registrar.playToServer(ArmorWidgetSetSlotPositionsPayload.TYPE, ArmorWidgetSetSlotPositionsPayload.STREAM_CODEC, ArmorWidgetSetSlotPositionsPayload::handle);
        registrar.playToServer(SetAttackTypePayload.TYPE, SetAttackTypePayload.STREAM_CODEC, SetAttackTypePayload::handle);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.BATTERY_CAPACITY, PowerArmorConfig.BATTERY_TRANSFER),
                PAItems.BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_HELMET.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_CHESTPLATE.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_LEGGINGS.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(stack, PowerArmorConfig.POWER_ARMOR_TRANSFER),
                PAItems.POWER_ARMOR_BOOTS.get());

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, PABlockEntityTypes.COMPRESSOR.get(), (be, ctx) -> be.getEnergyStorage());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PABlockEntityTypes.COMPRESSOR.get(), (be, ctx) -> be.getItemHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, PABlockEntityTypes.ARMOR_MODIFICATION_TABLE.get(), (be, ctx) -> be.getItemHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, PABlockEntityTypes.ARMOR_MODIFICATION_TABLE.get(), (be, ctx) -> be.getEnergyStorage());

    }

    private static @NotNull ComponentEnergyStorage getComponentEnergyStorage(ItemStack stack, ModConfigSpec.IntValue capacity, ModConfigSpec.IntValue transfer) {
        return new PAComponentEnergyStorage(stack, PDLDataComponents.ENERGY.get(), capacity.getAsInt(), transfer.getAsInt());
    }

    private static @NotNull ComponentEnergyStorage getComponentEnergyStorage(ItemStack stack, ModConfigSpec.IntValue transfer) {
        return new PAComponentEnergyStorage(stack, PDLDataComponents.ENERGY.get(), stack.get(PAComponents.ENERGY_CAPACITY.get()), transfer.getAsInt());
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
