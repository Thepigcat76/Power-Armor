package com.portingdeadmods.power_armor.datagen.assets;

import com.portingdeadmods.portingdeadlibs.utils.Utils;
import com.portingdeadmods.power_armor.PARegistries;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.api.modules.ArmorModule;
import com.portingdeadmods.power_armor.registries.PAArmorModules;
import com.portingdeadmods.power_armor.registries.PABlocks;
import com.portingdeadmods.power_armor.registries.PAItems;
import com.portingdeadmods.power_armor.registries.PATranslations;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

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

        addItem(PAItems.ARMOR_PLATING, "Armor Plating");
        addItem(PAItems.BATTERY, "Battery");

        addBlock(PABlocks.MACHINE_FRAME, "Machine Frame");
        addBlock(PABlocks.COMPRESSOR, "Compressor");
        addBlock(PABlocks.ARMOR_MODIFICATION_TABLE, "Armor Modification Table");

        addItem(PAItems.POWER_ARMOR_HELMET, "Power Armor Helmet");
        addItem(PAItems.POWER_ARMOR_CHESTPLATE, "Power Armor Chestplate");
        addItem(PAItems.POWER_ARMOR_LEGGINGS, "Power Armor Leggings");
        addItem(PAItems.POWER_ARMOR_BOOTS, "Power Armor Boots");

        addItem(PAItems.BLANK_ARMOR_MODULE, "Blank Armor Module");
        addItem(PAItems.JETPACK_ARMOR_MODULE, "Jetpack Armor Module");
        addItem(PAItems.LASER_ARMOR_MODULE, "Laser Armor Module");
        addItem(PAItems.SOLAR_ARMOR_MODULE, "Solar Armor Module");
        addItem(PAItems.PLATING_ARMOR_MODULE, "Armor Plating Module");
        addItem(PAItems.NIGHT_VISION_ARMOR_MODULE, "Night Vision Armor Module");
        addItem(PAItems.ENERGY_ARMOR_MODULE, "Energy Armor Module");
        addItem(PAItems.SPEED_ARMOR_MODULE, "Speed Armor Module");

        addArmorModule(PAArmorModules.JETPACK, "Jetpack");
        addArmorModule(PAArmorModules.LASER, "Laser");
        addArmorModule(PAArmorModules.SOLAR, "Solar");
        addArmorModule(PAArmorModules.PLATING, "Armor Plating");
        addArmorModule(PAArmorModules.NIGHT_VISION, "Night Vision");
        addArmorModule(PAArmorModules.ENERGY, "Energy");
        addArmorModule(PAArmorModules.SPEED, "Speed");

    }

    private void addArmorModule(Supplier<? extends ArmorModule> module, String translation) {
        add("armor_module.%s.%s".formatted(PowerArmor.MODID, PARegistries.ARMOR_MODULE.getKey(module.get()).getPath()), translation);
    }

}
