package com.dreamteam.datavisualizator.services.xmlparser;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XmlHandler extends DefaultHandler {

    public static final Logger LOGGER = Logger.getLogger(XmlHandler.class);

    private boolean isRow = false;
    private boolean isElement = false;
    List<Map<String, Object>> rows;
    private Map<String, Object> elements;
    private String elementName;
    private String elementValue;
    private int countOfRows = 0;
    private boolean stopFlag = false;

    public XmlHandler() {
    }

    public XmlHandler(boolean stopper, int countOfRows) {
        this.stopFlag = stopper;
        this.countOfRows = countOfRows;
    }

    @Override
    public void startDocument() throws SAXException {
        LOGGER.debug("Start parsing of file...");
    }

    @Override
    public void endDocument() throws SAXException {
        LOGGER.debug("End parsing of file...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "Table":
                rows = new ArrayList<>();
                break;
            case "Row":
                if (stopFlag && countOfRows == 0) {
                    throw new SaxTerminatorException();
                }
                isRow = true;
                elements = new LinkedHashMap<>();
                break;
            default:
                if (isRow) {
                    isElement = true;
                    elementName = qName;
                }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "Row":
                if (stopFlag) {
                    countOfRows--;
                }
                isRow = false;
                rows.add(elements);
                break;
        }
        if (isElement) {
            isElement = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = String.copyValueOf(ch, start, length).trim();
        if (isElement) {
            if (elementValue.matches("^([0-9]+\\.[0-9]+)|([0-9])$")) {
                elements.put(elementName, BigDecimal.valueOf(Double.parseDouble(elementValue)));
            } else {
                try {
                    if (elementValue.matches("^[0-9]{2}(\\.|\\-|\\/)[0-9]{2}(\\.|\\-|\\/)(([0-9]{4})|([0-9]{2}))$")) {
                        elements.put(elementName, new SimpleDateFormat("dd.MM.yyyy").parse(elementValue));
                    } else if (elementValue.matches("^[0-9]{2}(\\.|\\-|\\/)[0-9]{2}(\\.|\\-|\\/)(([0-9]{4})|([0-9]{2}))\\s([0-9]{1}|[0-9]{2})\\:[0-9]{2}\\:[0-9]{2}$")) {
                        elements.put(elementName, new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").parse(elementValue));
                    } else {
                        elements.put(elementName, elementValue);
                    }
                } catch (ParseException e) {
                    throw new SaxInvalidDateFormatException();
                }
            }
        }
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }
}
