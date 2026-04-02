//package com.portingdeadmods.power_armor.client.screens.widgets;
//
//import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
//import com.portingdeadmods.portingdeadlibs.api.capabilities.EnergyStorageWrapper;
//import com.portingdeadmods.portingdeadlibs.api.capabilities.NeoEnergyStorageWrapper;
//import com.portingdeadmods.power_armor.PowerArmor;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.client.gui.components.AbstractWidget;
//import net.minecraft.client.gui.narration.NarrationElementOutput;
//import net.minecraft.network.chat.CommonComponents;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.Identifier;
//
//public class PAEnergyBarWidget extends AbstractWidget {
//    private static final Identifier ENERGY_BAR_BORDER = PowerArmor.id("energy_bar_vertical_border");
//    private static final Identifier ENERGY_BAR_EMPTY_BORDER = PowerArmor.id("energy_bar_vertical_empty_border");
//
//    private final boolean hasBorder;
//    private final EnergyStorageWrapper wrapper;
//    private final String energyUnit;
//
//    public PAEnergyBarWidget(int x, int y, EnergyStorageWrapper wrapper, String energyUnit, boolean hasBorder) {
//        super(x, y, 12, 110, CommonComponents.EMPTY);
//        this.hasBorder = hasBorder;
//        this.wrapper = wrapper;
//        this.energyUnit = energyUnit;
//    }
//
//    public PAEnergyBarWidget(int x, int y, ContainerBlockEntity blockEntity, boolean hasBorder) {
//        this(x, y, new NeoEnergyStorageWrapper(blockEntity.getEnergyStorage()), "FE", hasBorder);
//    }
//
//    @Override
//    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
//        guiGraphics.blitSprite(ENERGY_BAR_EMPTY_BORDER, width, height, 0, 0, getX(), getY(), width, height);
//
//        int energyStored = wrapper.getEnergyStored();
//        int maxStored = wrapper.getEnergyCapacity();
//
//        int progress = (int) (height * ((float) energyStored / maxStored));
//        guiGraphics.blitSprite(ENERGY_BAR_BORDER, width, height, 0, height - progress, getX(), getY() + height - progress, width, progress);
//
//        if (isHovered()) {
//            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(wrapper.getEnergyStored() + "/" + wrapper.getEnergyCapacity() + energyUnit), mouseX, mouseY);
//        }
//
//    }
//
//    @Override
//    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
//
//    }
//}
