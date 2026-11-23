package org.electron.gassy.gas.component;

import com.mojang.serialization.Codec;
import org.electron.compat.jei.GasComponentRenderer;

import java.util.Objects;
import java.util.Optional;

public class GasComponentType<T> {

    private final Codec<T> codec;
    private final GasComponentRenderer<T> renderer;
    private final GasComponentBehaviour behaviour;

    public GasComponentType(Codec<T> codec) {
        this(codec, null, null);
    }
    public GasComponentType(Codec<T> codec, GasComponentRenderer<T> renderer, GasComponentBehaviour behave) {
        this.codec = Objects.requireNonNull(codec);
        this.renderer = renderer;
        this.behaviour = behave;
    }

    public Codec<T> getCodec() {
        return codec;
    }

    public Optional<GasComponentRenderer<T>> getRenderer() {
        return Optional.ofNullable(this.renderer);
    }

    public GasComponentBehaviour getBehaviour(){
        return this.behaviour;
    }
}
