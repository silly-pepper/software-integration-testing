package ru.ifmo.se.trig;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.MathFunction;

@RequiredArgsConstructor
public class TrigonometryEquation extends MathFunction {
    private final Cos cos;
    private final Sin sin;

    @Override
    public Double evaluate(Double x, Double epsilon) {
        Double cos = this.cos.evaluate(x, epsilon);
        Double sin = this.sin.evaluate(x, epsilon);
        return (Math.pow((((sin - cos) * cos) / cos), 3) - cos);
    }
}
