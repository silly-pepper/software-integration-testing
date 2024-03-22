package ru.ifmo.se;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.log.LogarithmEquation;
import ru.ifmo.se.trig.TrigonometryEquation;

@RequiredArgsConstructor
public class SystemFunction extends MathFunction {
    private final LogarithmEquation logarithmEquation;
    private final TrigonometryEquation trigonometryEquation;

    @Override
    public Double evaluate(Double x, Double epsilon) {
        if (x > 0) {
            return logarithmEquation.evaluate(x, epsilon);
        } else {
            return trigonometryEquation.evaluate(x, epsilon);
        }
    }
}
