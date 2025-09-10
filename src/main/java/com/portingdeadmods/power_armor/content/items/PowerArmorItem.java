package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.items.IEnergyItem;
import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.registries.PAArmorMaterials;
import com.portingdeadmods.power_armor.registries.PATranslations;
import com.portingdeadmods.power_armor.utils.ItemBarUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PowerArmorItem extends ArmorItem implements IEnergyItem {
    public PowerArmorItem(Type type, Properties properties) {
        super(BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(PAArmorMaterials.POWER_ARMOR.get()), type, properties);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(PATranslations.BATTERY_TOOLTIP.component(stack.get(PDLDataComponents.ENERGY), this.getEnergyCapacity())
                .withColor(FastColor.ARGB32.color(255, 245, 192, 89)));
    }
}
