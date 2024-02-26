package com.library;

import java.util.List;

public class Layout {
	public static void printHeader() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("******************************************************************");
		System.out.println("|                                                                |");
		System.out.println("|                   Welcome to Knowledge Citadel                 |");
		System.out.println("|                                                                |");
		System.out.println("******************************************************************");
		System.out.println("------------------------------------------------------------------");
	}

	public static void printMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Register                                               |");
		System.out.println("|      2. Login                                                  |");
		System.out.println("|      3. Guest                                                  |");
		System.out.println("|      4. Exit                                                   |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void printAdminMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Admin                                                  |");
		System.out.println("|      2. Librarian                                              |");
		System.out.println("|      3. Patron                                                 |");
		System.out.println("|      4. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}


	public static void printPatronMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Membership Menu                                        |");
		System.out.println("|      2. Search Menu                                            |");
		System.out.println("|      3. Services Menu                                          |");
		System.out.println("|      4. Go back to the main menu                               |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void printLibrarianMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Add Librarian                                          |");
		System.out.println("|      2. Remove Librarian                                       |");
		System.out.println("|      3. Update Librarian                                       |");
		System.out.println("|      4. View Librarians                                        |");
		System.out.println("|      5. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void printLibraryMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Add Library                                            |");
		System.out.println("|      2. Update Library                                         |");
		System.out.println("|      3. View Library                                           |");
		System.out.println("|      4. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}


	public static void printGuestMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Search Books                                           |");
		System.out.println("|      2. Register Membership                                    |");
		System.out.println("|      3. Go back to the main menu                               |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}



	public static void printServicesMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. View Account                                           |");
		System.out.println("|      2. View fine                                              |");
		System.out.println("|      3. View Feedback                                          |");
		System.out.println("|      4. View Borrow History                                    |");
		System.out.println("|      5. Go back to the main menu                               |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");


	}






	public  static void AdminManagement(){
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Admin Management                            |");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Add Admin                                              |");
		System.out.println("|      2. Remove Admin                                           |");
		System.out.println("|      3. Update Admin Profile                                   |");
		System.out.println("|      4. View Policies                                          |");
		System.out.println("|      5. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");

	}

	public static void usermanagement() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    User Management                             |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. View User                                              |");
		System.out.println("|      2. View Feedback                                          |");
		System.out.println("|      3. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void printNotificationMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. View Notifications                                     |");
		System.out.println("|      2. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}


	public static void printFineMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. View Fine                                              |");
		System.out.println("|      2. Pay Fine                                               |");
		System.out.println("|      3. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}


	public static void policymanagement(){
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Policy Management                           |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Add Policy                                             |");
		System.out.println("|      2. Remove Policy                                          |");
		System.out.println("|      3. Update Policy                                          |");
		System.out.println("|      4. View Policies                                          |");
		System.out.println("|      5. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");

	}


	public static void printPaymentMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. View Payments                                          |");
		System.out.println("|      2. Make Payment                                           |");
		System.out.println("|      3. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void printAdminBookMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Book Management                             |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Add Book                                               |");
		System.out.println("|      2. Remove Book                                            |");
		System.out.println("|      3. Update Book                                            |");
		System.out.println("|      4. View Books                                             |");
		System.out.println("|      5. Search                                                 |");
		System.out.println("|      6. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void printAdminLibrarianMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Librarian Management                        |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Add Librarian                                          |");
		System.out.println("|      2. Remove Librarian                                       |");
		System.out.println("|      3. Update Librarian                                       |");
		System.out.println("|      4. View Librarians                                        |");
		System.out.println("|      5. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}


	public static void printAdminMembershipMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Register Member                                        |");
		System.out.println("|      2. Update Member                                          |");
		System.out.println("|      3. View Member                                            |");
		System.out.println("|      4. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void theEnd() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("******************************************************************");
		System.out.println("|                                                                |");
		System.out.println("|                   Thank you for using the Library              |");
		System.out.println("|                                                                |");
		System.out.println("******************************************************************");
		System.out.println("------------------------------------------------------------------");
	}

	public static void ptintLibraryManagementMenuOptions() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Library Management                          |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Add Library                                            |");
		System.out.println("|      2. Update Library                                         |");
		System.out.println("|      3. View Library                                           |");
		System.out.println("|      4. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}


	public static void Admin() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Admin Management                            |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Book Management                                        |");
		System.out.println("|      2. Librarian Management                                   |");
		System.out.println("|      3. Library Management                                     |");
		System.out.println("|      4. Admin Management                                       |");
		System.out.println("|      5. service Management                                     |");
		System.out.println("|      6. Policy Management                                      |");
		System.out.println("|      7.Go back to the previous menu                            |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}

	public static void Membermenu() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Profile Management                           |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Update Profile                                         |");
		System.out.println("|      2. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}



	public static void servicemenu() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Service Menu                                |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Borrow Books                                           |");
		System.out.println("|      2. Return Books                                           |");
		System.out.println("|      3. View Borrows                                           |");
		System.out.println("|      4. Do Reservation                                         |");
		System.out.println("|      5. View Reserved Books                                    |");
		System.out.println("|      6. view fines                                             |");
		System.out.println("|      7. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");

	}

	public void searchoption(){
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Search Menu                                 |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Search by Title                                        |");
		System.out.println("|      2. Search by Author                                       |");
		System.out.println("|      3. Search by Publisher                                    |");
		System.out.println("|      4. Search by Genre                                        |");
		System.out.println("|      5. Search by rack                                         |");
		System.out.println("|      6. Search by ISBN                                         |");
		System.out.println("|      7. Search by languages                                    |");
		System.out.println("|      8. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
	}



public static void tableTopLayout() {
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	public static void tableMiddleLayout() {
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}


	public static void PatronMenu(){
        System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Membership Management                       |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. pause  Membership                                      |");
		System.out.println("|      2. resume Membership                                      |");
		System.out.println("|      3. Go back to the main menu                               |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");

	}

	public static void BookMenu(){
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Book Management                            |");
		System.out.println("|                    Choose an Option:                          |");
		System.out.println("|                                                               |");
		System.out.println("|      1. Add Book                                              |");
		System.out.println("|      2. Remove Book                                           |");
		System.out.println("|      3. Update Book                                           |");
		System.out.println("|      4. View Books                                            |");
		System.out.println("|      5. Search                                                |");
		System.out.println("|      6. removeBook                                            |");
		System.out.println("|      7. displayRequestedReservations                          |");
		System.out.println("|      8. Go back to the previous menu                          |");
		System.out.println("|                                                               |");
		System.out.println("------------------------------------------------------------------");
	}
	public static void LibrarianMenu(){
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                    Librarian Menu                              |");
		System.out.println("|                    Choose an Option:                           |");
		System.out.println("|                                                                |");
		System.out.println("|      1. Patron Menu                                            |");
		System.out.println("|      2. Book Menu                                              |");
		System.out.println("|      3. Go back to the previous menu                           |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");

	}


	public static void printTable(String[] headers, List<String[]> rows) {
		// Calculate column widths
		int[] widths = new int[headers.length];
		for (int i = 0; i < headers.length; i++) {
			widths[i] = headers[i].length();
		}
		for (String[] row : rows) {
			for (int i = 0; i < row.length; i++) {
				if (row[i] != null) {
					widths[i] = Math.max(widths[i], row[i].length());
				}
			}
		}

		// Print table
		StringBuilder formatBuilder = new StringBuilder();
		StringBuilder separatorBuilder = new StringBuilder();
		for (int width : widths) {
			formatBuilder.append("| %-" + (width + 2) + "s ");
			separatorBuilder.append("+-" + "-".repeat(width + 2) + "-");
		}
		formatBuilder.append("|\n");
		separatorBuilder.append("+\n");

		String format = formatBuilder.toString();
		String separator = separatorBuilder.toString();

		System.out.print(separator);
		System.out.printf(format, (Object[]) headers);
		System.out.print(separator);
		for (String[] row : rows) {
			System.out.printf(format, (Object[]) row);
		}
		System.out.print(separator);
	}


}
