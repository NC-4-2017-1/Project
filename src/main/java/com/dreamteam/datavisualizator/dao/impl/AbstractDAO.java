package com.dreamteam.datavisualizator.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;

@Repository
public abstract class AbstractDAO {
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class);

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
    protected static final String INSERT_ATTR_BIG_VALUE = "insert into attributes(attr_id, object_id, big_value) values (?, ?, ?)";
    protected static final String INSERT_OBJREFERENCE_RELATION = "insert into objreference(attr_id, reference, object_id) values (?, ?, ?)";


    protected static final String DELETE_OBJECT = "delete from objects where object_id = ?";

    protected static final String SELECT_USERS_THAT_HAVE_ACCESS_TO_PROJECT = "select obj_user.object_id id, first_name.value first_name, \n" +
            " last_name.value last_name, email.value email, usertype.list_value_id usertype " +
            " from objects obj_user, attributes first_name, attributes last_name, attributes email, " +
            " attributes usertype, objreference ref, objects project " +
            " where obj_user.object_type_id = 1 " +
            " and obj_user.object_id = first_name.object_id " +
            " and first_name.attr_id = 2 " +
            " and obj_user.object_id = last_name.object_id " +
            " and last_name.attr_id = 3 " +
            " and obj_user.object_id = email.object_id " +
            " and email.attr_id = 1 " +
            " and obj_user.object_id = usertype.object_id " +
            " and usertype.attr_id = 5 " +
            " and usertype.list_value_id = 1 " +
            " and ref.attr_id = 18 " +
            " and ref.object_id = project.object_id " +
            " and ref.reference = obj_user.object_id " +
            " and project.object_id=?";



}
