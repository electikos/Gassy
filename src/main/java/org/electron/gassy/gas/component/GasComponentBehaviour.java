package org.electron.gassy.gas.component;

public interface GasComponentBehaviour {

    <T> T merge(T current, T incoming, long currentAmount, long incomingAmount);

    <T> T extract(T current, T incoming, long currentAmount, long incomingAmount);

    <T> T extracted(T current, T incoming, long currentAmount, long incomingAmount);

    <T> T extractFromVolume(T current, T incoming,
                            long currentAmount, long incomingAmount);

    <T> T extractedFromVolume(T current, T incoming,
                              long currentAmount, long incomingAmount);
}
