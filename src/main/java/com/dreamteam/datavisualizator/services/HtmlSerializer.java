package com.dreamteam.datavisualizator.services;

import org.apache.log4j.Logger;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HtmlSerializer {
    private static final Logger LOGGER = Logger.getLogger(HtmlSerializer.class);

    public static String createHtmlTable(ResultSetWrappingSqlRowSet selectorResultSet, String cssId) {
        try {
            int rowCount = 0;
            StringBuffer htmlTable = new StringBuffer();
            htmlTable.append("<table border=1 class=\"selector\" id=\"" + cssId + "\">");
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
                    htmlTable.append("<td><pre style=\"margin: 0;\">" + selectorResultSet.getString(i + 1) + "</pre></td>");
                }
                htmlTable.append("</tr>");
            }

            htmlTable.append("</table>");
            if (rowCount == 0) {
                return "no data";
            }
            return htmlTable.toString();
        } catch (InvalidResultSetAccessException e) {
            LOGGER.error("Serialize selector error", e);
            throw e;
        }
    }

    public static String createHtmlTableForClob(ResultSetWrappingSqlRowSet selectorResultSet, String cssId) throws SQLException {
        try {
            int rowCount = 0;
            StringBuffer htmlTable = new StringBuffer();
            htmlTable.append("<table border=1 class=\"selector\" id=\"" + cssId + "\">");
            SqlRowSetMetaData selectorSetMeta = selectorResultSet.getMetaData();
            int columnCount = selectorSetMeta.getColumnCount();
            htmlTable.append("<tr>");
            for (int i = 0; i < columnCount; i++) {
                htmlTable.append("<th>" + selectorSetMeta.getColumnLabel(i + 1) + "</th>");
            }
            htmlTable.append("</tr>");
            ResultSet resultData = selectorResultSet.getResultSet();
            while (resultData.next()) {
                rowCount++;
                htmlTable.append("<tr>");
                htmlTable.append("<td><pre style=\"margin: 0;\">" + resultData.getString(1) + "</pre></td>");
                String clobString = ClobToStringService.clobToString(resultData.getClob(2));
                htmlTable.append("<td><pre style=\"margin: 0;\">" + clobString + "</pre></td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");
            if (rowCount == 0) {
                return "no data";
            }
            return htmlTable.toString();
        } catch (InvalidResultSetAccessException e) {
            LOGGER.error("Serialize selector with clob error", e);
            throw e;
        } catch (SQLException e) {
            LOGGER.error("Serialize selector with clob error", e);
            throw e;
        }
    }

    public static String createHtmlTableForParsingFile(List<Map<String, Object>> resultPrepareParsing, String cssId) {
        StringBuffer htmlTable = new StringBuffer();
        htmlTable.append("<table border=1 class=\"selector\" id=\"" + cssId + "\">");
        htmlTable.append("<tr>");
        Set<String> headers = resultPrepareParsing.get(0).keySet();
        for (String header : headers) {
            htmlTable.append("<th>" + header + "</th>");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        htmlTable.append("</tr>");
        for (Map<String, Object> row : resultPrepareParsing) {
            htmlTable.append("<tr>");
            for (String header : headers) {
                if (row.get(header) instanceof Date) {
                    htmlTable.append("<td>" + dateFormat.format((Date) row.get(header)) + "</td>");
                } else {
                    htmlTable.append("<td>" + row.get(header) + "</td>");
                }
            }
            htmlTable.append("</tr>");
        }
        htmlTable.append("</table>");
        return htmlTable.toString();
    }
}
