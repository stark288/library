package com.Library.Search;

import com.Library.databaseservices.DataBaseutils;
import com.Library.exceptions.SqlConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
            if (resultSet.next()) {
                System.out.println("Book found: ");
                System.out.println("Title: " + resultSet.getString("Title"));
                System.out.println("Author: " + resultSet.getString("AuthorName"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Genre: " + resultSet.getString("Genre"));
            } else {
                System.out.println("Book not found.");
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
            if (resultSet.next()) {
                System.out.println("Book found: ");
                System.out.println("Title: " + resultSet.getString("Title"));
                System.out.println("Author: " + resultSet.getString("AuthorName"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Genre: " + resultSet.getString("Genre"));
            } else {
                System.out.println("Book not found.");
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
            if (resultSet.next()) {
                System.out.println("Book found: ");
                System.out.println("Title: " + resultSet.getString("Title"));
                System.out.println("Author: " + resultSet.getString("AuthorName"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Genre: " + resultSet.getString("Genre"));
            } else {
                System.out.println("Book not found.");
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
            if (resultSet.next()) {
                System.out.println("Book found: ");
                System.out.println("Title: " + resultSet.getString("Title"));
                System.out.println("Author: " + resultSet.getString("AuthorName"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Genre: " + resultSet.getString("Genre"));
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
            if (resultSet.next()) {
                System.out.println("Book found: ");
                System.out.println("Title: " + resultSet.getString("Title"));
                System.out.println("Author: " + resultSet.getString("AuthorName"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Genre: " + resultSet.getString("Genre"));
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
            statement.setString(1, String.valueOf(languages));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Book found: ");
                System.out.println("Title: " + resultSet.getString("Title"));
                System.out.println("Author: " + resultSet.getString("AuthorName"));
                System.out.println("ISBN: " + resultSet.getString("ISBN"));
                System.out.println("Genre: " + resultSet.getString("Genre"));
            } else {
                System.out.println("Book not found.");
            }
        }
    }
    public static void search(){
        System.out.println("Enter the search criteria: ");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publisher");
        System.out.println("4. Genre");
        System.out.println("5. Rack");
        System.out.println("6. ISBN");
        System.out.println("7. Languages");
        System.out.println("8. Back");
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
