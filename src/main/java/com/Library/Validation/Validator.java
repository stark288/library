package com.Library.Validation;

import com.Library.databaseservices.DataBaseutils;
import com.Library.exceptions.SqlConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public  static boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^[6-9]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9_-]{3,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isbnExists(String isbn) throws SQLException, SqlConnectionException {
        String sql = "SELECT COUNT(*) FROM finallibrary.Book WHERE ISBN = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, isbn);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public static boolean isvalidfirstname(String firstname) {
        //pattern for first name
        String regex = "^[a-zA-Z]{3,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(firstname);
        return matcher.matches();
    }

}
