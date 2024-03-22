package ru.ifmo.se.trig;


import ru.ifmo.se.MathFunction;

import static java.lang.Math.PI;

public abstract class TrigFunction extends MathFunction {
    @Override
    public Double validateInput(double x) {
        x = super.validateInput(x);
        return x % (2 * PI);
    }
}