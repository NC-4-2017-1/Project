package com.dreamteam.datavisualizator.common.utils;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class XmlHandler extends DefaultHandler {
    public static final Logger LOGGER = Logger.getLogger(XmlHandler.class);

    private XmlTable table = new XmlTable();
    private XmlRow row = null;
    private Object content = null;
    private String typeOfData = "";
    private int countOfRows = 0;
    private boolean stopFlag = false;

    public XmlHandler(boolean stopper, int countOfRows){
        this.stopFlag = stopper;
        this.countOfRows = countOfRows;
    }

    public XmlHandler(){
    }

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
        switch (qName) {
            case "Row":
                if (stopFlag&&countOfRows==0){
                    throw new SaxTerminatorException();
                }
                row = new XmlRow();
                break;
            case "Data":
                typeOfData = attributes.getValue(0);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "Row":
                if (stopFlag){
                    countOfRows--;
                }
                table.rows.add(row);
                break;
            case "Data":
                row.cells.add(content);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = String.copyValueOf(ch, start, length).trim();
        switch (typeOfData) {
            case "DateTime":
                try {
                    content = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss").parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "Number":
                content = BigDecimal.valueOf(Long.parseLong(value));
                break;
            default:
                content = value;
                break;
        }
        typeOfData = "";
    }

    public XmlTable getTable() {
        return table;
    }
}
