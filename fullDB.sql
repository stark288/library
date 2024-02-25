-- Drop existing triggers
BEGIN
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.useractivity_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.feedback_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.payment_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.fine_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.borrow_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.reservation_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.book_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.patron_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.librarian_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.admins_trg';
   EXECUTE IMMEDIATE 'DROP TRIGGER finallibrary.accounts_trg';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -4080 THEN
         RAISE;
      END IF;
END;
/

-- Drop existing tables
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.UserActivity CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Feedback CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Payment CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Fine CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Borrow CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Reservation CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Book CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Patron CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Librarian CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Admins CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Accounts CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE finallibrary.Library CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- Drop existing sequences
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.useractivity_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.feedback_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.payment_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.fine_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.borrow_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.reservation_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.book_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.patron_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.librarian_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.admins_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.accounts_seq';
   EXECUTE IMMEDIATE 'DROP SEQUENCE finallibrary.library_seq';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

-- Create sequences
CREATE SEQUENCE finallibrary.accounts_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.admins_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.librarian_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.patron_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.book_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.reservation_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.borrow_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.fine_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.payment_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.library_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.feedback_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE finallibrary.useractivity_seq START WITH 1 INCREMENT BY 1;

-- Create tables without foreign keys first
CREATE TABLE finallibrary.Library (
    LibraryID INT PRIMARY KEY,
    Name VARCHAR2(255) NOT NULL,
    Location VARCHAR2(255) NOT NULL,
    ManagerID INT,
    ContactEmail VARCHAR2(255),
    ContactPhone VARCHAR2(15),
    HoursOfOperation VARCHAR2(255),
    CONSTRAINT Unique_Library_Name_Location UNIQUE (Name, Location)
);

-- Then create tables with foreign keys
CREATE TABLE finallibrary.Accounts (
    AccountID INT PRIMARY KEY,
    Username VARCHAR2(255) NOT NULL UNIQUE,
    Password VARCHAR2(255) NOT NULL,
    AccountType VARCHAR2(255) NOT NULL
);

CREATE TABLE finallibrary.Admins (
    AdminID INT PRIMARY KEY,
    FirstName VARCHAR2(255) NOT NULL,
    LastName VARCHAR2(255) NOT NULL,
    Email VARCHAR2(255) NOT NULL UNIQUE,
    Phone VARCHAR2(15) NOT NULL UNIQUE,
    AccountID INT,
    LibraryID INT,
    FOREIGN KEY (LibraryID) REFERENCES finallibrary.Library(LibraryID),
    FOREIGN KEY (AccountID) REFERENCES finallibrary.Accounts(AccountID)
);

CREATE TABLE finallibrary.Librarian (
    LibrarianID INT PRIMARY KEY,
    FirstName VARCHAR2(255) NOT NULL,
    LastName VARCHAR2(255) NOT NULL,
    Email VARCHAR2(255) NOT NULL UNIQUE,
    Phone VARCHAR2(15) NOT NULL UNIQUE,
    AccountID INT,
    LibraryID INT,
    FOREIGN KEY (AccountID) REFERENCES finallibrary.Accounts(AccountID),
    FOREIGN KEY (LibraryID) REFERENCES finallibrary.Library(LibraryID)
);

CREATE TABLE finallibrary.Patron (
    PatronID INT PRIMARY KEY,
    AccountID INT,
    LibraryID INT,
    FirstName VARCHAR2(255) NOT NULL,
    LastName VARCHAR2(255) NOT NULL,
    Address VARCHAR2(255) NOT NULL,
    Email VARCHAR2(255) NOT NULL UNIQUE,
    Phone VARCHAR2(15) NOT NULL UNIQUE,
    DateofJoining DATE NOT NULL,
    DateOfExpiry DATE NOT NULL,
    Status VARCHAR2(255) NOT NULL,
    FOREIGN KEY (AccountID) REFERENCES finallibrary.Accounts(AccountID),
    FOREIGN KEY (LibraryID) REFERENCES finallibrary.Library(LibraryID)
);

CREATE TABLE finallibrary.Book (
    BookID INT PRIMARY KEY,
    Title VARCHAR2(255) NOT NULL,
    ISBN VARCHAR2(13) UNIQUE NOT NULL,
    PublishedDate DATE NOT NULL,
    Languages VARCHAR2(50) NOT NULL,
    Availability VARCHAR2(30) NOT NULL,
    Genre VARCHAR2(255) NOT NULL,
    RackNo VARCHAR2(255),
    PublisherName VARCHAR2(255) NOT NULL,
    PublisherDetails VARCHAR2(255),
    AuthorName VARCHAR2(255) NOT NULL,
    AuthorDetails VARCHAR2(255)
);

CREATE TABLE finallibrary.Reservation (
    ReservationID INT PRIMARY KEY,
    BookID INT NOT NULL,
    PatronID INT NOT NULL,
    ReservationDate DATE NOT NULL,
    Status VARCHAR2(100) NOT NULL,
    FOREIGN KEY (BookID) REFERENCES finallibrary.Book(BookID),
    FOREIGN KEY (PatronID) REFERENCES finallibrary.Patron(PatronID)
);

CREATE TABLE finallibrary.Borrow (
    BorrowID INT PRIMARY KEY,
    BookID INT NOT NULL,
    PatronID INT NOT NULL,
    BorrowDate DATE NOT NULL,
    Status VARCHAR2(100) NOT NULL,
    DueDate DATE NOT NULL,
    FOREIGN KEY (BookID) REFERENCES finallibrary.Book(BookID),
    FOREIGN KEY (PatronID) REFERENCES finallibrary.Patron(PatronID)
);

