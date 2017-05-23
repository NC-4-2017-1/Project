package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.common.utils.XmlParser;
import com.dreamteam.datavisualizator.common.utils.XmlRow;
import com.dreamteam.datavisualizator.common.utils.XmlTable;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class XmlParserTest {

    File file;
    XmlTable testTable;

    @Before
    public void setUp(){
        file = new File("src/test/resources/test_table.xml");
        testTable = new XmlTable();
        XmlRow row = new XmlRow();
        row.cells.add("id");
        row.cells.add("title");
        row.cells.add("date");
        testTable.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(1L));
        row.cells.add("test title 1");
        row.cells.add(new Date(1483221900000L));
        testTable.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(2L));
        row.cells.add("test title 2");
        row.cells.add(new Date(1483309235000L));
        testTable.rows.add(row);
        row = new XmlRow();
        row.cells.add(BigDecimal.valueOf(3L));
        row.cells.add("test title 3");
        row.cells.add(new Date(1483395635000L));
        testTable.rows.add(row);
    }

    @Test
    public void testParseXmlFile() throws IOException {
        XmlTable result = XmlParser.parseXmlFile(file);
        assertEquals(testTable, result);
    }

}
