package com.dreamteam.datavisualizator.services.csvparser;

import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.common.dateconverter.StringToDateConverter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CsvParserImpl implements CsvParser {
    public static final Logger LOGGER = Logger.getLogger(CsvParserImpl.class);

    public List<Map<String, Object>> parseCsvFile(File file, DateFormat dateFormat) throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        Reader reader = new FileReader(file);
        Map<String, Object> elements;
        try {
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            Map<String, Integer> headers = csvParser.getHeaderMap();
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord record : records) {
                elements = new LinkedHashMap<>();
                Map<String, String> headerValue = record.toMap();
                parseRowByType(dateFormat, elements, headers, headerValue);
                rows.add(elements);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("File not parsed", e);
            return null;
        } catch (IOException e) {
            LOGGER.error("Wrong file format", e);
            throw new IOException(e);
        } finally {
            reader.close();
            LOGGER.error("Parse of file is finished with mistakes");
        }
        return rows;
    }

    public List<Map<String, Object>> parseCsvFile(File file, DateFormat dateFormat, int countOfRows) throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> elements;
        try {

            Reader reader = new FileReader(file);
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            Map<String, Integer> headers = csvParser.getHeaderMap();
            List<CSVRecord> records = csvParser.getRecords();
            int i = 0;
            for (CSVRecord record : records) {

                elements = new LinkedHashMap<>();
                Map<String, String> headerValue = record.toMap();
                parseRowByType(dateFormat, elements, headers, headerValue);
                rows.add(elements);
                i++;

                if (i >= countOfRows) {
                    csvParser.close();
                    reader.close();
                    return rows;
                }
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("File not parsed", e);
            return null;
        } catch (IOException e) {
            LOGGER.error("Wrong file format", e);
            throw new IOException(e);
        } finally {
            LOGGER.error("Parse of file is finished with mistakes");
        }
        return rows;
    }

    private void parseRowByType(DateFormat dateFormat, Map<String, Object> elements, Map<String, Integer> headers, Map<String, String> headerValue) {
        for (String header : headers.keySet()) {
            String value = headerValue.get(header).trim();
            if (value.matches("^([0-9]+\\.[0-9]+)|([0-9])$")) {
                elements.put(header, BigDecimal.valueOf(Double.parseDouble(value)));
            } else if (value.matches("^([0-9]*(\\.|-|/)[0-9]*(\\.|-|/)[0-9]*)|([0-9]*(\\.|-|/)[0-9]*(\\.|-|/)[0-9]*\\s[0-9]*:[0-9]*:[0-9]*)$") || value.matches("^([0-9]*(\\.|-|/)[0-9]*(\\.|-|/)[0-9]*)|([0-9]*(\\.|-|/)[0-9]*(\\.|-|/)[0-9]*\\s[0-9]*:[0-9]*)$")) {
                Date date = new StringToDateConverter(dateFormat).convertDateFromString(value);
                elements.put(header, date);
            } else {
                elements.put(header, value);
            }
        }
    }


}
