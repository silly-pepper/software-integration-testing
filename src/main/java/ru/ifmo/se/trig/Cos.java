package ru.ifmo.se.trig;

import static java.lang.Double.NaN;
import static java.lang.Math.*;

public class Cos extends TrigFunction {

    private final Sin sin;

    public Cos(Sin sin) {
        this.sin = sin;
    }

    @Override
    public Double evaluate(Double x, Double eps) {
        x = validateInput(x);
        if (x.equals(NaN)) return NaN;

        if (x == -PI / 2 || x == -3 * PI / 2) {
            return 0.0;
        }
        double resultSin = sin.evaluate(x, eps);
        double resultCos = sqrt(1 - pow(resultSin, 2));
        if (x < -PI / 2 && x > -3 * PI / 2) {
            resultCos *= -1;
        }
        return resultCos;
    }
}