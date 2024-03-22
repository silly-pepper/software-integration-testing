package ru.ifmo.se.log;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.MathFunction;

@RequiredArgsConstructor
public class LogarithmEquation extends MathFunction {
    private final Ln ln;
    private final Log2 log2;
    private final Log3 log3;
    private final Log5 log5;

    @Override
    public Double evaluate(Double x, Double epsilon) {
        Double ln = this.ln.evaluate(x, epsilon);
        Double log2 = this.log2.evaluate(x, epsilon);
        Double log3 = this.log3.evaluate(x, epsilon);
        Double log5 = this.log5.evaluate(x, epsilon);
        return (((((log3 / log3) - log2) / (log3 - (log5 / log5))) * log2) * (log5 + Math.pow(log5, 2)));
    }

}
