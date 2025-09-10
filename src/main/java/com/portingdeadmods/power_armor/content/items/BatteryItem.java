package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.registries.PATranslations;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.items.IEnergyItem;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BatteryItem extends Item implements IEnergyItem {
    public BatteryItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyCapacity() {
        return PowerArmorConfig.BATTERY_CAPACITY.getAsInt();
    }

    @Override
    public int getMaxTransfer() {
        return PowerArmorConfig.BATTERY_TRANSFER.getAsInt();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(PATranslations.BATTERY_TOOLTIP.component(stack.get(PDLDataComponents.ENERGY), this.getEnergyCapacity())
                .withColor(FastColor.ARGB32.color(255, 245, 192, 89)));
    }
}
