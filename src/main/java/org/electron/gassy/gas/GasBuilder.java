package org.electron.gassy.gas;

import net.minecraft.resources.ResourceLocation;
import org.electron.gassy.gas.component.GasComponentType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GasBuilder {

    private int tint = 0xFFFFFFFF;
    private ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("gassy", "default");
    private final Map<GasComponentType<?>, Object> defaultComponents = new HashMap<>();

    public GasBuilder tint(int tint) {
        this.tint = tint;
        return this;
    }

    public GasBuilder texture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public <T> GasBuilder defaultComponent(GasComponentType<T> type, T value) {
        defaultComponents.put(
                Objects.requireNonNull(type),
                Objects.requireNonNull(value)
        );
        return this;
    }

    public Gas build() {

        return new Gas(tint, texture, defaultComponents);
    }
}
