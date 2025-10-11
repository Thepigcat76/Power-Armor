package com.portingdeadmods.power_armor.compat;

import com.portingdeadmods.portingdeadlibs.api.client.screens.PDLAbstractContainerScreen;
import com.portingdeadmods.power_armor.client.screens.ArmorModificationTableScreen;
import com.portingdeadmods.power_armor.client.screens.CompressorScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

public class JeiWidgetBounds implements IGuiContainerHandler<PDLAbstractContainerScreen<?>> {
    @Override
    public List<Rect2i> getGuiExtraAreas(PDLAbstractContainerScreen<?> containerScreen) {
        return switch (containerScreen) {
            case CompressorScreen screen -> screen.getBounds();
            case ArmorModificationTableScreen screen -> screen.getBounds();
            default -> IGuiContainerHandler.super.getGuiExtraAreas(containerScreen);
        };
    }
}