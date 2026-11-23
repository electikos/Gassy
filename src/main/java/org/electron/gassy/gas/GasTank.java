package org.electron.gassy.gas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.electron.gassy.gas.component.GasComponentBehaviour;
import org.electron.gassy.gas.component.GasComponentType;
import org.electron.gassy.handler.GasHandlerMode;
import org.electron.gassy.handler.IGasTankHandler;

import java.util.Map;

public class GasTank implements IGasTankHandler {

    private GasStack stack;
    private final long capacity;
    private GasHandlerMode mode;

    public GasTank(long capacity, GasStack stack, GasHandlerMode mode) {
        this.capacity = capacity;
        GasStack maybe = stack.copy();
        maybe.setAmount(Math.min(maybe.getAmount(), capacity));
        this.stack = maybe.copy();
        this.mode = mode;
    }

    @Override
    public GasStack getGasStack() {
        return this.stack.copy();
    }

    @Override
    public long getCapacity() {
        return this.capacity;
    }

    @Override
    public boolean isEmpty() {
        return this.stack.getAmount() <= 0;
    }

    public long getAmount(){
        return this.stack.getAmount();
    }

    public void setStack(GasStack stack){
        GasStack maybe = stack.copy();
        maybe.setAmount(Math.min(maybe.getAmount(), capacity));
        this.stack = maybe.copy();
    }

    public long leftCapacity(){
        long nAmount = capacity - getAmount();
        if (nAmount < 0){
            return 0;
        }
        return  nAmount;
    }

    @Override
    public long insert(GasStack stack, boolean simulate){
        if(simulate){
            if (canInsert()) {
                if (stack.getAmount() <= leftCapacity()) {
                    if (this.stack.isSameGas(stack)) return Math.min(stack.getAmount(), leftCapacity());
                } else {
                    return 0;
                }
            }
            return 0;
        }
        else{
            if (canInsert()) {
                if (stack.getAmount() <= leftCapacity() && !this.stack.isEmpty()) {
                    if (this.stack.isSameGas(stack)) {
                        long accepted = Math.min(stack.getAmount(), leftCapacity());
                        for (Map.Entry<GasComponentType<?>, Object> entry : stack.getAllComponents().entrySet()) {

                            GasComponentType<?> type = entry.getKey();
                            Object incoming = entry.getValue();
                            Object current = this.stack.getAllComponents().get(type);

                            mergeAndSet(type, current, incoming, this.stack.getAmount(), stack.getAmount());

                        }

                        this.stack.grow(accepted);

                        return accepted;
                    }
                } else if (this.stack.isEmpty()) {
                    long accepted = Math.min(leftCapacity(), stack.getAmount());
                    this.stack = stack.copy();
                    this.stack.setAmount(accepted);
                    return accepted;
                } else {
                    return 0;
                }
            }
            return 0;
        }

    }

    @Override
    public GasStack extract(long amount,boolean simulate){
        if(simulate){
            if (canExtract()) {
                GasStack newStack = this.stack.copy();
                newStack.setAmount(Math.min(this.stack.getAmount(), amount));
                return newStack;
            }
            GasStack newStack = this.stack.copy();
            newStack.setAmount(0);
            return newStack;

        }
        else{
            if (canExtract()) {
                if (this.stack.getAmount() >= amount) {
                    GasStack newStack = this.stack.copy();
                    for (Map.Entry<GasComponentType<?>, Object> entry : this.stack.getAllComponents().entrySet()) {

                        GasComponentType<?> type = entry.getKey();
                        Object incoming = entry.getValue();
                        Object current = this.stack.getAllComponents().get(type);

                        extractedAndSet(newStack, type, current, incoming, this.stack.getAmount(), amount);
                        extractAndSet(type, current, incoming, this.stack.getAmount(), amount);

                    }

                    this.stack.shrink(amount);
                    newStack.setAmount(amount);

                    return newStack;
                } else if (this.stack.getAmount() < amount && this.stack.getAmount() > 0) {
                    GasStack newStack = this.stack.copy();
                    for (Map.Entry<GasComponentType<?>, Object> entry : this.stack.getAllComponents().entrySet()) {

                        GasComponentType<?> type = entry.getKey();
                        Object incoming = entry.getValue();
                        Object current = this.stack.getAllComponents().get(type);

                        extractedAndSet(newStack, type, current, incoming, this.stack.getAmount(), amount);
                        extractAndSet(type, current, incoming, this.stack.getAmount(), amount);

                    }
                    newStack.setAmount(amount - this.stack.getAmount());
                    this.stack.setAmount(GasStack.EMPTY);
                    return newStack;
                } else {
                    GasStack newStack = this.stack.copy();
                    newStack.setAmount(0);
                    return newStack;
                }
            }
            GasStack newStack = this.stack.copy();
            newStack.setAmount(0);
            return newStack;
        }
    }


    private <T> void mergeAndSet(GasComponentType<T> type, Object current, Object incoming, long currentAmount, long incomingAmount) {
        T newValue = type.getBehaviour().merge((T) current, (T) incoming, currentAmount, incomingAmount);
        this.stack.setComponent(type, newValue);
    }

    private <T> void extractAndSet(GasComponentType<T> type, Object current, Object incoming, long currentAmount, long incomingAmount) {
        T newValue = type.getBehaviour().extractFromVolume((T) current, (T) incoming, currentAmount, incomingAmount);
        this.stack.setComponent(type, newValue);
    }

    private <T> void extractedAndSet(GasStack stack,GasComponentType<T> type, Object current, Object incoming, long currentAmount, long incomingAmount) {
        T newValue = type.getBehaviour().extractedFromVolume((T) current, (T) incoming, currentAmount, incomingAmount);
        stack.setComponent(type, newValue);
    }

    @Override
    public boolean canInsert(){
        return this.mode == GasHandlerMode.INPUT || this.mode == GasHandlerMode.BOTH;
    }

    @Override
    public boolean canExtract(){
        return this.mode == GasHandlerMode.OUTPUT || this.mode == GasHandlerMode.BOTH;
    }

    @Override
    public GasHandlerMode getMode(){
        return this.mode;
    }

    @Override
    public void setMode(GasHandlerMode mode) {
        this.mode = mode;
    }


    @Override
    public void drain() {
        this.stack.setAmount(GasStack.EMPTY);
    }


    private <T> void setFloat(GasComponentType<T> type, Float value) {
        setComponentUnchecked(type, value);
    }

    private <T> void setDouble(GasComponentType<T> type, Double value) {
        setComponentUnchecked(type, value);
    }

    private <T> void setInt(GasComponentType<T> type, Integer value) {
        setComponentUnchecked(type, value);
    }

    private <T> void setLong(GasComponentType<T> type, Long value) {
        setComponentUnchecked(type, value);
    }

    @SuppressWarnings("unchecked")
    private <T> void setComponentUnchecked(GasComponentType<?> type, Object value) {
        this.stack.setComponent((GasComponentType<Object>) type, value);
    }


    public static final Codec<GasTank> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.LONG.fieldOf("capacity").forGetter(GasTank::getCapacity),
                    GasCodec.CODEC.fieldOf("stack").forGetter(GasTank::getGasStack),
                    GasHandlerMode.CODEC.fieldOf("mode").forGetter(GasTank::getMode)
            ).apply(instance, GasTank::new));
}
