package com.Library.Driver;
import com.Library.Layout;
import com.Library.Users.Admin;
import com.Library.Accounts.AccountManagement;
import com.Library.Search.Catalogue;
import com.Library.exceptions.*;

import java.sql.SQLException;
import java.util.Scanner;

import static com.Library.Layout.searchInterface;

/**
 * This class is for the guest menu
 */
public class GuestMenu {
    Scanner sc = new Scanner(System.in);
    Admin admin = new Admin();
    AccountManagement accountManagement = new AccountManagement();
    Catalogue catalogue = new Catalogue();
    static Driver driver = new Driver();
    public void guestMenu() throws SqlConnectionException, SQLException, InvalidDateFormatException, ValidPasswordExceptions, ValidemailExceptions {
            Layout.printGuestMenuOptions();
            int choice = driver.getValidInput();
            sc.nextLine();
            switch (choice) {
                case 1:
                    searchInterface();
                    catalogue.search();
                    guestMenu();
                    break;
                case 2:
                    accountManagement.registerMember();
                    guestMenu();
                    break;

                case 3:
                  Driver driver = new Driver();
                  driver.mainmenu();
                  break;
                  default:
                    System.out.println("Invalid choice for Guest Menu");
                    guestMenu();
            }
        }
    }




