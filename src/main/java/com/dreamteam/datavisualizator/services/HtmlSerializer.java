package com.dreamteam.datavisualizator.services;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.sql.ResultSet;
import java.sql.SQLException;

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
            throw e;
        }
    }

    public static String createHtmlTableForClob(ResultSetWrappingSqlRowSet selectorResultSet, String cssId) throws SQLException {
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
            ResultSet resultData = selectorResultSet.getResultSet();
            while (resultData.next()) {
                rowCount++;
                htmlTable.append("<tr>");
                htmlTable.append("<td>" + resultData.getString(1) + "</td>");
                String clobString = ClobToStringService.clobToString(resultData.getClob(2));
                htmlTable.append("<td>" + clobString + "</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");
            if (rowCount == 0) {
                return "no data";
            }
            return htmlTable.toString();
        }catch (InvalidResultSetAccessException e){
            LOGGER.error("Serialize selector with clob error", e);
            throw e;
        } catch (SQLException e) {
            LOGGER.error("Serialize selector with clob error", e);
            throw e;
        }
    }
}
