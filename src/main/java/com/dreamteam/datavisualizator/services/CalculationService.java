package com.dreamteam.datavisualizator.services;

import com.dreamteam.datavisualizator.models.Graphic;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.math.BigDecimal;
import java.util.*;


public class CalculationService {

    public BigDecimal calculateAverage(ArrayList<BigDecimal> dataForCalculate){
        double[] arrayForCalculate = this.arrayListToDouble(dataForCalculate);
        BigDecimal result = new BigDecimal((new Mean()).evaluate(arrayForCalculate));

        return result;
    }

    public BigDecimal calculateOlympicAverage(ArrayList<BigDecimal> dataForCalculate){
        Collections.sort(dataForCalculate, Collections.<BigDecimal>reverseOrder());

        BigDecimal minElement = dataForCalculate.get(dataForCalculate.size() - 1);
        BigDecimal maxElement = dataForCalculate.get(0);

        for(int i=dataForCalculate.size()-1; i!=0; i--){
            if(maxElement == dataForCalculate.get(i)){
                dataForCalculate.remove(i);
            }
            else{
                break;
            }
        }

        for(int i=0; i<dataForCalculate.size(); i++){
            if(minElement == dataForCalculate.get(i)){
                dataForCalculate.remove(i);
                i--;
            }
            else{
                break;
            }
        }

        BigDecimal result = calculateAverage(dataForCalculate);

        return result;
    }

    public BigDecimal calculateCorrelaction(ArrayList<BigDecimal> listOfFirstGraph, ArrayList<BigDecimal> listOfSecondGraph){
        double[] arrayOfFristGraph = this.arrayListToDouble(listOfFirstGraph);
        double[] arrayOfSecondGraph = this.arrayListToDouble(listOfSecondGraph);

        double correlation = new PearsonsCorrelation().correlation(arrayOfFristGraph, arrayOfSecondGraph);


        return new BigDecimal(correlation);
    }

    public BigDecimal calculateDispersion(ArrayList<BigDecimal> dataForCalculate){
        double[] arrayForCalculate = this.arrayListToDouble(dataForCalculate);
        BigDecimal result = new BigDecimal((new Variance()).evaluate(arrayForCalculate));

        return result;
    }

    public BigDecimal calculationMathExpectation(ArrayList<BigDecimal> dataForCalculate){
        double[] arrayForCalculate = this.arrayListToDouble(dataForCalculate);
        double probability = 1/arrayForCalculate.length;
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
