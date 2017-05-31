package com.dreamteam.datavisualizator.services.xmlparser;

import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface XmlParser {

    List<Map<String, Object>> parseXmlFile(File file, DateFormat dateFormat) throws IOException;

    List<Map<String, Object>> parseXmlFile(File file, DateFormat dateFormat, int countOfRows) throws IOException;
}
