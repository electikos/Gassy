package org.electron.gassy.gas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.electron.gassy.gas.component.GasComponentType;
import org.electron.gassy.handler.IGasTankHandler;

import java.util.Map;

public class GasTank implements IGasTankHandler {

    private GasStack stack;
    private final long capacity;

    public GasTank(long capacity, GasStack stack) {
        this.capacity = capacity;
        GasStack maybe = stack.copy();
        maybe.setAmount(Math.min(maybe.getAmount(), capacity));
        this.stack = maybe.copy();
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
    public <T> long addC(GasStack stack, boolean simulate) {
        if (!simulate && !isEmpty()) {
            if (stack.isSameGas(this.stack)) {
                for (Map.Entry<GasComponentType<?>, Object> entry : stack.getAllComponents().entrySet()) {
                    GasComponentType<?> type = entry.getKey();

                    if (this.stack.getAllComponents().containsKey(type)) {
                        Object oldValue = this.stack.getAllComponents().get(type);
                        Object newValue = entry.getValue();

                        if (oldValue instanceof Float oldFloat && newValue instanceof Float newFloat) {
                            setFloat(type, oldFloat + newFloat);
                        } else if (oldValue instanceof Double oldDouble && newValue instanceof Double newDouble) {
                            setDouble(type, oldDouble + newDouble);
                        } else if (oldValue instanceof Integer oldInt && newValue instanceof Integer newInt) {
                            setInt(type, oldInt + newInt);
                        } else if (oldValue instanceof Long oldLong && newValue instanceof Long newLong) {
                            setLong(type, oldLong + newLong);
                        }
                    }
                }

                long newAmount = Math.min(leftCapacity(), stack.getAmount());
                this.stack.affect(newAmount);
                return newAmount;
            }
            return 0;
        }
        else if(!simulate){
            GasStack maybe = stack.copy();
            maybe.setAmount(Math.min(maybe.getAmount(), capacity));
            this.stack = maybe.copy();
            return this.stack.getAmount();
        }
        else{
            return Math.min(leftCapacity(), stack.getAmount());
        }
    }

    public <T> long addR(GasStack stack, boolean simulate){
        if (!simulate && !isEmpty()) {
            if (stack.isSameGas(this.stack)) {
                for (Map.Entry<GasComponentType<?>, Object> entry : stack.getAllComponents().entrySet()) {
                    GasComponentType<?> type = entry.getKey();

                    if (this.stack.getAllComponents().containsKey(type)) {
                        Object oldValue = this.stack.getAllComponents().get(type);
                        Object newValue = entry.getValue();

                        if (oldValue instanceof Float oldFloat && newValue instanceof Float newFloat) {
                            setFloat(type, newFloat);
                        } else if (oldValue instanceof Double oldDouble && newValue instanceof Double newDouble) {
                            setDouble(type, newDouble);
                        } else if (oldValue instanceof Integer oldInt && newValue instanceof Integer newInt) {
                            setInt(type, newInt);
                        } else if (oldValue instanceof Long oldLong && newValue instanceof Long newLong) {
                            setLong(type, newLong);
                        }
                    }
                }
                long newAmount = Math.min(leftCapacity(), stack.getAmount());
                this.stack.affect(newAmount);
                return newAmount;
            }
            return 0;
        }
        else if(!simulate){
            GasStack maybe = stack.copy();
            maybe.setAmount(Math.min(maybe.getAmount(), capacity));
            this.stack = maybe.copy();
            return this.stack.getAmount();
        }
        else{
            return Math.min(leftCapacity(), stack.getAmount());
        }
    }

    @Override
    public <T> long addK(GasStack stack, boolean simulate) {
        if (!simulate && !isEmpty()) {
            if (stack.isSameGas(this.stack)){
                for (Map.Entry<GasComponentType<?>, Object> entry : stack.getAllComponents().entrySet()) {
                    GasComponentType<?> type = entry.getKey();

                    if (this.stack.getAllComponents().containsKey(type)) {
                        Object oldValue = this.stack.getAllComponents().get(type);
                        Object newValue = entry.getValue();

                        if (oldValue instanceof Float oldFloat && newValue instanceof Float newFloat) {
                            setFloat(type, oldFloat);
                        } else if (oldValue instanceof Double oldDouble && newValue instanceof Double newDouble) {
                            setDouble(type, oldDouble);
                        } else if (oldValue instanceof Integer oldInt && newValue instanceof Integer newInt) {
                            setInt(type, oldInt);
                        } else if (oldValue instanceof Long oldLong && newValue instanceof Long newLong) {
                            setLong(type, oldLong);
                        }
                    }
                }
                long newAmount = Math.min(leftCapacity(), stack.getAmount());
                this.stack.affect(newAmount);
                return newAmount;
            }
            return 0;
        }
        else if(!simulate){
            GasStack maybe = stack.copy();
            maybe.setAmount(Math.min(maybe.getAmount(), capacity));
            this.stack = maybe.copy();
            return this.stack.getAmount();
        }
        else{
            return Math.min(leftCapacity(), stack.getAmount());
        }
    }

    @Override
    public <T> long addM(GasStack stack, boolean simulate) {
        if (!simulate && !isEmpty()){
            if (stack.isSameGas(this.stack)) {
                long newAmount = Math.min(leftCapacity(), stack.getAmount());
                for (Map.Entry<GasComponentType<?>, Object> entry : stack.getAllComponents().entrySet()) {
                    GasComponentType<?> type = entry.getKey();

                    if (this.stack.getAllComponents().containsKey(type)) {
                        Object oldValue = this.stack.getAllComponents().get(type);
                        Object newValue = entry.getValue();

                        if (oldValue instanceof Float oldFloat && newValue instanceof Float newFloat) {
                            setFloat(type, ((oldFloat * (float) this.stack.getAmount()) + (newFloat * (float) newAmount)) / (float) (this.stack.getAmount() + newAmount));
                        } else if (oldValue instanceof Double oldDouble && newValue instanceof Double newDouble) {
                            setDouble(type, ((oldDouble * (double) this.stack.getAmount()) + (newDouble * (double) newAmount)) / (double) (this.stack.getAmount() + newAmount));
                        } else if (oldValue instanceof Integer oldInt && newValue instanceof Integer newInt) {
                            setInt(type, ((oldInt * (int) this.stack.getAmount()) + (newInt * (int) newAmount)) / (int) (this.stack.getAmount() + newAmount));
                        } else if (oldValue instanceof Long oldLong && newValue instanceof Long newLong) {
                            setLong(type, ((oldLong * this.stack.getAmount()) + (newLong * newAmount)) / (this.stack.getAmount() + newAmount));
                        }
                    }
                }

                this.stack.affect(newAmount);
                return newAmount;
            }
            return 0;
        }
        else if(!simulate){
            GasStack maybe = stack.copy();
            maybe.setAmount(Math.min(maybe.getAmount(), capacity));
            this.stack = maybe.copy();
            return this.stack.getAmount();
        }
        else{
            return Math.min(leftCapacity(), stack.getAmount());
        }
    }

    public long insert(long in){
        long newAmount = Math.min(leftCapacity(), in);
        this.stack.affect(newAmount);
        return newAmount;
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
                    GasCodec.CODEC.fieldOf("stack").forGetter(GasTank::getGasStack)
            ).apply(instance, GasTank::new));
}
