package com.portingdeadmods.power_armor.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;

public record CompressingRecipe(SizedIngredient ingredient, int duration, ItemStackTemplate result) implements Recipe<SingleRecipeInput> {
    public static final RecipeType<CompressingRecipe> TYPE = RecipeType.simple(PowerArmor.id("compressing"));
    public static final MapCodec<CompressingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            SizedIngredient.NESTED_CODEC.fieldOf("ingredient").forGetter(CompressingRecipe::ingredient),
            Codec.INT.fieldOf("duration").forGetter(CompressingRecipe::duration),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(CompressingRecipe::result)
    ).apply(inst, CompressingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CompressingRecipe> STREAM_CODEC = StreamCodec.composite(
            SizedIngredient.STREAM_CODEC,
            CompressingRecipe::ingredient,
            ByteBufCodecs.INT,
            CompressingRecipe::duration,
            ItemStackTemplate.STREAM_CODEC,
            CompressingRecipe::result,
            CompressingRecipe::new
    );
    public static final RecipeSerializer<CompressingRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return ingredient.test(singleRecipeInput.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(SingleRecipeInput input) {
        return this.result.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "compressing";
    }

    @Override
    public RecipeSerializer<CompressingRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<CompressingRecipe> getType() {
        return TYPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(ingredient.ingredient());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

}
