package ru.ifmo.se.log;

import static java.lang.Double.NaN;

public class Ln extends LogFunction {
    @Override
    public Double evaluate(Double x, Double eps) {
        x = validateInput(x);
        if (x.equals(NaN)) return NaN;
        double constant = ((x - 1) * (x - 1)) / ((x + 1) * (x + 1));

        double sum = 0;
        double currentValue = (x - 1) / (x + 1);
        int step = 1;
        while (Math.abs(currentValue) > eps / 2) {
            sum += currentValue;
            currentValue = (2 * step - 1) * currentValue * constant / (2 * step + 1);
            step++;
        }
        sum *= 2;
        return sum;
    }
}