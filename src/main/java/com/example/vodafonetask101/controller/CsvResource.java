package com.example.vodafonetask101.controller;

import com.example.vodafonetask101.config.RequestPath;
import com.example.vodafonetask101.model.DeviceEvent;
import com.example.vodafonetask101.model.DeviceReport;
import com.example.vodafonetask101.model.EventPath;
import com.example.vodafonetask101.service.CsvService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("http://localhost:8088")
@Controller
@RequestMapping(RequestPath.BASE_API)
public class CsvResource {

    private final CsvService fileService;

    @Inject
    public CsvResource(CsvService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileReadMessage = fileService.processDataFromMultiPartFile(file);
        if (fileReadMessage != null) return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(fileReadMessage));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("ERROR: no data file found!"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> uploadFileFromBody(@RequestBody @Valid EventPath eventPath){
        String fileReadMessage = fileService.processDataFromCsvFile(eventPath);
        if (fileReadMessage != null) return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(fileReadMessage));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("ERROR: no data file found!"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeviceEvent>> getAllEvents() {
        try {
            List<DeviceEvent> events = fileService.getAllEvents();

            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<DeviceReport> getEvent(@RequestParam String productId, @RequestParam(required = false) Long dateTime) {
        DeviceReport deviceReport = fileService.getEventByProductIdAndDateTime(productId, dateTime);
        return new ResponseEntity<>(deviceReport, HttpStatus.OK);
    }

    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}

