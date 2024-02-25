package com.Library.Driver;

import com.Library.Accounts.AccountManagement;
import com.Library.Layout;
import com.Library.Search.Catalogue;
import com.Library.Services.Borrow;
import com.Library.Services.fineandPayment;
import com.Library.Services.Reservation;
import com.Library.Services.ReturnBook;

import com.Library.Users.Admin;
import com.Library.Users.Patron;
import com.Library.exceptions.*;

import java.sql.SQLException;
import java.util.Scanner;
import com.Library.Payments.Payment;

import static com.Library.Layout.*;

public class PatronMenu {
    Scanner sc = new Scanner(System.in);
    AccountManagement accountManagement = new AccountManagement();
    Catalogue catalogue = new Catalogue();
    Reservation reservation = new Reservation();
    Admin admin = new Admin();
    Patron patron = new Patron();
    ReturnBook returnBook = new ReturnBook();
    Payment payment = new Payment();
    Borrow borrow = new Borrow();
    fineandPayment fineandPayment = new fineandPayment();
    static Driver driver = new Driver();

    // Patron Menu

    public void MemberMenu() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        Membermenu();
        int choice = driver.getValidInput();
        sc.nextLine();
        switch (choice) {
            case 1:
                accountManagement.updateMemberProfile();
                MemberMenu();
                break;
            case 2:
                patron();
            default:
                System.out.println("Invalid choice");
                MemberMenu();
        }
    }

    public void searchMenu() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        searchInterface();
        int choice = driver.getValidInput();
        sc.nextLine();
        switch (choice) {
            case 1:
                catalogue.searchbyTitle();
                searchMenu();
                break;
            case 2:
                catalogue.searchbyauthor();
                searchMenu();
                break;
            case 3:
                catalogue.searchbygenre();
                searchMenu();
                break;
            case 4:
                catalogue.searchbyisbn();
                searchMenu();
                break;
            case 5:
                catalogue.searchbypublisher();
                searchMenu();
                break;
            case 6:
                catalogue.searchbylanguages();
                searchMenu();
                break;
            case 7:
                patron();
            default:
                System.out.println("Invalid choice for search menu");
                searchMenu();
        }
    }

    public void services() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        servicemenu();
        int choice = driver.getValidInput();
        sc.nextLine();
        switch (choice) {
            case 1:
                borrow.borrowBook();
                services();
                break;
            case 2:
                returnBook.returnBook();
                services();
                break;
            case 3:
                borrow.viewBorrows();
                services();
                break;
            case 4:
                reservation.doReservation();
                services();
                break;
            case 5:
                reservation.viewReservedBooks();
                services();
                case 6:
                    patron();
            default:
                System.out.println("Invalid choice for services menu");
                services();
        }
    }

    public void patron() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        while (true) {
            Layout.printPatronMenuOptions();
            int choice = driver.getValidInput();
            sc.nextLine();
            switch (choice) {
                case 1:
                    MemberMenu();
                    break;
                case 2:
                    searchMenu();
                    break;
                case 3:
                    services();
                    break;
                case 4:
                    Driver driver = new Driver();
                    driver.mainmenu();
                    break;
                default:
                    System.out.println("Invalid choice for patron menu");
                    patron();
            }
        }
    }

}
