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
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class PowerArmorItem extends Item implements IEnergyItem, ArmorRemoveHandler {
    public PowerArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyCapacity() {
        return PowerArmorConfig.powerArmorEnergyCapacity;
    }

    @Override
    public int getMaxTransfer() {
        return PowerArmorConfig.powerArmorEnergyTransfer;
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
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @org.jspecify.annotations.Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, owner, slot);

        if (owner instanceof Player player) {
            Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
            ItemStack itemBySlot = player.getItemBySlot(equippable.slot());
            if (itemBySlot == stack) {
                ArmorModulesComponent armorModulesComponent = stack.get(PAComponents.ARMOR_MODULES);
                for (ArmorModule module : armorModulesComponent.modulesUnsafe()) {
                    module.tick(stack, player);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        if (!tooltipFlag.hasShiftDown()) {
            builder.accept(Component.literal("<Press SHIFT for more info>").withStyle(ChatFormatting.GRAY));
        }
        builder.accept(PATranslations.BATTERY_TOOLTIP.component(stack.get(PDLDataComponents.ENERGY), stack.getOrDefault(PAComponents.ENERGY_CAPACITY, this.getEnergyCapacity()))
                .withColor(ARGB.color(255, 245, 192, 89)));
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        EnergyHandler energyHandler = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
        if (energyHandler != null && energyHandler.getAmountAsInt() > 0) {
            return stack.getOrDefault(PAComponents.DEFAULT_ATTRIBUTES, ItemAttributeModifiers.EMPTY);
        }
        return ItemAttributeModifiers.EMPTY;
    }

    public static final Identifier ARMOR_TEXTURE_LAYER_1 = Identifier.withDefaultNamespace("textures/models/armor/power_armor.png");
    public static final Identifier ARMOR_TEXTURE_LAYER_SOLAR = Identifier.withDefaultNamespace("textures/models/armor/power_armor_solar.json.png");
    public static final Identifier ARMOR_TEXTURE_LAYER_2 = Identifier.withDefaultNamespace("textures/models/armor/power_armor.png");

    // FIXME: Readd using data components
//    @Override
//    public @Nullable Identifier getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
//        if (!innerModel && ArmorModuleUtils.hasModule(stack, PAArmorModules.SOLAR)) {
//            return ARMOR_TEXTURE_LAYER_SOLAR;
//        }
//        return innerModel ? ARMOR_TEXTURE_LAYER_2 : ARMOR_TEXTURE_LAYER_1;
//    }

    @Override
    public void onArmorRemoved(Player player, ItemStack armorItem) {
        armorItem.get(PAComponents.ARMOR_MODULES).modulesUnsafe().forEach(m -> m.onArmorUnequipped(player, armorItem));
    }
}
