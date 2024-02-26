package com.library.databaseservices;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.library.exceptions.*;

public class DataBaseutils {


    private static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DATABASE_USER = "SYSTEM";
    private static final String DATABASE_PASSWORD = "oracle";
    private static final String DATABASE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static Connection connection;

    private DataBaseutils() {

    }

    public static Connection getConnection() throws SqlConnectionException {
        try {
            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SqlConnectionException(
                    "Unable to connect to the database. Please check the database connection details.");
        }
        return connection;
    }

    public static void closeConnection() throws SqlConnectionException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new SqlConnectionException("Unable to close the database connection.");
        }
    }




}