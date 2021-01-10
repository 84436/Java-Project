package natic;

import natic.account.*;
import natic.account.AccountProvider;
import natic.account.AccountEnums.AccountType;
import natic.book.BookProvider;
import natic.book.BranchStockList;
import natic.book.CustomerLibrary;
import natic.book.Book;
import natic.book.BookListProvider;
import natic.branch.Branch;
import natic.branch.BranchProvider;
import natic.receipt.Receipt;
import natic.receipt.ReceiptProvider;
import natic.review.Review;
import natic.review.ReviewProvider;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class Mediator {
    // Singleton, Eager Initialization
    private static Mediator this_instance = new Mediator();
    private Mediator() { }
    public static Mediator getInstance() { return this_instance; }

    // Providers
    private static AccountProvider ACCOUNT;
    private static BookProvider BOOK;
    private static BookListProvider BOOKLIST;
    private static BranchProvider BRANCH;
    private static ReceiptProvider RECEIPT;
    private static ReviewProvider REVIEW;

    // Database
    private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_NAME = "natic"; // KEEP THIS LOWERCASE
    private final String DB_SCHEMA_FILE = "NATIC.sql";
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;
    private static Connection SharedConnection = null;

    // Properties file name
    private static final String PROP_FILENAME = "natic.properties";
    
    // Logging
    private boolean enableLogging = true;
    
    /**
     * Read the properties file (specified by `PROP_FILENAME`) and configure accordingly.
     */
    private boolean readProperties() {
        try (FileReader fr = new FileReader(PROP_FILENAME)) {
            Properties prop = new Properties();
            prop.load(fr);

            // Databases
            DB_URL = prop.getProperty("db.url");
            DB_USER = prop.getProperty("db.user");
            DB_PASS = prop.getProperty("db.password");

            // Logging
            enableLogging = Boolean.parseBoolean(prop.getProperty("log.verbose", "true"));

            return true;
        }
        
        catch (IOException e) {
            // The rare println()
            System.out.println(String.format(
                "ERROR: Failed to find and/or read %s",
                PROP_FILENAME
            ));
            return false;
        }
    }

    /**
     * Initialize the server
     */
    public void init() {
        try {
            // Properties
            if (!readProperties()) { return; }

            // Logging
            Log.initLogger();
            Log.l.info("Logger configured");
            if (!enableLogging) {
                Log.l.info("Logger is now limited to SEVERE messages only");
                Log.off();
            }

            //#region: Test if database exists; fail immediately if not.
            Class.forName(DB_DRIVER);
            SharedConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Log.l.info("Getting a DB connection");

            if (SharedConnection != null) {
                Log.l.info("Houston, we've got a connection.");
            }

            boolean dbExists = false;
            ResultSet rsDatabase = SharedConnection.getMetaData().getCatalogs();
            while (rsDatabase.next()) {
                if (rsDatabase.getString(1).equals(DB_NAME)) {
                    dbExists = true;
                    break;
                }
            }

            if (!dbExists) {
                Log.l.severe(String.format(
                    "Database not exists. Please manually run %s to create the database first, then try again later.",
                    DB_SCHEMA_FILE
                ));
                return;
            }
            else {
                SharedConnection.setCatalog(DB_NAME);
                Log.l.info("Database exists and selected.");
            }
            //#endregion

            //#region: Fetch ID generators state and create
            IDGenerator ACCOUNT_IDGEN = new IDGenerator(SharedConnection, "AC");
            IDGenerator BRANCH_IDGEN = new IDGenerator(SharedConnection, "BR");
            IDGenerator RECEIPT_IDGEN = new IDGenerator(SharedConnection, "RC");
            //#endregion

            //#region: Create providers, then init with DB connection and ID generators
            ACCOUNT = new AccountProvider(SharedConnection, ACCOUNT_IDGEN);
            BRANCH = new BranchProvider(SharedConnection, BRANCH_IDGEN);
            BOOK = new BookProvider(SharedConnection);
            BOOKLIST = new BookListProvider(SharedConnection);
            RECEIPT = new ReceiptProvider(SharedConnection, RECEIPT_IDGEN);
            REVIEW = new ReviewProvider(SharedConnection);
            //#endregion
        }
        
        catch (ClassNotFoundException ex) {
            Log.l.severe("Unable to load driver class");
            System.exit(1);
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // finally {    
        //     try {
        //         if (SharedConnection != null) SharedConnection.close();
        //     } catch (SQLException se3) {
        //         se3.printStackTrace();
        //     }
        // }
    }

    public AccountType checkLogin(String email, String password) throws SQLException {
        if ((email != null && password != null)) {
            if (ACCOUNT.getEmailforLogin(email)) {
                if (ACCOUNT.checkPassword(email, password)) {
                    return ACCOUNT.getType(email);
                }
            } 
        }
        else {
            Log.l.info(String.format("%s: NOT FOUND", email));
            return AccountType.UNKNOWN;
        }
        return null;
    }
    
    public void createAccount(Account oAccount) throws SQLException {
        ACCOUNT.add(oAccount);
    }

    public void editAccount(Account oAccount) throws SQLException {
        ACCOUNT.edit(oAccount);
    }

    public void removeStaff(Account oAccount) throws SQLException {
        ACCOUNT.remove(oAccount);
    }

    public void addStaff(Account oAccount) throws SQLException {
        Staff add = new Staff();
        add.setEmail(oAccount.getEmail());
        add.setName(oAccount.getName());
        add.setPhone(oAccount.getPhone());
        add.setBranchID("BR00000000");
        add.setType(AccountType.STAFF);
        ACCOUNT.add(add);
    }
    
    // BOOKS functions
    public void addBook(Book b) {
        BOOK.add(b);
    }

    public void editBook(Book b) {
        BOOK.edit(b);
    }

    public Book getByISBN(String match) {
        return BOOK.get(match);
    }
    
    public ArrayList<Book> getAllBooks() {
        return BOOK.getAll();
    }

    public ArrayList<Book> searchBook(String query) {
        return BOOK.searchBook(query);
    }

    // REVIEWS function
    public void reviewBook(Review o) {
        // Add a review
        REVIEW.add(o);

        // Calculate rating
        Book b = BOOK.get(o.getISBN());
        b.setRating(REVIEW.getRating(o.getISBN()));
        BOOK.edit(b);
    }

    public ArrayList<Review> getReview(String ISBN) {
        return REVIEW.get(ISBN);
    }

    // Buy-Rent function
    public void buyBook(String StaffID, String CustomerID, String ISBN) {
        Book b = BOOK.get(ISBN);

        // Add to lib
        CustomerLibrary c = new CustomerLibrary();
        c.setOwnerID(CustomerID);
        c.setBook(b);
        c.setExpireDate(LocalDate.of(1, 1, 1));
        BOOKLIST.add(c);

        // Create a receipt
        Receipt receipt = new Receipt();
        receipt.setISBN(ISBN);
        receipt.setStaffID(StaffID);
        receipt.setCustomerID(CustomerID);
        receipt.setDate(LocalDate.now());
        receipt.setPrice(b.getPrice());
        receipt.setReturnOn(LocalDate.of(1, 1, 1));
        RECEIPT.add(receipt);
    }

    public void rentBook(String StaffID, String CustomerID, String ISBN, int numberOfMonth) {
        Book b = BOOK.get(ISBN);

        // Add to lib
        CustomerLibrary c = new CustomerLibrary();
        c.setOwnerID(CustomerID);
        c.setBook(b);
        c.setExpireDate(LocalDate.now().plusMonths(numberOfMonth));
        BOOKLIST.add(c);

        // Create a receipt
        Receipt receipt = new Receipt();
        receipt.setISBN(ISBN);
        receipt.setStaffID(StaffID);
        receipt.setCustomerID(CustomerID);
        receipt.setDate(LocalDate.now());
        receipt.setPrice(b.getPrice());
        receipt.setReturnOn(LocalDate.now().plusMonths(numberOfMonth));
        RECEIPT.add(receipt);
    }

    public void buyAtCounter(String StaffID, String CustomerID, ArrayList<String> ISBNs) {
        for (int i = 0; i < ISBNs.size(); i++) {
            buyBook(StaffID, CustomerID, ISBNs.get(i));
        }
    }

    // BOOKLISTS function
    public void addBookToBranch(String ISBN, String BranchID) {
        BranchStockList b = new BranchStockList();
        b.setOwnerID(BranchID);
        b.setStock(1);
        b.setBook(BOOK.get(ISBN));

        BOOKLIST.add(b);
    }

    public void updateStock(String BranchID, String ISBN, int amount) {
        if (amount == 0) {
            BOOKLIST.removeOne(BranchID, ISBN);
        } else {
            BranchStockList b = new BranchStockList();
            b.setOwnerID(BranchID);
            b.setBook(BOOK.get(ISBN));
            b.setStock(amount);
            BOOKLIST.edit(b);
        }
    }

    public void removeBookFromBranch(String ISBN, String BranchID) {
        BOOKLIST.removeOne(BranchID, ISBN);
    }

    public ArrayList<CustomerLibrary> getCustomerLibrary(String CustomerID) {
        return BOOKLIST.getCustomerLibrary(CustomerID);
    }
    
    public void editAccount(String email, String name, String phone, LocalDate dob, String CusAddress, String branchID) throws SQLException {
        if (ACCOUNT.getEmailforLogin(email)) {
            AccountType oType = ACCOUNT.getType(email);
            switch (oType.getClass().getName()) {
                case "Customer":
                    Customer updateCus = new Customer();
                    updateCus.setName(name);
                    updateCus.setPhone(phone);
                    updateCus.setAddress(CusAddress);
                    updateCus.setDoB(dob);
                    ACCOUNT.edit(updateCus);
                    break;

                case "Staff":
                    Staff updateStaff = new Staff();
                    updateStaff.setName(name);
                    updateStaff.setPhone(phone);
                    updateStaff.setBranchID(branchID);
                    ACCOUNT.edit(updateStaff);
                    break;

                case "Admin":
                    Admin updateAdmin = new Admin();
                    updateAdmin.setName(name);
                    updateAdmin.setPhone(phone);
                    ACCOUNT.edit(updateAdmin);
                    break;
                
                case "Unknown":
                    break;
            }
        }
    }

    public void removeStaff(String ID, String BranchID) throws SQLException {
        if (BranchID != null && ID != null) {
            RECEIPT.bypassReceiptForVirtualAcc("AC00000000", ID);
            ACCOUNT.removeStaffFromBranch(ID);
            Log.l.info(String.format("%s: STAFF in %s", ID, BranchID));
        } else {
            Log.l.info("These fields must have value");
            return;
        }
    }

    public ArrayList<Account> getAllStaff() throws SQLException {
        return ACCOUNT.getALlStaff();
    }

    // GetBranch()
    public ArrayList<Branch> getAllBranch() throws SQLException {
        return BRANCH.getAll();
    }

    public void addBranch(Branch branch) throws SQLException {
        BRANCH.add(branch);
    }

    public void removeBranch(String ID) throws SQLException {
        BOOKLIST.removeAll(ID, false);
        BRANCH.remove(ID);
    }

    public void addBranch(String BranchID, String name, String address) throws SQLException {
        if (!BRANCH.checkExistBranch(BranchID)){
            Branch newBranch = new Branch();
            newBranch.setID(BranchID);
            newBranch.setName(name);
            newBranch.setAddress(address);
            BRANCH.add(newBranch);
        }
    }
    
    public void editBranch(String BranchID, String name, String address) throws SQLException {
        if (BRANCH.checkExistBranch(BranchID)) {
            Branch updateBranch = new Branch();
            updateBranch.setName(name);
            updateBranch.setAddress(address);
            BRANCH.edit(updateBranch);
        } 
    }

    public void deleteBranch(String ID) throws SQLException {
        ArrayList<Account> staffList = ACCOUNT.getAllStaffAtBranch(ID);
        if (staffList == null) {
            BRANCH.remove(ID);
        } else {
            String newBranchID = "BR00000000";
            ACCOUNT.setBranchForStaff(ID, newBranchID);
            BRANCH.remove(ID);
        }
    }

    public void changePassword(String email, String oldPassword, String newPassword) throws SQLException {
        if (ACCOUNT.checkPassword(email, oldPassword)) {
            ACCOUNT.changePasswordinDB(email, newPassword);
        }
    }
    
    public ArrayList<BranchStockList> getBranchStockList(String BranchID) {
        return BOOKLIST.getBranchLibrary(BranchID);
    }

    public ArrayList<CustomerLibrary> searchInCusLib(String CustomerID, String match) {
        return BOOKLIST.searchInCusLib(CustomerID, match);
    }

    public ArrayList<BranchStockList> searchInBranLib(String BranchID, String match) {
        return BOOKLIST.searchInBranLib(BranchID, match);
    }

    // RECEIPTS function
    public ArrayList<Receipt> getAllReceipts(String CustomerID) {
        return RECEIPT.get(CustomerID);
    }
}