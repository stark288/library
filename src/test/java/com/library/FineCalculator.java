package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.SqlConnectionException;

public class FineCalculator {
    public static double calculateFineAmount(String patronEmail) throws SQLException, SqlConnectionException {
        String fineQuery = "SELECT b.DueDate, CURRENT_TIMESTAMP AS ReturnDate " +
                "FROM finallibrary.Borrow b " +
                "JOIN finallibrary.Patron p ON b.PatronID = p.PatronID " +
                "WHERE p.Email = ?";

        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement fineStatement = conn.prepareStatement(fineQuery)) {

            fineStatement.setString(1, patronEmail);
            ResultSet resultSet = fineStatement.executeQuery();

            if (resultSet.next()) {
                LocalDateTime dueDateTime = resultSet.getTimestamp("DueDate").toLocalDateTime();
                LocalDateTime returnDateTime = resultSet.getTimestamp("ReturnDate").toLocalDateTime();

                if (returnDateTime.isAfter(dueDateTime)) {
                    long minutesDifference = Duration.between(dueDateTime, returnDateTime).toMinutes();
                    double fineAmount = minutesDifference * 0.01; // Assuming 0.01 Rs per minute fine
                    return fineAmount;
                }
            }
            return 0; // No fine if return is on or before due date
        }
    }

    public static void main(String[] args) {
        // Example usage:
        try {
            String patronEmail = "steve@gmail.com"; // Replace with actual patron email
            double fineAmount = calculateFineAmount(patronEmail);
            System.out.println("Fine Amount: " + fineAmount);
        } catch (SQLException | SqlConnectionException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