CREATE TABLE finallibrary.Fine (
    FineID INT PRIMARY KEY,
    Amount DECIMAL(10, 2) NOT NULL,
    BorrowID INT NOT NULL,
    PatronID INT NOT NULL,
    FOREIGN KEY (BorrowID) REFERENCES finallibrary.Borrow(BorrowID),
    FOREIGN KEY (PatronID) REFERENCES finallibrary.Patron(PatronID)
);

CREATE TABLE finallibrary.Payment (
    PaymentID INT PRIMARY KEY,
    PatronID INT NOT NULL,
    FineID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentDate DATE NOT NULL,
    PaymentMethod VARCHAR2(50) NOT NULL,
    FOREIGN KEY (PatronID) REFERENCES finallibrary.Patron(PatronID),
    FOREIGN KEY (FineID) REFERENCES finallibrary.Fine(FineID)
);

CREATE TABLE finallibrary.Feedback (
    FeedbackID INT PRIMARY KEY,
    PatronID INT NOT NULL,
    BookID INT NOT NULL,
    Comments VARCHAR2(255),
    Rating INT NOT NULL,
    SubmissionDate DATE NOT NULL,
    FOREIGN KEY (PatronID) REFERENCES finallibrary.Patron(PatronID),
    FOREIGN KEY (BookID) REFERENCES finallibrary.Book(BookID)
);

CREATE TABLE finallibrary.UserActivity (
    ActivityID INT PRIMARY KEY,
    UserID INT NOT NULL,
    ActivityType VARCHAR2(255) NOT NULL,
    ActivityDate DATE NOT NULL,
    Descriptions VARCHAR2(255),
    FOREIGN KEY (UserID) REFERENCES finallibrary.Accounts(AccountID)
);
-- Create Accounts Trigger
CREATE OR REPLACE TRIGGER finallibrary.accounts_trg
BEFORE INSERT ON finallibrary.Accounts
FOR EACH ROW
BEGIN
  SELECT finallibrary.accounts_seq.NEXTVAL INTO :new.AccountID FROM dual;
END;
/

-- Create Admins Trigger
CREATE OR REPLACE TRIGGER finallibrary.admins_trg
BEFORE INSERT ON finallibrary.Admins
FOR EACH ROW
BEGIN
  SELECT finallibrary.admins_seq.NEXTVAL INTO :new.AdminID FROM dual;
END;
/

-- Create Librarian Trigger
CREATE OR REPLACE TRIGGER finallibrary.librarian_trg
BEFORE INSERT ON finallibrary.Librarian
FOR EACH ROW
BEGIN
  SELECT finallibrary.librarian_seq.NEXTVAL INTO :new.LibrarianID FROM dual;
END;
/

-- Create Patron Trigger
CREATE OR REPLACE TRIGGER finallibrary.patron_trg
BEFORE INSERT ON finallibrary.Patron
FOR EACH ROW
BEGIN
  SELECT finallibrary.patron_seq.NEXTVAL INTO :new.PatronID FROM dual;
END;
/

-- Create Book Trigger
CREATE OR REPLACE TRIGGER finallibrary.book_trg
BEFORE INSERT ON finallibrary.Book
FOR EACH ROW
BEGIN
  SELECT finallibrary.book_seq.NEXTVAL INTO :new.BookID FROM dual;
END;
/

-- Create Reservation Trigger
CREATE OR REPLACE TRIGGER finallibrary.reservation_trg
BEFORE INSERT ON finallibrary.Reservation
FOR EACH ROW
BEGIN
  SELECT finallibrary.reservation_seq.NEXTVAL INTO :new.ReservationID FROM dual;
END;
/

-- Create Borrow Trigger
CREATE OR REPLACE TRIGGER finallibrary.borrow_trg
BEFORE INSERT ON finallibrary.Borrow
FOR EACH ROW
BEGIN
  SELECT finallibrary.borrow_seq.NEXTVAL INTO :new.BorrowID FROM dual;
END;
/

-- Create Fine Trigger
CREATE OR REPLACE TRIGGER finallibrary.fine_trg
BEFORE INSERT ON finallibrary.Fine
FOR EACH ROW
BEGIN
  SELECT finallibrary.fine_seq.NEXTVAL INTO :new.FineID FROM dual;
END;
/

-- Create Payment Trigger
CREATE OR REPLACE TRIGGER finallibrary.payment_trg
BEFORE INSERT ON finallibrary.Payment
FOR EACH ROW
BEGIN
  SELECT finallibrary.payment_seq.NEXTVAL INTO :new.PaymentID FROM dual;
END;
/

-- Create Feedback Trigger
CREATE OR REPLACE TRIGGER finallibrary.feedback_trg
BEFORE INSERT ON finallibrary.Feedback
FOR EACH ROW
BEGIN
  SELECT finallibrary.feedback_seq.NEXTVAL INTO :new.FeedbackID FROM dual;
END;
/

-- Create UserActivity Trigger
CREATE OR REPLACE TRIGGER finallibrary.useractivity_trg
BEFORE INSERT ON finallibrary.UserActivity
FOR EACH ROW
BEGIN
  SELECT finallibrary.useractivity_seq.NEXTVAL INTO :new.ActivityID FROM dual;
END;
/

