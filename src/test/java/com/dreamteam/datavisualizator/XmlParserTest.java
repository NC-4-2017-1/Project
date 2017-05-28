package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.services.xmlparser.XmlParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class XmlParserTest {

    private File file;
    private List<Map<String, Object>> expectedRows1;
    private List<Map<String, Object>> expectedRows2;

    @Before
    public void setUp() throws ParseException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("test_xml_document.xml");
        file = new File(url.getPath());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        Date date1 = formatter.parse("29.04.2017 00:55:05");
        Date date2 = formatter.parse("12.05.2017 00:00:01");
        expectedRows1 = new ArrayList<>();
        expectedRows2 = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("Title", "SomeTitle1");
        row.put("Date", date1);
        row.put("Process_count", BigDecimal.valueOf(1002.03));
        row.put("Db_load", BigDecimal.valueOf(3.0));
        expectedRows1.add(row);
        expectedRows2.add(row);
        row = new LinkedHashMap<>();
        row.put("Title", "SomeTitle2");
        row.put("Date", date2);
        row.put("Process_count", BigDecimal.valueOf(3.0));
        row.put("Db_load", BigDecimal.valueOf(4.0));
        expectedRows1.add(row);
    }

    @Test
    @Ignore
    public void testParseXmlFileWithCorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = XmlParser.parseXmlFile(file, "EET");
        assertEquals(expectedRows1, result);
    }

    @Test
    @Ignore
    public void testParseXmlFileWithIncorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = XmlParser.parseXmlFile(file, "WET");
        assertNotEquals(expectedRows1, result);
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseXmlFileWithWrongPath() throws IOException {
        XmlParser.parseXmlFile(new File("wrong url"), "EET");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseXmlFileWithNullArgument() throws IOException {
        XmlParser.parseXmlFile(null, "EET");
    }

    @Test
    @Ignore
    public void testParseXmlFileWithLimitOfRowsAndCorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = XmlParser.parseXmlFile(file, "EET", 1);
        assertEquals(expectedRows2, result);
    }

    @Test
    @Ignore
    public void testParseXmlFileWithLimitOfRowsAndIncorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = XmlParser.parseXmlFile(file, "WET", 1);
        assertNotEquals(expectedRows2, result);
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseXmlFileWithLimitOfRowsAndWrongPath() throws IOException {
        XmlParser.parseXmlFile(new File("wrong url"), "EET", 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseXmlFileWithLimitOfRowsAndNullArgument() throws IOException {
        XmlParser.parseXmlFile(null, "EET", 3);
    }
}