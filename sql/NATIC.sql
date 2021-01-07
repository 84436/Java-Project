-- WARNING: DESTRUCTIVE ACTION AHEAD
DROP DATABASE IF EXISTS NATIC;

-- Create database

CREATE DATABASE NATIC;
USE NATIC;

-- Create tables

CREATE TABLE IDGen_States (
    Prefix  CHAR(2) PRIMARY KEY,
    State   INT4
);

CREATE TABLE Accounts (
    ID      CHAR(10) PRIMARY KEY,
    Name    VARCHAR(255),
    Email   VARCHAR(255),
    Phone   VARCHAR(10),
    Type    INT1,
    Pass    VARCHAR(64)
    -- Customer: 0
    -- Staff: 1
    -- Admin: 2
);

CREATE TABLE Customers (
    ID          CHAR(10) PRIMARY KEY,
    DoB         DATE,
    Address     VARCHAR(255),
    SignUpDate  DATE,
    BookListID  CHAR(10)
);

CREATE TABLE Staff (
    ID          CHAR(10) PRIMARY KEY,
    BranchID    CHAR(10)
);

CREATE TABLE Branches (
    ID          CHAR(10) PRIMARY KEY,
    Name        VARCHAR(255),
    Address     VARCHAR(255)
);

CREATE TABLE Books (
    ISBN        CHAR(13) PRIMARY KEY,
    BookID      CHAR(10),
    VersionID   INT1,
    Title       VARCHAR(255),
    Author      VARCHAR(255),
    BookYear    DATE,
    Publisher   VARCHAR(255),
    Genre       INT1,
    Rating      FLOAT,
    BookFormat  INT1,
    Price       FLOAT
    -- Genre
        -- EDUCATION: 0
        -- SCIENCE: 1
        -- REFERENCES: 2
        -- BUSINESS: 3
        -- LIFESTYLE: 4
        -- BIO: 5
        -- RELIGIONS: 6
        -- POLITICS: 7
        -- CHILDREN: 8
        -- ROMANCE: 9
        -- FICTION: 10
        -- WIBU: 11
    -- FORMAT
        -- PAPER: 0
        -- HARD: 1
        -- EBOOK: 2
        -- AUDIO: 3
);

CREATE TABLE BranchStockLists (
    ListID  CHAR(10),
    ISBN    CHAR(13),
    Stock   INT4
);

CREATE TABLE CustomerLibraries (
    ListID      CHAR(10),
    ISBN        CHAR(13),
    ExpiryDate  DATE
);

CREATE TABLE Receipts (
    ID          CHAR(10) PRIMARY KEY,
    ISBN        CHAR(13),
    StaffID     CHAR(10),
    BranchID    CHAR(10),
    ReceiptDate DATE,
    Price       FLOAT,
    ReturnOn    DATE
);

CREATE TABLE Reviews (
    ID          CHAR(10) PRIMARY KEY,
    ReviewScore FLOAT,
    ReviewText  VARCHAR(255)
);

-- ID generators: create initial states

INSERT INTO IDGen_States (Prefix, State)
VALUES
    ("AC", 0), -- Accounts
    ("BR", 0), -- Branches
    ("BK", 0), -- Books
    ("LB", 0), -- List:Branch (BranchStockLists)
    ("LC", 0), -- List:Customer (CustomerLibraries)
    ("RC", 0), -- Receipts
    ("RV", 0)  -- Reviews
;

INSERT INTO Accounts (ID, Name, Email, Phone, Type)
VALUES
    ("AC00000001", "Nguyễn Văn AB", "ab.nguyen@email.com", NULL, 2),
    ("AC00000002", "Trần Văn CD", "cd.tran@email.com", "0123456789", 0),
    ("AC00000003", "Lê Thị EF", "ef.lethi@email.com", NULL, 1),
    ("AC00000004", "Lý Quốc GH", "gh.lyquoc@email.com", "0928471742", 0),
    ("AC00000005", "Phạm Lê IJ", "ij.phamle@email.com", "0728471238", 1),
    ("AC00000006", "Vũ Thị XY", "xy.vuthi@email.com", "0734591231", 0)
