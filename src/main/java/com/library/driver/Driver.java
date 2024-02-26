package com.library.driver;
import com.library.Layout;
import com.library.exceptions.*;

import com.library.accounts.AccountManagement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Driver {
    AdminMenu adminMenu = new AdminMenu();
    LibrarianMenu librarianMenu = new LibrarianMenu();
    PatronMenu patronMenu = new PatronMenu();
    AccountManagement accountManagement = new AccountManagement();
    GuestMenu guestMenu = new GuestMenu();
    Layout layout = new Layout();

    Scanner sc = new Scanner(System.in);


    public int getValidInput()  {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            try {
                System.out.println("Enter your choice (number): ");
                choice = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return choice;
    }



    public void mainmenu() throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
        Layout.printHeader();
       Layout.printMenuOptions();
        int choice = getValidInput();
        switch (choice) {
            case 1:
                Layout.printAdminMenuOptions();
                int choice1 = getValidInput();
                sc.nextLine();
                switch (choice1) {
                    case 1:
                        accountManagement.registerAdmin();
                        mainmenu();
                        break;
                    case 2:
                        accountManagement.registerLibrarian();
                        mainmenu();
                        break;
                    case 3:
                        accountManagement.registerMember();
                        mainmenu();
                        break;
                    case 4:
                        mainmenu();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        mainmenu();
                }
                break;
            case 2:
                System.out.println("Login as:");
                Layout.printAdminMenuOptions();
                int choice2 = getValidInput();
                sc.nextLine();
                switch(choice2) {
                    case 1:
                        accountManagement.adminLogin();
                        break;
                    case 2:
                        accountManagement.librarianLogin();
                        break;
                    case 3:
                        accountManagement.patronLogin();
                        break;
                    case 4:
                        mainmenu();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        mainmenu() ;
                }
                break;

            case 3:

                guestMenu.guestMenu();
                break;
            case 4:
                Layout.theEnd();
                System.exit(0);
                default:
                    System.out.println("Invalid choice");
                    mainmenu();
        }
    }
        public static void main (String[] args) throws SQLException, SqlConnectionException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions{
            Driver driver = new Driver();
            driver.mainmenu();
        }
    }





