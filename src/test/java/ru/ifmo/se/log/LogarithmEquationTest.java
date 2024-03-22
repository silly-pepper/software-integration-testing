package ru.ifmo.se.log;

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

class LogarithmEquationTest {
    private static final Ln lnWithMock = mock(Ln.class);
    private static final Log2 log2WithMock = mock(Log2.class);
    private static final Log3 log3WithMock = mock(Log3.class);
    private static final Log5 log5WithMock = mock(Log5.class);
    private static final Ln ln = new Ln();
    private static final Log2 log2 = new Log2(ln);
    private static final Log3 log3 = new Log3(ln);
    private static final Log5 log5 = new Log5(ln);
    private static final double eps = 0.000000000000000001;
    private final double accuracy = 0.1;
    private static final CsvLogger csvLogger = new CsvLogger();


    @BeforeAll
    public static void setup() {
        fillMock(lnWithMock, "src/test/resources/InputLog/lnData.csv");
        fillMock(log2WithMock, "src/test/resources/InputLog/log2Data.csv");
        fillMock(log3WithMock, "src/test/resources/InputLog/log3Data.csv");
        fillMock(log5WithMock, "src/test/resources/InputLog/log5Data.csv");
    }

    private static void fillMock(LogFunction tf, String tableName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(tableName))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                final double x = Double.parseDouble(record[0]);
                final double y = Double.parseDouble(record[1]);
                when(tf.evaluate(x, LogarithmEquationTest.eps)).thenReturn(y);
            }
        } catch (IOException | CsvException ignored) {
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/InputLog/logFuncData.csv")
    @DisplayName("logarithmic function test with full mocks")
    void testFunctionWithMocks(Double x, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/log/logFunc.csv");
        LogarithmEquation calculator = new LogarithmEquation(lnWithMock, log2WithMock, log3WithMock, log5WithMock);
        double result = calculator.evaluate(x, eps);
        csvLogger.logger(x, result);
        assertEquals(trueResult, result, accuracy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/InputLog/logFuncData.csv")
    @DisplayName("logarithmic function")
    void testFunction(Double x, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/log/logFunc.csv");
        LogarithmEquation calculator = new LogarithmEquation(ln, log2, log3, log5);
        double result = calculator.evaluate(x, eps);
        csvLogger.logger(x, result);
        assertEquals(trueResult, result, accuracy);
    }
}