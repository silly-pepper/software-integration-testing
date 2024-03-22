package ru.ifmo.se.trig;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.ifmo.se.utils.CsvLogger;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrigFuncTest {

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);
    private final CsvLogger csvLogger = new CsvLogger();
    private final double accuracy = 1;
    private final double eps = 0.0000000001;

    @BeforeAll
    public void clearFiles() {
        String directoryPath = "src/test/resources/results/trig/";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            csvLogger.setFilePath(String.valueOf(file));
            csvLogger.clearFile();
        }
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/inputTrig/sinData.csv")
    @DisplayName("sin(x) test")
    void sinTest(Double divisible, Double divider, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/trig/sin.csv");
        double x = divisible * Math.PI / divider;
        double result = sin.evaluate(x, eps);
        csvLogger.logger(x, result);
        assertEquals(trueResult, result, accuracy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/inputTrig/cosData.csv")
    @DisplayName("cos(x) test")
    void cosTest(Double divisible, Double divider, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/trig/cos.csv");
        double x = divisible * Math.PI / divider;
        double result = cos.evaluate(x, eps);
        csvLogger.logger(x, result);
        assertEquals(trueResult, result, accuracy);
    }

}
