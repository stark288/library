package com.Library.Services;

import com.Library.databaseservices.DataBaseutils;
import com.Library.exceptions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import com.Library.Services.fineandPayment;


import  com.Library.Users.Patron;

public class ReturnBook {
    public void returnBook() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
        Patron patron = new Patron();
        // Display borrow details
        System.out.println("Borrow Details:");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Borrow ID to return the book:");
        int borrowIdToReturn = scanner.nextInt();

        // Check if the borrow ID is valid
        if (!isBorrowValid(borrowIdToReturn)) {
            System.out.println("Invalid Borrow ID.");
            return;
        }

        // Get the return date from the user
        System.out.println("Enter the return date (YYYY-MM-DD):");
        String returnDateString = scanner.next();
        LocalDate returnDate = LocalDate.parse(returnDateString);

        // Calculate fine and update borrow status
        fineandPayment fineService = new fineandPayment();
        fineService.calculateFine(borrowIdToReturn, returnDate);
        updateBorrowStatusAndPayFine(borrowIdToReturn, returnDate);
    }


    private boolean isBorrowValid(int borrowId) throws SQLException, SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Borrow WHERE BORROWID = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, borrowId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // If the borrow ID exists, return true
            }
        }
    }

    private void updateBorrowStatusAndPayFine(int borrowId, LocalDate returnDate) throws SQLException, SqlConnectionException {
        // Update the borrow status to 'returned'
        String updateBorrowStatusQuery = "UPDATE finallibrary.Borrow SET STATUS = 'returned' WHERE BORROWID = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(updateBorrowStatusQuery)) {
            statement.setInt(1, borrowId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Failed to return book.");
                return;
            }
        } catch (SQLException | SqlConnectionException e) {
            System.out.println("Error updating borrow status in the database.");
            e.printStackTrace();
            throw e;
        }
    }
}
