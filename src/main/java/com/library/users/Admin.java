package com.library.users;
import com.library.accounts.*;
import com.library.books.Book;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Comparator;

import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.InvalidDateFormatException;
import com.library.exceptions.SqlConnectionException;
import com.library.books.Author;
import com.library.validation.Validator;



import java.io.*;

import static com.library.Layout.printTable;
import static com.library.validation.Validator.isValidEmail;

//import static com.Library.Layout.BookLayout;
//import static com.Library.Layout.tableBottomLayout;

public class Admin extends Person {
    private int adminID;
    static Scanner sc = new Scanner(System.in);

    public Admin(String First_Name, String Last_Name, String Email, String Phone, Account account) {
        super(First_Name, Last_Name, Email, Phone, account);
    }

    public Admin() {
        super();
    }

    static AccountManagement accountManagement = new AccountManagement();

    public Admin(String firstName, String lastName, String email, String phone) {
    }
    static Patron patron = new Patron();

    @Override
    public String toString() {
		return "Admin{" + "adminID=" + adminID + '}';
        
    }

    // Establish a connection to the database
    public static void addBook() throws SQLException, SqlConnectionException, InvalidDateFormatException {
        Date publishedDate = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the title of the book: ");
        String title = sc.nextLine();
        System.out.println("Enter the ISBN of the book: ");
        String isbn = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean validDate = false;
        while (!validDate) {
            try {
                System.out.println("Enter the published date of the book (in dd/MM/yyyy format): ");
                String inputDateStr = sc.nextLine();
                LocalDate parsedDate = LocalDate.parse(inputDateStr, formatter);
                publishedDate = Date.valueOf(parsedDate);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please enter the date in dd/MM/yyyy format.");
                // Ask for date input again
            }
        }

        System.out.println("Enter the languages of the book: ");
        String languages = sc.nextLine();
        System.out.println("Enter the genre of the book: ");
        String genre = sc.nextLine();
        System.out.println("Enter the rack number of the book: ");
        String rackNo = sc.nextLine();
        System.out.println("Enter the publisher name of the book: ");
        String publisherName = sc.nextLine();
        System.out.println("Enter the publisher details of the book: ");
        String publisherDetails = sc.nextLine();
        System.out.println("Enter the author name of the book: ");
        String authorName = sc.nextLine();
        System.out.println("Enter the author details of the book: ");
        String authorDetails = sc.nextLine();
        System.out.println("Enter the type of book: ");
        String type = sc.nextLine();
        System.out.println("Enter the number of copies: ");
        int copies = sc.nextInt();
        String availability = copies > 0 ? "available" : "not available";
        Book newBook = new Book(title, isbn, publishedDate, languages, availability, genre, rackNo, new Author(authorName, authorDetails), publisherName, publisherDetails, type, copies);
        // SQL statement to insert a new book into the Book table
        String sql = "INSERT INTO finallibrary.Book (BookID, Title, ISBN, PublishedDate, Languages, Availability, Genre, RackNo, publishername, publisherDetails, Authorname, AuthorDetails,LIBRARYID,Typeofbook,copies) VALUES (finallibrary.book_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
        // Establish a connection to the database
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            // Set the parameters for the PreparedStatement
            statement.setString(1, newBook.getTitle());
            statement.setString(2, newBook.getIsbn());
            statement.setDate(3, newBook.getPublishedDate());
            statement.setString(4, newBook.getLanguages());
            statement.setString(5, availability);
            statement.setString(6, newBook.getGenre());
            statement.setString(7, newBook.getRackNo());
            statement.setString(8, newBook.getPublisherName());
            statement.setString(9, newBook.getPublisherDetails());
            statement.setString(10, Author.getAuthorName());
            statement.setString(11, Author.getContactDetails());
            statement.setInt(12,accountManagement.getLastLibraryId());
            statement.setString(13, newBook.getType());
            statement.setInt(14, newBook.getCopies());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully");
            } else {
                System.out.println("No book was added");
            }
        } catch (SQLException e) {
            System.out.println("Error adding book to the database.");
            throw e;
        }
    }

    public void addPolicy() {
        System.out.println("Open file to write policy");
        System.out.println("Enter policy to be added: ");
        String policy = sc.nextLine();
        try {
            File file = new File("C:\\Users\\gcs21\\OneDrive\\Desktop\\LibraryManagementSystem\\policy.txt");

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(policy);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error adding policy to the database.");
        }
        System.out.println("Policy added successfully");
    }


    public static void addLibrary() throws SQLException, SqlConnectionException {
        Scanner scanner = new Scanner(System.in);
        String email,phone;
        System.out.println("Enter Library Name:");
        String Name = scanner.nextLine();
        System.out.println("Enter Location:");
        String location = scanner.nextLine();
        do {
            System.out.println("enter email of the library");
            email = scanner.nextLine();
        }while(!isValidEmail(email));
        do {
            System.out.println("enter phone of the library");
            phone = scanner.nextLine();
        }while(!Validator.isValidPhone(phone));
        System.out.println("Enter hours of operation:");
        String hours = scanner.nextLine();
        Integer AdminId = getAdminId();

        String sql = "INSERT INTO finallibrary.Library (LibraryID, Name, Location, AdminID, ContactEmail, ContactPhone, HoursOfOperation) VALUES (finallibrary.library_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, Name);
            statement.setString(2, location);
            if (AdminId != null) {
                statement.setInt(3, AdminId);
            } else {
                statement.setNull(3, Types.INTEGER); // Set the AdminID to NULL if it's not available
            }
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setString(6, hours);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Library added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding library to the database.");
            e.printStackTrace();
            throw e;
        }
    }


    public static void removeBook() throws SqlConnectionException {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the ISBN of the book you want to remove: ");
            String isbn = sc.nextLine();
            String sql = "UPDATE finallibrary.Book SET Availability = 'notavailable' WHERE ISBN = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, isbn);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book availability set to inactive successfully");
                    return; // Exit the method after successful update
                } else {
                    System.out.println("No book with that ISBN exists in the database");
                    attempts++;
                    if (attempts >= MAX_ATTEMPTS) {
                        System.out.println("Maximum attempts reached. Exiting removal process.");
                        return;
                    }
                    System.out.println("You have " + (MAX_ATTEMPTS - attempts) + " attempts left.");
                }
            } catch (SQLException e) {
                System.out.println("Error removing book availability from the database.");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        System.out.println("Maximum attempts reached. Exiting removal process.");
    }


    public void removePolicy() {
        System.out.println("Enter policy to be removed: ");
        String policy = sc.nextLine();
        try {
            File file = new File("C:\\Users\\gcs21\\OneDrive\\Desktop\\LibraryManagementSystem\\policy.txt");
            File tempFile = new File("C:\\Users\\gcs21\\OneDrive\\Desktop\\LibraryManagementSystem\\temp.txt");
            file.delete();
            tempFile.renameTo(file);
        } catch (Exception e) {
            System.out.println("Error removing policy from the database.");
        }
    }


    public static void updateBook() throws SQLException, SqlConnectionException {
        viewBooks();
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {
            try {
                System.out.println("Enter the ISBN of the book you want to update: ");
                String isbn = sc.nextLine();

                // Check if the ISBN exists in the database
                if (!Validator.isbnExists(isbn)) {
                    System.out.println("No book with that ISBN exists in the database");
                    attempts++;
                    if (attempts >= MAX_ATTEMPTS) {
                        System.out.println("Maximum attempts reached. Exiting update process.");
                        return;
                    }
                    System.out.println("You have " + (MAX_ATTEMPTS - attempts) + " attempts left.");
                    continue;
                }

                // Continue with the update process
                System.out.println("Select the field you want to update:");
                System.out.println("1. Published Date");
                System.out.println("2. Languages (Add new language)");
                System.out.println("3. Genre");
                System.out.println("4. Rack Number");
                System.out.println("5. Publisher Name");
                System.out.println("6. Publisher Details");
                System.out.println("7. Author Name");
                System.out.println("8. Author Details");
                System.out.println("9. Type of Book");
                System.out.println("10. Number of Copies");
                System.out.println("11. Exit update process");

                int choice = Integer.parseInt(sc.nextLine()); // Use nextLine and parse to avoid Scanner issues

                String sql = "UPDATE finallibrary.Book SET %s = ? WHERE ISBN = ?";
                String field = "";
                Object value = null;

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new published date of the book (in yyyy-MM-dd format): ");
                        String dateStr = sc.nextLine();
                        LocalDate date = LocalDate.parse(dateStr); // Default ISO_LOCAL_DATE format is yyyy-MM-dd
                        value = Date.valueOf(date);
                        field = "PublishedDate";
                        break;
                    case 2:
                        System.out.println("Enter the new language to add to the book: ");
                        value = sc.nextLine();
                        field = "Languages"; // Assuming you want to replace the entire languages field
                        break;
                    case 3:
                        System.out.println("Enter the new genre of the book: ");
                        value = sc.nextLine();
                        field = "Genre";
                        break;
                    case 4:
                        System.out.println("Enter the new rack number of the book: ");
                        value = sc.nextLine();
                        field = "RackNo";
                        break;
                    case 5:
                        System.out.println("Enter the new publisher name of the book: ");
                        value = sc.nextLine();
                        field = "PublisherName";
                        break;
                    case 6:
                        System.out.println("Enter the new publisher details of the book: ");
                        value = sc.nextLine();
                        field = "PublisherDetails";
                        break;
                    case 7:
                        System.out.println("Enter the new author name of the book: ");
                        value = sc.nextLine();
                        field = "AuthorName";
                        break;
                    case 8:
                        System.out.println("Enter the new author details of the book: ");
                        value = sc.nextLine();
                        field = "AuthorDetails";
                        break;
                    case 9:
                        System.out.println("Enter the new type of book: ");
                        value = sc.nextLine();
                        field = "TypeOfBook";
                        break;
                    case 10:
                        System.out.println("Enter the new number of copies: ");
                        value = Integer.parseInt(sc.nextLine()); // Use nextLine and parse to avoid Scanner issues
                        field = "Copies";
                        break;
                    case 11:
                        System.out.println("Exiting update process.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 11.");
                        continue;
                }

                sql = String.format(sql, field); // Format the SQL string with the chosen field

                try (Connection conn = DataBaseutils.getConnection();
                     PreparedStatement statement = conn.prepareStatement(sql)) {
                    if (value instanceof Date) {
                        statement.setDate(1, (Date) value);
                    } else if (value instanceof Integer) {
                        statement.setInt(1, (Integer) value);
                    } else if (value instanceof String) {
                        statement.setString(1, (String) value);
                    }
                    statement.setString(2, isbn);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Book updated successfully");
                    } else {
                        System.out.println("No book was updated");
                    }
                    return; // Exit the method after successful update
                } catch (SQLException e) {
                    System.out.println("Error updating book in the database.");
                    e.printStackTrace();
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        System.out.println("Maximum attempts reached. Exiting update process.");
    }
    public void updatePolicy() {
        System.out.println("Enter policy to be updated: ");
        String policy = sc.nextLine();
        try {
            File file = new File("C:\\Users\\gcs21\\OneDrive\\Desktop\\LibraryManagementSystem\\policy.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(policy);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error updating policy in the database.");
        }
        System.out.println("Policy updated successfully");
    }
    public static void updateLibrary() throws SQLException, SqlConnectionException {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        while (attempts < MAX_ATTEMPTS) {

            System.out.println("Enter the ID of the library you want to update: ");
            String libraryID = sc.nextLine();
            String sql = "SELECT * FROM finallibrary.Library WHERE LibraryID = ?";
            try (Connection conn = DataBaseutils.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, libraryID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Enter new name (leave blank to keep current value): ");
                    String newName = sc.nextLine();
                    System.out.println("Enter new location (leave blank to keep current value): ");
                    String newLocation = sc.nextLine();
                    System.out.println("Enter new email (leave blank to keep current value): ");
                    String newEmail = sc.nextLine();
                    System.out.println("Enter new phone (leave blank to keep current value): ");
                    String newPhone = sc.nextLine();
                    System.out.println("Enter new hours of operation (leave blank to keep current value): ");
                    String newHours = sc.nextLine();

                    return;
                }
                System.out.println("No library with that ID exists in the database");
                attempts++;
                if (attempts >= MAX_ATTEMPTS) {
                    System.out.println("Maximum attempts reached. Exiting update process.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating library in the database.");

            }

        }
    }

    public static void viewBooks() throws SQLException, SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Book";

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No books found in the database.");
                return;
            }

            List<String[]> rows = new ArrayList<>();
            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("Title"),
                        resultSet.getString("ISBN"),
                        resultSet.getDate("PublishedDate") != null ? resultSet.getDate("PublishedDate").toString() : "",
                        resultSet.getString("Languages"),
                        resultSet.getString("Availability"),
                        resultSet.getString("Genre"),
                        resultSet.getString("RackNo"),
                        resultSet.getString("publishername"),
                        resultSet.getString("publisherDetails"),
                        resultSet.getString("Authorname"),
                        resultSet.getString("AuthorDetails"),
                        resultSet.getString("Typeofbook"),
                        resultSet.getString("Copies"),
                        resultSet.getString("LibraryID")
                };
                rows.add(rowData);
            }

            // Sort rows by book ID
            rows.sort(Comparator.comparing(row -> row[0]));

            String[] headers = { "Title", "ISBN", "Published Date", "Languages", "Availability", "Genre", "Rack Number",
                    "Publisher Name", "Publisher Details", "Author Name", "Author Details", "Type of Book", "Copies", "LibraryID"};

            printTable(headers, rows);

        } catch (SQLException e) {
            System.out.println("Error viewing books from the database.");
            e.printStackTrace();
            throw e;
        }
    }
    public static void viewPolicies() {
        try {
            File file = new File("C:\\Users\\gcs21\\OneDrive\\Desktop\\LibraryManagementSystem\\policy.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error viewing policies from the database.");
        }
    }
    public static void viewLibrary() throws SQLException, SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Library";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Library ID", "Name", "Location", "Admin ID", "Contact Email", "Contact Phone", "Hours of Operation"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No libraries found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("LibraryID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Location"),
                        resultSet.getString("AdminID"),
                        resultSet.getString("ContactEmail"),
                        resultSet.getString("ContactPhone"),
                        resultSet.getString("HoursOfOperation")
                };
                rows.add(rowData);
            }
        }

        // Sort libraries by ID
        rows.sort(Comparator.comparing(row -> Integer.parseInt(row[0])));

        printTable(headers, rows);
    }

    public static void viewAccounts() throws SQLException, SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Accounts";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"AccountType", "Username", "Password"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No accounts found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("AccountType"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password")
                };
                rows.add(rowData);
            }
        }
        rows.sort(Comparator.comparing(row -> row[0]));

        printTable(headers, rows);
    }

    public void viewFeedback() throws SQLException, SqlConnectionException {
        String sql = "SELECT * FROM finallibrary.Feedback";

        List<String[]> rows = new ArrayList<>();
        String[] headers = {"Feedback ID", "Feedback", "Rating"};

        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No feedback found in the database.");
                return;
            }

            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("FeedbackID"),
                        resultSet.getString("Feedback"),
                        resultSet.getString("Rating")
                };
                rows.add(rowData);
            }
            rows.sort(Comparator.comparing(row -> row[0]));
        }

        printTable(headers, rows);
    }







    private static int getAdminId() throws SqlConnectionException {
        String sql = "SELECT AdminID FROM finallibrary.Admins";
        try (Connection conn = DataBaseutils.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt("AdminID");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching manager ID from the database.");
            e.printStackTrace();
        }
        return getAdminId();
    }


    public static void main(String[] args) throws SQLException, SqlConnectionException, InvalidDateFormatException {
        Scanner sc = new Scanner(System.in);
       //viewBooks();
        //addBook();
        //viewPolicies();
        //viewAccounts();
        //addLibrary();
        //addAdmin();
        //removeBook();
        updateBook();
    }
}