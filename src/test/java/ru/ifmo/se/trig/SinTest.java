package ru.ifmo.se.trig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.ifmo.se.utils.CsvLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SinTest {

    private final CsvLogger csvLogger = new CsvLogger();
    private final double accuracy = 1;
    private final double eps = 0.0000000001;
    private final Sin sin = new Sin();


    @ParameterizedTest
    @CsvFileSource(resources = "/unit/sinData.csv")
    @DisplayName("sin(x) test")
    void sinTest(Double x,  Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/trig/sin.csv");
        double result = sin.evaluate(x, eps);
        csvLogger.logger(x, result);
        assertEquals(trueResult, result, accuracy);
    }
}