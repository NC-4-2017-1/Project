package com.dreamteam.datavisualizator.common;


import java.math.BigInteger;

public interface IdList {
    BigInteger USER_OBJTYPE_ID = BigInteger.valueOf(1L);
    BigInteger PROJECT_OBJTYPE_ID = BigInteger.valueOf(2L);
    BigInteger DATA_VISUALIZATION_PROJECT_OBJTYPE_ID = BigInteger.valueOf(3L);
    BigInteger HEALTH_MONITOR_PROJECT_OBJTYPE_ID =BigInteger.valueOf(4L);
    BigInteger SELECTOR_OBJTYPE_ID =BigInteger.valueOf(5L);
    BigInteger GRAPHIC_OBJTYPE_ID = BigInteger.valueOf(6L);
    BigInteger CORRELATION_OBJTYPE_ID = BigInteger.valueOf(7L);

    BigInteger USER_EMAIL_ATTR_ID =BigInteger.valueOf(1L);
    BigInteger USER_FIRST_NAME_ATTR_ID = BigInteger.valueOf(2L);
    BigInteger USER_LAST_NAME_ATTR_ID = BigInteger.valueOf(3L);
    BigInteger USER_PASSWORD_ATTR_ID = BigInteger.valueOf(4L);
    BigInteger USER_TYPE_ATTR_ID = BigInteger.valueOf(5L);
    BigInteger PROJECT_DATE_ATTR_ID = BigInteger.valueOf(6L);
    BigInteger PROJECT_NAME_ATTR_ID = BigInteger.valueOf(7L);
    BigInteger PROJECT_DESCRIPTION_ATTR_ID = BigInteger.valueOf(8L);
    BigInteger CALCULATE_VALUE_DVPROJECT_ATTR_ID =BigInteger.valueOf(9L);
    BigInteger AVERAGE_DVPROJECT_ATTR_ID = BigInteger.valueOf(10L);
    BigInteger OLIMPIC_AVERAGE_DVPROJECT_ATTR_ID = BigInteger.valueOf(11L);
    BigInteger MATH_EXPECTATION_DVPROJECT_ATTR_ID = BigInteger.valueOf(12L);
    BigInteger DISPERSION_DVPROJECT_ATTR_ID = BigInteger.valueOf(13L);
    BigInteger JSON_RESULT_PROJECT_ATTR_ID = BigInteger.valueOf(14L);
}
