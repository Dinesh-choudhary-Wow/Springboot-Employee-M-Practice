package com.example.demo1.service.exceptions;

public class NoMatchingDataException extends RuntimeException {
	
	public NoMatchingDataException(String message) {
        super(message);
    }
}
