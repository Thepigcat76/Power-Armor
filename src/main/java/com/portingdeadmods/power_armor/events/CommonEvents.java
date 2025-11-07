package com.portingdeadmods.power_armor.events;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.AttackType;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.client.PAKeybinds;
import com.portingdeadmods.power_armor.content.items.ArmorRemoveHandler;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import com.portingdeadmods.power_armor.utils.ArmorModuleUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = PowerArmor.MODID)
public final class CommonEvents {
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
            if (ArmorModuleUtils.hasModule(stack, PAArmorModules.JETPACK) && energyStorage != null) {
                if (energyStorage.getEnergyStored() > 0) {
                    event.setDistance(0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        ItemStack stack = event.getFrom();
        if (event.getEntity() instanceof Player player && stack.getItem() instanceof ArmorRemoveHandler armorRemoveHandler) {
            armorRemoveHandler.onArmorRemoved(player, stack);
        }
    }

    @SubscribeEvent
    public static void onPlayerAttacked(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Player player) {
            for (ItemStack stack : player.getArmorSlots()) {
                ArmorModulesComponent modules = stack.getOrDefault(PAComponents.ARMOR_MODULES, ArmorModulesComponent.EMPTY);
                for (ArmorModule armorModule : modules.modulesUnsafe()) {
                    armorModule.onPlayerAttacked(player, stack);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (PAKeybinds.LASER_ATTACK.get().isDown()) {
            AttackType type = ArmorModuleUtils.getAttackType(player);

            if (type.vanilla()) return;

            type.handle(player, player, player.swingingArm);
        }
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
//        AttackType type = ArmorModuleUtils.getAttackType(event.getEntity());
//
//        if (type.vanilla()) return;
//
//        type.handle(event.getEntity(), null, event.getHand());

    }

    @SubscribeEvent
    public static void onHit(AttackEntityEvent event) {
//        AttackType type = ArmorModuleUtils.getAttackType(event.getEntity());
//
//        if (type.vanilla()) return;
//
//        type.handle(event.getEntity(), event.getTarget(), event.getEntity().swingingArm);
//
//        event.setCanceled(true);

    }

}
