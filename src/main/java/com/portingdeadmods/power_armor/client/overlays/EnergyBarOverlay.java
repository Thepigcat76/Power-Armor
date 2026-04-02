package com.portingdeadmods.power_armor.client.overlays;

import com.portingdeadmods.portingdeadlibs.api.wrappers.EnergyHandlerWrapper;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.utils.ArmorUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.gui.GuiLayer;

public class EnergyBarOverlay implements GuiLayer {
    public static final Identifier ENERGY_BAR_SPRITE = PowerArmor.id("energy_bar");
    public static final Identifier ENERGY_BAR_EMPTY_SPRITE = PowerArmor.id("energy_bar_empty");

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        int width = 80;
        int height = 12;

        int x = guiGraphics.guiWidth() - width - 8;
        int y = guiGraphics.guiHeight() - height - 4;

        EnergyHandlerWrapper wrapper = ArmorUtils.getEnergy(Minecraft.getInstance().player);
        int energyStored = wrapper.getEnergyStored();
        int maxStored = wrapper.getEnergyCapacity();

        if (maxStored != 0) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENERGY_BAR_EMPTY_SPRITE, width, height, 0, 0, x, y, width, height);
            int progress = (int) ((float) width * ((float) energyStored / (float) maxStored));
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENERGY_BAR_SPRITE, width, height, width - progress, 0, x + width - progress, y, progress, height);

            String text = "%.0f%%".formatted(((float) energyStored / maxStored) * 100f);
            guiGraphics.text(Minecraft.getInstance().font, text, x - Minecraft.getInstance().font.width(text) - 2, y + 2, -1);
        }
    }
}
