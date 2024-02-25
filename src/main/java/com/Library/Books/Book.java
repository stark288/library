package com.Library.Books;

import java.sql.Date;

public class Book {
    private final String type;
    private int copies;
    private int bookID;
    private String title;
    private String isbn;
    private Date publishedDate;
    private String languages;
    private String availability;
    private String genre;
    private String rackNo;
    private String PublisherDetails;
    private String Publishername;
    private Author Author;

    // Default constructor


    public Book(String title, String isbn, Date publishedDate, String languages, String availability, String genre, String rackNo, com.Library.Books.Author author, String publisherName, String publisherDetails, String type, int copies) {

        this.title = title;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.languages = languages;
        this.availability = availability;
        this.genre = genre;
        this.rackNo = rackNo;
        Publishername = publisherName;
        PublisherDetails = publisherDetails;
        this.type = type;
        this.copies = copies;

    }

    public String getTitle() {
        return title;
    }
    public String getIsbn() {
        return isbn;
    }
    public Date getPublishedDate() {
        return publishedDate;
    }
    public String getLanguages() {
        return languages;

    }
    public String getAvailability() {
        return availability;
    }
    public String getGenre() {
        return genre;
    }
    public String getRackNo() {
        return rackNo;
    }

    public String getPublishername() {
        return Publishername;
    }
    public String getPublisherDetails() {
        return PublisherDetails;
    }
    public int getBookID() {
        return bookID;
    }
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setGenre(String genre) {
                this.genre = genre;
    }
    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

    public void setPublishername(String publishername) {
        this.Publishername = publishername;
    }
    public void setPublisherDetails(String publisherDetails) {
        this.PublisherDetails = publisherDetails;
    }




    public String getPublisherName() {
            return Publishername;
    }
    public String getType() {
        return type;
    }
    public int getCopies() {
        return copies;
    }



}
