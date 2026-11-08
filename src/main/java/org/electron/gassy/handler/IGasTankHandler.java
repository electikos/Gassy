package org.electron.gassy.handler;

import org.electron.gassy.gas.GasStack;

public interface IGasTankHandler {

    GasStack getGasStack();

    long getCapacity();

    boolean isEmpty();

    <T> long addR(GasStack stack, boolean simulate);
    <T> long addK(GasStack stack, boolean simulate);
    <T> long addM(GasStack stack, boolean simulate);
    <T> long addC(GasStack stack, boolean simulate);

    void drain();

}
