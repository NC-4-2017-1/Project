package com.dreamteam.datavisualizator.common.exceptions;

import org.xml.sax.SAXException;

public class SaxTerminatorException extends SAXException {

    public SaxTerminatorException() {}

    public SaxTerminatorException(String message)
    {
        super(message);
    }
}
