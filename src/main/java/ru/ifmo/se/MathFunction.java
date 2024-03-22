package ru.ifmo.se;

import static java.lang.Double.*;


public abstract class MathFunction {

    public abstract Double evaluate(Double x, Double epsilon);

    public Double validateInput(double x) {
        if (isNaN(x) || isInfinite(x)) {
            return NaN;
        }
        return x;
    }
}