;

INSERT INTO Customers (ID, DoB, Address, SignUpDate, BookListID)
VALUES
    ("AC00000002", "1999-01-01", "227 Nguyễn Văn Cừ P4 Q5 TPHCM", "2019-06-21", NULL),
    ("AC00000004", "2005-12-31", NULL, "2020-02-14", NULL),
    ("AC00000006", "1975-11-21", "45 Trương Công Định Phường 3 BRVT", "2020-10-31", NULL)
;

INSERT INTO Staff (ID, BranchID)
VALUES
    ("AC00000003", "BR00000002"),
    ("AC00000005", "BR00000001")
;

INSERT INTO Branches (ID, Name, Address)
VALUES
    ("BR00000001", "NATiC Nguyễn Văn Cừ", "200 Nguyễn Văn Cừ Phường 4 Quận 5 TPHCM"),
    ("BR00000002", "NATiC Lê Trọng Tấn", "12 Lê Trọng Tấn Tây Thạnh Tân Phú TPHCM"),
    ("BR00000003", "NATiC Nguyễn Trãi", "320 Nguyễn Trãi Phường 7 Quận 5 TPHCM"),
    ("BR00000004", "NATiC Vũng Tàu", "123 Ba Cu Phường 4 BRVT")
;

INSERT INTO CustomerLibraries (ListID, ISBN, ExpiryDate)
VALUES
    ("AC00000002", "9781524763169", "0000-00-00"),
    ("AC00000002", "9780062316103", "2020-11-20"),
    ("AC00000006", "9781292263427", "2021-01-01")
;

INSERT INTO BranchStockLists (ListID, ISBN, Stock)
VALUES
    ("BR00000001", "9781292263427", 50),
    ("BR00000001", "9781506706382", 3),
    ("BR00000001", "9780062316103", 10),
    ("BR00000002", "9780062457714", 1),
    ("BR00000002", "9780062316103", 2)
;

INSERT INTO Books (ISBN, BookID, VersionID, Title, Author, BookYear, Publisher, Genre, Rating, BookFormat, Price)
VALUES
    ("9781292263427", "BK00000001", 13, "Computer Science: An Overview", "Brookshear & Brylow", "2019-01-01", "Pearson", 0, 0.0, 1, 1865.00),
    ("9781524763169", "BK00000002", 1, "A Promised Land", "Barack Obama", "2020-01-01", "Crown", 5, 0.0, 1, 580.00),
    ("9781506706382", "BK00000003", 1, "The Legend of Zelda Encyclopedia", "Nintendo", "2018-01-01", "Dark Horse Books ", 2, 0.0, 1, 580.00),
    ("9780062457714", "BK00000004", 2, "The Subtle Art of Not Giving a F*ck", "Mark Manson", "2016-01-01", "Harper", 4, 0.0, 1, 350.00),
    ("9780062316103", "BK00000005", 1, "Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "2015-01-01", "Harper Collins", 1, 0.0, 2, 305.00)
;

INSERT INTO Receipts (ID, ISBN, StaffID, BranchID, ReceiptDate, Price, ReturnOn)
VALUES
    ("RC00000001", "9781524763169", "AC00000003", "AC00000002", "2020-10-30", 350.00, "0000-00-00"),
    ("RC00000002", "9781292263427", "AC00000005", "AC00000006", "2020-12-03", 1865.00, "2021-01-01"),
    ("RC00000003", "9780062316103", "AC00000005", "AC00000002", "2020-11-25", 350.00, "2020-11-20")
;

INSERT INTO Reviews (ID, ReviewScore, ReviewText)
VALUES
    ("RC00000001", 4.0, "The book is a rather good choice for almost beginners. 
    Almost - because it is very useful to complete some introductory course in Python first, 
    it will help to better understand examples in the book.")
;