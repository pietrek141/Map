package com.mroczkowski.map.exceptions;

public class PointNotFoundException extends Exception{

    public PointNotFoundException() {
    }

    public PointNotFoundException(String message) {
        super(message);
    }

    public PointNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PointNotFoundException(Throwable cause) {
        super(cause);
    }
}
