package ru.ifmo.se.log;

public class Log2 extends LogFunction {
    private final Ln ln;

    public Log2(Ln ln) {
        this.ln = ln;
    }

    @Override
    public Double evaluate(Double x, Double eps) {
        x = validateInput(x);

        return ln.evaluate(x, eps) / 0.69314718055;
    }
}
