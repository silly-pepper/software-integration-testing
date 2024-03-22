package ru.ifmo.se;

import com.opencsv.CSVReader;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.ifmo.se.log.*;
import ru.ifmo.se.trig.Cos;
import ru.ifmo.se.trig.Sin;
import ru.ifmo.se.trig.TrigonometryEquation;
import ru.ifmo.se.utils.CsvLogger;

import java.io.FileReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SystemFunctionTest {
    private static final double accuracy = 0.1;
    private static final double eps = 0.000000000000000000000001;
    private static final Ln ln = new Ln();
    private static final Log2 log2 = new Log2(ln);
    private static final Log3 log3 = new Log3(ln);
    private static final Log5 log5 = new Log5(ln);
    private static final Sin sin = new Sin();
    private static final Cos cos = new Cos(sin);
    private static final TrigonometryEquation trigCalculatorMock = mock(TrigonometryEquation.class);
    private static final TrigonometryEquation trigCalculator = new TrigonometryEquation(cos, sin);
    private static final LogarithmEquation logCalculatorMock = mock(LogarithmEquation.class);
    private static final LogarithmEquation logCalculator = new LogarithmEquation(ln, log2, log3, log5);

    @BeforeAll
    public static void setup() {
        fillMock(logCalculatorMock);
        fillMock(trigCalculatorMock);
    }

    @SneakyThrows
    private static void fillMock(TrigonometryEquation tf) {
        try (CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/unit/trigFuncData.csv"))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                final double x = Double.parseDouble(record[0]);
                final double y = Double.parseDouble(record[1]);
                when(tf.evaluate(x, eps)).thenReturn(y);
            }
        }
    }

    @SneakyThrows
    private static void fillMock(LogarithmEquation tf) {
        try (CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/InputLog/logFuncData.csv"))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                final double x = Double.parseDouble(record[0]);
                final double y = Double.parseDouble(record[1]);
                when(tf.evaluate(x, eps)).thenReturn(y);
            }
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/calcData.csv")
    @DisplayName("allMock")
    void allMock(Double x, Double trueResult) {
        SystemFunction calculator = new SystemFunction(logCalculatorMock, trigCalculatorMock);
        double result = calculator.evaluate(x, eps);
        assertEquals(trueResult, result, accuracy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/calcData.csv")
    @DisplayName("all test without mock")
    void allTest(Double x, Double trueResult) {
        SystemFunction calculator = new SystemFunction(logCalculator, trigCalculator);
        double result = calculator.evaluate(x, eps);
        assertEquals(trueResult, result, accuracy);
    }
}