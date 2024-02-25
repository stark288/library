package com.Library.Services;

import com.Library.Users.Patron;
import com.Library.databaseservices.DataBaseutils;
import com.Library.exceptions.*;
import com.Library.Payments.Payment;
import com.Library.Services.Borrow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.sql.Date;

import static com.Library.Layout.printTable;

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
        double fineAmount = daysOverdue * 10; // Rs 10 per day

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
        payment.payFine(borrowId);
    }
    public void viewFine() throws SQLException, SqlConnectionException {
        Patron patron = new Patron();
        borrow.viewBorrows();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Borrow ID:");
        int borrowId = sc.nextInt();
        sc.nextLine();
        String fineSql = "SELECT f.FineID, f.Amount, f.BorrowID, f.PatronID, f.ReturnDate, f.Status, b.BookID, bk.Title " +
                "FROM finallibrary.Fine f " +
                "JOIN finallibrary.Borrow b ON f.BorrowID = b.BorrowID " +
                "JOIN finallibrary.Book bk ON b.BookID = bk.BookID " +
                "WHERE f.BorrowID = ?";

        List<String[]> fineRows = new ArrayList<>();
        String[] headers = {"Fine ID", "Amount", "Borrow ID", "Patron ID", "Return Date", "Status", "Book ID", "Book Title"};

        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement fineStmt = conn.prepareStatement(fineSql)) {
            fineStmt.setInt(1, borrowId);
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
    }



