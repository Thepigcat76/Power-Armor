package com.portingdeadmods.power_armor.client.overlays;

import com.portingdeadmods.portingdeadlibs.api.capabilities.EnergyStorageWrapper;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.utils.ArmorUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;

public class EnergyBarOverlay implements LayeredDraw.Layer {
    public static final ResourceLocation ENERGY_BAR_SPRITE = PowerArmor.rl("energy_bar");
    public static final ResourceLocation ENERGY_BAR_EMPTY_SPRITE = PowerArmor.rl("energy_bar_empty");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int width = 96;
        int height = 12;

        int x = guiGraphics.guiWidth() - width;
        int y = guiGraphics.guiHeight() - height - 8;

        EnergyStorageWrapper wrapper = ArmorUtils.getEnergy(Minecraft.getInstance().player);

        guiGraphics.blitSprite(ENERGY_BAR_EMPTY_SPRITE, width, height, 0, 0, x, y, width, height);
        int energyStored = wrapper.getEnergyStored();
        int maxStored = wrapper.getEnergyCapacity();
        int progress = (int)((float)width * ((float)energyStored / (float)maxStored));
        guiGraphics.blitSprite(ENERGY_BAR_SPRITE, width, height, width - progress, 0, x + width - progress, y, progress, height);

        String text = "%.0f%%".formatted(((float) energyStored / maxStored) * 100f);
        guiGraphics.drawString(Minecraft.getInstance().font, text, x - Minecraft.getInstance().font.width(text) - 1, y + 2, -1);

    }
}
