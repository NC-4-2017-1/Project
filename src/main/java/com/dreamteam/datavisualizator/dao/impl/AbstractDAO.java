package com.dreamteam.datavisualizator.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;

@Repository
public  abstract class AbstractDAO {
    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    @Autowired
    private JdbcTemplate generalTemplate;


    protected BigInteger createObject(String name, BigInteger objectTypeId) {
        simpleCallTemplate.withFunctionName(INSERT_OBJECT);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("obj_type_id", objectTypeId)
                .addValue("obj_name", name);
        return simpleCallTemplate.executeFunction(BigDecimal.class, in).toBigInteger();
    }

    protected static final String INSERT_OBJECT = "insert_object";
    protected static final String INSERT_ATTR_VALUE = "insert into attributes(attr_id, object_id, value) values (?, ?, ?)";
    protected static final String INSERT_ATTR_DATE_VALUE = "insert into attributes(attr_id, object_id, date_value) values (?, ?, ?)";
    protected static final String INSERT_ATTR_LIST_VALUE = "insert into attributes(attr_id, object_id, list_value_id) values (?, ?, ?)";
    protected static final String INSERT_OBJREFERENCE_RELATION = "insert into objreference(attr_id, reference, object_id) values (?, ?, ?)";


}
