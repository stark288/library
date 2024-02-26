package com.library.payments;

import com.library.exceptions.InvalidDateFormatException;
import com.library.exceptions.SqlConnectionException;

import java.sql.SQLException;

public interface Payable {
    public void pay() throws SQLException, SqlConnectionException, InvalidDateFormatException;
}
