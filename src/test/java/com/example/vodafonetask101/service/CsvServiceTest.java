package com.example.vodafonetask101.service;

import com.example.vodafonetask101.exception.DeviceNotReportedException;
import com.example.vodafonetask101.exception.ResourceNotFoundException;
import com.example.vodafonetask101.model.DeviceEvent;
import com.example.vodafonetask101.model.DeviceReport;
import com.example.vodafonetask101.model.EventPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvServiceTest {

    @Autowired
    private CsvService csvService;
    private List<DeviceEvent> events;

    @BeforeAll
    public void setup() {
        EventPath eventPath = new EventPath();
        eventPath.setFilePath("/Users/muster/Downloads/vodafone-task/task-101/src/test/java/com/example/vodafonetask101/data/events.csv");
        csvService.processDataFromCsvFile(eventPath);
        events = csvService.getAllEvents();
    }

    @Test
    @Description("verify events")
    public void getAllEvents() {
        assertTrue(events.size() > 0);
    }

    @Test
    @Description("Verify Event with matching Id and Date")
    void getEventMatchingWithIdAndDate() {
        DeviceReport deviceReport = csvService.getEventByProductIdAndDateTime("WG11155638", 1582605077000L);
        assertEquals(deviceReport.getId(), "10001");
    }

    @Test
    @Description("Verify Event with matching Id and No date specified")
    void getEventMatchingWithIdAndWithoutDate() {
        DeviceReport deviceReport = csvService.getEventByProductIdAndDateTime("6900001001", null);
        assertEquals(deviceReport.getId(), "10012");
    }

    @Test
    @Description("Verify Event with matching Id and Non matching Date")
    void getEventMatchingWithIdAndNonMatchedDate() {
        DeviceReport deviceReport = csvService.getEventByProductIdAndDateTime("6900001001", 1582605557099L);
        assertEquals(deviceReport.getId(), "10012");
    }

    @Test
    @Description("Verify Event with non matching Id")
    void getEventMatchingWithNonMatchedId() {
        assertThrows(ResourceNotFoundException.class,() -> {csvService.getEventByProductIdAndDateTime("xyz", null);});
    }

    @Test
    @Description("Verify Device not found when Airplane mode off and no GPS data")
    void deviceNotFoundWhenNoGpsData() {
        assertThrows(DeviceNotReportedException.class,() -> {csvService.getEventByProductIdAndDateTime("6900233111", 1582612875000L);});
    }
}
