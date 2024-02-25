package com.Library.exceptions;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class ValidemailExceptions extends Exception{
    public ValidemailExceptions(String message) throws ValidemailExceptions {
        throw new ValidemailExceptions("Invalid email format: " );

    }
}