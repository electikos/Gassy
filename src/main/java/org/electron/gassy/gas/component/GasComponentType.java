package org.electron.gassy.gas.component;

import com.mojang.serialization.Codec;
import org.electron.compat.jei.GasComponentRenderer;

import java.util.Objects;
import java.util.Optional;

public class GasComponentType<T> {

    private final Codec<T> codec;
    private final GasComponentRenderer<T> renderer;

    public GasComponentType(Codec<T> codec) {
        this(codec, null);
    }
    public GasComponentType(Codec<T> codec, GasComponentRenderer<T> renderer) {
        this.codec = Objects.requireNonNull(codec);
        this.renderer = renderer;
    }

    public Codec<T> getCodec() {
        return codec;
    }

    public Optional<GasComponentRenderer<T>> getRenderer() {
        return Optional.ofNullable(this.renderer);
    }

}
