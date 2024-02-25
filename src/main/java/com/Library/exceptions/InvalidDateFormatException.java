package com.Library.exceptions;

public class InvalidDateFormatException extends Exception{
    public InvalidDateFormatException(String message) {
        super("Invalid date format: " );
    }
}
