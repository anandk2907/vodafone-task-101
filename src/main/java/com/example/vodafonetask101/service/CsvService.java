package com.example.vodafonetask101.service;

import com.example.vodafonetask101.exception.DeviceNotReportedException;
import com.example.vodafonetask101.exception.ResourceNotFoundException;
import com.example.vodafonetask101.model.DeviceEvent;
import com.example.vodafonetask101.model.DeviceReport;
import com.example.vodafonetask101.model.EventPath;
import com.example.vodafonetask101.util.CsvUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvService {

    List<DeviceEvent> events;

    public void save(MultipartFile file) {
        try {
            events = CsvUtil.csvToEvents(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public void save(InputStream inputStream) {
        refreshEvents();
        events = CsvUtil.csvToEvents(inputStream);
    }

    private void refreshEvents (){
        events = new ArrayList<>();
    }

    public List<DeviceEvent> getAllEvents() {
        return events;
    }

    public String processDataFromMultiPartFile (MultipartFile file){
        if (CsvUtil.hasCSVFormat(file)) {
            try {
                save(file);
                return "Uploaded the file successfully: " + file.getOriginalFilename();
            } catch (Exception e) {
                return "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        }
        throw new ResourceNotFoundException("ERROR: no data file found");
    }

    public String processDataFromCsvFile(EventPath eventPath) {
        File initialFile = new File(eventPath.getFilePath());
        if (CsvUtil.hasCSVFormat(initialFile)) {
            try {
                InputStream targetStream = new FileInputStream(initialFile);
                save(targetStream);
                return "Uploaded the file successfully: " + initialFile.getName();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        throw new ResourceNotFoundException("ERROR: no data file found");
    }


    public DeviceReport getEventByProductIdAndDateTime(String productId, Long dateTime){

        List<DeviceEvent> matchedProducts = getMatchedDeviceEvents(productId);

        Collections.reverse(matchedProducts);

        if (dateTime == null) {
            dateTime = System.currentTimeMillis();
        }

        for (DeviceEvent matchedProduct : matchedProducts) {
            if (isMatchedDateTime(dateTime, matchedProduct)){
                if (matchedProduct.getProductId().startsWith("WG")){
                    if (matchedProducts.size() < 3){
                        matchedProduct.setActive("N/A");
                    }else {
                        for (int j=0; j < 3; j++ ){
                            String gpsData = matchedProducts.get(j).getLatitude() + matchedProducts.get(j).getLongitude();
                            String gpsData0 = matchedProducts.get(0).getLatitude() + matchedProducts.get(0).getLongitude();
                            if (!gpsData.equals(gpsData0)) {
                                matchedProduct.setActive("Active");
                                break;
                            }else {
                                matchedProduct.setActive("Inactive");
                            }
                        }
                    }
                }
               return createDeviceStatusReport(matchedProduct);
            }
        }

        throw new ResourceNotFoundException("ERROR: Id: " + productId + " not found");
    }

    private boolean isMatchedDateTime(Long dateTime, DeviceEvent deviceEvent) {
        return (dateTime.equals(deviceEvent.getDateTime()) || dateTime > deviceEvent.getDateTime());
    }

    private List<DeviceEvent> getMatchedDeviceEvents(String productId) {
        List<DeviceEvent> matchedProducts = events
                .stream()
                .filter(e -> productId.equals(e.getProductId()))
                .collect(Collectors.toList());
        return matchedProducts;
    }

    private DeviceReport createDeviceStatusReport(DeviceEvent event){

        DeviceReport report = new DeviceReport();
        report.setId(event.getEventId().toString());
        report.setDateTime(milliToUTCDateFormat(event.getDateTime()));

        if (event.getProductId().startsWith("69")){
            report.setName("GeneralTracker");
        }

        setDeviceStatus(event, report);

        float batteryLife = event.getBattery()*100;

        setBatteryStatus(report, batteryLife);

        return report;
    }

    private void setDeviceStatus(DeviceEvent event, DeviceReport report) {
        if (event.getAirplaneMode().equals("ON")){
            report.setDescription("SUCCESS: Location not available: Please turn off airplane mode");
            report.setStatus("Inactive");
        }else if (event.getAirplaneMode().equals("OFF") && event.getLatitude().isEmpty() && event.getLongitude().isEmpty()){
            throw new DeviceNotReportedException("ERROR: Device could not be located");
            }else {
            report.setDescription("SUCCESS: Location identified");
            report.setLatitude(event.getLatitude());
            report.setLongitude(event.getLongitude());
            report.setStatus("Active");
        }
        if (event.getProductId().startsWith("WG")){
            report.setName("CyclePlusTracker");
            report.setStatus(event.getActive());
        }
    }

    private void setBatteryStatus(DeviceReport report, float batteryLife) {
        if (batteryLife >= 98){
            report.setBattery("Full");
        }
        if (batteryLife >= 60 && batteryLife < 98){
            report.setBattery("High");
        }
        if (batteryLife >= 40 && batteryLife < 60){
            report.setBattery("Medium");
        }
        if (batteryLife >= 10 && batteryLife < 40){
            report.setBattery("Low");
        }
        if (batteryLife < 10){
            report.setBattery("Critical");
        }
    }

    public String milliToUTCDateFormat(Long dateTime ){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date(dateTime));
    }

}

