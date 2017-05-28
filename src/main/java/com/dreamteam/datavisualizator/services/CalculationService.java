package com.dreamteam.datavisualizator.services;

import com.dreamteam.datavisualizator.models.Graphic;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.math.BigDecimal;
import java.util.*;


public class CalculationService {

    public static BigDecimal calculateAverage(ArrayList<BigDecimal> dataForCalculate){
        double[] arrayForCalculate = new CalculationService().arrayListToDouble(dataForCalculate);
        BigDecimal result = new BigDecimal((new Mean()).evaluate(arrayForCalculate));

        return result;
    }

    public static BigDecimal calculateOlympicAverage(ArrayList<BigDecimal> dataForCalculate){
        ArrayList<BigDecimal> newDataForCalculate = new ArrayList<>(dataForCalculate);
        Collections.sort(newDataForCalculate, new Comparator<BigDecimal>() {
            public int compare(BigDecimal o1, BigDecimal o2) {
                return o1.compareTo(o2);
            }
        });

        BigDecimal maxElement = newDataForCalculate.get(newDataForCalculate.size() - 1);
        BigDecimal minElement = newDataForCalculate.get(0);

        for(int i=newDataForCalculate.size()-1; i!=0; i--){
            if(maxElement == newDataForCalculate.get(i)){
                newDataForCalculate.remove(i);
            }
            else{
                break;
            }
        }

        for(int i=0; i<newDataForCalculate.size(); i++){
            if(minElement == newDataForCalculate.get(i)){
                newDataForCalculate.remove(i);
                i--;
            }
            else{
                break;
            }
        }

        BigDecimal result = calculateAverage(newDataForCalculate);

        return result;
    }

    public static BigDecimal calculateCorrelation(ArrayList<BigDecimal> listOfFirstGraph, ArrayList<BigDecimal> listOfSecondGraph){
        double[] arrayOfFirstGraph = new CalculationService().arrayListToDouble(listOfFirstGraph);
        double[] arrayOfSecondGraph = new CalculationService().arrayListToDouble(listOfSecondGraph);

        double correlation = new PearsonsCorrelation().correlation(arrayOfFirstGraph, arrayOfSecondGraph);

        return new BigDecimal(correlation);
    }

    public static BigDecimal calculateDispersion(ArrayList<BigDecimal> dataForCalculate){
        double[] arrayForCalculate = new CalculationService().arrayListToDouble(dataForCalculate);
        BigDecimal result = new BigDecimal((new Variance()).evaluate(arrayForCalculate));

        return result;
    }

    public static BigDecimal calculationMathExpectation(ArrayList<BigDecimal> dataForCalculate){
        double[] arrayForCalculate = new CalculationService().arrayListToDouble(dataForCalculate);
        double probability = 1.0/arrayForCalculate.length;
        double expectedValue = 0;

        for(double elementOfArray : arrayForCalculate){
            expectedValue = expectedValue + (elementOfArray*probability);
        }

        return new BigDecimal(expectedValue);
    }

    private double[] arrayListToDouble(ArrayList<BigDecimal> listToCalculate){
        double[] arrayOfValues = new double[listToCalculate.size()];

        int i=0;
        for(BigDecimal oneValue : listToCalculate){
            arrayOfValues[i] = oneValue.doubleValue();
            i++;
        }

        return arrayOfValues;
    }
}
