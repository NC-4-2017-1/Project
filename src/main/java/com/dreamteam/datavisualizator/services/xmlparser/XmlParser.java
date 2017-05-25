package com.dreamteam.datavisualizator.services.xmlparser;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XmlParser {
    public static final Logger LOGGER = Logger.getLogger(XmlParser.class);

    public static XmlTable parseXmlFile(File file) throws IOException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        XmlHandler handler = new XmlHandler();
        try {
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(file, handler);
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.error("File not parsed", e);
            return null;
        }
        return handler.getTable();
    }

    public static XmlTable parseXmlFile(File file, int countOfRows) throws IOException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        XmlHandler handler = new XmlHandler(true, countOfRows);
        try {
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(file, handler);
        } catch (ParserConfigurationException e) {
            LOGGER.error("File not parsed", e);
            return null;
        } catch (SaxTerminatorException e){
            LOGGER.info("Parser stopped. Number of parsed rows: "+countOfRows);
        } catch (SAXException e){
            LOGGER.error("File not parsed, e");
        }
        return handler.getTable();
    }
}
