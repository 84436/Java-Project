create database NATIC;
use NATIC;

create table IDGEN_STATES (
	Prefix char(2),
    State int,
    primary key (Prefix)
);

create table ACCOUNTS (
	ID varchar(10),
    AccName nvarchar(255),
    Email varchar(255),
    Phone varchar(10),
    AccType int2,
    primary key (ID)
);

create table CUSTOMERS (
	ID varchar(10),
    DOB date,
    Address nvarchar(255),
    SignUpDate date,
    BookListID int2,
    primary key (ID)
);

create table STAFF (
	ID varchar(10),
    BranchID varchar(10),
    primary key (ID)
);

create table BRANCHES (
	ID varchar(10),
    BranchName	nvarchar(255),
    Address nvarchar(255),
    BookListID nvarchar(10),
    primary key (ID)
);

create table BOOKS (
	ISBN varchar(13),
    BookID varchar(10),
    VersionID int2,
    Title nvarchar(255),
    Author nvarchar(255),
    BookYear Date,
	Publisher nvarchar(255),
    Genre int,
    Rating float,
    BookFormat int2,
    Price float,
    primary key (ISBN)
);

create table BRANCHSTOCKLISTS (
	ID varchar(10),
    ISBN varchar(13),
    Stock int
);

create table CUSTOMERLIBRARIES (
	ID varchar(10),
    ISBN varchar(13),
    ExpiryDate date
);

create table RECEIPTS (
	ID varchar(10),
    ISBN varchar(13),
    StaffID varchar(10),
    BranchID varchar(10),
    ReceiptDate date,
    Price float,
    ReturnOn date,
    primary key (ID) 
);

create table REVIEWS (
	ID varchar(10),
    CustomerID varchar(10),
    ISBN varchar(13),
    ReviewScore float,
    ReviewText nvarchar(255),
    primary key (ID)
);