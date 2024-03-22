package ru.ifmo.se.log;

public class Log5 extends LogFunction {
    private final Ln ln;

    public Log5(Ln ln) {
        this.ln = ln;
    }

    @Override
    public Double evaluate(Double x, Double eps) {
        x = validateInput(x);
        return ln.evaluate(x, eps) / 1.60943791243;
    }
}
