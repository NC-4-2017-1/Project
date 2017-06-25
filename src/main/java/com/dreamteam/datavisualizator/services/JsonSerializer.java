package com.dreamteam.datavisualizator.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class JsonSerializer {
    private static final Logger LOGGER = Logger.getLogger(JsonSerializer.class);
    private static String jsStringForDvGraph;

    private static JsonArray serializeTableData(List<Map<String, Object>> dataForSerialize, String columnNameAxisX, String columnNameAxisY){
        JsonArray jsonWithValuesForGraphic = new JsonArray();
        for(Map<String, Object> oneElementForSerialize : dataForSerialize) {
            JsonArray arrayWithData = new JsonArray();

            arrayWithData = new JsonSerializer().addToArray(oneElementForSerialize, columnNameAxisX, arrayWithData);
            arrayWithData = new JsonSerializer().addToArray(oneElementForSerialize, columnNameAxisY, arrayWithData);

            jsonWithValuesForGraphic.add(arrayWithData);
        }
        jsonWithValuesForGraphic = new JsonSerializer().sortingArray(jsonWithValuesForGraphic);

        return jsonWithValuesForGraphic;
    }

    private JsonArray sortingArray(JsonArray jsonWithValuesForGraphic){
        for(int i = jsonWithValuesForGraphic.size()-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
                JsonArray firstElement = jsonWithValuesForGraphic.get(j).getAsJsonArray();
                JsonArray secondElement = jsonWithValuesForGraphic.get(j+1).getAsJsonArray();
                if( new BigDecimal(firstElement.get(0).toString()).compareTo(new BigDecimal(secondElement.get(0).toString()))>0){
                    JsonArray tmp = jsonWithValuesForGraphic.get(j).getAsJsonArray();
                    jsonWithValuesForGraphic.set(j, jsonWithValuesForGraphic.get(j+1).getAsJsonArray());
                    jsonWithValuesForGraphic.set(j+1, tmp);
                }
            }
        }

        return  jsonWithValuesForGraphic;
    }

    public static JsonObject serializeGraph(List<Map<String, Object>> dataForSerialize,
                                            String columnNameAxisX, String columnNameAxisY, String columnNameOfMathCalculate){
        JsonArray arrayForGraph = serializeTableData(dataForSerialize,columnNameAxisX,columnNameAxisY);
        String typeOfAxisX = columnNameAxisX.compareTo("Date")==0 ? "datetime" : "linear";

        String jsStringForGraph = ("var chart = new Highcharts.chart(container_name, {" +
                "        chart: {" +
                "            zoomType: 'x'" +
                "        }," +
                "        title: {" +
                "            text: 'DataVisualization'" +
                "        }," +
                "        subtitle: {" +
                "            text: document.ontouchstart === undefined ?" +
                "                    'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'" +
                "        }," +
                "        xAxis: {" +
                "            type: '" + typeOfAxisX + "'," +
                "            title: {" +
                "                text: '" + (columnNameAxisX.length() < 25 ?
                                                columnNameAxisX :
                                                columnNameAxisX.substring(0,24) + "...")/*columnNameAxisX*/ + "'" +
                "            }" +
                "        }," +
                "        yAxis: {" +
                "            title: {" +
                "                text: '" + (columnNameAxisY.length() < 25 ?
                                                columnNameAxisY :
                                                columnNameAxisY.substring(0,24) + "...")/*columnNameAxisY*/ + "'" +
                "            }" +
                "        }," +
                "        legend: {" +
                "            enabled: false" +
                "        }," +
                "        plotOptions: {" +
                "            area: {" +
                "                fillColor: {" +
                "                    linearGradient: {" +
                "                        x1: 0," +
                "                        y1: 0," +
                "                        x2: 0," +
                "                        y2: 1" +
                "                    }," +
                "                    stops: [" +
                "                        [0, Highcharts.getOptions().colors[0]]," +
                "                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]" +
                "                    ]" +
                "                }," +
                "                marker: {" +
                "                    radius: 2" +
                "                }," +
                "                lineWidth: 1," +
                "                states: {" +
                "                    hover: {" +
                "                        lineWidth: 1" +
                "                    }" +
                "                }," +
                "                threshold: null" +
                "            }" +
                "        }," +
                "        series: [{" +
                "            type: 'area'," +
                "            name: '" + (columnNameAxisY.length() < 25 ?
                                            columnNameAxisY :
                                            columnNameAxisY.substring(0,24) + "...") + " at " + (columnNameAxisX.length() < 25 ?
                                                                                                    columnNameAxisX :
                                                                                                    columnNameAxisX.substring(0,24) + "...") + "'," +
                "            data:"+ arrayForGraph + " " +
                "        }]" +
                "    });");

        jsStringForDvGraph = String.format("%s", jsStringForGraph);

        JsonObject jsonGraph = new JsonObject();
        jsonGraph.addProperty("jsCodeForGraph", jsStringForDvGraph);
        jsonGraph.addProperty("math", columnNameOfMathCalculate);

        return jsonGraph;
    }


    private JsonArray addToArray(Map<String, Object> elementOfSerialize, String columnName, JsonArray arrayWithData){
        for(Map.Entry<String, Object> elementOfList : elementOfSerialize.entrySet()){
            if(elementOfList.getKey().compareTo(columnName)==0){
                if(elementOfList.getValue()instanceof Date){
                    arrayWithData.add(ConvertDateToMs.convertDateToMs(elementOfList.getValue().toString()));
                }
                else{
                    arrayWithData.add(new BigDecimal(elementOfList.getValue().toString()));
                }
            }
        }

        return arrayWithData;
    }
}
