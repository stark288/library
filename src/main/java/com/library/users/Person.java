package com.library.users;

import com.library.accounts.Account;

public class Person {
    private String First_Name;
    private String Last_Name;
    private String Email;
    private String Phone;
    Account account;

    public Person(){
        First_Name = "";
        Last_Name = "";
        Email = "";
        Phone = "";
    }

    public Person(String First_Name, String Last_Name, String Email, String Phone,Account account){
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Email = Email;
        this.Phone = Phone;
        this.account = account;
    }



    public String getFirst_Name() {
        return First_Name;
    }
    public String getLast_Name() {
        return Last_Name;
    }
    public String getEmail() {
        return Email;
    }
    public String getPhone() {
        return Phone;
    }
    public void setFirst_Name(String First_Name) {
        this.First_Name = First_Name;
    }
    public void setLast_Name(String Last_Name) {
        this.Last_Name = Last_Name;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    protected String getFirstName() {
        return First_Name;
    }

    protected String getLastName() {
        return Last_Name;
    }

    protected String getEmailAddress() {
        return Email;
    }

    protected String getPhoneNumber() {
        return Phone;
    }


}
