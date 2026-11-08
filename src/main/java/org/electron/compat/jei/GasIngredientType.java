package org.electron.compat.jei;

import mezz.jei.api.ingredients.IIngredientType;
import org.electron.gassy.gas.GasStack;

public class GasIngredientType implements IIngredientType<GasStack> {

    public static final GasIngredientType INSTANCE = new GasIngredientType();

    private GasIngredientType() {}

    @Override
    public Class<? extends GasStack> getIngredientClass() {
        return GasStack.class;
    }
}
