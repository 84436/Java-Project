package natic;

import natic.account.AccountProvider;
import natic.book.BookProvider;
import natic.book.BookListProvider;
import natic.branch.BranchProvider;
import natic.receipt.ReceiptProvider;
import natic.review.ReviewProvider;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
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
    private final String DB_NAME = "NATIC";
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

    public void init() {
        try {
            // Properties
            if (!readProperties()) { return; }

            // Logging
            if (enableLogging) { Log.initLogger(); }
            else { Log.off(); }

            // Test if database existed
            Class.forName(DB_DRIVER);
            SharedConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Log.i("Getting a DB connection");

            if (SharedConnection != null) {
                Log.i("Houston, we've got a connection.");
            }

            // Database not existed: creating new database

            // Database existed: reconnect to it instead
            
        } catch (ClassNotFoundException ex) {
            Log.s("Unable to load driver class");
            System.exit(1);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {    
            try {
                if (SharedConnection != null) SharedConnection.close();
            } catch (SQLException se3) {
                se3.printStackTrace();
            }
        }
    }
}
