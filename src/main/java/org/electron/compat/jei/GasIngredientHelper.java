package org.electron.compat.jei;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.electron.gassy.Gassy;
import org.electron.gassy.gas.Gas;
import org.electron.gassy.gas.GasRegistry;
import org.electron.gassy.gas.GasStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.*;
import java.util.stream.Stream;

public class GasIngredientHelper implements IIngredientHelper<GasStack> {

    @Override
    public @NotNull IIngredientType<GasStack> getIngredientType() {
        return GasIngredientType.INSTANCE;
    }

    @Override
    public @NotNull String getDisplayName(GasStack stack) {
        return stack.getGas().getDescription().getString();
    }

    @Override
    public String getUniqueId(GasStack stack, @NotNull UidContext context) {
        ResourceLocation id = GasRegistry.getGasId(stack.getGas());
        return id == null ? "unknown" : id.toString();
    }

    @Override
    public @NotNull ResourceLocation getResourceLocation(GasStack gasStack) {
        return GasRegistry.getGasId(gasStack.getGas());
    }
    @Override
    public @NotNull GasStack copyIngredient(GasStack stack) {
        return stack.copy();
    }

    @Override
    public boolean isValidIngredient(GasStack stack) {
        return stack != null;
    }

    @Override
    public String getErrorInfo(@Nullable GasStack gasStack) {
        if (gasStack == null) {
            return "GasStack is null";
        }

        ResourceLocation id = GasRegistry.getGasId(gasStack.getGas());

        if (id == null) {
            return "Unregistered gas: " + gasStack.getGas();
        }

        return "Gas: " + id + ", amount: " + gasStack.getAmount();
    }

    @Override
    public Stream<ResourceLocation> getTagStream(GasStack ingredient) {
        if (ingredient == null || ingredient.getGas() == null) {
            return Stream.empty();
        }

        ResourceLocation gasId = GasRegistry.getGasId(ingredient.getGas());

        var tags = GasRegistry.getTags(ingredient.getGas());

        System.out.println("JEI tags pour {} : {}" + String.valueOf(gasId) + String.valueOf(tags));

        return tags.stream().map(TagKey::location);
    }




    @Override
    public Optional<TagKey<?>> getTagKeyEquivalent(Collection<GasStack> ingredients) {
        if (ingredients == null || ingredients.isEmpty())return Optional.empty();


        Set<Gas> gases = ingredients.stream()
                .filter(Objects::nonNull)
                .map(GasStack::getGas)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (gases.size() != ingredients.size())return Optional.empty();


        return GasRegistry.GASES.getRegistry().get().getTags()
                .filter(pair -> {
                    Set<Gas> tagGases = pair.getSecond().stream()
                            .map(holder -> holder.value())
                            .collect(Collectors.toSet());

                    return tagGases.equals(gases);
                }).<TagKey<?>>map(pair -> pair.getFirst()).findFirst();
    }


}
