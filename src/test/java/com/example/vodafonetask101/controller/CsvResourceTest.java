package com.example.vodafonetask101.controller;

import com.example.vodafonetask101.exception.ResourceNotFoundException;
import com.example.vodafonetask101.model.DeviceReport;
import com.example.vodafonetask101.service.CsvService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CsvResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CsvService csvService;

    @Test
    @Description("Verify File upload success")
    void uploadCsvFile() throws Exception {
        when(csvService.processDataFromCsvFile(any())).thenReturn("Uploaded the file successfully: foo");

        String eventPath = "{\"filePath\" : \"correctPath\"}";

        mockMvc.perform(post("/iot/event/v1")
                .content(eventPath)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Description("Verify File upload fails with wrong path")
    void uploadCsvFileWithWrongPath() throws Exception {
        String eventPath = "{\"filePath\" : \"wrongPath\"}";

        mockMvc.perform(post("/iot/event/v1")
                .content(eventPath)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Description("Verify device found with matching id")
    void verifyDeviceFound() throws Exception {
        DeviceReport deviceReport = new DeviceReport("a","b","c","d","e","f","g","h");
        when(csvService.getEventByProductIdAndDateTime(any(),anyLong())).thenReturn(deviceReport);
        mockMvc.perform(get("/iot/event/v1?productId=WG11155638")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Description("Verify device not found with wrong id")
    void verifyDeviceNotFound() throws Exception {
        when(csvService.getEventByProductIdAndDateTime("xyz", null)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/iot/event/v1?productId=xyz")).andDo(print()).andExpect(status().isNotFound());
    }

}
