//package com.payment;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Scanner;
//
//import com.main.Layout;
//import com.main.dbConnection;
//
//public class Notification {
//	public static void makeNotification(int PAYMENTID){
//		String description = "SMS AND EMAIL: payemnt sucessfull car will be delivered on you specified date";
//		try {
//			String NotificationType = "sms and email";
//			Connection con = dbConnection.doDBConnection();
//
//			// Get the maximum NotificationID
//			String maxNotificationIdQuery = "SELECT MAX(NotificationID) AS MAXID FROM CAR.Notification";
//			PreparedStatement notificationIdStatement = con.prepareStatement(maxNotificationIdQuery);
//			ResultSet notificationIdResult = notificationIdStatement.executeQuery();
//			int maxNotificationId = 0;
//			if (notificationIdResult.next()) {
//			    maxNotificationId = notificationIdResult.getInt("MAXID");
//			}
//			notificationIdResult.close();
//			notificationIdStatement.close();
//
//
//			String insertNotificationQuery = "INSERT INTO CAR.Notification (NotificationID, NOTIFICATION_TYPE, PaymentID, Description) VALUES (?, ?, ?, ?)";
//			PreparedStatement insertNotificationStatement = con.prepareStatement(insertNotificationQuery);
//			insertNotificationStatement.setInt(1, maxNotificationId+1);
//			insertNotificationStatement.setString(2, NotificationType);
//			insertNotificationStatement.setInt(3, PAYMENTID);
//			insertNotificationStatement.setString(4, description);
//
//			int rowsAffected = insertNotificationStatement.executeUpdate();
//
//			if (rowsAffected > 0) {
//			    System.out.println("Notification sended successfully please check it out (login -> inbox) ");
//			} else {
//			    System.out.println("Failed to add notification.");
//			}
//
//			insertNotificationStatement.close();
//			con.close();
//
//
//		}
//		catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	public static void showNotifications() {
//	    try {
//	        Connection con = dbConnection.doDBConnection();
//	        Scanner sc = new Scanner(System.in);
//	        System.out.print("Enter Password To Update: ");
//	        String password = sc.next();
//	        final String FETCH_CUSTOMER_DETAILS_QUERY = "SELECT customerid AS customerID FROM CAR.customers WHERE accountid = ( SELECT accountid FROM CAR.accounts WHERE passwords = ?)";
//
//	        PreparedStatement fetchStatement = con.prepareStatement(FETCH_CUSTOMER_DETAILS_QUERY);
//	        fetchStatement.setString(1, password);
//	        ResultSet customerResult = fetchStatement.executeQuery();
//	        int customerID = 0;
//	        if (customerResult.next()) {
//	        	customerID = customerResult.getInt("customerID");
//	        }
//	        customerResult.close();
//	        fetchStatement.close();
//
//	        // Assuming you have a query to retrieve notifications
//	        String selectNotificationsQuery = "SELECT * FROM CAR.Notification WHERE PaymentID = (SELECT PaymentID FROM CAR.Payment WHERE BookingID = (SELECT BookingID FROM CAR.Booking WHERE CustomerID = ?))";
//
//	        PreparedStatement selectNotificationsStatement = con.prepareStatement(selectNotificationsQuery);
//	        selectNotificationsStatement.setInt(1, customerID);
//	        ResultSet notificationsResult = selectNotificationsStatement.executeQuery();
//
//	        Layout.notificationLayout();
//	        while (notificationsResult.next()) {
//	            int notificationId = notificationsResult.getInt("NotificationID");
//	            int paymentId = notificationsResult.getInt("PaymentID");
//	            String description = notificationsResult.getString("Description");
//
//	            System.out.println(notificationId + "\t               " + paymentId + "\t      " + description);
//	        }
//	        System.out.println("----------------------------------------------------------------------------------------------------------------");
//
//
//	        // Close the statements and connection
//	        notificationsResult.close();
//	        selectNotificationsStatement.close();
//	        con.close();
//	    } catch (Exception e) {
//	        System.out.println(e.getMessage());
//	    }
//	}
//
//}
