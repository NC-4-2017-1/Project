package com.dreamteam.datavisualizator.services;


import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConvertDateToMs {
    private static final Logger LOGGER = Logger.getLogger(JsonSerializer.class);

    public static Long convertDateToMs(String date){
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
