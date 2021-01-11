package natic;

import natic.account.*;
import natic.account.AccountProvider;
import natic.account.AccountEnums.AccountType;
import natic.book.BookProvider;
import natic.book.BranchStockList;
import natic.book.CustomerLibrary;
import natic.book.BookEnums.BookFormat;
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
    
    // BOOKS functions
    public void addBook(Book b) throws SQLException {
        BOOK.add(b);
    }

    public void editBook(Book b) throws SQLException {
        BOOK.edit(b);
    }

    public Book getByISBN(String match) throws SQLException {
        return BOOK.get(match);
    }
    
    public ArrayList<Book> getAllBooks() throws SQLException {
        return BOOK.getAll();
    }

    public ArrayList<Book> searchBook(String query) throws SQLException {
        return BOOK.searchBook(query);
    }

    // REVIEWS function
    public void reviewBook(Review o) throws SQLException {
        // Add a review
        REVIEW.add(o);

        // Calculate rating
        Book b = BOOK.get(o.getISBN());
        b.setRating(REVIEW.getRating(o.getISBN()));
        BOOK.edit(b);
    }

    public ArrayList<Review> getReview(String ISBN) throws SQLException {
        return REVIEW.get(ISBN);
    }

    // Buy-Rent function
    public void buyBook(String StaffID, String CustomerID, String ISBN) throws SQLException {
        Book b = BOOK.get(ISBN);
        CustomerLibrary c = BOOKLIST.getBookInCusLib(CustomerID, ISBN);

        // if not exist in lib
        if (c == null) {
            CustomerLibrary cAdd = new CustomerLibrary();
            cAdd.setOwnerID(CustomerID);
            cAdd.setBook(b);
            cAdd.setExpireDate(LocalDate.of(1, 1, 1));
            BOOKLIST.add(cAdd);
        }
        // if exist but not permanent
        else if (!c.getExpireDate().equals(LocalDate.of(1, 1, 1))) {
            c.setExpireDate(LocalDate.of(1, 1, 1));
            BOOKLIST.edit(c);
        }

        // Create a receipt
        Receipt receipt = new Receipt();
        receipt.setISBN(ISBN);
        receipt.setStaffID(StaffID);
        receipt.setCustomerID(CustomerID);
        receipt.setDate(LocalDate.now());
        receipt.setPrice(b.getPrice());
        receipt.setReturnOn(LocalDate.of(1, 1, 1));
        RECEIPT.add(receipt);

        if (!b.getFormat().equals(BookFormat.EBOOK)) {
            BranchStockList br = BOOKLIST.getBookInBranLib(ACCOUNT.getStaff(StaffID).getBranchID(), ISBN);
            br.setStock(br.getStock() - 1);
            BOOKLIST.edit(br);
        }
    }

    public void rentBook(String StaffID, String CustomerID, String ISBN, int numberOfMonth) throws SQLException {
        Book b = BOOK.get(ISBN);
        CustomerLibrary c = BOOKLIST.getBookInCusLib(CustomerID, ISBN);
        Receipt receipt = new Receipt();

        // if not exist in lib
        if (c == null) {
            CustomerLibrary cAdd = new CustomerLibrary();
            cAdd.setOwnerID(CustomerID);
            cAdd.setBook(b);
            cAdd.setExpireDate(LocalDate.now().plusMonths(numberOfMonth));
            BOOKLIST.add(cAdd);
        }
        else if(c.getExpireDate().equals(LocalDate.of(1, 1, 1))){
            return;
        }
        // if exist but expired
        else if (c.getExpireDate().compareTo(LocalDate.now()) < 0) {
            c.setExpireDate(LocalDate.now().plusMonths(numberOfMonth));
            receipt.setReturnOn(LocalDate.now().plusMonths(numberOfMonth));
            BOOKLIST.edit(c);
        }
        // if exist but not expired
        else if (c.getExpireDate().compareTo(LocalDate.now()) >= 0) {
            receipt.setReturnOn(c.getExpireDate().plusMonths(numberOfMonth));
            c.setExpireDate(c.getExpireDate().plusMonths(numberOfMonth));
            BOOKLIST.edit(c);
        }

        // Create a receipt
        receipt.setISBN(ISBN);
        receipt.setStaffID(StaffID);
        receipt.setCustomerID(CustomerID);
        receipt.setDate(LocalDate.now());
        receipt.setPrice(b.getPrice());
        RECEIPT.add(receipt);
    }

    public void buyAtCounter(String StaffID, String CustomerID, ArrayList<String> ISBNs) throws SQLException {
        for (int i = 0; i < ISBNs.size(); i++) {
            buyBook(StaffID, CustomerID, ISBNs.get(i));
        }
    }

    // BOOKLISTS function
    public void addBookToBranch(String ISBN, String BranchID) throws SQLException {
        BranchStockList b = new BranchStockList();
        b.setOwnerID(BranchID);
        b.setStock(1);
        b.setBook(BOOK.get(ISBN));

        BOOKLIST.add(b);
    }

    public void updateStock(String BranchID, String ISBN, int amount) throws SQLException {
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

    public void removeBookFromBranch(String ISBN, String BranchID) throws SQLException {
        BOOKLIST.removeOne(BranchID, ISBN);
    }

    public ArrayList<CustomerLibrary> getCustomerLibrary(String CustomerID) throws SQLException {
        return BOOKLIST.getCustomerLibrary(CustomerID);
    }
    
    public ArrayList<BranchStockList> getBranchStockList(String BranchID) throws SQLException {
        return BOOKLIST.getBranchLibrary(BranchID);
    }

    public ArrayList<CustomerLibrary> searchInCusLib(String CustomerID, String match) throws SQLException {
        return BOOKLIST.searchInCusLib(CustomerID, match);
    }

    public ArrayList<BranchStockList> searchInBranLib(String BranchID, String match) throws SQLException {
        return BOOKLIST.searchInBranLib(BranchID, match);
    }

    // RECEIPTS function
    public ArrayList<Receipt> getAllReceipts(String CustomerID) throws SQLException {
        return RECEIPT.get(CustomerID);
    }

    // ACCOUNTS functions
    public AccountType checkLogin(String email, String password) throws SQLException {
        if ((email != null && password != null)) {
            if (!(ACCOUNT.getEmailforLogin(email) || ACCOUNT.checkPassword(email, password)))
                return AccountType.UNKNOWN;

            return ACCOUNT.getType(email);
        } else {
            Log.l.info(String.format("%s: NOT FOUND", email));
            return AccountType.UNKNOWN;
        }
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
        ACCOUNT.add(oAccount);
    }

    public void removeStaff(String ID) throws SQLException {
        Staff s = ACCOUNT.getStaff(ID);
        RECEIPT.bypassReceiptForVirtualAcc("AC00000000", ID);
        ACCOUNT.removeStaffFromBranch(ID);
        ACCOUNT.remove(s);
        Log.l.info(String.format("%s: STAFF in %s", ID, s.getBranchID()));
    }

    public ArrayList<Staff> getAllStaff() throws SQLException {
        return ACCOUNT.getALlStaff();
    }

    // GetBranch()
    public ArrayList<Branch> getAllBranch() throws SQLException {
        return BRANCH.getAll();
    }

    public Branch getBranch(String BranchID) throws SQLException {
        return BRANCH.get(BranchID);
    }
    
    public void addBranch(Branch branch) throws SQLException {
        BRANCH.add(branch);
    }
    
    public void editBranch(Branch b) throws SQLException {
        BRANCH.edit(b);
    }

    public void deleteBranch(String ID) throws SQLException {
        String newBranchID = "BR00000000";
        BOOKLIST.removeAll(ID, false);
        ACCOUNT.setBranchForStaff(ID, newBranchID);
        BRANCH.remove(ID);
    }

    public void changePassword(String email, String oldPassword, String newPassword) throws SQLException {
        if (ACCOUNT.checkPassword(email, oldPassword)) {
            ACCOUNT.changePasswordinDB(email, newPassword);
            Log.l.info("Password changed");
        } else {
            Log.l.info("Password does not change");
        }
    }
    
    public Staff getStaffByID(String ID) throws SQLException {
        return ACCOUNT.getStaff(ID);
    }

    public Admin getAdminByID(String ID) throws SQLException {
        return ACCOUNT.getAdmin(ID);
    }

    public Customer getCustomerByID(String ID) throws SQLException {
        return ACCOUNT.getCustomer(ID);
    }

    public String getIDByEmail(String email) throws SQLException {
        return ACCOUNT.getIDByEmail(email);
    }

    public ArrayList<Staff> searchStaff(String match) throws SQLException {
        return ACCOUNT.searchStaffByEmailOrName(match);
    }

    public ArrayList<Branch> searchBranch(String match) throws SQLException {
        return BRANCH.searchBranchByNameOrAddress(match);
    }
}
