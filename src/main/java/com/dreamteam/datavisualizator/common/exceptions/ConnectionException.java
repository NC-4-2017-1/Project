package com.dreamteam.datavisualizator.common.exceptions;


import org.springframework.dao.DataAccessException;

public class ConnectionException extends DataAccessException {

    public ConnectionException(String message)
    {
        super(message);
    }
}
