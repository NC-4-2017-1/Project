package com.dreamteam.datavisualizator.common;

public interface SQL_Query_Constants {
    String INSERT_USER = "call insert_user(?,?,?,?)";
    String INSERT_DV_PROJECT = "call insert_dv_project(?,?,?)";
    String SELECT_USER_BY_MAIL="select obj_user.OBJECT_ID id, first_name.VALUE first_name, last_name.VALUE last_name" +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " +IdList.USER_OBJTYPE_ID.toString() +" "+
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + IdList.USER_FIRST_NAME_ATTR_ID+" "+
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + IdList.USER_LAST_NAME_ATTR_ID +" "+
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " +IdList.USER_EMAIL_ATTR_ID +" "+
            " and email.value=?";
    String GET_ALL_USERS ="";
    String SELECT_USER_BY_FULLNAME="select obj_user.OBJECT_ID id,  first_name.VALUE first_name, last_name.VALUE last_name, email.VALUE email " +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " +IdList.USER_OBJTYPE_ID.toString() +" "+
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + IdList.USER_FIRST_NAME_ATTR_ID+" "+
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + IdList.USER_LAST_NAME_ATTR_ID +" "+
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " +IdList.USER_EMAIL_ATTR_ID +" "+
            " and obj_user.name=?";
    String SELECT_USER_BY_ID="select obj_user.OBJECT_ID id, first_name.VALUE first_name, last_name.VALUE last_name, email.VALUE email" +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " +IdList.USER_OBJTYPE_ID.toString() +" "+
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + IdList.USER_FIRST_NAME_ATTR_ID+" "+
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + IdList.USER_LAST_NAME_ATTR_ID +" "+
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " +IdList.USER_EMAIL_ATTR_ID +" "+
            " and obj_user.OBJECT_ID=?";
}
