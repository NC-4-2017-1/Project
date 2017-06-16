package com.dreamteam.datavisualizator.services;

import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
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

    public static String createHtmlTable(ResultSetWrappingSqlRowSet selectorResultSet, String cssId, int typeCell) {
        try {
            int rowCount = 0;
            StringBuffer htmlTable = new StringBuffer();
            htmlTable.append("<table class=\"table table-striped table-condensed selector\" id=\"" + cssId + "\">");
            SqlRowSetMetaData selectorSetMeta = selectorResultSet.getMetaData();
            int columnCount = selectorSetMeta.getColumnCount();
            htmlTable.append("<thead><tr>");
            for (int i = 0; i < columnCount; i++) {
                htmlTable.append("<th>" + selectorSetMeta.getColumnLabel(i + 1) + "</th>");
            }
            htmlTable.append("</tr></thead><tbody>");
            while (selectorResultSet.next()) {
                rowCount++;
                htmlTable.append("<tr>");
                for (int i = 0; i < columnCount; i++) {
                    if (typeCell == 0) {
                        htmlTable.append("<td>" + selectorResultSet.getString(i + 1) + "</td>");
                    } else {
                        htmlTable.append("<td><pre class = \"selectorPre\">" + selectorResultSet.getString(i + 1) + "</pre></td>");
                    }
                }
                htmlTable.append("</tr>");
            }
            htmlTable.append("</tbody></table>");
            if (rowCount == 0) {
                return "<div class=\"alert alert-info selector-info-mess\"><strong>Info!</strong> No data in database for this selector.</div>";
            }
            return htmlTable.toString();
        } catch (InvalidResultSetAccessException e) {
            LOGGER.error("Serialize selector error", e);
            throw new SelectorCreateException("Serialize selector error" + e.getLocalizedMessage());
        }
    }

    public static String createHtmlTableForClob(ResultSetWrappingSqlRowSet selectorResultSet, String cssId) throws SQLException {
        try {
            int rowCount = 0;
            StringBuffer htmlTable = new StringBuffer();
            htmlTable.append("<table class=\"table table-condensed selector\" id=\"" + cssId + "\">");
            SqlRowSetMetaData selectorSetMeta = selectorResultSet.getMetaData();
            int columnCount = selectorSetMeta.getColumnCount();
            htmlTable.append("<thead><tr>");
            for (int i = 0; i < columnCount; i++) {
                htmlTable.append("<th>" + selectorSetMeta.getColumnLabel(i + 1) + "</th>");
            }
            htmlTable.append("</tr></thead><tbody>");
            ResultSet resultData = selectorResultSet.getResultSet();
            while (resultData.next()) {
                rowCount++;
                htmlTable.append("<tr>");
                htmlTable.append("<td><pre class = \"selectorPre\">" + resultData.getString(1) + "</pre></td>");
                String clobString = ClobToStringService.clobToString(resultData.getClob(2));
                htmlTable.append("<td><pre class = \"selectorPre\">" + clobString + "</pre></td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</tbody></table>");
            if (rowCount == 0) {
                return "<div class=\"alert alert-info selector-info-mess\"><strong>Info!</strong> No data in database for this selector.</div>";
            }
            return htmlTable.toString();
        } catch (InvalidResultSetAccessException e) {
            LOGGER.error("Serialize selector with clob error", e);
            throw e;
        } catch (SQLException e) {
            LOGGER.error("Serialize selector with clob error", e);
            throw new SelectorCreateException("Serialize selector error" + e.getLocalizedMessage());
        }
    }

    public static String createHtmlTableForParsingFile(List<Map<String, Object>> resultPrepareParsing, String cssId) {
        StringBuffer htmlTable = new StringBuffer();
        htmlTable.append("<table class=\"table table-responsive table-condensed\" id=\"" + cssId + "\">");
        htmlTable.append("<thead><tr>");
        Set<String> headers = resultPrepareParsing.get(0).keySet();
        for (String header : headers) {
            htmlTable.append("<th>" + header + "</th>");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        htmlTable.append("</tr></thead><tbody>");
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
        htmlTable.append("</tbody></table>");
        return htmlTable.toString();
    }
}
