package com.portingdeadmods.power_armor;

import com.portingdeadmods.portingdeadlibs.api.config.PDLConfigHelper;
import com.portingdeadmods.power_armor.client.InputHandler;
import com.portingdeadmods.power_armor.client.items.Energy;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.PDLItemAccessEnergyHandler;
import com.portingdeadmods.power_armor.networking.*;
import com.portingdeadmods.power_armor.registries.*;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
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
        modEventBus.addListener(this::registerRangeSelect);

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
        PASoundEvents.SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(new InputHandler());

        PDLConfigHelper.registerConfig(PowerArmorConfig.class, ModConfig.Type.COMMON, modContainer);

    }

    private void registerRangeSelect(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(id("energy"), Energy.MAP_CODEC);
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

        registrar.playToClient(SetDeltaMovementPayload.TYPE, SetDeltaMovementPayload.STREAM_CODEC, SetDeltaMovementPayload::handle);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.Energy.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(ctx, PowerArmorConfig.batteryEnergyCapacity, PowerArmorConfig.batteryEnergyTransfer),
                PAItems.BATTERY.get());
        event.registerItem(Capabilities.Energy.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(ctx, stack, PowerArmorConfig.powerArmorEnergyTransfer),
                PAItems.POWER_ARMOR_HELMET.get());
        event.registerItem(Capabilities.Energy.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(ctx, stack, PowerArmorConfig.powerArmorEnergyTransfer),
                PAItems.POWER_ARMOR_CHESTPLATE.get());
        event.registerItem(Capabilities.Energy.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(ctx, stack, PowerArmorConfig.powerArmorEnergyTransfer),
                PAItems.POWER_ARMOR_LEGGINGS.get());
        event.registerItem(Capabilities.Energy.ITEM,
                (stack, ctx) -> getComponentEnergyStorage(ctx, stack, PowerArmorConfig.powerArmorEnergyTransfer),
                PAItems.POWER_ARMOR_BOOTS.get());

        event.registerBlockEntity(Capabilities.Energy.BLOCK, PABlockEntityTypes.COMPRESSOR.get(), (be, ctx) -> be.getExposedEnergyHandler());
        event.registerBlockEntity(Capabilities.Item.BLOCK, PABlockEntityTypes.COMPRESSOR.get(), (be, ctx) -> be.getExposedItemHandler());

        event.registerBlockEntity(Capabilities.Item.BLOCK, PABlockEntityTypes.ARMOR_MODIFICATION_TABLE.get(), (be, ctx) -> be.getItemHandler());
        event.registerBlockEntity(Capabilities.Energy.BLOCK, PABlockEntityTypes.ARMOR_MODIFICATION_TABLE.get(), (be, ctx) -> be.getExposedEnergyHandler());

    }

    private static @NotNull PDLItemAccessEnergyHandler getComponentEnergyStorage(ItemAccess access, int capacity, int transfer) {
        return new PDLItemAccessEnergyHandler(access, PDLDataComponents.ENERGY.get(), capacity, transfer);
    }

    private static @NotNull PDLItemAccessEnergyHandler getComponentEnergyStorage(ItemAccess access, ItemStack stack, int transfer) {
        return new PDLItemAccessEnergyHandler(access, PDLDataComponents.ENERGY.get(), stack.get(PAComponents.ENERGY_CAPACITY.get()), transfer);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

}
