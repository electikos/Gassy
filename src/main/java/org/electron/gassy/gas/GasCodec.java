package org.electron.gassy.gas;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.resources.ResourceLocation;
import com.mojang.datafixers.util.Pair;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.StringRepresentable;
import org.electron.gassy.gas.component.GasComponentType;
import org.electron.gassy.handler.GasHandlerMode;

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

    private static final Codec<Map<GasComponentType<?>, Object>> DISPATCHED_COMPONENTS_CODEC =
            Codec.dispatchedMap(COMPONENT_TYPE_CODEC, GasComponentType::getCodec);

    public static final Codec<Map<GasComponentType<?>, Object>> COMPONENTS_CODEC =
            Codec.of(DISPATCHED_COMPONENTS_CODEC, GasCodec::decodeComponents);

    public static final Codec<GasStack> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    GAS_CODEC.fieldOf("gas").forGetter(GasStack::getGas),
                    Codec.LONG.fieldOf("amount").forGetter(GasStack::getAmount),
                    COMPONENTS_CODEC.fieldOf("components").forGetter(GasStack::getCustomComponents)
            ).apply(instance, GasStack::new));


    //codec are so weird I have to make comments just to read it
    private static <T> DataResult<Pair<Map<GasComponentType<?>, Object>, T>> decodeComponents(
            DynamicOps<T> ops,
            T input
    ) {
        return ops.getMap(input).flatMap(map -> {
            Map<GasComponentType<?>, Object> components = new HashMap<>();

            for (Pair<T, T> entry : map.entries().toList()) {
                DataResult<ResourceLocation> idResult =
                        ResourceLocation.CODEC.parse(ops, entry.getFirst());
                var LOGGER = org.slf4j.LoggerFactory.getLogger(GasCodec.class);

                if (idResult.result().isEmpty()) {
                    LOGGER.warn("Ignoring gas component with invalid identifier");
                    continue;
                }

                ResourceLocation id = idResult.result().get();
                GasComponentType<?> type = GasRegistry.getComponentType(id);

                if (type == null) {
                    LOGGER.warn("Ignoring unknown gas component: {}", id);
                    continue;
                }

                DataResult<Object> componentResult =
                        decodeComponent(type, ops, entry.getSecond());

                if (componentResult.result().isEmpty()) {
                    LOGGER.warn("Ignoring gas component with invalid data: {}", id);
                    continue;
                }

                components.put(type, componentResult.result().get());
            }

            return DataResult.success(Pair.of(components, input));
        });
    }

    private static <T, V> DataResult<Object> decodeComponent(GasComponentType<V> type, com.mojang.serialization.DynamicOps<T> ops, T input) {
        return type.getCodec().parse(ops, input).map(value -> (Object) value);
    }
}
