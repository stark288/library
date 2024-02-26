

package com.library.accounts;


import com.library.driver.*;

import com.library.driver.Driver;
import com.library.users.Admin;
import com.library.databaseservices.DataBaseutils;
import com.library.users.Librarian;
import com.library.users.Patron;
import com.library.exceptions.*;


import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.library.validation.Validator;

import static com.library.Layout.printTable;



public class AccountManagement{
    public static String loggedInAdminUsername;
    public static String loggedInLibrarianUsername;
    public static String loggedInPatronUsername;
    private static Scanner sc = new Scanner(System.in);
    public void librarianLogin() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        int loginAttempts = 0;
        final int MAX_LOGIN_ATTEMPTS = 3;
        Scanner sc = new Scanner(System.in);
        System.out.println("Maximum login attempts: " + MAX_LOGIN_ATTEMPTS);

        while (loginAttempts < MAX_LOGIN_ATTEMPTS) {
            System.out.println("Enter your username:");
            String username = sc.nextLine();
            System.out.println("Enter your password:");
            String password = sc.nextLine();

            String sql = "SELECT * FROM finallibrary.Accounts WHERE Username = ? AND Password = ? AND AccountType = 'librarian'";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Welcome, " + username );
                    loggedInLibrarianUsername = username;
                    LibrarianMenu librarianMenu = new LibrarianMenu();
                    librarianMenu.Librarian();
                    return; // Exit the method after successful login
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                    loginAttempts++;
                    System.out.println("Login attempts remaining: " + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                }
            } catch (SQLException e) {
                System.out.println("Error logging in as a librarian.");
                e.printStackTrace();
                throw e;
            }
        }

        // If login attempts exceed the maximum allowed, terminate the program
        System.out.println("Maximum login attempts exceeded. Exiting the program.");
        Driver driver = new Driver();
        driver.mainmenu();
    }
    public void adminLogin() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        int loginAttempts = 0;
        final int MAX_LOGIN_ATTEMPTS = 3;
        Scanner sc = new Scanner(System.in);
        System.out.println("Maximum login attempts: " + MAX_LOGIN_ATTEMPTS);

        while (loginAttempts < MAX_LOGIN_ATTEMPTS) {
            System.out.println("Enter your username:");
            String username = sc.nextLine();
            System.out.println("Enter your password:");
            String password = sc.nextLine();

            String sql = "SELECT * FROM finallibrary.Accounts WHERE Username = ? AND Password = ? AND AccountType = 'admin'";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Login successful");
                    loggedInAdminUsername = username;
                    // Store the logged-in admin's username
                    // ... proceed with admin menu ...
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.Admin();
                    return; // Exit the method after successful login
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                    loginAttempts++;
                    System.out.println("Login attempts remaining: " + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                }
            } catch (SQLException e) {
                System.out.println("Error logging in as an admin.");
                e.printStackTrace();
                throw e;
            }
        }

        // If login attempts exceed the maximum allowed, return to main menu
        System.out.println("Maximum login attempts exceeded. Returning to the main menu.");
        // ... return to main menu ...
    }

    public void patronLogin() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        int loginAttempts = 0;
        final int MAX_LOGIN_ATTEMPTS = 3;
        Scanner sc = new Scanner(System.in);
        System.out.println("Maximum login attempts: " + MAX_LOGIN_ATTEMPTS);

        while (loginAttempts < MAX_LOGIN_ATTEMPTS) {
            System.out.println("Enter your username:");
            String username = sc.nextLine();
            System.out.println("Enter your password:");
            String password = sc.nextLine();

            String sql = "SELECT * FROM finallibrary.Accounts WHERE Username = ? AND Password = ? AND AccountType = 'patron'";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Welcome, " + username + "!");
                    loggedInPatronUsername = username;
                    PatronMenu patronMenu = new PatronMenu();
                    patronMenu.patron();
                    return; // Exit the method after successful login
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                    loginAttempts++;
                    System.out.println("Login attempts remaining: " + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                }
            } catch (SQLException e) {
                System.out.println("Error logging in as a patron.");
                e.printStackTrace();
                throw e;
            }
        }

        // If login attempts exceed the maximum allowed, terminate the program
        System.out.println("Maximum login attempts exceeded. Exiting the program.");
        Driver driver = new Driver();
        driver.mainmenu();

    }

    public static void registerAdmin() throws SQLException, SqlConnectionException {
        String status = "active";
        String firstName, email, phone, username, password;
        Scanner sc = new Scanner(System.in);
        boolean isAdminAdded = false; // Flag to track if librarian was successfully added
        do {
            System.out.println("Enter the first name of the admin: ");
            firstName = sc.nextLine();
        }while (!Validator.isvalidfirstname(firstName));
        System.out.println("Enter Admin Last Name:");
        String lastName = sc.nextLine();

        do {
            System.out.println("Enter Admin Email:");
            email = sc.nextLine();
        }while (!Validator.isValidEmail(email));
        do {
            System.out.println("Enter Admin Phone:");
            phone = sc.nextLine();
        }while (!Validator.isValidPhone(phone));
        do {
            System.out.println("Enter Admin Username:");
            username = sc.nextLine();
        }while (!Validator.isValidUsername(username));
        do {
            System.out.println("Enter Admin Password:");
            password = sc.nextLine();
        }while (!Validator.isValidPassword(password));
        Admin newAdmin = new Admin(firstName, lastName, email, phone, new Account(username, password, "admin"));
        String sql = "INSERT INTO finallibrary.Admins (AdminID, FirstName, LastName, Email, Phone, AccountID,Accountstatus) VALUES (finallibrary.admins_seq.NEXTVAL, ?, ?, ?, ?, ?,?)";

        // Establish a connection to the database
        try (Connection conn = DataBaseutils.getConnection()) {
            String accountSql = "INSERT INTO finallibrary.Accounts (AccountID, Username, Password, AccountType) " +
                    "VALUES (finallibrary.accounts_seq.NEXTVAL, ?, ?, ?)";
            try (PreparedStatement accountStatement = conn.prepareStatement(accountSql)) {
                accountStatement.setString(1, username);
                accountStatement.setString(2, password);
                accountStatement.setString(3, "admin");
                accountStatement.executeUpdate();
            }
            // Insert Admin record
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setInt(5, getLastAccountId(conn));
            statement.setString(6, status);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Admin added successfully");
            } else {
                System.out.println("No admin was added");
            }
        } catch (SQLException e) {
            System.out.println("Error adding admin to the database.");
            if (isAdminAdded) {
                try {

                    String deleteAccountSql = "DELETE FROM finallibrary.Accounts WHERE Username = ?";
                    try (PreparedStatement deleteAccountStatement = DataBaseutils.getConnection().prepareStatement(deleteAccountSql)) {
                        deleteAccountStatement.setString(1, username);
                        int deletedRows = deleteAccountStatement.executeUpdate();
                        if (deletedRows > 0) {
                            System.out.println("Rollback: Account creation successfully reverted.");
                        } else {
                            System.out.println("Rollback: Account creation rollback failed. Please manually delete the account with username: " + username);
                        }
                    }
                } catch (SQLException rollbackException) {
                    System.out.println("Rollback: Error occurred during account creation rollback: " + rollbackException.getMessage());
                }
            }
        }
    }
    public void registerMember() throws SQLException, SqlConnectionException {
        Scanner scanner = new Scanner(System.in);
        String status = "active";
        String email, phone, username, password;
        boolean isPatronAdded = false; // Flag to track if patron was successfully added
        System.out.println("Enter Patron First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter Patron Last Name:");
        String lastName = scanner.nextLine();
        do {
            System.out.println("Enter Patron Email:");
            email = scanner.nextLine();
            try {
                if (!Validator.isValidEmail(email)) {
                    throw new ValidemailExceptions("Invalid email address");
                }
            } catch (ValidemailExceptions e) {
                System.out.println(e.getMessage());
            }
        } while (!Validator.isValidEmail(email));
        do {
            System.out.println("Enter Patron Phone:");
            phone = scanner.nextLine();
            try {
                if (!Validator.isValidPhone(phone)) {
                    throw new ValidPhoneExceptions("Invalid phone number");
                }
            } catch (ValidPhoneExceptions e) {
                System.out.println(e.getMessage());
            }
        } while (!Validator.isValidPhone(phone));
        System.out.println("Enter Patron Address:");
        String address = scanner.nextLine();
        LocalDate dateOfJoin = LocalDate.now();
        do {
            System.out.println("Enter Patron Username:");
            username = scanner.nextLine();
        }while (!Validator.isValidUsername(username));
        do{
            System.out.println("Enter Patron Password:");
            password = scanner.nextLine();
        }while (!Validator.isValidPassword(password));
        int lastLibraryId = getLastLibraryId();
        LocalDate dateOfExpiry = dateOfJoin.plusYears(1);
        Patron newPatron = new Patron(firstName, lastName, email, phone, address, new Account(username, password, "patron"), lastLibraryId);

        // SQL statement to insert a new patron into the Patrons table
        String sql = "INSERT INTO finallibrary.Patron (PatronID, FirstName, LastName, Email, Phone,address, AccountID, DATEOFJOINING, DATEOFEXPIRY, STATUS, LibraryID) " +
                "VALUES (finallibrary.patron_seq.NEXTVAL, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";

        // Establish a connection to the database
        try (Connection conn = DataBaseutils.getConnection()) {
            String accountSql = "INSERT INTO finallibrary.Accounts (AccountID, Username, Password, AccountType) " +
                    "VALUES (finallibrary.accounts_seq.NEXTVAL, ?, ?, ?)";
            try (PreparedStatement accountStatement = conn.prepareStatement(accountSql)) {
                accountStatement.setString(1, username);
                accountStatement.setString(2, password);
                accountStatement.setString(3, "patron");
                accountStatement.executeUpdate();
            }
            // Insert Patron record
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, address);
            statement.setInt(6, getLastAccountId(conn)); // Assuming you have a method to get the last inserted account ID
            statement.setDate(7, Date.valueOf(dateOfJoin)); // Convert LocalDate to SQL Date
            statement.setDate(8, Date.valueOf(dateOfExpiry)); // Convert LocalDate to SQL Date
            statement.setString(9, status);
            statement.setInt(10, getLastLibraryId());

            // Execute the patron creation
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Patron added successfully");
                isPatronAdded = true; // Set flag to true if patron was successfully added
            } else {
                throw new SQLException("Error adding patron to the database.");
            }
        } catch (SQLException e) {
            if (isPatronAdded) {
                try {

                    String deleteAccountSql = "DELETE FROM finallibrary.Accounts WHERE Username = ?";
                    try (PreparedStatement deleteAccountStatement = DataBaseutils.getConnection().prepareStatement(deleteAccountSql)) {
                        deleteAccountStatement.setString(1, username);
                        int deletedRows = deleteAccountStatement.executeUpdate();
                        if (deletedRows > 0) {
                            System.out.println("Rollback: Account creation successfully reverted.");
                        } else {
                            System.out.println("Rollback: Account creation rollback failed. Please manually delete the account with username: " + username);
                        }
                    }
                } catch (SQLException rollbackException) {
                    System.out.println("Rollback: Error occurred during account creation rollback: " + rollbackException.getMessage());
                }
            }
        }
    }

    public void registerLibrarian() throws SQLException, SqlConnectionException {
        String firstName;
        String lastName;
        String email;
        String phone;
        String username;
        String password;
        boolean isLibrarianAdded = false;
        // Flag to track if librarian was successfully added
        String status = "active";


        System.out.println("Enter the first name of the librarian: ");
        firstName = sc.nextLine();
        System.out.println("Enter Librarian Last Name:");
        lastName = sc.nextLine();
        do{
            System.out.println("Enter Librarian Email:");
            email = sc.nextLine();
        }while (!Validator.isValidEmail(email));
        do {
            System.out.println("Enter Librarian Phone:");
            phone = sc.nextLine();
        }while (!Validator.isValidPhone(phone));
        do {
            System.out.println("Enter Librarian Username:");
            username = sc.nextLine();
        }while (!Validator.isValidUsername(username));
        do {
            System.out.println("Enter Librarian Password:");
            password = sc.nextLine();
        }while (!Validator.isValidPassword(password));
        int lastLibraryId = getLastLibraryId();


        Librarian newLibrarian = new Librarian(firstName, lastName, email, phone, new Account(username, password, "librarian"),lastLibraryId);
        // SQL statement to insert a new librarian into the Librarians table
        String sql = "INSERT INTO finallibrary.Librarian (LibrarianID, FirstName, LastName, Email, Phone, AccountID, LibraryID, Accountstatus) VALUES (finallibrary.librarian_seq.NEXTVAL, ?, ?, ?, ?, ?,?,?)";
        // Establish a connection to the database
        try (Connection conn = DataBaseutils.getConnection()) {
            String accountSql = "INSERT INTO finallibrary.Accounts (AccountID, Username, Password, AccountType) " +
                    "VALUES (finallibrary.accounts_seq.NEXTVAL, ?, ?, ?)";
            try (PreparedStatement accountStatement = conn.prepareStatement(accountSql)) {
                accountStatement.setString(1, username);
                accountStatement.setString(2, password);
                accountStatement.setString(3, "librarian");
                accountStatement.executeUpdate();
            }
            // Insert Librarian record
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setInt(5, getLastAccountId(conn));
            statement.setInt(6, getLastLibraryId());
            statement.setString(7, status);

            // Execute the librarian creation

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Librarian added successfully");
            } else {
                System.out.println("No librarian was added");
            }
        } catch (SQLException e) {

            System.out.println("Error adding librarian to the database.");
            if (isLibrarianAdded) {
                try {

                    String deleteAccountSql = "DELETE FROM finallibrary.Accounts WHERE Username = ?";
                    try (PreparedStatement deleteAccountStatement = DataBaseutils.getConnection().prepareStatement(deleteAccountSql)) {
                        deleteAccountStatement.setString(1, username);
                        int deletedRows = deleteAccountStatement.executeUpdate();
                        if (deletedRows > 0) {
                            System.out.println("Rollback: Account creation successfully reverted.");
                        } else {
                            System.out.println("Rollback: Account creation rollback failed. Please manually delete the account with username: " + username);
                        }
                    }
                } catch (SQLException rollbackException) {
                    System.out.println("Rollback: Error occurred during account creation rollback: " + rollbackException.getMessage());
                }
            }
        }
    }
    public static void librarianviewpatron() throws SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Patron " +
                "JOIN finallibrary.Accounts ON Patron.AccountID = Accounts.AccountID";
        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Patron ID", "First Name", "Last Name", "Email", "Phone", "Address", "Date of Joining", "Expiry Date", "Status","username","password"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No patrons found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("PatronID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Address"),
                        resultSet.getString("DateOfJoining"),
                        resultSet.getString("DateofExpiry"),
                        resultSet.getString("Status"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password")
                };
                rows.add(rowData);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing patrons from the database.");
            e.printStackTrace();
            return;
        }
        printTable(headers, rows);
    }


    public static void viewPatron() throws SqlConnectionException {
        if (loggedInPatronUsername == null || loggedInPatronUsername.isEmpty()) {
            System.out.println("No patron is currently logged in.");
            return;
        }

        String sql = "SELECT * FROM finallibrary.Patron " +
                "JOIN finallibrary.Accounts ON Patron.AccountID = Accounts.AccountID " +
                "WHERE Username = ?";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Patron ID", "First Name", "Last Name", "Email", "Phone", "Address", "Date of Joining", "Expiry Date", "Status","username","password"};

        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) { // Changed to PreparedStatement

            preparedStatement.setString(1, loggedInPatronUsername); // Set the username value
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No patrons found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("PatronID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Address"),
                        resultSet.getString("DateOfJoining"),
                        resultSet.getString("DateofExpiry"),
                        resultSet.getString("Status"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password")
                };
                rows.add(rowData);
            }
            resultSet.close(); // Close the ResultSet
        } catch (SQLException e) {
            System.out.println("Error viewing patrons from the database.");
            e.printStackTrace();
            return;
        }
        printTable(headers, rows);
    }

    public static void viewLibrarians() throws SQLException, SqlConnectionException {
        if (loggedInLibrarianUsername == null || loggedInLibrarianUsername.isEmpty()) {
            System.out.println("No admin is currently logged in.");
            return;
        }

        String sql = "SELECT * FROM finallibrary.Librarian";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Librarian ID", "First Name", "Last Name", "Phone", "Email"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No librarians found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("LibrarianID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email")
                };
                rows.add(rowData);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing librarians from the database.");
            e.printStackTrace();
            return;
        }
        printTable(headers, rows);
    }

    public void removeLibrarian() throws SQLException, SqlConnectionException {
        viewLibrarians();
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the librarian you want to remove: ");
            String librarianID = sc.nextLine();
            String sql = "UPDATE finallibrary.Librarian SET Accountstatus = 'inactive' WHERE LibrarianID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, librarianID);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Librarian status updated successfully");
                    return; // Exit the method after successful status update
                } else {
                    System.out.println("No librarian with that ID exists in the database");
                    attempts++;
                    if (attempts >= MAX_ATTEMPTS) {
                        System.out.println("Maximum attempts reached. Exiting status update process.");
                        return;
                    }
                    System.out.println("You have " + (MAX_ATTEMPTS - attempts) + " attempts left.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating librarian status in the database.");
                e.printStackTrace();
                throw e;
            }
        }
        System.out.println("Maximum attempts reached. Exiting status update process.");
    }



    public void pauseMembership() throws SQLException, SqlConnectionException {
        librarianviewpatron();
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the patron you want to pause: ");
            String patronID = sc.nextLine();
            String sql = "SELECT * FROM finallibrary.Patron WHERE PatronID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, patronID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String pauseSql = "UPDATE finallibrary.Patron SET Status = 'Inactive' WHERE PatronID = ?";
                    try (PreparedStatement pauseStatement = conn.prepareStatement(pauseSql)) {
                        pauseStatement.setString(1, patronID);
                        pauseStatement.executeUpdate();
                        System.out.println("Membership paused successfully.");
                        return; // Exit the method after successful pause
                    } catch (SQLException e) {
                        System.out.println("Error pausing membership in the database.");
                        e.printStackTrace();
                        throw e;
                    }
                } else {
                    System.out.println("Patron not found.");
                }
                attempts++; // Increment attempts
            }
        }
        System.out.println("Maximum attempts reached. Exiting pause process.");
    }

    public void resumeMembership() throws SQLException, SqlConnectionException {
        librarianviewpatron();
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the patron you want to resume: ");
            String patronID = sc.nextLine();
            String sql = "SELECT * FROM finallibrary.Patron WHERE PatronID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, patronID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String resumeSql = "UPDATE finallibrary.Patron SET Status = 'Active' WHERE PatronID = ?";
                    try (PreparedStatement resumeStatement = conn.prepareStatement(resumeSql)) {
                        resumeStatement.setString(1, patronID);
                        resumeStatement.executeUpdate();
                        System.out.println("Membership resumed successfully");
                        return; // Exit the method after successful resume
                    } catch (SQLException e) {
                        System.out.println("Error resuming membership in the database.");
                        e.printStackTrace();
                        throw e;
                    }
                } else {
                    System.out.println("Patron not found.");
                }
                attempts++; // Increment attempts
            }
        }
        System.out.println("Maximum attempts reached. Exiting resume process.");
    }

    public void updateLibrarianProfile() throws SQLException, SqlConnectionException {
        viewLibrarians(); // Display current librarian details
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Select the field you want to update:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Phone");
            System.out.println("4. Email");
            System.out.println("5. Exit update process");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume invalid input
                continue;
            }

            String fieldToUpdate;
            switch (choice) {
                case 1:
                    fieldToUpdate = "FirstName";
                    break;
                case 2:
                    fieldToUpdate = "LastName";
                    break;
                case 3:
                    fieldToUpdate = "Phone";
                    break;
                case 4:
                    fieldToUpdate = "Email";
                    break;
                case 5:
                    System.out.println("Exiting update process.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    continue;
            }

            System.out.println("Enter new value for " + fieldToUpdate + " (leave blank to keep current value):");
            String newValue = sc.nextLine();

            // Fetch the LibrarianID based on the logged-in username
            int librarianId = getLibrarianIdFromUsername(loggedInAdminUsername);

            String updateLibrarianSql = "UPDATE finallibrary.Librarian SET " + fieldToUpdate + " = ? WHERE LibrarianID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(updateLibrarianSql)) {
                statement.setString(1, newValue.isEmpty() ? getCurrentFieldValue(librarianId, fieldToUpdate, "Librarian", conn) : newValue);
                statement.setInt(2, librarianId);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Librarian details updated successfully.");
                } else {
                    System.out.println("No changes were made to the librarian details.");
                }
                return; // Exit the method after successful update
            } catch (SQLException e) {
                System.out.println("Error updating librarian details.");
                e.printStackTrace();
            }
            attempts++; // Increment attempts
        }
        System.out.println("Maximum attempts reached. Exiting update process.");
    }




    public void updateMemberProfile() throws SQLException, SqlConnectionException {
        // Fetch the PatronID based on the logged-in patron's username
        int patronId = getPatronIdFromLoggedInUser(loggedInPatronUsername); // Use the modified method name
        if (patronId == -1) {
            System.out.println("No patron found with the logged-in username.");
            return; // Exit the method if no patron is found
        }

        viewPatron(); // Display current patron details
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Select the field you want to update:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Phone");
            System.out.println("4. Email");
            System.out.println("5. Address");
            System.out.println("6. Exit update process");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume invalid input
                continue;
            }

            String fieldToUpdate;
            switch (choice) {
                case 1:
                    fieldToUpdate = "FirstName";
                    break;
                case 2:
                    fieldToUpdate = "LastName";
                    break;
                case 3:
                    fieldToUpdate = "Phone";
                    break;
                case 4:
                    fieldToUpdate = "Email";
                    break;
                case 5:
                    fieldToUpdate = "Address";
                    break;
                case 6:
                    System.out.println("Exiting update process.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    continue;
            }

            System.out.println("Enter new value for " + fieldToUpdate + " (leave blank to keep current value):");
            String newValue = sc.nextLine();

            String updatePatronSql = "UPDATE finallibrary.Patron SET " + fieldToUpdate + " = ? WHERE PatronID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(updatePatronSql)) {
                statement.setString(1, newValue.isEmpty() ? getCurrentFieldValue(patronId, fieldToUpdate, "Patron", conn) : newValue);
                statement.setInt(2, patronId);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Patron details updated successfully.");
                } else {
                    System.out.println("No changes were made to the patron details.");
                }
                return; // Exit the method after successful update
            } catch (SQLException e) {
                System.out.println("Error updating patron details.");
                e.printStackTrace();
            }
            attempts++; // Increment attempts
        }
        System.out.println("Maximum attempts reached. Exiting update process.");
    }


    public int getLastLibraryId() throws SQLException, SqlConnectionException {
        String sql = "SELECT MAX(LibraryID) FROM finallibrary.Library";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("Failed to get last library ID");
            }
        }
    }
    private static int getLastAccountId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(AccountID) FROM finallibrary.Accounts";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("Failed to get last account ID");
            }
        }
    }



    public static void viewAdmin() throws SQLException, SqlConnectionException {
        if (loggedInAdminUsername == null || loggedInAdminUsername.isEmpty()) {
            System.out.println("No admin is currently logged in.");
            return;
        }

        String sql = "SELECT * FROM finallibrary.Admins " +
                "JOIN finallibrary.Accounts ON Admins.AccountID = Accounts.AccountID " +
                "WHERE Username = ?";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Admin ID", "First Name", "Last Name", "Email", "Phone", "Account ID"};

        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, loggedInAdminUsername);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No admin found with the current username.");
                    return;
                }

                while (resultSet.next()) {
                    String[] rowData = {
                            resultSet.getString("AdminID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("Phone"),
                            resultSet.getString("AccountID")
                    };
                    rows.add(rowData);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving admin details from the database.");
            e.printStackTrace();
            throw e;
        }
        printTable(headers, rows);

    }


    // Helper method to get the AdminID based on the username
    private int getAdminIdFromUsername(String username) throws SQLException, SqlConnectionException {
        String adminIdQuery = "SELECT AdminID FROM finallibrary.Admins " +
                "JOIN finallibrary.Accounts ON Admins.AccountID = Accounts.AccountID " +
                "WHERE Username = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement adminIdStatement = conn.prepareStatement(adminIdQuery)) {
            adminIdStatement.setString(1, username);
            try (ResultSet adminIdResultSet = adminIdStatement.executeQuery()) {
                if (adminIdResultSet.next()) {
                    return adminIdResultSet.getInt("AdminID");
                } else {
                    throw new SQLException("Admin not found with username: " + username);
                }
            }
        }
    }

    private int getLibrarianIdFromUsername(String username) throws SQLException, SqlConnectionException {
        String librarianIdQuery = "SELECT LibrarianID FROM finallibrary.Librarian " +
                "JOIN finallibrary.Accounts ON Librarian.AccountID = Accounts.AccountID " +
                "WHERE Username = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement librarianIdStatement = conn.prepareStatement(librarianIdQuery)) {
            librarianIdStatement.setString(1, username);
            try (ResultSet librarianIdResultSet = librarianIdStatement.executeQuery()) {
                if (librarianIdResultSet.next()) {
                    return librarianIdResultSet.getInt("LibrarianID");
                } else {
                    throw new SQLException("Librarian not found with username: " + username);
                }
            }
        }
    }

    public static int getPatronIdFromLoggedInUser(String loggedInPatronUsername) throws SQLException, SqlConnectionException {
        // Assuming loggedInPatronUsername holds the username of the currently logged-in patron
        String username = AccountManagement.loggedInPatronUsername; // Retrieve the logged-in patron's username
        String patronIdQuery = "SELECT p.PatronID FROM finallibrary.Patron p " +
                "JOIN finallibrary.Accounts a ON p.AccountID = a.AccountID " +
                "WHERE a.Username = ?";

        int patronId = -1; // Default to -1 to indicate not found

        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(patronIdQuery)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    patronId = rs.getInt("PatronID");
                } else {
                    System.out.println("No patron found with the username: " + username);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving PatronID from the database for the logged-in user.");
            e.printStackTrace();
            throw e;
        }

        return patronId;
    }
    private String getCurrentFieldValue(int adminID, String field, Connection conn) throws SQLException {
        String sql = "SELECT " + field + " FROM finallibrary.Admins WHERE AdminID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, adminID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(field);
                } else {
                    throw new SQLException("Admin not found with ID: " + adminID);
                }
            }
        }
    }
    private String getCurrentFieldValue(int userID, String field, String table, Connection conn) throws SQLException {
        String sql = "SELECT " + field + " FROM finallibrary." + table + " WHERE " + table + "ID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(field);
                } else {
                    throw new SQLException(table + " not found with ID: " + userID);
                }
            }
        }
    }





    public static void main(String[] args) throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        //registerAdmin();

        viewAdmin();
    }
}