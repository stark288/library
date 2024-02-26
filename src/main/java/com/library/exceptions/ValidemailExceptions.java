package com.library.exceptions;

@SuppressWarnings("serial")
public class ValidemailExceptions extends Exception{
    public ValidemailExceptions(String message) throws ValidemailExceptions {
        throw new ValidemailExceptions("Invalid email format: " );

    }
}