package com.library.payments;
import com.library.driver.PatronMenu;
import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.library.accounts.AccountManagement.getPatronIdFromLoggedInUser;
import static com.library.accounts.AccountManagement.loggedInPatronUsername;

public class Payment implements Payable{
    static PatronMenu patronMenu = new PatronMenu();
    Scanner sc = new Scanner(System.in);
    @Override
    public void pay() throws SQLException, SqlConnectionException, InvalidDateFormatException {
        Scanner sc = new Scanner(System.in);
        System.out.println("welcome to Payment");
        System.out.println("1. Pay Fine\n2. Pay Membership Fee\n3. Go back to the previous menu");
        System.out.println("Enter your choice: ");
        int choice = sc.nextInt();
        switch (choice) {


        }
    }
//    public void payFine(int Borrowid) throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
//        fineandPayment fineandPayment = new fineandPayment();
//        // Display fines for the patron
//
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter Fine ID to pay:");
//        int fineId = sc.nextInt();
//        sc.nextLine(); // Consume the newline
//
//        // Retrieve the fine amount and status
//        String fineSql = "SELECT Amount, Status FROM finallibrary.Fine WHERE FineID = ?";
//        double amountDue;
//        String status;
//        try (Connection conn = DataBaseutils.getConnection();
//             PreparedStatement fineStmt = conn.prepareStatement(fineSql)) {
//            fineStmt.setInt(1, fineId);
//            ResultSet rs = fineStmt.executeQuery();
//            if (!rs.next()) {
//                System.out.println("No fine record found with the given ID.");
//                return;
//            }
//            amountDue = rs.getDouble("Amount");
//            status = rs.getString("Status");
//            if (!"Unpaid".equals(status)) {
//                System.out.println("This fine has already been paid or is not in an unpaid status.");
//                return;
//            }
//        }
//
//        // Process the payment
//        System.out.println("Amount due: Rs " + amountDue);
//        System.out.println("Enter amount to pay:");
//        double amountPaid = sc.nextDouble();
//        sc.nextLine(); // Consume the newline
//
//        if (amountPaid < amountDue) {
//            System.out.println("The amount paid is less than the amount due. Please pay the full fine.");
//            System.out.println("Payment failed.");
//            payFine(Borrowid);
//
//        }
//
//        // Insert the payment record into the Payment table
//        String paymentSql = "INSERT INTO finallibrary.Payment (PaymentID, PatronID, FineID, Amount, PaymentDate, PaymentMethod, Status) " +
//                "VALUES (finallibrary.payment_seq.NEXTVAL, ?, ?, ?, ?, ?, 'Completed')";
//        // Update the fine status in the Fine table
//        String updateFineSql = "UPDATE finallibrary.Fine SET Status = 'Paid' WHERE FineID = ?";
//
//        try (Connection conn = DataBaseutils.getConnection()) {
//            // Start a transaction
//            conn.setAutoCommit(false);
//
//            // Insert payment record
//            try (PreparedStatement paymentStmt = conn.prepareStatement(paymentSql)) {
//                // Assuming PatronID is available from the current session/user context
//                int patronId = getCurrentPatronId(); // You need to implement this method
//                paymentStmt.setInt(1, patronId);
//                paymentStmt.setInt(2, fineId);
//                paymentStmt.setDouble(3, amountPaid);
//                paymentStmt.setDate(4, Date.valueOf(LocalDate.now()));
//                paymentStmt.setString(5, "Cash"); // Assuming payment by cash for simplicity
//
//                paymentStmt.executeUpdate();
//            }
//
//            // Update fine status
//            try (PreparedStatement updateFineStmt = conn.prepareStatement(updateFineSql)) {
//                updateFineStmt.setInt(1, fineId);
//                updateFineStmt.executeUpdate();
//            }
//
//            // Commit the transaction
//            conn.commit();
//            System.out.println("Fine paid successfully.");
//        } catch (SQLException e) {
//            System.out.println("Error processing fine payment in the database.");
//            e.printStackTrace();
//            throw e;
//        }
//    }



    public void payFine() throws SQLException, SqlConnectionException {
        int patronId = getPatronIdFromLoggedInUser(loggedInPatronUsername);
        if (patronId == -1) {
            System.out.println("No patron found with the username: " + loggedInPatronUsername);
            return;
        }

        // Retrieve the unpaid fines for the logged-in patron
        String fineSql = "SELECT FineID, Amount FROM finallibrary.Fine WHERE PatronID = ? AND Status = 'Unpaid'";
        List<Integer> fineIds = new ArrayList<>();
        List<Double> amountsDue = new ArrayList<>();

        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement fineStmt = conn.prepareStatement(fineSql)) {
            fineStmt.setInt(1, patronId);
            ResultSet rs = fineStmt.executeQuery();
            while (rs.next()) {
                fineIds.add(rs.getInt("FineID"));
                amountsDue.add(rs.getDouble("Amount"));
            }
        }

        if (fineIds.isEmpty()) {
            System.out.println("No unpaid fines found for the patron.");
            return;
        }

        boolean allFinesPaid = true;
        Scanner sc = new Scanner(System.in);

        // Process payment for each fine
        for (int i = 0; i < fineIds.size(); i++) {
            int fineId = fineIds.get(i);
            double amountDue = amountsDue.get(i);

            System.out.println("Fine ID: " + fineId + " Amount due: Rs " + amountDue);
            System.out.println("Enter amount to pay:");
            double amountPaid = sc.nextDouble();
            sc.nextLine(); // Consume the newline

            if (amountPaid < amountDue) {
                System.out.println("The amount paid is less than the amount due. Please pay the full fine.");
                allFinesPaid = false;
                break; // Exit the loop if the full amount is not paid
            } else {
                // Update the fine status in the Fine table
                String updateFineSql = "UPDATE finallibrary.Fine SET Status = 'Paid' WHERE FineID = ?";
                try (Connection conn = DataBaseutils.getConnection();
                     PreparedStatement updateFineStmt = conn.prepareStatement(updateFineSql)) {
                    updateFineStmt.setInt(1, fineId);
                    updateFineStmt.executeUpdate();
                }
            }
        }

        if (allFinesPaid) {
            System.out.println("All fines paid successfully.");
        } else {
            System.out.println("Not all fines were paid. Please try again.");
        }
    }
}
