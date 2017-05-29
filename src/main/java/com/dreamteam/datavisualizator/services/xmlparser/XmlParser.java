package com.dreamteam.datavisualizator.services.xmlparser;

import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class XmlParser {
    public static final Logger LOGGER = Logger.getLogger(XmlParser.class);

    public static List<Map<String, Object>> parseXmlFile(File file, DateFormat dateFormat) throws IOException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        XmlHandler handler = new XmlHandler(dateFormat);
        try {
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(file, handler);
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.error("File not parsed", e);
            return null;
        }
        return handler.getRows();
    }

    public static List<Map<String, Object>> parseXmlFile(File file, DateFormat dateFormat, int countOfRows) throws IOException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        XmlHandler handler = new XmlHandler(dateFormat, true, countOfRows);
        try {
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(file, handler);
        } catch (ParserConfigurationException e) {
            LOGGER.error("File not parsed", e);
            return null;
        } catch (SaxTerminatorException e) {
            LOGGER.debug("Parser stopped. Number of parsed rows: " + countOfRows);
        } catch (SaxInvalidDateFormatException e){
            LOGGER.error("Parser stopped. Invalid format of the date in file", e);
            return null;
        } catch (SAXException e){
            LOGGER.error("File not parsed, e");
        }
        return handler.getRows();
    }
}
