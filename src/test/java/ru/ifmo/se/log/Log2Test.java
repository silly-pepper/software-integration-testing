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

class Log2Test {
    private static final Ln lnWithMock = mock(Ln.class);
    private static final Ln ln = new Ln();
    private static final Log2 log2 = new Log2(ln);
    private static final Log2 log2WithMock = new Log2(lnWithMock);
    private static final double eps = 0.000000000001;
    private static final double accuracy = 0.1;
    private static final CsvLogger csvLogger = new CsvLogger();

    @BeforeAll
    public static void setup() {
        fillMock(lnWithMock, "src/test/resources/InputLog/lnData.csv");
    }

    private static void fillMock(LogFunction tf, String tableName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(tableName))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                final double x = Double.parseDouble(record[0]);
                final double y = Double.parseDouble(record[1]);
                when(tf.evaluate(x, Log2Test.eps)).thenReturn(y);
            }
        } catch (IOException | CsvException ignored) {
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/InputLog/log2Data.csv")
    @DisplayName("log2(x) test")
    void log2Test(Double input, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/log/log2.csv");
        double result = log2.evaluate(input, eps);
        csvLogger.logger(input, result);
        assertEquals(trueResult, result, accuracy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/InputLog/log2Data.csv")
    @DisplayName("log2(x) test with mock")
    void log2TestWithMock(Double input, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/log/log2.csv");
        double result = log2WithMock.evaluate(input, eps);
        csvLogger.logger(input, result);
        assertEquals(trueResult, result, accuracy);
    }
}