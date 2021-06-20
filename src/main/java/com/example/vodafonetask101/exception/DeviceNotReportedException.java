package com.example.vodafonetask101.exception;

public class DeviceNotReportedException extends RuntimeException{
    private static final long serialVersionUID = -3916525550413865316L;

    public DeviceNotReportedException() {
        super();
    }

    public DeviceNotReportedException(String message) {
        super(message);
    }

    public DeviceNotReportedException(String message, Throwable cause) {
        super(message, cause);
    }

}
