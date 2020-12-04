package Core;

import Account.AccountProvider;
import Review.ReviewProvider;
import Book.BookListProvider;
import Book.BookProvider;
import Receipt.ReceiptProvider;
import Branch.BranchProvider;

import java.sql.*;

public class Mediator {
   private AccountProvider ACCOUNT;
   private ReviewProvider REVIEW;
   private BookProvider BOOK;
   private BookListProvider BOOKLIST;
   private ReceiptProvider RECEIPT;
   private BranchProvider BRANCH;

   private static Connection SharedConnection = null;

   /** 
      * Temporary Database
      * Update later
      * Remember to Start up your Database before running the application
   */
   //#region Initalize DB
   static final String JDBC_Driver = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3300/NATIC";
   static final String USER = "root"; // Update later
   static final String PASS = "Mrsimple2504!"; // Update later

   public static void Initalize() {
   
      try {
         Class.forName(JDBC_Driver);
         SharedConnection = DriverManager.getConnection(DB_URL, USER, PASS);
         System.out.println("Connecting to NATIC Database...");
         
         //TODO: Add some code here

      } catch (ClassNotFoundException ex) {
         System.out.print("Error: unable to load driver class");
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
   //#endregion
}
