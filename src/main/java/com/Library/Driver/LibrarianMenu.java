package com.Library.Driver;
import com.Library.Accounts.AccountManagement;
import com.Library.exceptions.*;
import com.Library.Users.Admin;
import java.sql.SQLException;
import java.util.Scanner;
import com.Library.Search.Catalogue;
import com.Library.Services.Reservation;

public class LibrarianMenu  {
        Scanner sc = new Scanner(System.in);
        AccountManagement accountManagement = new AccountManagement();
        Admin admin = new Admin();
        Catalogue catalogue = new Catalogue();
        Reservation reservation = new Reservation();
        static Driver driver = new Driver();


        public void PatronMenu() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
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

