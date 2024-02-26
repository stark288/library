package com.library.driver;
import com.library.accounts.AccountManagement;
import com.library.Layout;
import com.library.exceptions.*;
import com.library.users.Admin;
import java.sql.SQLException;
import java.util.Scanner;
import com.library.search.Catalogue;
import com.library.services.Reservation;

public class LibrarianMenu  {
        Scanner sc = new Scanner(System.in);
        AccountManagement accountManagement = new AccountManagement();
        Admin admin = new Admin();
        Catalogue catalogue = new Catalogue();
        Reservation reservation = new Reservation();
        static Driver driver = new Driver();


        public void PatronMenu() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
            Layout.PatronMenu();
            System.out.println("Enter your choice");
            int choice = driver.getValidInput();
            sc.nextLine();
            switch (choice) {
                case 1:
                    accountManagement.pauseMembership();
                    PatronMenu();
                    break;
                case 2:
                    accountManagement.resumeMembership();
                    PatronMenu();
                    break;
                case 3:
                    Librarian();
                    break;

                default:
                    System.out.println("Invalid choice for Patron Menu");
                    PatronMenu();
            }
        }

        public void BookMenu() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
            Layout.BookMenu();
            System.out.println("Enter your choice");
            int choice = driver.getValidInput();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Admin.addBook();
                    BookMenu();
                    break;
                case 2:
                    Admin.updateBook();
                    BookMenu();
                    break;
                case 3:
                    Admin.viewBooks();
                    BookMenu();
                    break;
                case 4:
                    Catalogue.search();
                    BookMenu();
                    break;
                case 5:
                    Admin.removeBook();
                    BookMenu();
                    break;
                case 6:
                    reservation.displayRequestedReservations();
                    BookMenu();
                    break;
                case 7:
                    Librarian();
                    break;
                default:
                    System.out.println("Invalid choice");
                    BookMenu();
            }
        }

        public void Librarian() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
            Layout.LibrarianMenu();
            System.out.println("Enter your choice");
            int choice = driver.getValidInput();
            sc.nextLine();
            switch (choice) {

                case 1:
                    PatronMenu();
                    break;
                case 2:
                    BookMenu();
                    break;
                case 3:
                    Driver driver = new Driver();
                    driver.mainmenu();
                    break;
                default:
                    System.out.println("Invalid choice");
                    Librarian();
            }
        }
}

