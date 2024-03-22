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

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CosTest {
    public static Sin sin = new Sin();
    public static Sin sinWithMock = mock(Sin.class);
    private static final double eps = 0.0000001;
    private final double accuracy = 0.01;
    private final CsvLogger csvLogger = new CsvLogger();
    private final Cos cos = new Cos(sin);
    private final Cos cosWithMock = new Cos(sinWithMock);

    @BeforeAll
    public static void setup() {
        fillMock(sinWithMock, "src/test/resources/unit/sinData.csv");

    }

    private static void fillMock(TrigFunction tf, String tableName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(tableName))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                final double x = Double.parseDouble(record[0]);
                final double y = Double.parseDouble(record[1]);
                when(tf.evaluate(x % (2 * PI), CosTest.eps)).thenReturn(y);
            }
        } catch (IOException | CsvException ignored) {
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/unit/cosData.csv")
    @DisplayName("cos(x) test")
    void cosTest(Double input, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/trig/cos.csv");
        double result = cos.evaluate(input, eps);
        csvLogger.logger(input, result);
        assertEquals(trueResult, result, accuracy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/unit/cosData.csv")
    @DisplayName("cos(x) test with mock")
    void cosTestWithMock(Double input, Double trueResult) {
        csvLogger.setFilePath("src/test/resources/results/trig/cos.csv");
        double result = cosWithMock.evaluate(input, eps);
        csvLogger.logger(input, result);
        assertEquals(trueResult, result, accuracy);
    }
}