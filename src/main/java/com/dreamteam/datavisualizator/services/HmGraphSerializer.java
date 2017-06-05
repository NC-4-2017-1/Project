package com.dreamteam.datavisualizator.services;


import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.common.dateconverter.StringToDateConverter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class HmGraphSerializer {
    private static final Logger LOGGER = Logger.getLogger(HmGraphSerializer.class);
    private static String jsStringForHmGraph;

    public static JsonObject serialiseHmGraph(ResultSetWrappingSqlRowSet graphResultSet) {
        JsonArray arrayDataForHm = serializeDataForHmGraph(graphResultSet);

        String stringJsCode = "var chart = new Highcharts.chart('containerGraphic', {" +
                "    chart: {" +
                "        type: 'columnrange'," +
                "        inverted: true" +
                "    }," +
                "" +
                "    title: {" +
                "        text: 'Task by Date-Time'" +
                "    }," +
                "" +
                "    xAxis: {" +
                "        categories: " + arrayDataForHm.get(0) + "" +
                "    }," +
                "" +
                "    yAxis: {" +
                "       type: 'datetime',"+
                "        title:{ " +
                "            text: 'Date-Time'" +
                "        }" +
                "    }," +
                "" +
                "    plotOptions: {" +
                "        columnrange: {" +
                "            dataLabels: {" +
                "                enabled: true," +
                "                formatter: function () {" +
                "                    return new Date(this.y).toLocaleString();" +
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
                "        name: 'Time'," +

                "        data: " + arrayDataForHm.get(1) + " " +
                "    }]" +
                "" +
                "});";


        jsStringForHmGraph = String.format("%s", stringJsCode);

        JsonObject jsonGraph = new JsonObject();
        jsonGraph.addProperty("jsCodeForGraph", jsStringForHmGraph);

        return jsonGraph;
    }

    public static JsonArray serializeDataForHmGraph(ResultSetWrappingSqlRowSet graphResultSet) {
        SqlRowSetMetaData setMetaData = graphResultSet.getMetaData();
        int rowCount = setMetaData.getColumnCount();
        ArrayList<String> xNameTask = new ArrayList<>();
        ArrayList<Date> startDate = new ArrayList<>();
        ArrayList<Date> endDate = new ArrayList<>();
        ArrayList<String> informationTask = new ArrayList<>();
        ResultSet graphSet = graphResultSet.getResultSet();
        DateFormat dateFormat = DateFormat.getDateFormatById(BigInteger.valueOf(1L));

        try {
            while (graphSet.next()) {
                xNameTask.add(graphSet.getString(rowNames.task.toString()));
               // String s=graphSet.getString(rowNames.start.toString());
                startDate.add(new StringToDateConverter(dateFormat).convertDateFromString(graphSet.getString(rowNames.start.toString())));
                endDate.add(new StringToDateConverter(dateFormat).convertDateFromString(graphSet.getString(rowNames.end.toString())));
                //startDate.add(graphSet.getString(rowNames.start.toString()));
              //  endDate.add(graphSet.getDate(rowNames.end.toString()));
                informationTask.add(graphSet.getString(rowNames.status.toString()) + " " + graphSet.getString(rowNames.errorC.toString()) + " "
                        + graphSet.getString(rowNames.errorM.toString()) + " " + ClobToStringService.clobToString(graphSet.getClob(rowNames.sqlText.toString())));

            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        JsonArray serizlizeData = new JsonArray();
        JsonArray nameArray = new JsonArray();
        JsonArray dateArray = new JsonArray();
        JsonArray informationArray = new JsonArray();

        for (int i = 0; i < xNameTask.size(); i++) {
            JsonArray temporaryDateArray = new JsonArray();

            nameArray.add(xNameTask.get(i));
            temporaryDateArray.add(startDate.get(i).getTime());
            temporaryDateArray.add(endDate.get(i).getTime());
            dateArray.add(temporaryDateArray);
            informationArray.add(informationTask.get(i));
        }

        serizlizeData.add(nameArray);
        serizlizeData.add(dateArray);
        serizlizeData.add(informationArray);

        return serizlizeData;
    }

    private static enum rowNames {
        task("TASK"),
        start("Time start"),
        end("Time end"),
        status("Status"),
        errorC("Error code"),
        errorM("Error message"),
        sqlText("Sql text");

        String nameRow;

        rowNames(String nameRow) {
            this.nameRow = nameRow;

        }

        @Override
        public String toString() {
            return nameRow;
        }
    }

}
