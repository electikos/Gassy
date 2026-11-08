package org.electron.gassy.gas;

import net.minecraft.resources.ResourceLocation;
import org.electron.gassy.gas.component.GasComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class Gas {
    private final int tint;
    private final ResourceLocation texture;
    private final Map<GasComponentType<?>, Object> defaultComponents;

    public Gas(int tint, ResourceLocation texture, Map<GasComponentType<?>, Object> defaultComponents) {
        this.tint = tint;
        this.texture = texture;
        this.defaultComponents = Map.copyOf(defaultComponents);
    }
    public Gas(int tint, Map<GasComponentType<?>, Object> defaultComponents) {
        this.tint = tint;
        this.texture = ResourceLocation.fromNamespaceAndPath("gassy", "default");
        this.defaultComponents = Map.copyOf(defaultComponents);
    }

    public int getTint() {
        return tint;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    @SuppressWarnings("unchecked")
    public <T> T getDefaultComponent(GasComponentType<T> type) {
        return (T) defaultComponents.get(type);
    }

    public boolean hasDefaultComponent(GasComponentType<?> type) {
        return defaultComponents.containsKey(type);
    }
    public Map<GasComponentType<?>, Object> getAllDefaultComponents(){
        return Map.copyOf(defaultComponents);
    }

    public Map<GasComponentType<?>, Object> getAllComponent(){
        return this.defaultComponents;
    }

    public Component getDescription() {
        ResourceLocation id = GasRegistry.getGasId(this);

        return Component.translatable("gas." + id.getNamespace() + "." + id.getPath());
    }
}
