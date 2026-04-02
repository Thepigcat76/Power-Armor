package com.portingdeadmods.power_armor.content.modules;

import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAArmorMaterials;
import com.portingdeadmods.power_armor.registries.PAItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class SolarArmorModule implements ArmorModule {

    public static final Set<EquipmentSlot> SLOTS = Set.of(EquipmentSlot.HEAD);

    @Override
    public Item getItem() {
        return PAItems.SOLAR_ARMOR_MODULE.get();
    }

    @Override
    public @Nullable Set<EquipmentSlot> getArmorSlots() {
        return SLOTS;
    }

    public int getEnergyProduction(ItemStack stack) {
        return PowerArmorConfig.powerArmorSolarModuleEnergyProduction;
    }

    @Override
    public void onAdded(ItemStack armorItem, TransactionContext transaction) {
        ArmorModule.super.onAdded(armorItem, transaction);

        Equippable equippable = armorItem.get(DataComponents.EQUIPPABLE);
        if (equippable != null) {
            armorItem.set(DataComponents.EQUIPPABLE, new Equippable(
                    equippable.slot(),
                    equippable.equipSound(),
                    Optional.of(PAArmorMaterials.POWER_ARMOR_SOLAR_ASSET),
                    equippable.cameraOverlay(),
                    equippable.allowedEntities(),
                    equippable.dispensable(),
                    equippable.swappable(),
                    equippable.damageOnHurt(),
                    equippable.equipOnInteract(),
                    equippable.canBeSheared(),
                    equippable.shearingSound()
            ));
        }
    }

    @Override
    public void onRemoved(ItemStack armorItem, TransactionContext transaction) {
        ArmorModule.super.onRemoved(armorItem, transaction);

        Equippable equippable = armorItem.get(DataComponents.EQUIPPABLE);
        if (equippable != null) {
            armorItem.set(DataComponents.EQUIPPABLE, new Equippable(
                    equippable.slot(),
                    equippable.equipSound(),
                    Optional.of(PAArmorMaterials.POWER_ARMOR_ASSET),
                    equippable.cameraOverlay(),
                    equippable.allowedEntities(),
                    equippable.dispensable(),
                    equippable.swappable(),
                    equippable.damageOnHurt(),
                    equippable.equipOnInteract(),
                    equippable.canBeSheared(),
                    equippable.shearingSound()
            ));
        }
    }

    @Override
    public void tick(ItemStack armorItem, Player player) {
        if (!this.isActive(armorItem)) return;

        Level level = player.level();
        if (!level.isDarkOutside() && !level.isClientSide()) {
            for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
                ItemStack stack = player.getItemBySlot(slot);
                EnergyHandler energyHandler = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(armorItem));
                if (energyHandler != null) {
                    try (Transaction tx = Transaction.openRoot()) {
                        energyHandler.insert(this.getEnergyProduction(armorItem), tx);
                        tx.commit();
                    }
                }
            }
        }

    }

}
