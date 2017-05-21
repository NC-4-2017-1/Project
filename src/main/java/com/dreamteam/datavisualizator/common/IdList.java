package com.dreamteam.datavisualizator.common;


import java.math.BigInteger;

public interface IdList {
    BigInteger USER_OBJTYPE_ID = BigInteger.valueOf(1L);
    BigInteger PROJECT_OBJTYPE_ID = BigInteger.valueOf(2L);
    BigInteger HEALTH_MONITOR_PROJECT_OBJTYPE_ID =BigInteger.valueOf(3L);
    BigInteger DATA_VISUALIZATION_PROJECT_OBJTYPE_ID = BigInteger.valueOf(4L);
    BigInteger SELECTOR_OBJTYPE_ID =BigInteger.valueOf(5L);
    BigInteger GRAPHIC_OBJTYPE_ID = BigInteger.valueOf(6L);
    BigInteger CORRELATION_OBJTYPE_ID = BigInteger.valueOf(7L);
    BigInteger S_INSTANCE_INFO_OBJTYPE_ID = BigInteger.valueOf(8L);
    BigInteger S_SIZE_TABLESPACE_OBJTYPE_ID = BigInteger.valueOf(9L);
    BigInteger S_SIZE_INDEX_LOB_OBJTYPE_ID = BigInteger.valueOf(10L);
    BigInteger S_LAST_ERRORS_OBJTYPE_ID = BigInteger.valueOf(11L);
    BigInteger S_ACTIVE_SESSIONS_OBJTYPE_ID = BigInteger.valueOf(12L);
    BigInteger S_ACTIVE_QUERIES_OBJTYPE_ID = BigInteger.valueOf(13L);
    BigInteger S_QUERIES_RESULTS_OBJTYPE_ID = BigInteger.valueOf(14L);
    BigInteger S_SQL_MONITOR_OBJTYPE_ID = BigInteger.valueOf(15L);
    BigInteger S_DB_LOCKS_OBJTYPE_ID = BigInteger.valueOf(16L);
    BigInteger S_ACTIVE_JOBS_OBJTYPE_ID = BigInteger.valueOf(17L);

    BigInteger USER_EMAIL_ATTR_ID =BigInteger.valueOf(1L);
    BigInteger USER_FIRST_NAME_ATTR_ID = BigInteger.valueOf(2L);
    BigInteger USER_LAST_NAME_ATTR_ID = BigInteger.valueOf(3L);
    BigInteger PASSWORD_ATTR_ID = BigInteger.valueOf(4L);
    BigInteger USER_TYPE_ATTR_ID = BigInteger.valueOf(5L);
    BigInteger PROJECT_DATE_ATTR_ID = BigInteger.valueOf(6L);
    BigInteger PROJECT_DESCRIPTION_ATTR_ID = BigInteger.valueOf(7L);
    BigInteger CALCULATE_VALUE_ATTR_ID =BigInteger.valueOf(8L);
    BigInteger AVERAGE_DVPROJECT_ATTR_ID = BigInteger.valueOf(9L);
    BigInteger OLYMPIC_AVERAGE_DVPROJECT_ATTR_ID = BigInteger.valueOf(10L);
    BigInteger MATH_EXPECTATION_DVPROJECT_ATTR_ID = BigInteger.valueOf(11L);
    BigInteger DISPERSION_DVPROJECT_ATTR_ID = BigInteger.valueOf(12L);
    BigInteger JSON_RESULT_ATTR_ID = BigInteger.valueOf(13L);
    BigInteger SEGMENT_ATTR_ID = BigInteger.valueOf(14L);
    BigInteger TOP_ATTR_ID = BigInteger.valueOf(15L);
    BigInteger HOUR_COUNT_ATTR_ID = BigInteger.valueOf(16L);
    BigInteger HM_SERVER_NAME_ATTR_ID = BigInteger.valueOf(23L);
    BigInteger HM_PORT_ATTR_ID = BigInteger.valueOf(24L);
    BigInteger HM_SID_ATTR_ID = BigInteger.valueOf(25L);
    BigInteger HM_USER_NAME_ATTR_ID = BigInteger.valueOf(26L);

    BigInteger PROJECT_AUTHOR_RELATION_ATTR_ID = BigInteger.valueOf(17L);
    BigInteger PROJECT_SHARED_RELATION_ATTR_ID = BigInteger.valueOf(18L);
    BigInteger PROJECT_SELECTORS_RELATION_ATTR_ID = BigInteger.valueOf(19L);
    BigInteger PROJECT_GRAPHICS_RELATION_ATTR_ID = BigInteger.valueOf(20L);
    BigInteger CORR_FIRST_GRAPHICS_RELATION_ATTR_ID = BigInteger.valueOf(21L);
    BigInteger CORR_SECOND_GRAPHICS_RELATION_ATTR_ID = BigInteger.valueOf(22L);
}
