package com.dreamteam.datavisualizator.services.csvparser;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class})
public class CsvParserImplTest {

    @Autowired
    private CsvParser parser;

    private File file;
    private List<Map<String, Object>> expectedRows1;
    private List<Map<String, Object>> expectedRows2;

    @Before
    public void setUp() throws ParseException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("testdocuments/svt_sample.csv");
        file = new File(url.getPath());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date date1 = formatter.parse("12.04.2017 00:25");
        Date date2 = formatter.parse("12.04.2017 02:15");
        expectedRows1 = new ArrayList<>();
        expectedRows2 = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("Date", date1);
        row.put("PROCESS_COUNT", BigDecimal.valueOf(14.0));
        row.put("DB_LOAD", BigDecimal.valueOf(5.0));
        row.put("THREAD_COUNT", BigDecimal.valueOf(15.0));
        row.put("SESSIONS_COUNT", BigDecimal.valueOf(15.0));
        expectedRows1.add(row);
        expectedRows2.add(row);
        row = new LinkedHashMap<>();
        row.put("Date", date2);
        row.put("PROCESS_COUNT", BigDecimal.valueOf(54.0));
        row.put("DB_LOAD", BigDecimal.valueOf(100.0));
        row.put("THREAD_COUNT", BigDecimal.valueOf(125.0));
        row.put("SESSIONS_COUNT", BigDecimal.valueOf(238.0));
        expectedRows1.add(row);
    }

    @Test
    public void testParseXmlFileWithCorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = parser.parseCsvFile(file, DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS);
        assertEquals(expectedRows1, result);
    }

    @Test(expected = IOException.class)
    public void testParseXmlFileWithIncorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = parser.parseCsvFile(file, DateFormat.EET_WITH_SLASH_DELIMITER);
        assertNull(result);
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseXmlFileWithWrongPath() throws IOException {
        parser.parseCsvFile(new File("wrong url"), DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS);
    }

    @Test(expected = NullPointerException.class)
    public void testParseXmlFileWithNullArgument() throws IOException {
        parser.parseCsvFile(null, DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS);
    }

    @Test
    public void testParseXmlFileWithLimitOfRowsAndCorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = parser.parseCsvFile(file, DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS, 1);
        assertEquals(expectedRows2, result);
    }

    @Test(expected = IOException.class)
    public void testParseXmlFileWithLimitOfRowsAndIncorrectTimeZone() throws IOException {
        List<Map<String, Object>> result = parser.parseCsvFile(file, DateFormat.EET_WITH_SLASH_DELIMITER, 1);
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseXmlFileWithLimitOfRowsAndWrongPath() throws IOException {
        parser.parseCsvFile(new File("wrong url"), DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS, 3);
    }

    @Test(expected = NullPointerException.class)
    public void testParseXmlFileWithLimitOfRowsAndNullArgument() throws IOException {
        parser.parseCsvFile(null, DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS, 3);
    }

}