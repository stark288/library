package com.library.services;

import com.library.users.Admin;
import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.SqlConnectionException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static com.library.accounts.AccountManagement.getPatronIdFromLoggedInUser;
import static com.library.accounts.AccountManagement.loggedInPatronUsername;
import static com.library.Layout.printTable;

public class Borrow {
    Admin admin = new Admin();

    public static int getBookIdByISBNAndTitle(String isbn, String title) throws SQLException, SqlConnectionException {
        int bookId = -1;
        String sql = "SELECT BookID FROM finallibrary.Book WHERE ISBN = ? AND Title = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, isbn);
            statement.setString(2, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bookId = resultSet.getInt("BookID");
            }
        }
        return bookId;
    }

    public static int getPatronIdByIdentifier(String identifier) throws SQLException, SqlConnectionException {
        int patronId = -1;
        String sql = "SELECT PatronID FROM finallibrary.Patron WHERE Email = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, identifier);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                patronId = resultSet.getInt("PatronID");
            }
        }
        return patronId;
    }

    // Method to check if the book is available for borrowing
    private boolean isBookAvailable(int bookId) throws SQLException, SqlConnectionException {
        String sql = "SELECT Availability FROM finallibrary.Book WHERE BookID = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String availability = resultSet.getString("Availability");
                    return availability.equalsIgnoreCase("available");
                }
            }
        }
        return false; // Book not found or availability not available
    }

    public boolean isPatronReservedBook(int patronId, int bookId) throws SQLException, SqlConnectionException {
        boolean isReserved = false;
        String sql = "SELECT * FROM finallibrary.Reservation WHERE PatronID = ? AND BookID = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, patronId);
            statement.setInt(2, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isReserved = true;
            }
        }
        return isReserved;
    }

    private boolean hasPatronReturnedBook(int patronId, int bookId) throws SQLException, SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Borrow WHERE PatronID = ? AND BookID = ? AND Status = 'returned'";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, patronId);
            statement.setInt(2, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // If there's a record, the patron has returned the book
            }

        }

    }
    public void borrowBook() throws SQLException, SqlConnectionException {
        Admin.viewBooks();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ISBN of the book to borrow:");
        String isbnToBorrow = scanner.nextLine();

        // Retrieve the BookID using the ISBN
        int bookIdToBorrow = getBookIdFromISBN(isbnToBorrow);
        if (bookIdToBorrow == -1) {
            System.out.println("No book found with the given ISBN.");
            return;
        }

        if (!isBookAvailable(bookIdToBorrow)) {
            System.out.println("Book is not available for borrowing.");
            return;
        }

        // Retrieve the PatronID using the logged-in patron's username
        int patronId = getPatronIdFromLoggedInUser(loggedInPatronUsername);
        if (patronId == -1) {
            System.out.println("No patron found with the username: " + loggedInPatronUsername);
            return;
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusWeeks(2); // Assuming a 2-week borrowing period
        String status = "borrowed";

        // Start a transaction
        try (Connection conn = DataBaseutils.getConnection()) {
            conn.setAutoCommit(false); // Disable auto-commit for transaction

            // Update the number of copies and availability in the Book table
            String updateBookSql = "UPDATE finallibrary.Book SET Copies = Copies - 1, Availability = CASE WHEN (Copies - 1) <= 0 THEN 'not available' ELSE 'available' END WHERE ISBN = ? AND Copies > 0";
            try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql)) {
                updateBookStmt.setString(1, isbnToBorrow);
                int booksUpdated = updateBookStmt.executeUpdate();
                if (booksUpdated == 0) {
                    System.out.println("No copies available to borrow.");
                    conn.rollback(); // Rollback the transaction
                    return;
                }
            }

            // Insert the borrow record
            String insertBorrowSql = "INSERT INTO finallibrary.Borrow (BORROWID, BOOKID, PATRONID, BORROWDATE, STATUS, DUEDATE) VALUES (finallibrary.borrow_seq.NEXTVAL, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertBorrowStmt = conn.prepareStatement(insertBorrowSql)) {
                insertBorrowStmt.setInt(1, bookIdToBorrow);
                insertBorrowStmt.setInt(2, patronId);
                insertBorrowStmt.setDate(3, Date.valueOf(borrowDate));
                insertBorrowStmt.setString(4, status);
                insertBorrowStmt.setDate(5, Date.valueOf(dueDate));
                int rowsInserted = insertBorrowStmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Book with ISBN " + isbnToBorrow + " borrowed successfully by " + loggedInPatronUsername);
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("Failed to borrow book.");
                    conn.rollback(); // Rollback the transaction
                }
            } catch (SQLException e) {
                conn.rollback(); // Rollback the transaction on error
                throw e;
            } finally {
                conn.setAutoCommit(true); // Restore auto-commit mode
            }
        } catch (SQLException | SqlConnectionException e) {
            System.out.println("Error borrowing book from the database.");
            e.printStackTrace();
            throw e;
        }
    }

    private int getBookIdFromISBN(String isbn) throws SQLException, SqlConnectionException {
        String sql = "SELECT BookID FROM finallibrary.Book WHERE ISBN = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, isbn);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("BookID");
                } else {
                    System.out.println("No book found with ISBN: " + isbn);
                    return -1;
                }
            }
        } catch (SQLException | SqlConnectionException e) {
            System.out.println("Error retrieving BookID from the database.");
            e.printStackTrace();
        }
        return -1;
    }


    public void viewBorrows() throws SqlConnectionException, SQLException {
        String sql = "SELECT * FROM finallibrary.Borrow";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Borrow ID", "Date", "Status", "Due Date"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No borrows found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("BorrowID"),
                        resultSet.getString("BorrowDate"),
                        resultSet.getString("Status"),
                        resultSet.getString("DueDate")
                };
                rows.add(rowData);
            }
        }

        // Sort borrows by ID
        rows.sort(Comparator.comparing(row -> Integer.parseInt(row[0])));

        printTable(headers, rows);
    }


}



