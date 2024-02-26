package com.library.services;

import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.library.accounts.AccountManagement.*;

public class ReturnBook {


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

    private boolean updateBorrowStatusAndPayFine(int borrowId, LocalDate returnDate) throws SQLException, SqlConnectionException {
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
                return false;
            }
        } catch (SQLException | SqlConnectionException e) {
            System.out.println("Error updating borrow status in the database.");
            e.printStackTrace();
            throw e;
        }
        return false;
    }


    // BorrowRecord class to hold borrow ID and book name
    private class BorrowRecord {
        private final int borrowId;
        private final String bookName;
        private final int bookId; // Add bookId field

        public BorrowRecord(int borrowId, String bookName, int bookId) { // Include bookId in the constructor
            this.borrowId = borrowId;
            this.bookName = bookName;
            this.bookId = bookId; // Initialize bookId
        }

        public int getBorrowId() {
            return borrowId;
        }

        public String getBookName() {
            return bookName;
        }

        public int getBookId() { // Getter for bookId
            return bookId;
        }
    }


    private void updateBookCopiesAndHandleReservations(int bookId) throws SQLException, SqlConnectionException {
        try (Connection conn = DataBaseutils.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Update the number of copies
            String updateCopiesSql = "UPDATE finallibrary.Book SET Copies = Copies + 1 WHERE BookID = ?";
            try (PreparedStatement updateCopiesStmt = conn.prepareStatement(updateCopiesSql)) {
                updateCopiesStmt.setInt(1, bookId);
                updateCopiesStmt.executeUpdate();
            }

            // Check for reservations
            String checkReservationSql = "SELECT PatronID FROM finallibrary.Reservation WHERE BookID = ? AND Status = 'reserved'";
            try (PreparedStatement checkReservationStmt = conn.prepareStatement(checkReservationSql)) {
                checkReservationStmt.setInt(1, bookId);
                try (ResultSet resultSet = checkReservationStmt.executeQuery()) {
                    if (resultSet.next()) {
                        int reservedPatronId = resultSet.getInt("PatronID");
                        // Notify the patron about the book's availability
                        notifyPatron(reservedPatronId, bookId);
                        // Update the book's availability to 'reserved'
                        String updateAvailabilitySql = "UPDATE finallibrary.Book SET Availability = 'reserved' WHERE BookID = ?";
                        try (PreparedStatement updateAvailabilityStmt = conn.prepareStatement(updateAvailabilitySql)) {
                            updateAvailabilityStmt.setInt(1, bookId);
                            updateAvailabilityStmt.executeUpdate();
                        }
                    } else {
                        // If no reservations, set the book's availability to 'available'
                        String updateAvailabilitySql = "UPDATE finallibrary.Book SET Availability = 'available' WHERE BookID = ?";
                        try (PreparedStatement updateAvailabilityStmt = conn.prepareStatement(updateAvailabilitySql)) {
                            updateAvailabilityStmt.setInt(1, bookId);
                            updateAvailabilityStmt.executeUpdate();
                        }
                    }
                }
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            System.out.println("Error updating book copies and handling reservations.");
            e.printStackTrace();
            throw e;
        }
    }



    public List<Integer> getBookIdsByPatronUsername(String username) throws SQLException, SqlConnectionException {
        List<Integer> bookIds = new ArrayList<>();
        int patronId = getPatronIdFromLoggedInUser(username);
        if (patronId == -1) {
            System.out.println("No patron found with the username: " + username);
            return bookIds; // Return an empty list if no patron is found
        }

        String sql = "SELECT BookID FROM finallibrary.Borrow WHERE PatronID = ? AND STATUS = 'borrowed'";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, patronId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookIds.add(resultSet.getInt("BookID"));
                }
            }
        }
        return bookIds;
    }
    public void returnBook() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        int patronId = getPatronIdFromLoggedInUser(loggedInPatronUsername);
        if (patronId == -1) {
            System.out.println("No patron found with the username: " + loggedInPatronUsername);
            return;
        }

        // Retrieve the active borrow records for the patron, including book names
        List<BorrowRecord> borrowRecordsToReturn = getActiveBorrowRecordsByPatronId(patronId);
        if (borrowRecordsToReturn.isEmpty()) {
            System.out.println("No active borrows found for the patron: " + loggedInPatronUsername);
            return;
        }

        // Get the return date from the user once, outside the loop
        System.out.println("Enter the return date in the format (YYYY-MM-DD): ");
        Scanner scanner = new Scanner(System.in);
        String returnDateString = scanner.next();
        LocalDate returnDate = LocalDate.parse(returnDateString);

        // Process each borrow record for return
        for (BorrowRecord record : borrowRecordsToReturn) {
            System.out.println("Returning book: " + record.getBookName());

            // Calculate fine and update borrow status
            fineandPayment fineService = new fineandPayment();
            fineService.calculateFine(record.getBorrowId(), returnDate);
            boolean isReturned = updateBorrowStatusAndPayFine(record.getBorrowId(), returnDate);

            // Update the number of copies and check for reservations
            if (isReturned) {
                updateBookCopiesAndHandleReservations(record.getBookId());

            }
        }
    }

    private void notifyPatron(int patronId, int bookId) {
        String bookName = getBookNameById(bookId);
        if (bookName != null) {
            System.out.println("Notification sent to patron ID " + patronId + " about the availability of book: " + bookName);
        } else {
            System.out.println("Failed to send notification. Book name not found for book ID " + bookId);
        }
    }

    private String getBookNameById(int bookId) {
        String bookName = null;
        String sql = "SELECT Title FROM finallibrary.Book WHERE BookID = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    bookName = resultSet.getString("Title");
                }
            }
        } catch (SQLException | SqlConnectionException e) {
            System.out.println("Error retrieving book name from the database.");
            e.printStackTrace();
        }
        return bookName;
    }


    private List<BorrowRecord> getActiveBorrowRecordsByPatronId(int patronId) throws SQLException, SqlConnectionException {
        List<BorrowRecord> borrowRecords = new ArrayList<>();
        String sql = "SELECT br.BORROWID, bk.Title, bk.BookID FROM finallibrary.Borrow br " + // Retrieve BookID as well
                "JOIN finallibrary.Book bk ON br.BookID = bk.BookID " +
                "WHERE br.PatronID = ? AND br.STATUS = 'borrowed'";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, patronId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int borrowId = resultSet.getInt("BORROWID");
                    String bookName = resultSet.getString("Title");
                    int bookId = resultSet.getInt("BookID"); // Get the BookID
                    borrowRecords.add(new BorrowRecord(borrowId, bookName, bookId)); // Pass BookID to the constructor
                }
            }
        }
        return borrowRecords;
    }


}
