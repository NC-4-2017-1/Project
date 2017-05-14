package com.dreamteam.datavisualizator.common;


import java.math.BigInteger;

public interface IdList {
    BigInteger USER_OBJTYPE_ID = new BigInteger("1");
    BigInteger PROJECT_OBJTYPE_ID = new BigInteger("2");
    BigInteger DATA_VISUALIZATION_PROJECT_OBJTYPE_ID = new BigInteger("3");
    BigInteger HEALTH_MONITOR_PROJECT_OBJTYPE_ID = new BigInteger("4");
    BigInteger SELECTOR_OBJTYPE_ID = new BigInteger("5");
    BigInteger GRAPHIC_OBJTYPE_ID = new BigInteger("6");
    BigInteger CORRELATION_OBJTYPE_ID = new BigInteger("7");

    BigInteger USER_EMAIL_ATTR_ID = new BigInteger("1");
    BigInteger USER_FIRST_NAME_ATTR_ID = new BigInteger("2");
    BigInteger USER_SECOND_NAME_ATTR_ID = new BigInteger("3");
    BigInteger USER_PASSWORD_ATTR_ID = new BigInteger("4");
    BigInteger USER_TYPE_ATTR_ID = new BigInteger("5");
    BigInteger PROJECT_DATE_ATTR_ID = new BigInteger("6");
    BigInteger PROJECT_NAME_ATTR_ID = new BigInteger("7");
    BigInteger PROJECT_DESCRIPTION_ATTR_ID = new BigInteger("8");
    BigInteger CALCULATE_VALUE_DVPROJECT_ATTR_ID = new BigInteger("9");
    BigInteger AVERAGE_DVPROJECT_ATTR_ID = new BigInteger("10");
    BigInteger OLIMPIC_AVERAGE_DVPROJECT_ATTR_ID = new BigInteger("11");
    BigInteger MATH_EXPECTATION_DVPROJECT_ATTR_ID = new BigInteger("12");
    BigInteger DISPERSION_DVPROJECT_ATTR_ID = new BigInteger("13");
    BigInteger JSON_RESULT_PROJECT_ATTR_ID = new BigInteger("14");
}
