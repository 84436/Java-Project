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
    Type    INT1
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
    Address     VARCHAR(255),
    BookListID  CHAR(10)
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
    CustomerID  CHAR(10),
    ISBN        CHAR(13),
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
