package com.Library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class FineCalculation {
    public static double calculateFine(LocalDate returnDate, LocalDate dueDate) {
        long daysBetween = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (daysBetween <= 0) {
            return 0;
        }
        return daysBetween * 0.5;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the due date (YYYY-MM-DD):");
        String dueDateString = scanner.next();
        LocalDate dueDate = LocalDate.parse(dueDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println("Enter the return date (YYYY-MM-DD):");
        String returnDateString = scanner.next();
        LocalDate returnDate = LocalDate.parse(returnDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        double fine = calculateFine(returnDate, dueDate);
        System.out.println("The fine is: " + fine);
    }
}
