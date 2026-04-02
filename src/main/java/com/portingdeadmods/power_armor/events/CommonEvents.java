package com.portingdeadmods.power_armor.events;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.content.items.ArmorRemoveHandler;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import com.portingdeadmods.power_armor.utils.ArmorModuleUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

@EventBusSubscriber(modid = PowerArmor.MODID)
public final class CommonEvents {
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            EnergyHandler energyHandler = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
            if (ArmorModuleUtils.hasModule(stack, PAArmorModules.JETPACK) && energyHandler != null) {
                if (energyHandler.getAmountAsInt() > 0) {
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

            for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
                ItemStack stack = player.getItemBySlot(slot);
                ArmorModulesComponent modules = stack.getOrDefault(PAComponents.ARMOR_MODULES, ArmorModulesComponent.EMPTY);
                for (ArmorModule armorModule : modules.modulesUnsafe()) {
                    armorModule.onPlayerAttacked(player, stack);
                }
            }
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
