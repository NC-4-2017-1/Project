package com.dreamteam.datavisualizator.services;


import com.google.gson.JsonArray;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class HmGraphSerializer {
    private static final Logger LOGGER = Logger.getLogger(HmGraphSerializer.class);
    private static String jsStringForHmGraph;

    public static String serialiseHmGraph(ResultSetWrappingSqlRowSet graphResultSet) {
        JsonArray arrayDataForHm = serializeDataForHmGraph(graphResultSet);

        String stringJsCode = "var chart = new Highcharts.chart('container', {" +
                "    chart: {" +
                "        type: 'columnrange'," +
                "        inverted: true" +
                "    }," +
                "" +
                "    title: {" +
                "        text: 'Temperature variation by month'" +
                "    }," +
                "" +
                "    subtitle: {" +
                "        text: 'Observed in Vik i Sogn, Norway'" +
                "    }," +
                "" +
                "    xAxis: {" +
                "        categories: "+arrayDataForHm.get(0) +"" +
                "    }," +
                "" +
                "    yAxis: {" +
                "        title: " +
                "            text: 'Temperature ( °C )'" +
                "        }" +
                "    }," +
                "" +
                "    tooltip: {" +
                "        valueSuffix: '°C'" +
                "    }," +
                "" +
                "    plotOptions: {" +
                "        columnrange: {" +
                "            dataLabels: {" +
                "                enabled: true," +
                "                formatter: function () {" +
                "                    return this.y + '°C';" +
                "                }" +
                "            }" +
                "        }" +
                "    }," +
                "" +
                "    legend: {" +
                "        enabled: false" +
                "    }," +
                "" +
                "    series: [{" +
                "        name: 'Temperatures'," +
                "        data: "+arrayDataForHm.get(0) +" "+
                "    }]" +
                "" +
                "});";


        jsStringForHmGraph = String.format("%s", stringJsCode);

        return jsStringForHmGraph;
    }

    public static JsonArray serializeDataForHmGraph(ResultSetWrappingSqlRowSet graphResultSet) {
        SqlRowSetMetaData setMetaData = graphResultSet.getMetaData();
        int rowCount = setMetaData.getColumnCount();
        ArrayList<String> xNameTask = new ArrayList<>();
        ArrayList<Date> startDate = new ArrayList<>();
        ArrayList<Date> endDate = new ArrayList<>();
        ArrayList<String> informationTask = new ArrayList<>();
        ResultSet graphSet=graphResultSet.getResultSet();
        try {
            while (graphSet.next()) {
                    xNameTask.add(graphSet.getString(0));
                    startDate.add(graphSet.getDate(1));
                    endDate.add(graphSet.getDate(2));
                    informationTask.add(graphSet.getString(3)+" "+graphSet.getString(4)+" "
                            +graphSet.getString(5)+" "+ClobToStringService.clobToString(graphSet.getClob(6)));

            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return  null;
        }

        JsonArray serizlizeData = new JsonArray();
        JsonArray nameArray = new JsonArray();
        JsonArray dateArray = new JsonArray();
        JsonArray informationArray = new JsonArray();

        for(int i=0; i<xNameTask.size(); i++){
            JsonArray temporaryDateArray = new JsonArray();

            nameArray.add(xNameTask.get(i));
            temporaryDateArray.add(new BigDecimal(startDate.get(i).toString()));
            temporaryDateArray.add(new BigDecimal(endDate.get(i).toString()));
            dateArray.add(temporaryDateArray);
            informationArray.add(informationTask.get(i));
        }

        serizlizeData.add(nameArray);
        serizlizeData.add(dateArray);
        serizlizeData.add(informationArray);

        return serizlizeData;
    }

}
