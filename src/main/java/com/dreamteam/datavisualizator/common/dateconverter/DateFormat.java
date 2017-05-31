package com.dreamteam.datavisualizator.common.dateconverter;

import java.math.BigInteger;

public enum DateFormat {

    EET_WITH_TIME_AND_DOT_DELIMITER (BigInteger.valueOf(1L), "dd.MM.yy HH:mm:ss"),
    EET_WITH_DOT_DELIMITER (BigInteger.valueOf(2L), "dd.MM.yy"),
    EET_WITH_TIME_AND_HYPHEN_DELIMITER(BigInteger.valueOf(3L), "dd-MM-yy HH:mm:ss"),
    EET_WITH_HYPHEN_DELIMITER(BigInteger.valueOf(4L), "dd-MM-yy"),
    EET_WITH_TIME_AND_SLASH_DELIMITER(BigInteger.valueOf(5L), "dd/MM/yy HH:mm:ss"),
    EET_WITH_SLASH_DELIMITER(BigInteger.valueOf(6L), "dd/MM/yy"),

    WET_WITH_TIME_AND_DOT_DELIMITER(BigInteger.valueOf(7L), "MM.dd.yy HH:mm:ss"),
    WET_WITH_DOT_DELIMITER(BigInteger.valueOf(8L), "MM.dd.yy"),
    WET_WITH_TIME_AND_HYPHEN_DELIMITER(BigInteger.valueOf(9L), "MM-dd-yy HH:mm:ss"),
    WET_WITH_HYPHEN_DELIMITER(BigInteger.valueOf(10L), "MM-dd-yy"),
    WET_WITH_TIME_AND_SLASH_DELIMITER(BigInteger.valueOf(11L), "MM/dd/yy HH:mm:ss"),
    WET_WITH_SLASH_DELIMITER(BigInteger.valueOf(12L), "MM/dd/yy"),

    EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS (BigInteger.valueOf(13L), "dd.MM.yy HH:mm"),
    EET_WITH_TIME_AND_HYPHEN_DELIMITER_WITHOUT_SECONDS(BigInteger.valueOf(14L), "dd-MM-yy HH:mm"),
    EET_WITH_TIME_AND_SLASH_DELIMITER_WITHOUT_SECONDS(BigInteger.valueOf(15L), "dd/MM/yy HH:mm"),
    WET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS(BigInteger.valueOf(16L), "MM.dd.yy HH:mm"),
    WET_WITH_TIME_AND_HYPHEN_DELIMITER_WITHOUT_SECONDS(BigInteger.valueOf(17L), "MM-dd-yy HH:mm"),
    WET_WITH_TIME_AND_SLASH_DELIMITER_WITHOUT_SECONDS(BigInteger.valueOf(18L), "MM/dd/yy HH:mm");
    private final BigInteger id;
    private final String dateFormat;

    DateFormat(BigInteger id, String dateFormat){
        this.id = id;
        this.dateFormat = dateFormat;
    }

    public static DateFormat getDateFormatById(BigInteger id) {
        for (DateFormat e : DateFormat.values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }

    public BigInteger getId() {
        return id;
    }

    @Override
    public String toString() {
        return dateFormat;
    }

}
