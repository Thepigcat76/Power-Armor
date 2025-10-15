package com.portingdeadmods.power_armor.events;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.items.ArmorRemoveHandler;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import com.portingdeadmods.power_armor.utils.ArmorModuleUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

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

}
