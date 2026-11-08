package org.electron.compat.jei;

import net.minecraft.network.chat.Component;

import java.util.List;

@FunctionalInterface
public interface GasComponentRenderer<T> {
    void render(T value, List<Component> tooltip);
}
