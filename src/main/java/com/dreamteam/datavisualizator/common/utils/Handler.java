package com.dreamteam.datavisualizator.common.utils;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler {
    public static final Logger LOGGER = Logger.getLogger(Handler.class);

    @Override
    public void startDocument() throws SAXException {
        LOGGER.info("Start parsing of file...");
    }

    @Override
    public void endDocument() throws SAXException {
        LOGGER.info("End parsing of file...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        LOGGER.info("Start parsing element: "+qName);
        //TODO body of method startElement
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        //TODO body of method endElement
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //TODO body of method characters
    }
}
