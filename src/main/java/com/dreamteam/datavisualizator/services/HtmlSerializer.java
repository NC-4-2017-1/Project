package com.dreamteam.datavisualizator.services;

import org.apache.log4j.Logger;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class HtmlSerializer {
    private static final Logger LOGGER = Logger.getLogger(HtmlSerializer.class);

    public static String createHtmlTable(ResultSetWrappingSqlRowSet selectorResultSet, String cssId){
        try {
            int rowCount = 0;
            StringBuffer htmlTable = new StringBuffer();
            htmlTable.append("<table border=1 class=\"selector\" id=\""+cssId+"\">");
            SqlRowSetMetaData selectorSetMeta = selectorResultSet.getMetaData();
            int columnCount = selectorSetMeta.getColumnCount();
            htmlTable.append("<tr>");
            for (int i = 0; i < columnCount; i++) {
                htmlTable.append("<th>" + selectorSetMeta.getColumnLabel(i + 1) + "</th>");
            }
            htmlTable.append("</tr>");
            while (selectorResultSet.next()) {
                rowCount++;
                htmlTable.append("<tr>");
                for (int i = 0; i < columnCount; i++) {
                    htmlTable.append("<td>" + selectorResultSet.getString(i + 1) + "</td>");
                }
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");
            if (rowCount == 0) {
                return "no data";
            }
            return htmlTable.toString();
        }catch (InvalidResultSetAccessException e){
            LOGGER.error("Serialize selector error", e);
            return "no data";
        }
    }
}
