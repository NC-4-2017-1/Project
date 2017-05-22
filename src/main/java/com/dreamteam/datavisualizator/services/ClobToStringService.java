package com.dreamteam.datavisualizator.services;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class ClobToStringService {
    private static final Logger LOGGER = Logger.getLogger(ClobToStringService.class);

    public static String clobToString(java.sql.Clob data) {
        if (data != null) {
            final StringBuilder sb = new StringBuilder();

            try {
                final Reader reader = data.getCharacterStream();
                final BufferedReader br = new BufferedReader(reader);

                int b;
                while (-1 != (b = br.read())) {
                    sb.append((char) b);
                }

                br.close();
            } catch (SQLException e) {
                LOGGER.error("SQL. Could not convert CLOB to string", e);
            } catch (IOException e) {
                LOGGER.error("IO. Could not convert CLOB to string", e);
            }

            return sb.toString();
        } else return null;
    }
}
