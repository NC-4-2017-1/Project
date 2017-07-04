package com.dreamteam.datavisualizator.services;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;


public class CalculationService {
    private static final Logger LOGGER = Logger.getLogger(CalculationService.class);

    public static BigDecimal calculateAverage(List<Map<String, Object>> parseData, String header) {
        ArrayList<BigDecimal> dataForCalculate = convertParseDate(parseData, header);
        if(dataForCalculate == null){
            return BigDecimal.ZERO;
        }
        try {
            double[] arrayForCalculate = new CalculationService().arrayListToDouble(dataForCalculate);
            BigDecimal result = new BigDecimal((new Mean()).evaluate(arrayForCalculate));
            return result;
        } catch (NullArgumentException e) {
            LOGGER.error("No data for Calculate", e);
            return null;
        } catch (NullPointerException e) {
            LOGGER.error("Wrong date format for Calculate", e);
            return null;
        }
    }

    public static BigDecimal calculateOlympicAverage(List<Map<String, Object>> parseData, String header) {
        ArrayList<BigDecimal> dataForCalculate = convertParseDate(parseData, header);
        if(dataForCalculate == null){
            return BigDecimal.ZERO;
        }
        ArrayList<BigDecimal> newDataForCalculate = new ArrayList<>(dataForCalculate);
        Collections.sort(newDataForCalculate, new Comparator<BigDecimal>() {
            public int compare(BigDecimal o1, BigDecimal o2) {
                return o1.compareTo(o2);
            }
        });

        BigDecimal maxElement = newDataForCalculate.get(newDataForCalculate.size() - 1);
        BigDecimal minElement = newDataForCalculate.get(0);

        for (int i = newDataForCalculate.size() - 1; i != 0; i--) {
            if (maxElement == newDataForCalculate.get(i)) {
                newDataForCalculate.remove(i);
            } else {
                break;
            }
        }

        for (int i = 0; i < newDataForCalculate.size(); i++) {
            if (minElement == newDataForCalculate.get(i)) {
                newDataForCalculate.remove(i);
                i--;
            } else {
                break;
            }
        }

        BigDecimal result = calculateAverage(parseData, header);

        return result;
    }

    public static BigDecimal calculateCorrelation(List<Map<String, Object>> parseData, String headerOfFirstColumn, String headerOfSecondColumn) {
        ArrayList<BigDecimal> listOfFirstGraph = convertParseDate(parseData, headerOfFirstColumn);
        ArrayList<BigDecimal> listOfSecondGraph = convertParseDate(parseData, headerOfSecondColumn);
        if(listOfFirstGraph == null || listOfSecondGraph == null){
            return BigDecimal.ZERO;
        }
        try {
            double[] arrayOfFirstGraph = new CalculationService().arrayListToDouble(listOfFirstGraph);
            double[] arrayOfSecondGraph = new CalculationService().arrayListToDouble(listOfSecondGraph);

            double correlation = new PearsonsCorrelation().correlation(arrayOfFirstGraph, arrayOfSecondGraph);

            return new BigDecimal(correlation);
        } catch (DimensionMismatchException e) {
            LOGGER.error("Different sets of lists values", e);
            return null;
        } catch (MathIllegalArgumentException e) {
            LOGGER.error("", e);
            return null;
        }
    }

    public static BigDecimal calculateDispersion(List<Map<String, Object>> parseData, String header) {
        ArrayList<BigDecimal> dataForCalculate = convertParseDate(parseData, header);
        if(dataForCalculate == null){
            return BigDecimal.ZERO;
        }
        try {
            double[] arrayForCalculate = new CalculationService().arrayListToDouble(dataForCalculate);
            BigDecimal result = new BigDecimal((new Variance()).evaluate(arrayForCalculate));
            return result;
        } catch (NullArgumentException e) {
            LOGGER.error("No data for Calculate", e);
            return null;
        }
    }

    public static BigDecimal calculationMathExpectation(List<Map<String, Object>> parseData, String header) {
        ArrayList<BigDecimal> dataForCalculate = convertParseDate(parseData, header);
        if(dataForCalculate == null){
            return BigDecimal.ZERO;
        }
        double[] arrayForCalculate = new CalculationService().arrayListToDouble(dataForCalculate);
        double probability = 1.0 / arrayForCalculate.length;
        double expectedValue = 0;

        for (double elementOfArray : arrayForCalculate) {
            expectedValue = expectedValue + (elementOfArray * probability);
        }

        return new BigDecimal(expectedValue);
    }

    public static Map<String, BigDecimal> getAllCorreletion(List<Map<String, Object>> parseData) {
        List<String> headerWichClassTypeBigDecimal = new ArrayList<>();
        Map<String, BigDecimal> resultCorrelationMap = new LinkedHashMap<>();
        for (String header : parseData.get(0).keySet()) {
            for (Object typeOfDate : parseData.get(1).values()) {
                if (parseData.get(1).get(header) instanceof BigDecimal) {
                    headerWichClassTypeBigDecimal.add(header);
                }
            }
        }
        for (int i = 0; i < headerWichClassTypeBigDecimal.size() - 1; i++) {
            for (int j = i + 1; j < headerWichClassTypeBigDecimal.size(); j++) {
                if (!headerWichClassTypeBigDecimal.get(i).equals(headerWichClassTypeBigDecimal.get(j)))
                    resultCorrelationMap.put(headerWichClassTypeBigDecimal.get(i) + " " + headerWichClassTypeBigDecimal.get(j),
                            calculateCorrelation(parseData, headerWichClassTypeBigDecimal.get(i), headerWichClassTypeBigDecimal.get(j)));
            }
        }
        return resultCorrelationMap;
    }

    private double[] arrayListToDouble(ArrayList<BigDecimal> listToCalculate) {
        double[] arrayOfValues = new double[listToCalculate.size()];

        int i = 0;
        for (BigDecimal oneValue : listToCalculate) {
            arrayOfValues[i] = oneValue.doubleValue();
            i++;
        }

        return arrayOfValues;
    }

    private static ArrayList<BigDecimal> convertParseDate(List<Map<String, Object>> data, String header) {
        if(data == null){
            return null;
        }
        if (!(data.get(0).get(header) instanceof BigDecimal)) {
            return null;
        }
        ArrayList<BigDecimal> dataForCalculate = new ArrayList<>();
        for (Map<String, Object> map : data) {
            dataForCalculate.add((BigDecimal) map.get(header));
        }
        return dataForCalculate;
    }
}
