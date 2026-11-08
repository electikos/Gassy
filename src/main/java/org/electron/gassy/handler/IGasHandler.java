package org.electron.gassy.handler;

public interface IGasHandler {

    int getTanks();

    IGasTankHandler getTank(int index);

}
