package com.portingdeadmods.power_armor.registries;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.portingdeadlibs.api.translations.DeferredTranslation;
import com.portingdeadmods.portingdeadlibs.api.translations.DeferredTranslationRegister;
import com.portingdeadmods.portingdeadlibs.api.translations.TranslatableConstant;
import com.portingdeadmods.portingdeadlibs.api.translations.TranslationCategory;

public final class PATranslations {
    public static final DeferredTranslationRegister TRANSLATIONS = DeferredTranslationRegister.createTranslations(PowerArmor.MODID);

    // -- Messages --
    private static final TranslationCategory MESSAGES_CATEGORY = TRANSLATIONS.createCategory("messages");

    public static final DeferredTranslation<TranslatableConstant> NICE_MESSAGE = MESSAGES_CATEGORY.registerWithDefault("nice_message", "Hello, silly :3");

    // -- Creative Tabs --
    private static final TranslationCategory CREATIVE_TAB_CATEGORY = TRANSLATIONS.createCategory("creative_tabs");

    public static final DeferredTranslation<TranslatableConstant> MAIN_TAB = CREATIVE_TAB_CATEGORY.registerWithDefault("main", PowerArmor.MODNAME);

    // -- Screens --
    private static final TranslationCategory SCREEN_CATEGORY = TRANSLATIONS.createCategory("screens");

    public static final DeferredTranslation<TranslatableConstant> COMPRESSOR_SCREEN_TITLE = CREATIVE_TAB_CATEGORY.registerWithDefault("title.compressor", "Compressor");

    // -- Screens --
    private static final TranslationCategory JEI_CATEGORY = TRANSLATIONS.createCategory("jei");

    public static final DeferredTranslation<TranslatableConstant> COMPRESSING_JEI_CATEGORY = JEI_CATEGORY.registerWithDefault("category.compressing", "Compressing");

    // -- Tooltips --
    private static final TranslationCategory TOOLTIP_CATEGORY = TRANSLATIONS.createCategory("tooltips");

    public static final DeferredTranslation<TranslatableConstant> BATTERY_TOOLTIP = TOOLTIP_CATEGORY.registerWithDefault("battery", "%d/%d FE");
}
