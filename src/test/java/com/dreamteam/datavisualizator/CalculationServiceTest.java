package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.services.CalculationService;
import com.dreamteam.datavisualizator.services.csvparser.CsvParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class})
public class CalculationServiceTest {

    @Autowired
    private CsvParser parser;

    private File file;
    private List<Map<String, Object>> result;
    private String fieldForCalculate = "DB_LOAD";
    private String filedOfData = "Date";

    @Before
    public void setUp() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("testdocuments/svt_sample.csv");
        file = new File(url.getPath());
        result = parser.parseCsvFile(file, DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS);
    }

    @Test
    public void calculateAverageWithCorrectData(){
        BigDecimal expectedResult = new BigDecimal(52.5);
        BigDecimal calculationResult = CalculationService.calculateAverage(result, fieldForCalculate);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateAverageWithFieldDate(){
        BigDecimal expectedResult = BigDecimal.ZERO;
        BigDecimal calculationResult = CalculationService.calculateAverage(result, filedOfData);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateAverageWithNullParseData(){
        BigDecimal expectedResult = BigDecimal.ZERO;
        BigDecimal calculationResult = CalculationService.calculateAverage(null, filedOfData);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateOlympicAverageWithCorrectData(){
        BigDecimal expectedResult = new BigDecimal(52.5);
        BigDecimal calculationResult = CalculationService.calculateOlympicAverage(result, fieldForCalculate);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateOlympicAverageWithFieldDate(){
        BigDecimal expectedResult = BigDecimal.ZERO;
        BigDecimal calculationResult = CalculationService.calculateOlympicAverage(result, filedOfData);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateCorrelationWithCorrectData(){
        BigDecimal expectedResult = new BigDecimal(1.0);
        BigDecimal calculationResult = CalculationService.calculateCorrelation(result, fieldForCalculate, "THREAD_COUNT");;
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateCorrelationWithFieldDate(){
        BigDecimal expectedResult = BigDecimal.ZERO;
        BigDecimal calculationResult = CalculationService.calculateCorrelation(result, filedOfData, filedOfData);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateDispersionWithCorrectData(){
        BigDecimal expectedResult = new BigDecimal(4512.5);
        BigDecimal calculationResult = CalculationService.calculateDispersion(result, fieldForCalculate);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculateDispersionWithFieldDate(){
        BigDecimal expectedResult = BigDecimal.ZERO;
        BigDecimal calculationResult = CalculationService.calculateDispersion(result, filedOfData);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculationMathExpectationWithCorrectData(){
        BigDecimal expectedResult = new BigDecimal(52.5);
        BigDecimal calculationResult = CalculationService.calculationMathExpectation(result, fieldForCalculate);
        assertEquals(expectedResult, calculationResult);
    }

    @Test
    public void calculationMathExpectationWithFieldDate(){
        BigDecimal expectedResult = BigDecimal.ZERO;
        BigDecimal calculationResult = CalculationService.calculationMathExpectation(result, filedOfData);
        assertEquals(expectedResult, calculationResult);
    }


}
