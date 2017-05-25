package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.services.xmlparser.XmlParser;
import com.dreamteam.datavisualizator.services.xmlparser.XmlRow;
import com.dreamteam.datavisualizator.services.xmlparser.XmlTable;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class XmlParserTest {

    private File file;
    private XmlTable expectedTable1;
    private XmlTable expectedTable2;

    @Before
    public void setUp() throws ParseException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("test_table.xml");
        file = new File(url.getPath());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
        Date date1 = formatter.parse("2017-01-01T00:05:00.000");
        Date date2 = formatter.parse("2017-01-02T00:04:59.995");
        Date date3 = formatter.parse("2017-01-03T00:04:59.995");
        expectedTable1 = new XmlTable();
        expectedTable2 = new XmlTable();
        XmlRow row = new XmlRow();
        row.cells.add("id");
        row.cells.add("title");
        row.cells.add("date");
        expectedTable1.rows.add(row);
        expectedTable2.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(1L));
        row.cells.add("test title 1");
        row.cells.add(date1);
        expectedTable1.rows.add(row);
        expectedTable2.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(2L));
        row.cells.add("test title 2");
        row.cells.add(date2);
        expectedTable1.rows.add(row);
        expectedTable2.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(3L));
        row.cells.add("test title 3");
        row.cells.add(date3);
        expectedTable1.rows.add(row);
    }

    @Test
    public void testParseXmlFile() throws IOException {
        XmlTable result = XmlParser.parseXmlFile(file);
        assertEquals(expectedTable1, result);
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseXmlFileWithWrongPath() throws IOException {
        XmlParser.parseXmlFile(new File("wrong url"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseXmlFileWithNullArgument() throws IOException {
        XmlParser.parseXmlFile(null);
    }

    @Test
    public void testParseXmlFileWithLimitOfRows() throws IOException {
        XmlTable result = XmlParser.parseXmlFile(file, 3);
        assertEquals(expectedTable2, result);
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseXmlFileWithLimitOfRowsAndWrongPath() throws IOException {
        XmlParser.parseXmlFile(new File("wrong url"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseXmlFileWithLimitOfRowsAndNullArgument() throws IOException {
        XmlParser.parseXmlFile(null);
    }
}