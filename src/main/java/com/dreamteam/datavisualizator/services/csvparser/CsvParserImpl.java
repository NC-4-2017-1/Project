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
import java.util.zip.DataFormatException;

@Service
public class CsvParserImpl implements CsvParser {
    public static final Logger LOGGER = Logger.getLogger(CsvParserImpl.class);

    public List<Map<String, Object>> parseCsvFile(File file, DateFormat dateFormat) throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        Reader reader = new FileReader(file);
        Map<String, Object> elements;
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
        CSVParser csvParser = new CSVParser(reader, csvFormat);
        try {
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
            csvParser.close();
            reader.close();
        }
        return rows;
    }

    public List<Map<String, Object>> parseCsvFile(File file, DateFormat dateFormat, int countOfRows) throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> elements;
        Reader reader = new FileReader(file);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
        CSVParser csvParser = new CSVParser(reader, csvFormat);
        try {

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
            csvParser.close();
            reader.close();
        }
        return rows;

    }

    private void parseRowByType(DateFormat dateFormat, Map<String, Object> elements, Map<String, Integer> headers, Map<String, String> headerValue) throws IOException {
        for (String header : headers.keySet()) {
            String value = headerValue.get(header).trim();
            if (value.matches(checkStringConvet.toDouble.toString())) {
                elements.put(header, BigDecimal.valueOf(Double.parseDouble(value)));
            } else if (value.matches(checkStringConvet.toDateWithSec.toString()) || value.matches(checkStringConvet.toDateWithoutSec.toString())) {
                Date date = new StringToDateConverter(dateFormat).convertDateFromString(value);
                try{
                    if(date==null){
                        throw new DataFormatException();
                    }
                } catch (DataFormatException e) {
                    LOGGER.error("Wrong date. Stop parcing file");
                    throw new IOException("wrong date");
                }
                elements.put(header, date);
            } else {
                elements.put(header, value);
            }
        }
    }
    private enum checkStringConvet{
        toDouble("\\-?\\d+(\\.\\d{0,})?"),
        toDateWithSec(" ^ ([0 - 9] * (\\.|-|/)[0-9]*(\\.|-|/)[0-9]*)|([0-9]*(\\.|-|/)[0-9]*(\\.|-|/)[0-9]*\\s[0-9]*:[0-9]*:[0-9]*)$"),
        toDateWithoutSec("^([0-9]*(\\.|-|/)[0-9]*(\\.|-|/)[0-9]*)|([0-9]*(\\.|-|/)[0-9]*(\\.|-|/)[0-9]*\\s[0-9]*:[0-9]*)$");

        private final String stringConvert;

        checkStringConvet(String stringConvert) {
            this.stringConvert=stringConvert;
        }
        @Override
        public String toString() {
            return stringConvert;
        }
    }
}
