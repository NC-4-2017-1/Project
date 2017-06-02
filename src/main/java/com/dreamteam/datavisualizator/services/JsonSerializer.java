package com.dreamteam.datavisualizator.services;

import com.google.gson.JsonArray;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonSerializer {
    private static final Logger LOGGER = Logger.getLogger(JsonSerializer.class);

    public static JsonArray serializeData(List<Map<String, Object>> dataForSerialize, String columnNameAxisX, String columnNameAxisY){
        JsonArray jsonWithValuesForGraphic = new JsonArray();
        for(Map<String, Object> oneElementForSerialize : dataForSerialize) {
            JsonArray arrayWithData = new JsonArray();

            arrayWithData = new JsonSerializer().addToArray(oneElementForSerialize, columnNameAxisX, arrayWithData);
            arrayWithData = new JsonSerializer().addToArray(oneElementForSerialize, columnNameAxisY, arrayWithData);

            jsonWithValuesForGraphic.add(arrayWithData);
        }

        return jsonWithValuesForGraphic;
    }

    public static String serializeDataToJsStringWithGraph(List<Map<String, Object>> dataForSerialize, String columnNameAxisX, String columnNameAxisY){
        StringBuilder jsStringForGraph = new StringBuilder();

        JsonArray arrayForGraph = serializeData(dataForSerialize,columnNameAxisX,columnNameAxisY);
        String typeOfAxisX = columnNameAxisX == "Date" ? "datetime" : "linear";

        jsStringForGraph.append("var chart = new Highcharts.chart('container', {" +
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
                "            type: '"+typeOfAxisX+"'," +
                "            title: {" +
                "                text: '"+columnNameAxisX+"'" +
                "            }" +
                "        }," +
                "        yAxis: {" +
                "            title: {" +
                "                text: '"+columnNameAxisY+"'" +
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
                "            name: '"+columnNameAxisY+" at "+columnNameAxisX+"'," +
                "            data:"+ arrayForGraph + " " +
                "        }]" +
                "    });");

        return jsStringForGraph.toString();
    }


    private JsonArray addToArray(Map<String, Object> elementOfSerialize, String columnName, JsonArray arrayWithData){
        for(Map.Entry<String, Object> elementOfList : elementOfSerialize.entrySet()){
            if(elementOfList.getKey() == columnName){
                if(columnName == "Date") {
                    arrayWithData.add(new JsonSerializer().convertDateToMs(elementOfList.getValue().toString()));
                }
                else{
                    arrayWithData.add(new BigDecimal(elementOfList.getValue().toString()));
                }
            }
        }

        return arrayWithData;
    }

    private Long convertDateToMs(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EET"));

        try {
            Date convertDate =  simpleDateFormat.parse(date);

            return convertDate.getTime();
        } catch (ParseException e) {
            LOGGER.error("Serialize json error in convert date", e);
            return null;
        }
    }
}
