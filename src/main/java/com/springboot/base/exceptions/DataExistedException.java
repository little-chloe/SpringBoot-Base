package com.springboot.base.exceptions;

public class DataExistedException extends RuntimeException {

    public DataExistedException(String message) {
        super(message);
    }

}
