package com.library.search;

import com.library.Layout;
import com.library.databaseservices.DataBaseutils;
import com.library.exceptions.SqlConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.library.Layout.printTable;

public class Catalogue implements Searchable {

    @Override
    public void searchbyTitle() throws SQLException, SqlConnectionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the title of the book you want to search for: ");
        String title = sc.nextLine();
        String sql = "SELECT * FROM finallibrary.Book WHERE Title = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> rows = new ArrayList<>();
            String[] headers = {"Title", "Author", "ISBN", "Genre", "Publisher", "Rack Number", "Availability", "Copies", "Type"};
            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("Title"),
                        resultSet.getString("AuthorName"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Genre"),
                        resultSet.getString("PublisherName"),
                        resultSet.getString("RackNo"),
                        resultSet.getString("Availability"),
                        String.valueOf(resultSet.getInt("Copies")),
                        resultSet.getString("Typeofbook")
                };
                rows.add(rowData);
            }
            if (!rows.isEmpty()) {
                printTable(headers, rows);
            } else {
                System.out.println("No books found for the given title.");
            }
        }
    }

    @Override
    public void searchbyauthor() throws SQLException, SqlConnectionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the author of the book you want to search for: ");
        String author = sc.nextLine();
        String sql = "SELECT * FROM finallibrary.Book WHERE AuthorName = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> rows = new ArrayList<>();
            String[] headers = {"Title", "Author", "ISBN", "Genre", "Publisher", "Rack Number", "Availability", "Copies", "Type", "Language"};
            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("Title"),
                        resultSet.getString("AuthorName"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Genre"),
                        resultSet.getString("PublisherName"),
                        resultSet.getString("RackNo"),
                        resultSet.getString("Availability"),
                        String.valueOf(resultSet.getInt("Copies")),
                        resultSet.getString("Typeofbook"),
                        resultSet.getString("Languages")
                };
                rows.add(rowData);
            }
            if (!rows.isEmpty()) {
                printTable(headers, rows);
            } else {
                System.out.println("No books found for the given author.");
            }
        }
    }


    @Override
    public void searchbypublisher() throws SQLException, SqlConnectionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the publisher of the book you want to search for: ");
        String publisher = sc.nextLine();
        String sql = "SELECT * FROM finallibrary.Book WHERE PublisherName = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, publisher);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> rows = new ArrayList<>();
            String[] headers = {"Title", "Author", "ISBN", "Genre", "Publisher", "Rack Number", "Availability", "Copies", "Type", "Language"};
            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("Title"),
                        resultSet.getString("AuthorName"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Genre"),
                        resultSet.getString("PublisherName"),
                        resultSet.getString("RackNo"),
                        resultSet.getString("Availability"),
                        String.valueOf(resultSet.getInt("Copies")),
                        resultSet.getString("Typeofbook"),
                        resultSet.getString("Languages")
                };
                rows.add(rowData);
            }
            if (!rows.isEmpty()) {
                printTable(headers, rows);
            } else {
                System.out.println("No books found for the given publisher.");
            }
        }
    }

    @Override
    public void searchbygenre() throws SQLException, SqlConnectionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the genre of the book you want to search for: ");
        String genre = sc.nextLine();
        String sql = "SELECT * FROM finallibrary.Book WHERE Genre = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, genre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Book found: ");
                System.out.println("Title: " + resultSet.getString("Title"));
                System.out.println("Author: " + resultSet.getString("AuthorName"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Genre: " + resultSet.getString("Genre"));
                System.out.println("Publisher: " + resultSet.getString("PublisherName"));
                System.out.println("Rack Number: " + resultSet.getString("RackNo"));
                System.out.println("Availability: " + resultSet.getString("Availability"));
                System.out.println("Copies: " + resultSet.getInt("Copies"));
                System.out.println("Type: " + resultSet.getString("Typeofbook"));
                System.out.println("Language: " + resultSet.getString("Languages"));
            } else {
                System.out.println("Book not found.");
            }

        }

    }
    @Override
    public void searchbyrack() throws SQLException, SqlConnectionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the rack number of the book you want to search for: ");
        String rack = sc.nextLine();
        String sql = "SELECT * FROM finallibrary.Book WHERE RackNo = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, rack);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> rows = new ArrayList<>();
            String[] headers = {"Title", "Author", "ISBN", "Genre", "Rack No", "Availability", "Copies", "Type", "Language", "Publisher"};
            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("Title"),
                        resultSet.getString("AuthorName"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Genre"),
                        resultSet.getString("RackNo"),
                        resultSet.getString("Availability"),
                        String.valueOf(resultSet.getInt("Copies")),
                        resultSet.getString("Typeofbook"),
                        resultSet.getString("Languages"),
                        resultSet.getString("PublisherName")
                };
                rows.add(rowData);
            }
            if (!rows.isEmpty()) {
                printTable(headers, rows);
            } else {
                System.out.println("Book not found.");
            }
        }
    }


    @Override
    public void searchbyisbn() throws SQLException, SqlConnectionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ISBN of the book you want to search for: ");
        String isbn = sc.nextLine();
        String sql = "SELECT * FROM finallibrary.Book WHERE ISBN = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> rows = new ArrayList<>();
            String[] headers = {"Title", "Author", "ISBN", "Genre", "Publisher", "Rack Number", "Availability", "Copies", "Type", "Language"};
            if (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("Title"),
                        resultSet.getString("AuthorName"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Genre"),
                        resultSet.getString("PublisherName"),
                        resultSet.getString("RackNo"),
                        resultSet.getString("Availability"),
                        String.valueOf(resultSet.getInt("Copies")),
                        resultSet.getString("Typeofbook"),
                        resultSet.getString("Languages")
                };
                rows.add(rowData);
            }
            if (!rows.isEmpty()) {
                printTable(headers, rows);
            } else {
                System.out.println("Book not found.");
            }
        }
    }

    @Override
    public void searchbylanguages() throws SQLException, SqlConnectionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the languages of the book you want to search for: ");
        String languages = sc.nextLine();
        String sql = "SELECT * FROM finallibrary.Book WHERE Languages = ?";
        try (Connection conn = DataBaseutils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, languages);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> rows = new ArrayList<>();
            String[] headers = {"Title", "Author", "ISBN", "Genre", "Languages", "Publisher", "Rack Number", "Availability", "Copies", "Type"};
            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("Title"),
                        resultSet.getString("AuthorName"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Genre"),
                        resultSet.getString("Languages"),
                        resultSet.getString("PublisherName"),
                        resultSet.getString("RackNo"),
                        resultSet.getString("Availability"),
                        String.valueOf(resultSet.getInt("Copies")),
                        resultSet.getString("Typeofbook")
                };
                rows.add(rowData);
            }
            if (!rows.isEmpty()) {
                printTable(headers, rows);
            } else {
                System.out.println("No books found for the given language.");
            }
        }
    }

    public static void search(){
       Layout layout = new Layout();
       layout.searchoption();
        System.out.println("Enter your choice: ");
        Scanner sc = new Scanner(System.in);
        Catalogue Catalogue = new Catalogue();
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                // Search by title
                try {
                    Catalogue.searchbyTitle();
                } catch (SQLException | SqlConnectionException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                // Search by author
                try {
                    Catalogue.searchbyauthor();
                } catch (SQLException | SqlConnectionException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                // Search by publisher
                try {
                    Catalogue.searchbypublisher();
                } catch (SQLException | SqlConnectionException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                // Search by genre
                try {
                    Catalogue.searchbygenre();
                } catch (SQLException | SqlConnectionException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                // Search by rack
                try {
                    Catalogue.searchbyrack();
                } catch (SQLException | SqlConnectionException e) {
                    e.printStackTrace();
                }
                break;

            case 6:
                // Search by ISBN
                try {
                    Catalogue.searchbyisbn();
                }
                catch (SQLException | SqlConnectionException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try{
                    Catalogue.searchbylanguages();
                }
                catch (SQLException | SqlConnectionException e) {
                    e.printStackTrace();
                }
                break;

            case 8:
                break;

            default:
                System.out.println("Invalid choice");
                search();
        }
    }
}
