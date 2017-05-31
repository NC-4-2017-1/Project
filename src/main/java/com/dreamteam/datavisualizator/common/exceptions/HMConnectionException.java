package com.dreamteam.datavisualizator.common.exceptions;


import org.springframework.dao.DataAccessException;

public class HMConnectionException extends DataAccessException {

    public HMConnectionException(String message)
    {
        super(message);
    }
}
