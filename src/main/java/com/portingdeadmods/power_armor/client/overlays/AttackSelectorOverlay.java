package com.portingdeadmods.power_armor.client.overlays;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.AttackType;
import com.portingdeadmods.power_armor.api.modules.AttackArmorModule;
import com.portingdeadmods.power_armor.events.ClientEvents;
import com.portingdeadmods.power_armor.registries.PAAttachments;
import com.portingdeadmods.power_armor.utils.ArmorModuleUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.gui.GuiLayer;

import java.util.List;

public class AttackSelectorOverlay implements GuiLayer {
    public static final Identifier ATTACK_SLOT = PowerArmor.id("attack_slot");
    public static final Identifier ATTACK_SLOT_HIGHLIGHTED = PowerArmor.id("attack_slot_highlighted");

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();

        int index = mc.player.getData(PAAttachments.ATTACK_TYPE);

        if (ArmorModuleUtils.hasMultipleAttacks(mc.player)) {
            List<AttackArmorModule> modules = ArmorModuleUtils.getModules(mc.player, AttackArmorModule.class).toList();

            int attackTypesHeight = (modules.size() + 1) * 24;
            int y = (guiGraphics.guiHeight() - attackTypesHeight) / 2;

            renderAttackType(guiGraphics, AttackType.VANILLA.sprite(), guiGraphics.guiWidth() - 24, y, index, 0);
            for (int i = 0; i < modules.size(); i++) {
                AttackArmorModule module = modules.get(i);
                renderAttackType(guiGraphics, module.getAttackType().sprite(), guiGraphics.guiWidth() - 24, y + (i + 1) * 24, index, i + 1);
            }

            ClientEvents.ATTACK_TYPES_AMOUNT = 1 + modules.size();
        } else {
            ClientEvents.ATTACK_TYPES_AMOUNT = 1;
        }
    }

    private void renderAttackType(GuiGraphicsExtractor guiGraphics, Identifier sprite, int x, int y, int attackTypeIndex, int i) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, attackTypeIndex == i ? ATTACK_SLOT_HIGHLIGHTED : ATTACK_SLOT, x, y, 24, 24);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x + 4, y + 4, 16, 16);
    }

}
