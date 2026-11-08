package org.electron.gassy.gas.component;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.electron.gassy.gas.GasRegistry;


public class GassyGasComponent {
    public static final GasComponentType<Double> HEAT_TYPE =
            new GasComponentType<>(
                    Codec.DOUBLE,
                    (temperature, tooltip) -> tooltip.add(Component.translatable(temperature + "°k"))
            );

    public static final GasComponentType<Double> PRESSURE_TYPE =
            new GasComponentType<>(
                    Codec.DOUBLE,
                    (pressure, tooltip) -> tooltip.add(Component.translatable(pressure + " Pa"))
            );

    public static final GasComponentType<Double> RADIOACTIVITY_TYPE =
            new GasComponentType<>(
                    Codec.DOUBLE,
                    (radioactivity, tooltip) -> tooltip.add(Component.translatable(radioactivity + " mSv"))
            );

    public static final DeferredHolder<GasComponentType<?>, GasComponentType<Double>> HEAT =
            GasRegistry.GAS_COMPONENTS.register("heat", () -> HEAT_TYPE);
    public static final DeferredHolder<GasComponentType<?>, GasComponentType<Double>> PRESSURE =
            GasRegistry.GAS_COMPONENTS.register("pressure", () -> PRESSURE_TYPE);
    public static final DeferredHolder<GasComponentType<?>, GasComponentType<Double>> RADIOACTIVITY =
            GasRegistry.GAS_COMPONENTS.register("radioactivity", () -> RADIOACTIVITY_TYPE);


    public static void init() {}
}
