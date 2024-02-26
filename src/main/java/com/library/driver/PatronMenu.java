package com.library.driver;

import com.library.accounts.AccountManagement;
import com.library.Layout;
import com.library.search.Catalogue;
import com.library.services.Borrow;
import com.library.services.fineandPayment;
import com.library.services.Reservation;
import com.library.services.ReturnBook;

import com.library.users.Admin;
import com.library.users.Patron;
import com.library.exceptions.*;

import java.sql.SQLException;
import java.util.Scanner;
import com.library.payments.Payment;

import static com.library.Layout.*;

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
                break;
            case 6:
                fineandPayment.viewFine();
                services();
                break;
            case 7:
                patron();
                break;
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
                    catalogue.search();
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
