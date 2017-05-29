package com.dreamteam.datavisualizator.common.dateconverter;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter {

    private static Logger LOGGER = Logger.getLogger(StringToDateConverter.class);

    private DateFormat pattern;

    public StringToDateConverter(DateFormat pattern){
        this.pattern=pattern;
    }

    public Date convertDateFromString(String dateValue){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern.toString());
        formatter.setLenient(false);
        Date result;
        try {
            result = formatter.parse(dateValue);
        } catch (ParseException e) {
            LOGGER.error("Invalid format of the date", e);
            return null;
        }
        return result;
    }

}
