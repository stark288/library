package com.Library.Services;

import com.Library.Users.Admin;
import com.Library.databaseservices.DataBaseutils;
import com.Library.exceptions.SqlConnectionException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static com.Library.Layout.printTable;

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
        admin.viewBooks();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Book ID to borrow:");
        int bookIdToBorrow = scanner.nextInt();

        if (!isBookAvailable(bookIdToBorrow)) {
            System.out.println("Book is not available for borrowing.");
            return;
        }

        System.out.println("Enter Patron's unique identifier (e.g., username or email):");
        String patronIdentifier = scanner.next();

        int patronId = getPatronIdByIdentifier(patronIdentifier);
        if (patronId == -1) {
            System.out.println("No patron found with the given identifier.");
            return;
        }

        if (isPatronReservedBook(patronId, bookIdToBorrow) && !hasPatronReturnedBook(patronId, bookIdToBorrow)) {
            System.out.println("Patron has reserved this book but has not returned it. Cannot borrow.");
            return;
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14); // Assuming a 2-week borrowing period
        String status = "borrowed";

        String sql = "INSERT INTO finallibrary.Borrow (BORROWID, BOOKID, PATRONID, BORROWDATE, STATUS, DUEDATE) " +
                "VALUES (finallibrary.borrow_seq.NEXTVAL, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookIdToBorrow);
            statement.setInt(2, patronId);
            statement.setDate(3, Date.valueOf(borrowDate));
            statement.setString(4, status);
            statement.setDate(5, Date.valueOf(dueDate));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book borrowed successfully!");
            } else {
                System.out.println("Failed to borrow book.");
            }
        } catch (SQLException | SqlConnectionException e) {
            System.out.println("Error borrowing book from the database.");
            e.printStackTrace();
            throw e;
        }
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



