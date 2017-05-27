package com.dreamteam.datavisualizator.services.xmlparser;

import org.xml.sax.SAXException;

public class SaxInvalidDateFormatException extends SAXException {

    public SaxInvalidDateFormatException() {}

    public SaxInvalidDateFormatException(String message)
    {
        super(message);
    }
}
