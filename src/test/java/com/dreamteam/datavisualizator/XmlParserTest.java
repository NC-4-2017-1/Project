package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.common.utils.XmlParser;
import com.dreamteam.datavisualizator.common.utils.XmlRow;
import com.dreamteam.datavisualizator.common.utils.XmlTable;
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

import static org.junit.Assert.assertTrue;

public class XmlParserTest {

    File file;
    XmlTable testTable;

    @Before
    public void setUp(){
        URL url = Thread.currentThread().getContextClassLoader().getResource("test_table.xml");
        file = new File(url.getPath());

        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
        try {
            date1 = formatter.parse("2017-01-01T00:05:00.000");
            date2 = formatter.parse("2017-01-02T00:04:59.995");
            date3 = formatter.parse("2017-01-03T00:04:59.995");
        } catch (ParseException e) {
            e.printStackTrace();
            //TODO solution for exception
        }
        testTable = new XmlTable();
        XmlRow row = new XmlRow();
        row.cells.add("id");
        row.cells.add("title");
        row.cells.add("date");
        testTable.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(1L));
        row.cells.add("test title 1");
        row.cells.add(date1);
        testTable.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(2L));
        row.cells.add("test title 2");
        row.cells.add(date2);
        testTable.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(3L));
        row.cells.add("test title 3");
        row.cells.add(date3);
        testTable.rows.add(row);
    }

    @Test
    public void testParseXmlFile() throws IOException {
        XmlTable result = XmlParser.parseXmlFile(file);
        assertTrue(result.equals(testTable));
    }

    @Test (expected = FileNotFoundException.class)
    public void testParseXmlFileWithWrongPath() throws IOException {
        XmlParser.parseXmlFile(new File("wrong url"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testParseXmlFileWithNullArgument() throws IOException {
        XmlParser.parseXmlFile(null);
    }

}