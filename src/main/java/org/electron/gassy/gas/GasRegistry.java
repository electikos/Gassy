package org.electron.gassy.gas;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.electron.gassy.Gassy;
import org.electron.gassy.gas.component.GasComponentType;

import java.util.Collection;
import java.util.List;

public class GasRegistry {
    public static final ResourceKey<Registry<Gas>> GAS_REGISTRY_KEY =
            ResourceKey.createRegistryKey(
                    ResourceLocation.fromNamespaceAndPath(Gassy.MODID, "gas")
            );
    public static final ResourceKey<Registry<GasComponentType<?>>> GAS_COMPONENT_REGISTRY_KEY =
            ResourceKey.createRegistryKey(
                    ResourceLocation.fromNamespaceAndPath(Gassy.MODID, "gas_component")
            );

    public static final DeferredRegister<Gas> GASES =
            DeferredRegister.create(GAS_REGISTRY_KEY, Gassy.MODID);
    public static final DeferredRegister<GasComponentType<?>> GAS_COMPONENTS =
            DeferredRegister.create(GAS_COMPONENT_REGISTRY_KEY, Gassy.MODID);

    static {
        GAS_COMPONENTS.makeRegistry(builder -> builder.sync(true));
        GASES.makeRegistry(builder -> builder.sync(true));

    }

    public static GasComponentType<?> getComponentType(ResourceLocation id) {
        return GAS_COMPONENTS.getRegistry().get().get(id);
    }

    public static Gas get(ResourceLocation id){
        return GASES.getRegistry().get().get(id);
    }
    public static ResourceLocation getGasId(Gas gas) {
        return GASES.getRegistry().get().getKey(gas);
    }
    public static ResourceLocation getComponentTypeId(GasComponentType<?> type) {
        return GAS_COMPONENTS.getRegistry().get().getKey(type);
    }

    public static Collection<TagKey<Gas>> getTags(Gas gas) {
        ResourceLocation id = getGasId(gas);

        if (id == null) {
            return List.of();
        }

        return GASES.getRegistry().get()
                .getHolder(ResourceKey.create(GAS_REGISTRY_KEY, id))
                .map(holder -> holder.tags().toList())
                .orElse(List.of());
    }
}
