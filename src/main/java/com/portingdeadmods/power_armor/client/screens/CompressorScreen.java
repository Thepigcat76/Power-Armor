package com.portingdeadmods.power_armor.client.screens;

import com.portingdeadmods.portingdeadlibs.client.screens.widgets.EnergyBarWidget;
import com.portingdeadmods.portingdeadlibs.client.screens.widgets.RedstonePanelWidget;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.content.blockentities.CompressorBlockEntity;
import com.portingdeadmods.power_armor.content.menus.CompressorMenu;
import com.portingdeadmods.portingdeadlibs.api.client.screens.PanelContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CompressorScreen extends PanelContainerScreen<CompressorMenu> {
    private static final Identifier BACKGROUND_TEXTURE = PowerArmor.id("textures/gui/compressor.png");
    public static final Identifier PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/burn_progress");

    public CompressorScreen(CompressorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableOnly(EnergyBarWidget.forgeEnergy(this.leftPos + 11, this.topPos + 17, menu.blockEntity, true));
        addPanelWidget(new RedstonePanelWidget(this.leftPos + this.imageWidth, this.topPos + 2));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        CompressorBlockEntity be = this.menu.getBlockEntity();
        if (be.getMaxProgress() != 0) {
            float progress = (float) be.getProgress() / be.getMaxProgress();
            int width = (int) (24 * progress);
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_SPRITE, 24, 16, 0, 0, this.leftPos + 79, this.topPos + 34, width, 16);
        }
    }

    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.getBackgroundTexture(), this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    public @NotNull Identifier getBackgroundTexture() {
        return BACKGROUND_TEXTURE;
    }

}
