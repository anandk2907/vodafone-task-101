package com.example.vodafonetask101.util;

import com.example.vodafonetask101.model.DeviceEvent;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class CsvUtil {
    public static String TYPE1 = "application/vnd.ms-excel";
    public static String TYPE2 = "text/csv";
    public static String TYPE_CSV = "csv";


    static String[] HEADERS = {"DateTime","EventId","ProductId","Latitude","Longitude","Battery","Light","AirplaneMode"};

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE2.equals(file.getContentType()) || TYPE1.equals(file.getContentType());
    }

    public static boolean hasCSVFormat(File file) {
        int dotIndex = file.getName().lastIndexOf('.');
        String fileType =  (dotIndex == -1) ? "" : file.getName().substring(dotIndex + 1);
        return TYPE_CSV.equals(fileType) || TYPE2.equals(fileType) || TYPE1.equals(fileType);
    }

    public static List<DeviceEvent> csvToEvents(InputStream is) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<DeviceEvent> events = new ArrayList<>();


            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                DeviceEvent event = new DeviceEvent();
                event.setDateTime(Long.valueOf(csvRecord.get("DateTime")));
                event.setEventId(Long.valueOf(csvRecord.get("EventId")));
                event.setProductId(csvRecord.get("ProductId"));
                event.setLatitude(csvRecord.get("Latitude"));
                event.setLongitude(csvRecord.get("Longitude"));
                event.setBattery(Float.valueOf(csvRecord.get("Battery")));
                event.setLight(csvRecord.get("Light"));
                event.setAirplaneMode(csvRecord.get("AirplaneMode"));
                events.add(event);
            }

            return events;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }



}
