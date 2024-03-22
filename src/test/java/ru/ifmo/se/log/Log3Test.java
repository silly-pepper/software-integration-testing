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

class Log3Test {
    private static final Ln lnWithMock = mock(Ln.class);
    private static final Ln ln = new Ln();
    private static final Log3 log3 = new Log3(ln);
    private static final Log3 log3WithMock = new Log3(lnWithMock);
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
                when(tf.evaluate(x, Log3Test.eps)).thenReturn(y);
            }
        } catch (IOException | CsvException ignored) {
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/InputLog/log3Data.csv")
    @DisplayName("log3(x) test")
    void log3Test(Double input, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/log/log3.csv");
        double result = log3.evaluate(input, eps);
        csvLogger.logger(input, result);
        assertEquals(trueResult, result, accuracy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/InputLog/log3Data.csv")
    @DisplayName("log3(x) test with mock")
    void log3TestWithMock(Double input, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/log/log3.csv");
        double result = log3WithMock.evaluate(input, eps);
        csvLogger.logger(input, result);
        assertEquals(trueResult, result, accuracy);
    }
}