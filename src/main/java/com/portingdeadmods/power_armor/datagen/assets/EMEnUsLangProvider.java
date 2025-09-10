package com.portingdeadmods.power_armor.datagen.assets;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.registries.PATranslations;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EMEnUsLangProvider extends LanguageProvider {
    public EMEnUsLangProvider(PackOutput output) {
        super(output, PowerArmor.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        PATranslations.TRANSLATIONS.getDefaultTranslations().forEach(this::add);

        addItem(PAItems.COPPER_PLATE, "Copper Plate");
        addItem(PAItems.COPPER_WIRE, "Copper Wire");
        addItem(PAItems.IRON_PLATE, "Iron Plate");

        addBlock(PABlocks.MACHINE_FRAME, "Machine Frame");
        addBlock(PABlocks.COMPRESSOR, "Compressor");
    }

}
