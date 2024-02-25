package com.Library.Accounts;


import com.Library.Driver.*;

import com.Library.Driver.Driver;
import com.Library.Layout;
import com.Library.Users.Admin;
import com.Library.databaseservices.DataBaseutils;
import com.Library.Users.Librarian;
import com.Library.Users.Patron;
import com.Library.exceptions.*;


import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.Library.Validation.Validator;

import static com.Library.Layout.printTable;



public class AccountManagement{
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
                    System.out.println("Login successful");
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
        Driver driver = new Driver();
        driver.mainmenu();
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
                    System.out.println("Login successful");
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
        boolean isAdminAdded = false; // Flag to track if librarian was successfully added
        System.out.println("Enter the first name of the admin: ");
        String firstName = sc.nextLine();
        System.out.println("Enter Admin Last Name:");
        String lastName = sc.nextLine();
        String email, username, password, phone;
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
        String sql = "INSERT INTO finallibrary.Admins (AdminID, FirstName, LastName, Email, Phone, AccountID) VALUES (finallibrary.admins_seq.NEXTVAL, ?, ?, ?, ?, ?)";

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
        boolean isLibrarianAdded = false; // Flag to track if librarian was successfully added


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
        String sql = "INSERT INTO finallibrary.Librarian (LibrarianID, FirstName, LastName, Email, Phone, AccountID, LibraryID) VALUES (finallibrary.librarian_seq.NEXTVAL, ?, ?, ?, ?, ?,?)";
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

