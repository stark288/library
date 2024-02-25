package com.Library.Accounts;


public class Account {
    private String username;
    private String password;
    public String accountType;
    private int accountId;


    public Account(String username, String password, String accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;

    }




    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    void setAccountType(String accountType) {
        this.accountType = accountType;
        if (accountType.equals("LIBRARIAN") || accountType.equals("PATRON") || accountType.equals("ADMIN")) {
            this.accountType = accountType;
        } else {
            throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
        throw new IllegalArgumentException("Invalid account type: " + accountType);
    }

    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public String getAccountType() {
        return accountType;
    }






}

    

