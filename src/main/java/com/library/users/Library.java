package com.library.users;

public class Library {
    private String libraryID;
    private String Name;
    private String location;
    private int managerID;
    private String ContactEmail;
    private String ContactPhone;
    private String HoursofOperation;

    public Library( String Name, String location,   String ContactEmail, String ContactPhone, String HoursofOperation) {

        this.Name = Name;
        this.location = location;


        this.ContactEmail = ContactEmail;
        this.ContactPhone = ContactPhone;
        this.HoursofOperation = HoursofOperation;
    }


    public Library(String name, String location, String email, String phone) {
    }

    public String getLibraryID() {
        return libraryID;
    }
    public String getName() {
        return Name;
    }
    public String getLocation() {
        return location;
    }
    public int getManagerID() {
        return managerID;
    }

    public String getContactEmail() {
        return ContactEmail;
    }
    public String getContactPhone() {
        return ContactPhone;
    }
    public String getHoursofOperation() {
        return HoursofOperation;
    }
    public void setLibraryID(String libraryID) {
        this.libraryID = libraryID;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }
    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
    }
    public void setHoursofOperation(String hoursofOperation) {
        HoursofOperation = hoursofOperation;
    }
    public String toString() {
        return "Library ID: " + libraryID + "\nName: " + Name + "\nLocation: " + location + "\nManager ID: " + managerID + "\nContact Email: " + ContactEmail + "\nContact Phone: " + ContactPhone + "\nHours of Operation: " + HoursofOperation;
    }

}
