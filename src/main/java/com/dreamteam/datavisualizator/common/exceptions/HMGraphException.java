package com.dreamteam.datavisualizator.common.exceptions;


import org.springframework.dao.DataAccessException;

public class HMGraphException extends DataAccessException {

    public HMGraphException(String message)
    {
        super(message);
    }
}
