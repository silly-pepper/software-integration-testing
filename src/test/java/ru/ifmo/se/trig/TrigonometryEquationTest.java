package ru.ifmo.se.trig;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.ifmo.se.utils.CsvLogger;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrigonometryEquationTest {

    public static Sin sinMock = mock(Sin.class);
    public static Cos cosMock = mock(Cos.class);
    public static Sin sin = new Sin();
    public static Cos cos = new Cos(sin);
    private static final double eps = 0.000000000000000001;
    private final double accuracy = 0.1;
    private static final CsvLogger csvLogger = new CsvLogger();


    @BeforeAll
    public static void setup() {
        fillMock(sinMock, "src/test/resources/unit/sinData.csv");
        fillMock(cosMock, "src/test/resources/unit/cosData.csv");
    }

    private static void fillMock(TrigFunction tf, String tableName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(tableName))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                final double x = Double.parseDouble(record[0]);
                final double y = Double.parseDouble(record[1]);
                when(tf.evaluate(x, TrigonometryEquationTest.eps)).thenReturn(y);
            }
        } catch (IOException | CsvException ignored) {
        }
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/unit/trigFuncData.csv")
    @DisplayName("trigonometric function test with full mocks")
    void trigFuncTestWithMock(Double input, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/trig/trigFunc.csv");
        TrigonometryEquation trigonometricCalculator = new TrigonometryEquation(cosMock, sinMock);
        double result = trigonometricCalculator.evaluate(input, eps);
        csvLogger.logger(input, result);
        assertEquals(trueResult, result, accuracy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/unit/trigFuncData.csv")
    @DisplayName("trigonometric function test without mocks")
    void trigFuncTest(Double input, Double trueResult) {
        TrigonometryEquation trigonometricCalculator = new TrigonometryEquation(cos, sin);
        double result = trigonometricCalculator.evaluate(input, eps);
        assertEquals(trueResult, result, accuracy);
    }
}