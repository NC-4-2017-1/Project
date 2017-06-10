package com.dreamteam.datavisualizator.services;


import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.common.dateconverter.StringToDateConverter;
import com.dreamteam.datavisualizator.common.exceptions.HMGraphException;
import com.dreamteam.datavisualizator.common.exceptions.HMGraphSerializerException;
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

    public static JsonObject serialiseHmGraph(ResultSetWrappingSqlRowSet graphResultSet) throws HMGraphSerializerException {
        try {
            JsonArray arrayDataForHm = serializeDataForHmGraph(graphResultSet);

            String stringJsCode = "var chart = new Highcharts.chart('containerGraphic', {" +
                    "    chart: {" +
                    "        type: 'columnrange'," +
                    "        inverted: true," +
                    "        zoomType: 'xy'" +
                    "    }," +
                    "    tooltip: {" +
                    "        backgroundColor: '#FCFFC5'," +
                    "        formatter: function () {" +
                    "            return '<b>' + this.x + '</b><br>'+'<b>Start: </b>'+new Date(this.point.low).toLocaleString()+' <b>End:</b> '+new Date(this.point.high).toLocaleString() + ' ' + this.point.name;" +
                    "        }" +
                    "    }," +
                    "" +
                    "    title: {" +
                    "        text: 'Tasks by Date-Time'" +
                    "    }," +
                    "" +
                    "    xAxis: {" +
                    "        categories: " + arrayDataForHm.get(0) + "" +
                    "    }," +
                    "" +
                    "    yAxis: {" +
                    "       type: 'datetime'," +
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
        }catch (Exception e){
            LOGGER.error("HM graph serialize exception. ", e);
            throw new HMGraphSerializerException("HM graph can not be serialized." + e.getMessage());
        }
    }

    public static JsonArray serializeDataForHmGraph(ResultSetWrappingSqlRowSet graphResultSet) {
        SqlRowSetMetaData setMetaData = graphResultSet.getMetaData();
        int rowCount = 0;
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
                informationTask.add("<br><b>Status:</b> " + (graphSet.getString(rowNames.status.toString()) == null?" not defined":graphSet.getString(rowNames.status.toString()))
                        + "; <br><b>Error code:</b> " + (graphSet.getString(rowNames.errorC.toString()) == null ?" not defined":graphSet.getString(rowNames.errorC.toString()))
                        + "; <br><b>Error message: </b>"  + (graphSet.getString(rowNames.errorM.toString()) == null?" not defined":graphSet.getString(rowNames.errorM.toString()))
                        + "; <br><b>SQL:</b> " + ClobToStringService.clobToString(graphSet.getClob(rowNames.sqlText.toString())));

            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        JsonArray serizlizeData = new JsonArray();
        JsonArray nameArray = new JsonArray();
        JsonArray dateArray = new JsonArray();

        for (int i = 0; i < xNameTask.size(); i++) {
            JsonArray temporaryDateArray = new JsonArray();

            nameArray.add(xNameTask.get(i));
            temporaryDateArray.add(informationTask.get(i));
            temporaryDateArray.add(startDate.get(i).getTime());
            temporaryDateArray.add(endDate.get(i).getTime());
            dateArray.add(temporaryDateArray);
        }

        serizlizeData.add(nameArray);
        serizlizeData.add(dateArray);
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
