package com.portingdeadmods.power_armor.compat;

import com.portingdeadmods.portingdeadlibs.api.client.screens.PDLAbstractContainerScreen;
import com.portingdeadmods.power_armor.client.screens.CompressorScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

public class JeiWidgetBounds implements IGuiContainerHandler<PDLAbstractContainerScreen<?>> {
    @Override
    public List<Rect2i> getGuiExtraAreas(PDLAbstractContainerScreen<?> containerScreen) {
        if (containerScreen instanceof CompressorScreen screen) {
            return screen.getBounds();
        }
        return IGuiContainerHandler.super.getGuiExtraAreas(containerScreen);
    }
}