    public void viewAdmin() throws SQLException, SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Admins";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Admin ID", "First Name", "Last Name", "Email", "Phone", "Account ID"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No admins found in the database.");
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
        } catch (SQLException e) {
            System.out.println("Error retrieving admins from the database.");
            e.printStackTrace();
            return;
        }

        // Sort admins by Admin ID
        Collections.sort(rows, Comparator.comparing(row -> Integer.parseInt(row[0])));

        printTable(headers, rows);
    }
    public void viewPatron() throws SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Patron";
        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Patron ID", "First Name", "Last Name", "Email", "Phone", "Address", "Date of Joining", "Expiry Date", "Status", "Username", "Password", "Account Type"};
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
                        resultSet.getString("UserName"),
                        resultSet.getString("Password"),
                        resultSet.getString("AccountType")
                };
                rows.add(rowData);

                rows.sort(Comparator.comparing(row -> Integer.parseInt(row[0])));

                printTable(headers, rows);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing patrons from the database.");
            return;
        }
    }

    public static void viewLibrarians() throws SQLException, SqlConnectionException {
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
        }

        // Sort librarians by ID
        rows.sort(Comparator.comparing(row -> Integer.parseInt(row[0])));

        printTable(headers, rows);
    }
        public void removeAdmin() throws SQLException, SqlConnectionException {
        viewAdmin();
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the admin you want to remove: ");
            String adminID = sc.nextLine();
            String sql = "DELETE FROM finallibrary.Admins WHERE AdminID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, adminID);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Admin removed successfully");
                    return; // Exit the method after successful removal
                } else {
                    System.out.println("No admin with that ID exists in the database");
                    attempts++;
                    if (attempts >= MAX_ATTEMPTS) {
                        System.out.println("Maximum attempts reached. Exiting removal process.");
                        return;
                    }
                    System.out.println("You have " + (MAX_ATTEMPTS - attempts) + " attempts left.");
                }
            } catch (SQLException e) {
                System.out.println("Error removing admin from the database.");
                e.printStackTrace();
                throw e;
            }
        }
        System.out.println("Maximum attempts reached. Exiting removal process.");
    }
    public void removeLibrarian() throws SQLException, SqlConnectionException {
        viewLibrarians();
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the librarian you want to remove: ");
            String librarianID = sc.nextLine();
            String sql = "DELETE FROM finallibrary.Librarian WHERE LibrarianID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, librarianID);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Librarian removed successfully");
                    return; // Exit the method after successful removal
                } else {
                    System.out.println("No librarian with that ID exists in the database");
                    attempts++;
                    if (attempts >= MAX_ATTEMPTS) {
                        System.out.println("Maximum attempts reached. Exiting removal process.");
                        return;
                    }
                    System.out.println("You have " + (MAX_ATTEMPTS - attempts) + " attempts left.");
                }
            } catch (SQLException e) {
                System.out.println("Error removing librarian from the database.");
                e.printStackTrace();
                throw e;
            }
        }
        System.out.println("Maximum attempts reached. Exiting removal process.");
    }

    public void pauseMembership() throws SQLException, SqlConnectionException {
        viewPatron();
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
                    String pauseSql = "UPDATE finallibrary.Patron SET Status = 'Not a Member' WHERE PatronID = ?";
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
                }
                attempts++; // Increment attempts
            }
        }
        System.out.println("Maximum attempts reached. Exiting pause process.");
        return;
    }


    public void resumeMembership() throws SQLException, SqlConnectionException {
        viewPatron();
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
                    String pauseSql = "UPDATE finallibrary.Patron SET Status = 'Member' WHERE PatronID = ?";
                    try (PreparedStatement pauseStatement = conn.prepareStatement(pauseSql)) {
                        pauseStatement.setString(1, patronID);
                        pauseStatement.executeUpdate();
                        System.out.println("Membership resumed successfully");
                        return; // Exit the method after successful pause
                    } catch (SQLException e) {
                        System.out.println("Error resuming membership in the database.");
                        e.printStackTrace();
                        throw e;
                    }
                }
                attempts++; // Increment attempts
            }
        }
        System.out.println("Maximum attempts reached. Exiting pause process.");
        return;

    }


    public void updateAdminProfile() throws SQLException, SqlConnectionException {

        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the admin you want to update: ");
            String adminID = sc.nextLine();
            String sql = "SELECT * FROM finallibrary.Admins WHERE AdminID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, adminID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Enter new first name (leave blank to keep current value): ");
                    String newFirstName = sc.nextLine();
                    System.out.println("Enter new last name (leave blank to keep current value): ");
                    String newLastName = sc.nextLine();
                    String newPhone, newEmail;
                    do {
                        System.out.println("Enter new phone (leave blank to keep current value): ");
                        newPhone = sc.nextLine();
                    } while (!Validator.isValidPhone(newPhone));
                    do {
                        System.out.println("Enter new email (leave blank to keep current value): ");
                        newEmail = sc.nextLine();
                    } while (!Validator.isValidEmail(newEmail));
                    // Update Admin Details
                    String updateAdminSql = "UPDATE finallibrary.Admins SET FirstName = ?, LastName = ?, Phone = ?, Email = ? WHERE AdminID = ?";
                    try (PreparedStatement updateAdminStatement = conn.prepareStatement(updateAdminSql)) {
                        updateAdminStatement.setString(1, newFirstName.isEmpty() ? resultSet.getString("FirstName") : newFirstName);
                        updateAdminStatement.setString(2, newLastName.isEmpty() ? resultSet.getString("LastName") : newLastName);
                        updateAdminStatement.setString(3, newPhone.isEmpty() ? resultSet.getString("Phone") : newPhone);
                        updateAdminStatement.setString(4, newEmail.isEmpty() ? resultSet.getString("Email") : newEmail);
                        updateAdminStatement.setString(5, adminID);
                        updateAdminStatement.executeUpdate();
                    }
                    System.out.println("Admin details updated successfully.");
                    return; // Exit the method after successful update
                } else {
                    System.out.println("Admin with ID " + adminID + " not found.");
                }
            }
            attempts++; // Increment attempts
        }
        System.out.println("Maximum attempts reached. Exiting update process.");
    }

    public void updateMemberProfile() throws SQLException, SqlConnectionException,ValidemailExceptions,ValidPasswordExceptions {

        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the patron you want to update: ");
            String patronID = sc.nextLine();
            String sql = "SELECT * FROM finallibrary.Patron WHERE PatronID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, patronID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Enter new first name (leave blank to keep current value): ");
                    String newFirstName = sc.nextLine();
                    System.out.println("Enter new last name (leave blank to keep current value): ");
                    String newLastName = sc.nextLine();
                    String newPhone, newEmail,newUsername,newPassword;
                    do {
                        System.out.println("Enter new phone (leave blank to keep current value): ");
                        newPhone = sc.nextLine();
                    } while (!Validator.isValidPhone(newPhone));
                    do {
                        System.out.println("Enter new email (leave blank to keep current value): ");
                        newEmail = sc.nextLine();
                    }while(!Validator.isValidEmail(newEmail));
                    System.out.println("Enter new address (leave blank to keep current value): ");
                    String newAddress = sc.nextLine();
                    // Update Patron Details
                    String updatePatronSql = "UPDATE finallibrary.Patron SET FirstName = ?, LastName = ?, Phone = ?, Email = ?, Address = ? WHERE PatronID = ?";
                    try (PreparedStatement updatePatronStatement = conn.prepareStatement(updatePatronSql)) {
                        updatePatronStatement.setString(1, newFirstName.isEmpty() ? resultSet.getString("FirstName") : newFirstName);
                        updatePatronStatement.setString(2, newLastName.isEmpty() ? resultSet.getString("LastName") : newLastName);
                        updatePatronStatement.setString(3, newPhone.isEmpty() ? resultSet.getString("Phone") : newPhone);
                        updatePatronStatement.setString(4, newEmail.isEmpty() ? resultSet.getString("Email") : newEmail);
                        updatePatronStatement.setString(5, newAddress.isEmpty() ? resultSet.getString("Address") : newAddress);
                        updatePatronStatement.setString(6, patronID);
                        updatePatronStatement.executeUpdate();
                    }
                    // Update UserName and Password in Accounts Tab

                    do {
                        System.out.println("Enter new UserName (leave blank to keep current value): ");
                        newUsername = sc.nextLine();
                    }while(!Validator.isValidUsername(newUsername));
                    do{
                        System.out.println("Enter new Password (leave blank to keep current value): ");
                        newPassword = sc.nextLine();
                    }while(!Validator.isValidPassword(newPassword));
                    String updateAccountSql = "UPDATE finallibrary.Accounts SET UserName = ?, Password = ? WHERE AccountID = ?";
                    try (PreparedStatement updateAccountStatement = conn.prepareStatement(updateAccountSql)) {
                        // Retrieve AccountID from the current patron's record
                        String accountID = resultSet.getString("AccountID");
                        updateAccountStatement.setString(1, newUsername.isEmpty() ? resultSet.getString("UserName") : newUsername);
                        updateAccountStatement.setString(2, newPassword.isEmpty() ? resultSet.getString("Password") : newPassword);
                        updateAccountStatement.setString(3, accountID);
                        updateAccountStatement.executeUpdate();
                    }


                    System.out.println("Patron details updated successfully.");
                    return; // Exit the method after successful update
                }
                else {
                    System.out.println("Patron with ID " + patronID + " not found.");
                }

            }

            attempts++; // Increment attempts
        }
        System.out.println("Maximum attempts reached. Exiting update process.");
    }

    public void UpdateLibrarianProfile() throws SQLException, SqlConnectionException {


        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter the ID of the librarian you want to update: ");
            String librarianID = sc.nextLine();
            String sql = "SELECT * FROM finallibrary.Librarian WHERE LibrarianID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, librarianID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {

                    System.out.println("Enter new first name  ");
                    String newFirstName = sc.nextLine();
                    System.out.println("Enter new last name  ");
                    String newLastName = sc.nextLine();
                    String newPhone, newEmail, newUserName, newPassword;

                    // Loop for getting a valid phone number
                    do {
                        System.out.println("Enter new phone  ");
                        newPhone = sc.nextLine();
                        if (newPhone.isEmpty()) {
                            // If input is empty, break the loop
                            break;
                        }
                    } while (!Validator.isValidPhone(newPhone));

                    // Loop for getting a valid email
                    do {
                        System.out.println("Enter new email  ");
                        newEmail = sc.nextLine();
                        if (newEmail.isEmpty()) {
                            // If input is empty, break the loop
                            break;
                        }
                    } while (!Validator.isValidEmail(newEmail));

                    do {


                        System.out.println("Enter new UserName  ");
                        newUserName = sc.nextLine();
                    }while(!Validator.isValidUsername(newUserName));

                    do {
                        System.out.println("Enter new Password  ");
                        newPassword = sc.nextLine();
                    }while(!Validator.isValidPassword(newPassword));

                    // Update Librarian Details
                    String updateLibrarianSql = "UPDATE finallibrary.Librarian SET FirstName = ?, LastName = ?, Phone = ?, Email = ? WHERE LibrarianID = ?";
                    try (PreparedStatement updateLibrarianStatement = conn.prepareStatement(updateLibrarianSql))  {
                        updateLibrarianStatement.setString(1, newFirstName.isEmpty() ? resultSet.getString("FirstName") : newFirstName);
                        updateLibrarianStatement.setString(2, newLastName.isEmpty() ? resultSet.getString("LastName") : newLastName);
                        updateLibrarianStatement.setString(3, newPhone.isEmpty() ? resultSet.getString("Phone") : newPhone);
                        updateLibrarianStatement.setString(4, newEmail.isEmpty() ? resultSet.getString("Email") : newEmail);
                        updateLibrarianStatement.setString(5, librarianID);
                        updateLibrarianStatement.executeUpdate();
                    }

                    // Update Account Details
                    String updateAccountSql = "UPDATE finallibrary.Accounts SET UserName = ?, Password = ? WHERE AccountID = ?";
                    PreparedStatement updateAccountStatement = conn.prepareStatement(updateAccountSql);
                    // Retrieve AccountID from the current librarian's record
                    String accountID = resultSet.getString("AccountID");
                    updateAccountStatement.setString(1, newUserName.isEmpty() ? resultSet.getString("UserName") : newUserName);
                    updateAccountStatement.setString(2, newPassword.isEmpty() ? resultSet.getString("Password") : newPassword);
                    updateAccountStatement.setString(3, accountID);
                    updateAccountStatement.executeUpdate();

                    System.out.println("Librarian details updated successfully.");
                    return; // Exit the method after successful update
                }
            } catch (SQLException e) {
                System.out.println("Error updating librarian details in the database.");
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

    public static void main(String[] args) throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        registerAdmin();
    }
}