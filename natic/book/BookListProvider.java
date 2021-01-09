package natic.book;

import natic.IDGenerator;
import natic.Log;
import natic.Provider;
import natic.book.BookEnums.BookFormat;
import natic.book.BookEnums.BookGenre;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;

public class BookListProvider implements Provider<BookList> {
    private ArrayList<BookList> BookListList;
    private IDGenerator IDGen;
    private Connection conn;

    public BookListProvider(Connection conn, IDGenerator idgen) {
        this.conn = conn;
        this.IDGen = idgen;
    }

    public BookList get(BookList o) {
        return null;
    }

    public ArrayList<Book> getCustomerLibrary(String CustomerID) {
        try {
            String query = String.format(
                    "SELECT Books.* FROM Books, CustomerLibraries WHERE (CustomerID = '%s' AND Books.ISBN = CustomerLibraries.ISBN)",
                    CustomerID);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Book> resList = new ArrayList<>();
            while (rs.next()) {
                Book res = new Book();
                res.setISBN(rs.getString("ISBN"));
                res.setVersionID(rs.getInt("VersionID"));
                res.setTitle(rs.getString("Title"));
                res.setAuthor(rs.getString("Author"));
                res.setYear(Year.of(rs.getDate("BookYear").getYear() + 1900));
                res.setPublisher(rs.getString("Publisher"));
                res.setGenre(BookGenre.values()[rs.getInt("Genre")]);
                res.setRating(rs.getFloat("Rating"));
                res.setFormat(BookFormat.values()[rs.getInt("BookFormat")]);
                res.setPrice(rs.getFloat("Price"));
                resList.add(res);
            }
            Log.l.info(String.format("%s: Get by ID CUSTOMERLIBRARIES", CustomerID));
            return resList;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(BookList o) {
        try {
            if (o.getClass().getName() == "BranchStockList") {
                BranchStockList b = (BranchStockList) o;
                String query = String.format(
                        "INSERT INTO BranchStockLists (BranchID, ISBN, Stock) VALUES ('%s', '%s', %d)", b.getOwnerID(),
                        b.getISBN(), b.getStock());
                Statement stmt = conn.createStatement();
                stmt.executeQuery(query);
                Log.l.info(String.format("%s: inserted into BRANCHSTOCKLISTS %s", b.getISBN(), b.getOwnerID()));
            } else if (o.getClass().getName() == "CustomerLibrary") {
                CustomerLibrary c = (CustomerLibrary) o;
                String query = String.format(
                        "INSERT INTO CustomerLibraries (CustomerID, ISBN, ExpiryDate) VALUES ('%s', '%s', '%d-%d-%d')",
                        c.getOwnerID(), c.getISBN(), c.getExpireDate().getYear(), c.getExpireDate().getMonth(),
                        c.getExpireDate().getDayOfMonth());
                Statement stmt = conn.createStatement();
                stmt.executeQuery(query);
                Log.l.info(String.format("%s: inserted into CUSTOMERLIBRARIES %s", c.getISBN(), c.getOwnerID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(BookList o) {
        try {
            if (o.getClass().getName() == "BranchStockList") {
                BranchStockList b = (BranchStockList) o;
                String query = String.format(
                        "UPDATE BranchStockLists SET Stock = %d WHERE BranchID = '%s' AND ISBN = '%s'", b.getStock(),
                        b.getOwnerID(), b.getISBN());
                Statement stmt = conn.createStatement();
                stmt.executeQuery(query);
                Log.l.info(String.format("%s: Updated BRANCHSTOCKLISTS %s", b.getISBN(), b.getOwnerID()));

            } else if (o.getClass().getName() == "CustomerLibrary") {
                CustomerLibrary c = (CustomerLibrary) o;
                String query = String.format(
                        "UPDATE C SET ExpiryDate = '%d-%d-%d' WHERE CustomerID = '%s' AND ISBN = '%s'",
                        c.getExpireDate().getYear(), c.getExpireDate().getMonth(), c.getOwnerID(), c.getISBN());
                Statement stmt = conn.createStatement();
                stmt.executeQuery(query);
                Log.l.info(String.format("%s: Updated CUSTOMERLIBRARIES %s", c.getISBN(), c.getOwnerID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(BookList o) {
    }

    public void removeOne(String BranchID, String ISBN) {
        try {
            String query = String.format("DELETE FROM BRANCHSTOCKLISTS WHERE BranchID = '%s' AND ISBN = '%s'", BranchID,
                    ISBN);
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            Log.l.info(String.format("%s: Delted from BRANCHSTOCKLISTS %s", ISBN, BranchID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAll(String OwnerID, boolean isCustomer) {
        try {
            if (isCustomer) {
                String query = String.format("DELETE FROM CUSTOMERLIBRARIES WHERE CustomerID = '%s'", OwnerID);
                Statement stmt = conn.createStatement();
                stmt.executeQuery(query);
                Log.l.info(String.format("%s: Delted from CUSTOMERLIBRARIES", OwnerID));
            }
            else {
                String query = String.format("DELETE FROM BRANCHSTOCKLISTS WHERE BranchID = '%s'", OwnerID);
                Statement stmt = conn.createStatement();
                stmt.executeQuery(query);
                Log.l.info(String.format("%s: Delted from BRANCHSTOCKLISTS", OwnerID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
