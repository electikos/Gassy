package org.electron.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;
import org.electron.gassy.Gassy;
import org.electron.gassy.gas.GasCodec;
import org.electron.gassy.gas.GasRegistry;
import org.electron.gassy.gas.GasStack;

import java.util.List;
import java.util.stream.StreamSupport;

@JeiPlugin
public class GassyJEIPlugin implements IModPlugin {

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(Gassy.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        List<GasStack> gases = StreamSupport
                .stream(GasRegistry.GASES.getRegistry().get().spliterator(), false)
                .map(gas -> new GasStack(gas, 0))
                .toList();

        registration.register(
                GasIngredientType.INSTANCE,
                gases,
                new GasIngredientHelper(),
                new GasStackRenderer(),
                GasCodec.CODEC
        );
    }
}
