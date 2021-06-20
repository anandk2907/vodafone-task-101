package com.example.vodafonetask101.model;

public class DeviceReport {
    private String id;
    private String name;
    private String dateTime;
    private String longitude;
    private String latitude;
    private String status;
    private String battery;
    private String description;

    public DeviceReport() {
    }

    public DeviceReport(String id, String name, String dateTime, String longitude, String latitude, String status, String battery, String description) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
        this.battery = battery;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
