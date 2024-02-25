package com.Library.Payments;

import com.Library.exceptions.InvalidDateFormatException;
import com.Library.exceptions.SqlConnectionException;

import java.sql.SQLException;

public interface Payable {
    public void pay() throws SQLException, SqlConnectionException, InvalidDateFormatException;
}
