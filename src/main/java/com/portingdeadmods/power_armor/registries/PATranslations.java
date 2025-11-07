package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.portingdeadlibs.api.translations.*;
import com.portingdeadmods.power_armor.PowerArmor;

public final class PATranslations {
    public static final DeferredTranslationRegister TRANSLATIONS = DeferredTranslationRegister.createTranslations(PowerArmor.MODID);

    // -- Messages --
    private static final DefaultTranslationCategory MESSAGES_CATEGORY = TRANSLATIONS.createCategory("messages");

    public static final DeferredTranslation<TranslatableConstant> NICE_MESSAGE = MESSAGES_CATEGORY.registerWithDefault("nice_message", "Hello, silly :3");

    // -- Creative Tabs --
    private static final DefaultTranslationCategory CREATIVE_TAB_CATEGORY = TRANSLATIONS.createCategory("creative_tabs");

    public static final DeferredTranslation<TranslatableConstant> MAIN_TAB = CREATIVE_TAB_CATEGORY.registerWithDefault("main", PowerArmor.MODNAME);

    // -- Screens --
    private static final DefaultTranslationCategory SCREEN_CATEGORY = TRANSLATIONS.createCategory("screens");

    public static final DeferredTranslation<TranslatableConstant> COMPRESSOR_SCREEN_TITLE = SCREEN_CATEGORY.registerWithDefault("title.compressor", "Compressor");

    public static final DeferredTranslation<TranslatableConstant> ARMOR_MODIFICATION_SCREEN_TITLE = SCREEN_CATEGORY.registerWithDefault("title.armor_modification_table", "Armor Modification Table");

    // -- Jei Categories --
    private static final DefaultTranslationCategory JEI_CATEGORY = TRANSLATIONS.createCategory("jei");

    public static final DeferredTranslation<TranslatableConstant> COMPRESSING_JEI_CATEGORY = JEI_CATEGORY.registerWithDefault("category.compressing", "Compressing");

    // -- Tooltips --
    private static final DefaultTranslationCategory TOOLTIP_CATEGORY = TRANSLATIONS.createCategory("tooltips");

    public static final DeferredTranslation<TranslatableConstant> BATTERY_TOOLTIP = TOOLTIP_CATEGORY.registerWithDefault("battery", "%d/%d FE");
}
