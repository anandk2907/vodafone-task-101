package com.example.vodafonetask101.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeviceEventTest {

    private static DeviceEvent deviceEvent;

    @BeforeAll
    static void setUp(){

        deviceEvent = new DeviceEvent(1234L,111L,"WD323","45.66","23.44", 0.55f,"OFF","OFF","N/A");
    }

    @Test
    public void shouldCreateDeviceEvent() {
        assertNotNull(deviceEvent);
    }


}
