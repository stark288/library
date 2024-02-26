package com.library.users;

import java.sql.*;
import java.time.LocalDate;

import com.library.accounts.Account;


public class Librarian  extends Person {

    public LocalDate DateofJoining = Date.valueOf(LocalDate.now()).toLocalDate();
    private String librarianID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Phone;

    public Librarian() {
    }

    public Librarian(String firstName, String lastName, String email, String phone) {

        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Phone = phone;

    }


    public Librarian(String firstName, String lastName, String email, String phone, Account librarian, int lastLibraryId) {
    }


    public String getLibrarianID() {
        return librarianID;

    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setLibrarianID(String librarianID) {
        this.librarianID = librarianID;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

@Override
public String toString() {
	return "Librarian{" + "librarianID='" + librarianID + '\'' + ", FirstName='" + FirstName + '\'' + ", LastName='"
			+ LastName + '\'' + ", Email='" + Email + '\'' + ", Phone='" + Phone + '\'' + '}';
}

}



