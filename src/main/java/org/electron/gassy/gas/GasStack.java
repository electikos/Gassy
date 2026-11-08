package org.electron.gassy.gas;

import net.minecraft.resources.ResourceLocation;
import org.electron.gassy.gas.component.GasComponentType;

import java.util.HashMap;
import java.util.Map;

public class GasStack {
    private final Gas gas;
    private long amount;
    private final Map<GasComponentType<?>, Object> components;

    public GasStack(Gas gas, long amount) {
        this.gas = gas;
        this.amount = Math.max(0, amount);
        this.components = new HashMap<>();
    }
    public GasStack(Gas gas, long amount, Map<GasComponentType<?>, Object> components) {
        this(gas, amount);
        this.components.putAll(components);
    }

    public Gas getGas() {
        return this.gas;
    }

    public long getAmount() {
        return this.amount;
    }

    public boolean isEmpty() {
        return this.amount <= 0;
    }

    public void setAmount(long amount) {
        this.amount = Math.max(0, amount);
    }

    public void grow(long amount) {
        if (amount <= 0 || amount > Long.MAX_VALUE - this.amount) {
            return;
        }

        this.amount += amount;
    }

    public void shrink(long amount) {
        if (amount > 0) {
            this.amount = Math.max(0, this.amount - amount);
        }
    }

    public void affect(long amount){
        if (this.amount - amount < 0 || amount > Long.MAX_VALUE - this.amount || this.amount < -amount){
            return;
        }
        this.amount += amount;
    }

    public <T> void setComponent(GasComponentType<T> type, T component) {
        this.components.put(type, component);
    }


    @SuppressWarnings("unchecked")
    public <T> T getComponent(GasComponentType<T> type) {
        if (this.components.containsKey(type)) {
            return (T) this.components.get(type);
        }

        return this.gas.getDefaultComponent(type);
    }


    public void removeComponent(GasComponentType<?> type) {
        this.components.remove(type);
    }


    public boolean hasComponent(GasComponentType<?> type) {
        return this.components.containsKey(type) || this.gas.hasDefaultComponent(type);
    }

    @SuppressWarnings("unchecked")
    public  <T> T getComponent(ResourceLocation id) {
        GasComponentType<?> type = GasRegistry.getComponentType(id);
        if (type == null) {
            return null;
        }
        return getComponentUnchecked(type);
    }

    @SuppressWarnings("unchecked")
    private <T> T getComponentUnchecked(GasComponentType<?> type) {
        if (components.containsKey(type)) {
            return (T) components.get(type);
        }
        return (T) this.gas.getDefaultComponent(type);
    }


        public Map<GasComponentType<?>, Object> getAllComponents() {
            Map<GasComponentType<?>, Object> allComponents =
                    new HashMap<>(gas.getAllDefaultComponents());

            allComponents.putAll(components);

            return allComponents;
        }


    public GasStack copy() {
        GasStack nGasStack = new GasStack(this.gas, this.amount);
        nGasStack.components.putAll(this.components);
        return nGasStack;
    }


    public boolean isSameGas(GasStack other) {
        return other != null && this.gas == other.gas;
    }

    public static long EMPTY = 0;

}

