package com.library.services;

import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.*;
import com.library.payments.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.sql.Date;

import static com.library.accounts.AccountManagement.getPatronIdFromLoggedInUser;
import static com.library.accounts.AccountManagement.loggedInPatronUsername;
import static com.library.Layout.printTable;

public class fineandPayment {
    Borrow borrow = new Borrow();
    public void calculateFine(int borrowId, LocalDate returnDate) throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
        // Retrieve the BorrowDate and DueDate from the Borrow table
        String borrowSql = "SELECT BorrowDate, DueDate, PatronID FROM finallibrary.Borrow WHERE BorrowID = ?";
        LocalDate dueDate;
        int patronId;
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement borrowStmt = conn.prepareStatement(borrowSql)) {
            borrowStmt.setInt(1, borrowId);
            ResultSet rs = borrowStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No borrow record found with the given ID.");
                return;
            }
            dueDate = rs.getDate("DueDate").toLocalDate();
            patronId = rs.getInt("PatronID");
        }

        // Calculate the number of days overdue
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (daysOverdue <= 0) {
            System.out.println("No fine is due. The book was returned on time or before the due date.");
            return;
        }

        // Calculate the fine amount
        double fineAmount = daysOverdue * 5; // Rs 5 per day

        // Insert the fine record into the Fine table
        String fineSql = "INSERT INTO finallibrary.Fine (FineID, Amount, BorrowID, PatronID, ReturnDate, Status) " +
                "VALUES (finallibrary.fine_seq.NEXTVAL, ?, ?, ?, ?, 'Unpaid')";
        try (PreparedStatement fineStmt = DataBaseutils.getConnection().prepareStatement(fineSql)) {
            fineStmt.setDouble(1, fineAmount);
            fineStmt.setInt(2, borrowId);
            fineStmt.setInt(3, patronId);
            fineStmt.setDate(4, Date.valueOf(returnDate));

            int rowsInserted = fineStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Fine calculated and recorded successfully. Amount due: Rs " + fineAmount);
            } else {
                System.out.println("Failed to record the fine.");
            }
        }
        // Assuming Payment class has a method to pay fine by borrow ID
        Payment payment = new Payment();
        payment.payFine();
    }
    public void viewFine() throws SQLException, SqlConnectionException {
        int patronId = getPatronIdFromLoggedInUser(loggedInPatronUsername);
        if (patronId == -1) {
            System.out.println("No patron found with the username: " + loggedInPatronUsername);
            return;
        }

        String fineSql = "SELECT f.FineID, f.Amount, f.BorrowID, f.PatronID, f.ReturnDate, f.Status, b.BookID, bk.Title " +
                "FROM finallibrary.Fine f " +
                "JOIN finallibrary.Borrow b ON f.BorrowID = b.BORROWID " +
                "JOIN finallibrary.Book bk ON b.BookID = bk.BookID " +
                "WHERE f.PatronID = ?";

        List<String[]> fineRows = new ArrayList<>();
        String[] headers = {"Fine ID", "Amount", "Borrow ID", "Patron ID", "Return Date", "Status", "Book ID", "Book Title"};

        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement fineStmt = conn.prepareStatement(fineSql)) {
            fineStmt.setInt(1, patronId);
            ResultSet rs = fineStmt.executeQuery();
            while (rs.next()) {
                String[] rowData = {
                        String.valueOf(rs.getInt("FineID")),
                        String.valueOf(rs.getDouble("Amount")),
                        String.valueOf(rs.getInt("BorrowID")),
                        String.valueOf(rs.getInt("PatronID")),
                        String.valueOf(rs.getDate("ReturnDate")),
                        rs.getString("Status"),
                        String.valueOf(rs.getInt("BookID")),
                        rs.getString("Title")
                };
                fineRows.add(rowData);
            }
        }

        // Sort fines by Fine ID
        Collections.sort(fineRows, Comparator.comparing(row -> Integer.parseInt(row[0])));

        // Print fine information in tabular format
        printTable(headers, fineRows);
    }


    public void adminviewFine() throws SQLException, SqlConnectionException{
        String fineSql
= "SELECT f.FineID, f.Amount, f.BorrowID, f.PatronID, f.ReturnDate, f.Status, b.BookID, bk.Title, p.FirstName, p.LastName "
                + "FROM finallibrary.Fine f "
                + "JOIN finallibrary.Borrow b ON f.BorrowID = b.BORROWID "
                + "JOIN finallibrary.Book bk ON b.BookID = bk.BookID "
                + "JOIN finallibrary.Patron p ON f.PatronID = p.PatronID";
        List<String[]> fineRows = new ArrayList<>();
        String[] headers = {"Fine ID", "Amount", "Borrow ID", "Patron ID", "Return Date", "Status", "Book ID", "Book Title", "First Name", "Last Name"};
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement fineStmt = conn.prepareStatement(fineSql)) {
            ResultSet rs = fineStmt.executeQuery();
            while (rs.next()) {
                String[] rowData = {
                        String.valueOf(rs.getInt("FineID")),
                        String.valueOf(rs.getDouble("Amount")),
                        String.valueOf(rs.getInt("BorrowID")),
                        String.valueOf(rs.getInt("PatronID")),
                        String.valueOf(rs.getDate("ReturnDate")),
                        rs.getString("Status"),
                        String.valueOf(rs.getInt("BookID")),
                        rs.getString("Title"),
                        rs.getString("FirstName"),
                        rs.getString("LastName")
                };
                fineRows.add(rowData);
            }
        }
        // Sort fines by Fine ID
        Collections.sort(fineRows, Comparator.comparing(row -> Integer.parseInt(row[0])));
        printTable(headers, fineRows);
    }

}



