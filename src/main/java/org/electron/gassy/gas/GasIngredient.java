package org.electron.gassy.gas;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class GasIngredient {

    private final Gas gas;
    private final TagKey<Gas> tag;

    private GasIngredient(Gas gas, TagKey<Gas> tag) {
        this.gas = gas;
        this.tag = tag;
    }

    public static GasIngredient of(Gas gas) {
        return new GasIngredient(gas, null);
    }

    public static GasIngredient of(TagKey<Gas> tag) {
        return new GasIngredient(null, tag);
    }

    public boolean test(GasStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }

        if (this.gas != null) {
            return stack.getGas() == this.gas;
        }

        ResourceLocation gasId = GasRegistry.getGasId(stack.getGas());

        return this.tag != null
                && gasId != null
                && GasRegistry.GASES.getRegistry().get()
                .getHolder(ResourceKey.create(GasRegistry.GAS_REGISTRY_KEY, gasId))
                .map(holder -> holder.is(this.tag))
                .orElse(false);
    }

    public Gas getDisplayGas() {
        if (this.gas != null) {
            return this.gas;
        }

        if (this.tag == null) {
            return null;
        }

        return GasRegistry.GASES.getRegistry().get()
                .getTag(this.tag)
                .flatMap(named -> named.stream().findFirst())
                .map(holder -> holder.value())
                .orElse(null);
    }
}
