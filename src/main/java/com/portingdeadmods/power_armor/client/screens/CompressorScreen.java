package com.portingdeadmods.power_armor.client.screens;

import com.portingdeadmods.portingdeadlibs.api.capabilities.NeoEnergyStorageWrapper;
import com.portingdeadmods.portingdeadlibs.client.screens.widgets.EnergyBarWidget;
import com.portingdeadmods.portingdeadlibs.client.screens.widgets.RedstonePanelWidget;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.blockentities.CompressorBlockEntity;
import com.portingdeadmods.power_armor.content.menus.CompressorMenu;
import com.portingdeadmods.portingdeadlibs.api.client.screens.PanelContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CompressorScreen extends PanelContainerScreen<CompressorMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = PowerArmor.rl("textures/gui/compressor.png");
    public static final ResourceLocation PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");

    public CompressorScreen(CompressorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }


    @Override
    protected void init() {
        super.init();

        addRenderableOnly(new EnergyBarWidget(this.leftPos + 11, this.topPos + 17, menu.blockEntity, true));
        addPanelWidget(new RedstonePanelWidget(this.leftPos + this.imageWidth, this.topPos + 2));
    }


    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        CompressorBlockEntity be = this.menu.getBlockEntity();
        if (be.getMaxProgress() != 0) {
            float progress = (float) be.getProgress() / be.getMaxProgress();
            int width = (int) (24 * progress);
            pGuiGraphics.blitSprite(PROGRESS_SPRITE, 24, 16, 0, 0, this.leftPos + 79, this.topPos + 34, width, 16);
        }

    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return BACKGROUND_TEXTURE;
    }

}
