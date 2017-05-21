package com.dreamteam.datavisualizator.common.utils;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XmlParser {
    public static final Logger LOGGER = Logger.getLogger(XmlParser.class);

    public static void parseXmlFile(File file){
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        Handler handler = new Handler();
        try {
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(file, handler);
        } catch (ParserConfigurationException e) {
            LOGGER.error("File not parsed", e);
        } catch (SAXException e) {
            LOGGER.error("File not parsed", e);
        } catch (IOException e){
            LOGGER.error("File not parsed", e);
        }

    }

}
