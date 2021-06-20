package com.example.vodafonetask101.model;

public class DeviceEvent {
    private Long dateTime;
    private Long eventId;
    private String productId;
    private String latitude;
    private String longitude;
    private Float battery;
    private String light;
    private String airplaneMode;
    private String active;

    public DeviceEvent(Long dateTime, Long eventId, String productId, String latitude, String longitude, Float battery, String light, String airplaneMode, String active) {
        this.dateTime = dateTime;
        this.eventId = eventId;
        this.productId = productId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery = battery;
        this.light = light;
        this.airplaneMode = airplaneMode;
        this.active = active;
    }

    public DeviceEvent() {

    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Float getBattery() {
        return battery;
    }

    public void setBattery(Float battery) {
        this.battery = battery;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getAirplaneMode() {
        return airplaneMode;
    }

    public void setAirplaneMode(String airplaneMode) {
        this.airplaneMode = airplaneMode;
    }
}
