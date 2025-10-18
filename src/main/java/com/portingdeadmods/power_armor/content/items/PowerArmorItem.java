package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.items.IEnergyItem;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModulesComponent;
import com.portingdeadmods.power_armor.registries.PAArmorMaterials;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import com.portingdeadmods.power_armor.registries.PATranslations;
import com.portingdeadmods.power_armor.utils.ArmorModuleUtils;
import com.portingdeadmods.power_armor.utils.ItemBarUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class PowerArmorItem extends ArmorItem implements IEnergyItem, ArmorRemoveHandler {
    public PowerArmorItem(Type type, Properties properties) {
        super(PAArmorMaterials.POWER_ARMOR.getDelegate(), type, properties);
    }

    @Override
    public int getEnergyCapacity() {
        return PowerArmorConfig.POWER_ARMOR_CAPACITY.getAsInt();
    }

    @Override
    public int getMaxTransfer() {
        return PowerArmorConfig.POWER_ARMOR_TRANSFER.getAsInt();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return ItemBarUtils.energyBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ItemBarUtils.energyBarColor(stack);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new PowerArmorTooltipComponent(stack.get(PAComponents.ARMOR_MODULES)));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (entity instanceof Player player) {
            ItemStack itemBySlot = player.getItemBySlot(this.type.getSlot());
            if (itemBySlot == stack) {
                ArmorModulesComponent armorModulesComponent = stack.get(PAComponents.ARMOR_MODULES);
                for (ArmorModule module : armorModulesComponent.modulesUnsafe()) {
                    module.tick(stack, player);
                }
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (!tooltipFlag.hasShiftDown()) {
            tooltip.add(Component.literal("<Press SHIFT for more info>").withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(PATranslations.BATTERY_TOOLTIP.component(stack.get(PDLDataComponents.ENERGY), stack.getOrDefault(PAComponents.ENERGY_CAPACITY, this.getEnergyCapacity()))
                .withColor(FastColor.ARGB32.color(255, 245, 192, 89)));
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energyStorage != null && energyStorage.getEnergyStored() > 0) {
            return stack.getOrDefault(PAComponents.DEFAULT_ATTRIBUTES, ItemAttributeModifiers.EMPTY);
        }
        return ItemAttributeModifiers.EMPTY;
    }

    public static final ResourceLocation ARMOR_TEXTURE_LAYER_1 = ResourceLocation.withDefaultNamespace("textures/models/armor/power_armor_layer_1.png");
    public static final ResourceLocation ARMOR_TEXTURE_LAYER_SOLAR = ResourceLocation.withDefaultNamespace("textures/models/armor/power_armor_layer_solar.png");
    public static final ResourceLocation ARMOR_TEXTURE_LAYER_2 = ResourceLocation.withDefaultNamespace("textures/models/armor/power_armor_layer_2.png");

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        if (!innerModel && ArmorModuleUtils.hasModule(stack, PAArmorModules.SOLAR)) {
            return ARMOR_TEXTURE_LAYER_SOLAR;
        }
        return innerModel ? ARMOR_TEXTURE_LAYER_2 : ARMOR_TEXTURE_LAYER_1;
    }

    @Override
    public void onArmorRemoved(Player player, ItemStack armorItem) {
        armorItem.get(PAComponents.ARMOR_MODULES).modulesUnsafe().forEach(m -> m.onArmorUnequipped(player, armorItem));
    }
}
