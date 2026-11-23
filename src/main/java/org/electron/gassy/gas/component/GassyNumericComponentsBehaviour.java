package org.electron.gassy.gas.component;

public class GassyNumericComponentsBehaviour implements GasComponentBehaviour{
    @Override
    public <T> T merge(T current, T incoming, long currentAmount, long incomingAmount) {
        if (current instanceof Number a && incoming instanceof Number b) {
            return castNumber(a.doubleValue() + b.doubleValue(), current);
        }
        throw  new IllegalArgumentException("Components must be numeric");
    }

    @Override
    public <T> T extract(T current, T incoming, long currentAmount, long incomingAmount) {
        if (current instanceof Number a && incoming instanceof Number b) {
            return castNumber(Math.max(0, a.doubleValue() - b.doubleValue()), current);
        }
        throw  new IllegalArgumentException("Components must be numeric");
    }

    public <T> T extractFromVolume(T current, T incoming, long currentAmount, long incomingAmount){
        if (current instanceof Number a && incoming instanceof Number b) {
            if (currentAmount >= incomingAmount) return castNumber((1-((double) incomingAmount/ (double) currentAmount)) * a.doubleValue(), current);
            else return castNumber(0, current);
        }
        throw  new IllegalArgumentException("Components must be numeric");
    }
    @Override
    public <T> T extracted(T current, T incoming, long currentAmount, long incomingAmount) {
        if (current instanceof Number a && incoming instanceof Number b) {
            if (a.doubleValue() >= b.doubleValue()) return castNumber(b.doubleValue(), current);
            else return castNumber(b.doubleValue() - a.doubleValue(), current);
        }
        throw  new IllegalArgumentException("Components must be numeric");
    }

    public <T> T extractedFromVolume(T current, T incoming, long currentAmount, long incomingAmount){
        if (current instanceof Number a && incoming instanceof Number b) {
            if (currentAmount >= incomingAmount) return castNumber(((double) incomingAmount/ (double) currentAmount) * a.doubleValue(), current);
            else return castNumber(a.doubleValue(), current);
        }
        throw  new IllegalArgumentException("Components must be numeric");
    }


    @SuppressWarnings("unchecked")
    private <T> T castNumber(Number value, T original) {
        if (original instanceof Integer) return (T) Integer.valueOf(value.intValue());
        if (original instanceof Long) return (T) Long.valueOf(value.longValue());
        if (original instanceof Float) return (T) Float.valueOf(value.floatValue());
        if (original instanceof Double) return (T) Double.valueOf(value.doubleValue());

        throw new IllegalArgumentException("Unsupported number type");
    }
}
