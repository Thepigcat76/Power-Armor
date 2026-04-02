package com.portingdeadmods.power_armor.content.items;

import com.portingdeadmods.power_armor.PowerArmorConfig;
import com.portingdeadmods.power_armor.registries.PATranslations;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.items.IEnergyItem;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class BatteryItem extends Item implements IEnergyItem {
    public static final int STAGES = 6;

    public BatteryItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyCapacity() {
        return PowerArmorConfig.batteryEnergyCapacity;
    }

    @Override
    public int getMaxTransfer() {
        return PowerArmorConfig.batteryEnergyTransfer;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);

        builder.accept(PATranslations.BATTERY_TOOLTIP.component(stack.get(PDLDataComponents.ENERGY), this.getEnergyCapacity())
                .withColor(ARGB.color(255, 245, 192, 89)));
    }

}
