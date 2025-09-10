package com.portingdeadmods.power_armor.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.portingdeadlibs.api.recipes.PDLRecipe;
import com.portingdeadmods.power_armor.PowerArmor;
import com.portingdeadmods.power_armor.utils.IngredientWithCount;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public record CompressingRecipe(IngredientWithCount ingredient, int duration, ItemStack result) implements PDLRecipe<SingleRecipeInput> {
    public static final RecipeType<CompressingRecipe> TYPE = RecipeType.simple(PowerArmor.rl("compressing"));

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return ingredient.test(singleRecipeInput.getItem(0));
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    public static final class Serializer implements RecipeSerializer<CompressingRecipe> {
        public static final MapCodec<CompressingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                IngredientWithCount.CODEC.fieldOf("ingredient").forGetter(CompressingRecipe::ingredient),
                Codec.INT.fieldOf("duration").forGetter(CompressingRecipe::duration),
                ItemStack.CODEC.fieldOf("result").forGetter(CompressingRecipe::result)
        ).apply(inst, CompressingRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, CompressingRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC,
                CompressingRecipe::ingredient,
                ByteBufCodecs.INT,
                CompressingRecipe::duration,
                ItemStack.STREAM_CODEC,
                CompressingRecipe::result,
                CompressingRecipe::new
        );
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        @Override
        public MapCodec<CompressingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CompressingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
