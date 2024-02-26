package com.library.books;

public class Author  {
    private static String Authorname;
    private static String ContactDetails;

    public Author(String Authorname, String contactDetails) {
        this.Authorname = Authorname;
        ContactDetails = contactDetails;
    }
    public static String getAuthorName() {
        return Authorname;
    }
    public static String getContactDetails() {
        return ContactDetails;
    }
    public void setName(String Authorname) {
        this.Authorname = Authorname;
    }



}
