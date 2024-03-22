package ru.ifmo.se.log;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.ifmo.se.utils.CsvLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LnTest {
    private static final double eps = 0.000000000001;
    private static final double accuracy = 0.1;
    private static final CsvLogger csvLogger = new CsvLogger();
    private static final Ln ln = new Ln();

    @ParameterizedTest
    @CsvFileSource(resources = "/InputLog/lnData.csv")
    @DisplayName("ln(x) test")
    void lnTest(Double x, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/log/ln.csv");
        double result = ln.evaluate(x, eps);
        csvLogger.logger(x, result);
        assertEquals(trueResult, result, accuracy);
    }

}