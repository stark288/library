package com.library.driver;

import com.library.Layout;
import com.library.services.*;
import com.library.users.*;
import com.library.accounts.AccountManagement;
import com.library.search.Catalogue;
import com.library.exceptions.*;

import java.sql.SQLException;
import java.util.Scanner;

import static com.library.Layout.printServicesMenuOptions;


public class AdminMenu {

    Admin admin = new Admin();
    Borrow borrow = new Borrow();
    Reservation reservation = new Reservation();
    AccountManagement accountManagement = new AccountManagement();
   static  Driver driver = new Driver();
   Layout layout = new Layout();
   fineandPayment fineandPayment = new fineandPayment();
   Patron patron = new Patron();

    Scanner sc = new Scanner(System.in);
    public void BookActions() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {

        layout.printAdminBookMenuOptions();
        int choice = driver.getValidInput();
        sc.nextLine();
        switch (choice) {

            case 1:
                Admin.addBook();
                BookActions();
                break;
            case 2:
                Admin.removeBook();
                BookActions();
                break;
            case 3:
                Admin.updateBook();
                BookActions();
                break;
            case 4:
                Admin.viewBooks();
                BookActions();
                break;
            case 5:

                Catalogue.search();
                BookActions();
                break;
            case 6:
                Admin();
                break;

            default:
                System.out.println("Invalid choice");
                BookActions();
        }
    }

    public void LibrarianActions() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        layout.printAdminLibrarianMenuOptions();

        int choice = driver.getValidInput();
        sc.nextLine();
        switch (choice) {
            case 1:
                accountManagement.registerLibrarian();
                LibrarianActions();
                break;
            case 2:
                accountManagement.removeLibrarian();
                LibrarianActions();
                break;
            case 3:
                accountManagement.updateLibrarianProfile();
                LibrarianActions();
                break;
            case 4:
                accountManagement.viewLibrarians();
                LibrarianActions();
                break;

            case 5:
                Admin();
                break;

            default:
                System.out.println("Invalid choice");
                LibrarianActions() ;
        }

    }

    public void LibraryActions() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        layout.ptintLibraryManagementMenuOptions();

        int choice = driver.getValidInput();
        sc.nextLine();
        switch (choice) {
            case 1:
                Admin.addLibrary();
                LibraryActions();
                break;

            case 2:
                Admin.updateLibrary();
                LibraryActions();
                break;
            case 3:
                Admin.viewLibrary();
                LibraryActions();
                break;
            case 4:
                Admin();
                break;
            default:
                System.out.println("Invalid choice");
                LibraryActions();
        }
    }
    public void AdminActions() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        layout.AdminManagement();

        int choice = driver. getValidInput();
        sc.nextLine();
        switch (choice) {
            case 1:
                accountManagement.registerAdmin();
                AdminActions();
                break;

            case 3:
                Admin();
                break;
            default:
                System.out.println("Invalid choice");
                AdminActions();
        }
    }

    public void viewservices() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        printServicesMenuOptions();

        int choice = driver.getValidInput();
        sc.nextLine(); // Consume the newline left-over
        switch (choice) {
            case 1:
                // View accounts
                admin.viewAccounts();
                viewservices();
                break;
            case 3:
                // View feedback
                admin.viewFeedback();
                viewservices();
                break;
            case 2:
                // View fines
                fineandPayment.adminviewFine();
                viewservices();
                break;
            case 4:
                borrow.viewBorrows();
                Admin();
            default:
                System.out.println("Invalid choice");
                viewservices();
        }
    }

    public void manageLibraryPolicies() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
        layout.policymanagement();
        int choice =driver. getValidInput();
        sc.nextLine(); // Consume the newline left-over
        switch (choice) {
            case 1:
                // Add a policy
                admin.addPolicy();
                manageLibraryPolicies();
                break;
            case 2:
                // Remove a policy
                admin.removePolicy();
                manageLibraryPolicies();
                break;
            case 3:
                // Update a policy
                admin.updatePolicy();
                manageLibraryPolicies();
                break;
            case 4:
                // View all policies
                admin.viewPolicies();
                manageLibraryPolicies();
                break;
            case 5:

                Admin();
                return;
            default:
                System.out.println("Invalid choice for managing library policies");
                manageLibraryPolicies();
        }

    }

    public void Admin() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
        layout.Admin();
        int choice = driver.getValidInput();
        sc.nextLine();
        switch (choice) {
            case 1:
                BookActions();
                break;
            case 2:
                LibrarianActions();
                break;
            case 3:
                LibraryActions();
                break;
            case 4:
                AdminActions();
                break;
            case 5:
                viewservices();
                break;
            case 6:
                manageLibraryPolicies();
                break;
            case 7:
                Driver driver = new Driver();
                driver.mainmenu();
                break;
            default:
                System.out.println("Invalid choice");
                Admin();
        }
    }
}
