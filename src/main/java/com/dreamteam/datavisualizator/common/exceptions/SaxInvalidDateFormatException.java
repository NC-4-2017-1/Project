package com.dreamteam.datavisualizator.common.exceptions;

import org.xml.sax.SAXException;

public class SaxInvalidDateFormatException extends SAXException {

    public SaxInvalidDateFormatException() {}

    public SaxInvalidDateFormatException(String message)
    {
        super(message);
    }
}
