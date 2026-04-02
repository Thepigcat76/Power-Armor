package com.portingdeadmods.power_armor.datagen;

import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.datagen.assets.EMEnUsLangProvider;
import com.portingdeadmods.power_armor.datagen.assets.PAModelProvider;
import com.portingdeadmods.power_armor.datagen.data.EMBlockLootTableProvider;
import com.portingdeadmods.power_armor.datagen.data.PARecipeProvider;
import com.portingdeadmods.power_armor.datagen.data.EMTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PowerArmor.MODID)
public final class EMDataGatherer {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new PAModelProvider(packOutput));
        generator.addProvider(true, new EMEnUsLangProvider(packOutput));

        EMTagsProvider.createTagProviders(generator, packOutput, lookupProvider);
        generator.addProvider(true, new PARecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(EMBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
    }
}
