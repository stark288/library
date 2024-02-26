package com.library.users;

import com.library.accounts.Account;
import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.SqlConnectionException;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static com.library.Layout.printTable;


public class Patron extends Person {
    private String patronID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Phone;
    private String Address;
    private int AccountID;

    private int libraryID;
    private LocalDate DateOfJoining;
    private LocalDate DateofExpiry;
    private String status;
    private String UserName;
    private String Password;
    private String AccountType;

    private Library library;


    public Patron(String firstName, String lastName, String email, String phone, String address, LocalDate dateofJoining, LocalDate expiryDate, Account account) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Phone = phone;
        Address = address;
        DateOfJoining = dateofJoining;
        DateofExpiry = expiryDate;
        UserName = account.getUsername();
        Password = account.getPassword();
        AccountType = account.getAccountType();
    }


    public Patron(String firstName, String lastName, String email, String phone, String address, Account patron, int lastLibraryId) {
    }


    public String getFirstName() {
        return FirstName;
    }


    public String getLastName() {
        return LastName;

    }
    public String getEmail() {
        return Email;
    }
    public String getPhone() {
        return Phone;
    }


@Override
public String toString() {
	return "Patron{" + "patronID='" + patronID + '\'' + ", FirstName='" + FirstName + '\'' + ", LastName='" + LastName
			+ '\'' + ", Email='" + Email + '\'' + ", Phone='" + Phone + '\'' + ", Address='" + Address + '\''
			+ ", AccountID=" + AccountID + ", libraryID=" + libraryID + ", DateOfJoining=" + DateOfJoining
			+ ", DateofExpiry=" + DateofExpiry + ", status='" + status + '\'' + ", UserName='" + UserName + '\''
			+ ", Password='" + Password + '\'' + ", AccountType='" + AccountType + '\'' + ", library=" + library + '}';
}



    public Patron() {
    }

    public void viewPayment() throws SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Payment";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Payment ID", "Amount", "Date", "Patron ID"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No payments found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("PaymentID"),
                        resultSet.getString("Amount"),
                        resultSet.getString("PaymentDate"),
                        resultSet.getString("PatronID")
                };
                rows.add(rowData);
            }

        } catch (SQLException e) {
            System.out.println("Error viewing payments from the database.");
            return;
        }

        // Sort payments by Payment ID
        rows.sort(Comparator.comparing(row -> Integer.parseInt(row[0])));

        printTable(headers, rows);
    }
}