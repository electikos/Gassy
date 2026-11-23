package org.electron.gassy.handler;

import org.electron.gassy.gas.GasStack;

public interface IGasTankHandler {

    GasStack getGasStack();

    long getCapacity();

    boolean isEmpty();

    boolean canInsert();

    boolean canExtract();

    GasHandlerMode getMode();

    void setMode(GasHandlerMode mode);

    long insert(GasStack stack, boolean simulate);

    GasStack extract(long amount, boolean simulate);

    void drain();

}
