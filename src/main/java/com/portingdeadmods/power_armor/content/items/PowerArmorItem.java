package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.items.IEnergyItem;
import com.portingdeadmods.portingdeadlibs.utils.UniqueArray;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.data.PAComponents;
import com.portingdeadmods.power_armor.data.components.ArmorModuleComponent;
import com.portingdeadmods.power_armor.registries.PAArmorMaterials;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import com.portingdeadmods.power_armor.registries.PATranslations;
import com.portingdeadmods.power_armor.utils.ItemBarUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PowerArmorItem extends ArmorItem implements IEnergyItem {
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
        return Optional.of(new PowerArmorTooltipComponent(stack.get(PAComponents.ARMOR_MODULE)));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (entity instanceof Player player) {
            ItemStack itemBySlot = player.getItemBySlot(this.type.getSlot());
            if (itemBySlot == stack) {
                ArmorModuleComponent armorModuleComponent = stack.get(PAComponents.ARMOR_MODULE);
                for (ArmorModule module : armorModuleComponent.modulesUnsafe()) {
                    module.tick(stack, player);
                }
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(PATranslations.BATTERY_TOOLTIP.component(stack.get(PDLDataComponents.ENERGY), this.getEnergyCapacity())
                .withColor(FastColor.ARGB32.color(255, 245, 192, 89)));
        tooltip.add(Component.literal("Modules:"));
        if (!tooltipFlag.hasShiftDown()) {
            tooltip.add(Component.literal("<Press SHIFT to display>"));
        } else {
            for (int i = 0; i < 8; i++) {
                tooltip.add(Component.literal("[ ] - Empty"));
            }
        }
    }
}
