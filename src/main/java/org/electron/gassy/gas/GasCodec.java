package org.electron.gassy.gas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import org.electron.gassy.gas.component.GasComponentType;

public class GasCodec {
    private static final Codec<Gas> GAS_CODEC =
            ResourceLocation.CODEC.flatXmap(
                    id -> {
                        Gas gas = GasRegistry.get(id);
                        return gas != null ? DataResult.success(gas) : DataResult.error(() -> "Unknown gas: " + id);
                    },
                    gas -> {
                        ResourceLocation id = GasRegistry.getGasId(gas);
                        return id != null ? DataResult.success(id) : DataResult.error(() -> "Unregistered gas");
                    }
            );

    private static final Codec<GasComponentType<?>> COMPONENT_TYPE_CODEC =
            ResourceLocation.CODEC.flatXmap(
                    id -> {
                        GasComponentType<?> type = GasRegistry.getComponentType(id);

                        return type != null
                                ? DataResult.success(type)
                                : DataResult.error(() -> "Unknown component: " + id);
                    },
                    type -> {
                        ResourceLocation id = GasRegistry.getComponentTypeId(type);

                        return id != null
                                ? DataResult.success(id)
                                : DataResult.error(() -> "Unregistered component");
                    }
            );

    public static final Codec<Map<GasComponentType<?>, Object>> COMPONENTS_CODEC =
            Codec.dispatchedMap(COMPONENT_TYPE_CODEC, GasComponentType::getCodec);

    public static final Codec<GasStack> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    GAS_CODEC.fieldOf("gas").forGetter(GasStack::getGas),
                    Codec.LONG.fieldOf("amount").forGetter(GasStack::getAmount),
                    COMPONENTS_CODEC.fieldOf("components").forGetter(GasStack::getAllComponents)
            ).apply(instance, GasStack::new));


}
