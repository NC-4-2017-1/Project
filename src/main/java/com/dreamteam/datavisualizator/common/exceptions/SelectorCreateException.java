package com.dreamteam.datavisualizator.common.exceptions;

import org.springframework.dao.DataAccessException;

public class SelectorCreateException extends DataAccessException {

    public SelectorCreateException(String message)
    {
        super(message);
    }
}
