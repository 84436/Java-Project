package natic.book;

import natic.Log;
import natic.Provider;
import natic.book.BookEnums.BookFormat;
import natic.book.BookEnums.BookGenre;
import natic.branch.Branch;

import java.sql.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

public class BookListProvider implements Provider<BookList> {
    private Connection conn;

    public BookListProvider(Connection conn) {
        this.conn = conn;
    }

    public BookList get(BookList o) {
        return null;
    }

    public ArrayList<CustomerLibrary> getCustomerLibrary(String CustomerID) throws SQLException {
        String query = String.format(
                "SELECT Books.*, ExpiryDate, CustomerID FROM Books, CustomerLibraries WHERE (CustomerID = '%s' AND Books.ISBN = CustomerLibraries.ISBN)",
                CustomerID);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<CustomerLibrary> resList = new ArrayList<>();
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            CustomerLibrary cres = new CustomerLibrary();
            cres.setOwnerID(rs.getString("CustomerID"));
            cres.setBook(res);
            Date exDate = rs.getDate("ExpiryDate");
            cres.setExpireDate(LocalDate.of(exDate.getYear() + 1900, exDate.getMonth() + 1, exDate.getDate()));

            resList.add(cres);
        }
        Log.l.info(String.format("%s: Get by ID CUSTOMERLIBRARIES", CustomerID));
        return resList;
    }

    public ArrayList<BranchStockList> getBranchLibrary(String BranchID) throws SQLException {
        String query = String.format(
                "SELECT Books.*, Stock, BranchID FROM Books, BranchStockLists WHERE (BranchID = '%s' AND Books.ISBN = BranchStockLists.ISBN)",
                BranchID);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<BranchStockList> resList = new ArrayList<>();
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            BranchStockList cres = new BranchStockList();
            cres.setOwnerID(rs.getString("BranchID"));
            cres.setBook(res);
            cres.setStock(rs.getInt("Stock"));

            resList.add(cres);
        }
        Log.l.info(String.format("%s: Get by ID BRANCHSTOCKLISTS", BranchID));
        return resList;
    }

    public ArrayList<BranchStockList> searchInBranLib(String BranchID, String match) throws SQLException {
        String query = String.format(
                "SELECT Books.*, Stock, BranchID FROM Books, BranchStockLists WHERE BranchID = '%s' AND Books.ISBN = BranchStockLists.ISBN AND (Title LIKE '%%%s%%' OR Author LIKE '%%%s%%' OR Books.ISBN = '%s')",
                BranchID, match, match, match);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<BranchStockList> resList = new ArrayList<>();
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            BranchStockList bres = new BranchStockList();
            bres.setOwnerID(rs.getString("BranchID"));
            bres.setBook(res);
            bres.setStock(rs.getInt("Stock"));

            resList.add(bres);
        }
        Log.l.info(String.format("%s: Search in BRANCHSTOCKLISTS", BranchID));
        return resList;
    }

    public ArrayList<CustomerLibrary> searchInCusLib(String CustomerID, String match) throws SQLException {
        String query = String.format(
            "SELECT Books.*, ExpiryDate, CustomerID FROM Books, CustomerLibraries WHERE CustomerID = '%s' AND Books.ISBN = CustomerLibraries.ISBN AND (Title LIKE '%%%s%%' OR Author LIKE '%%%s%%' OR Books.ISBN = '%s')",
            CustomerID, match, match, match);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<CustomerLibrary> resList = new ArrayList<>();
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            CustomerLibrary cres = new CustomerLibrary();
            cres.setOwnerID(rs.getString("CustomerID"));
            cres.setBook(res);
            Date exDate = rs.getDate("ExpiryDate");
            cres.setExpireDate(LocalDate.of(exDate.getYear() + 1900, exDate.getMonth() + 1, exDate.getDate()));

            resList.add(cres);
        }
        Log.l.info(String.format("%s: Search in CUSTOMERLIBRARIES", CustomerID));
        return resList;
    }

    public CustomerLibrary getBookInCusLib(String CustomerID, String ISBN) throws SQLException {
        String query = String.format(
                "SELECT Books.*, ExpiryDate, CustomerID FROM Books, CustomerLibraries WHERE (CustomerID = '%s' AND Books.ISBN = CustomerLibraries.ISBN AND Books.ISBN = '%s')",
                CustomerID, ISBN);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        CustomerLibrary cres = null;
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            cres = new CustomerLibrary();
            cres.setOwnerID(rs.getString("CustomerID"));
            cres.setBook(res);
            Date exDate = rs.getDate("ExpiryDate");
            cres.setExpireDate(LocalDate.of(exDate.getYear() + 1900, exDate.getMonth() + 1, exDate.getDate()));
        }
        Log.l.info(String.format("%s: Get by ID CUSTOMERLIBRARIES", CustomerID));
        return cres;
    }

    public BranchStockList getBookInBranLib(String BranchID, String ISBN) throws SQLException {
        String query = String.format(
                "SELECT Books.*, Stock, BranchID FROM Books, BranchStockLists WHERE (BranchID = '%s' AND Books.ISBN = BranchStockLists.ISBN AND Books.ISBN = '%s')",
                BranchID, ISBN);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        BranchStockList bres = null;
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            bres = new BranchStockList();
            bres.setOwnerID(rs.getString("BranchID"));
            bres.setBook(res);
            bres.setStock(rs.getInt("Stock"));
        }
        Log.l.info(String.format("%s: Get by ID BRANCHSTOCKLISTS", BranchID));
        return bres;
    }

    public void add(BookList o) throws SQLException{
        if (o.getClass().getName() == "natic.book.BranchStockList") {
            BranchStockList b = (BranchStockList) o;
            String query = "INSERT INTO BranchStockLists (BranchID, ISBN, Stock) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, b.getOwnerID());
            stmt.setString(2, b.getBook().getISBN());
            stmt.setInt(3, 1);

            stmt.executeUpdate();
            Log.l.info(
                    String.format("%s: inserted into BRANCHSTOCKLISTS %s", b.getBook().getISBN(), b.getOwnerID()));
        } else if (o.getClass().getName() == "natic.book.CustomerLibrary") {
            CustomerLibrary c = (CustomerLibrary) o;
            String query = "INSERT INTO CustomerLibraries (CustomerID, ISBN, ExpiryDate) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, c.getOwnerID());
            stmt.setString(2, c.getBook().getISBN());
            stmt.setDate(3, Date.valueOf(c.getExpireDate()));
            ;
            stmt.executeUpdate();
            Log.l.info(
                    String.format("%s: inserted into CUSTOMERLIBRARIES %s", c.getBook().getISBN(), c.getOwnerID()));
        }
    }

    public void edit(BookList o) throws SQLException {
        Log.l.info(o.getClass().getName());
        if (o.getClass().getName() == "natic.book.BranchStockList") {
            BranchStockList b = (BranchStockList) o;
            String query = "UPDATE BranchStockLists SET Stock = ? WHERE BranchID = ? AND ISBN = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, b.getStock());
            stmt.setString(2, b.getOwnerID());
            stmt.setString(3, b.getBook().getISBN());

            stmt.executeUpdate();
            Log.l.info(String.format("%s: Updated BRANCHSTOCKLISTS %s", b.getBook().getISBN(), b.getOwnerID()));

        } else if (o.getClass().getName() == "natic.book.CustomerLibrary") {
            CustomerLibrary c = (CustomerLibrary) o;
            String query = "UPDATE CUSTOMERLIBRARIES SET ExpiryDate = ? WHERE CustomerID = ? AND ISBN = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDate(1, Date.valueOf(c.getExpireDate()));
            stmt.setString(2, c.getOwnerID());
            stmt.setString(3, c.getBook().getISBN());

            stmt.executeUpdate();
            Log.l.info(String.format("%s: Updated CUSTOMERLIBRARIES %s", c.getBook().getISBN(), c.getOwnerID()));
        }
    }

    public void remove(BookList o) throws SQLException {
    }

    public void removeOne(String BranchID, String ISBN) throws SQLException {
        String query = "DELETE FROM BRANCHSTOCKLISTS WHERE BranchID = ? AND ISBN = ?";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, BranchID);
        stmt.setString(2, ISBN);

        stmt.executeUpdate();
        Log.l.info(String.format("%s: Deleted from BRANCHSTOCKLISTS %s", ISBN, BranchID));
    }

    public void removeAll(String OwnerID, boolean isCustomer) throws SQLException {
        if (isCustomer) {
            String query = "DELETE FROM CUSTOMERLIBRARIES WHERE CustomerID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, OwnerID);

            stmt.executeUpdate();
            Log.l.info(String.format("%s: Delted from CUSTOMERLIBRARIES", OwnerID));
        } else {
            String query = "DELETE FROM BRANCHSTOCKLISTS WHERE BranchID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, OwnerID);

            stmt.executeUpdate();
            Log.l.info(String.format("%s: Deleted from BRANCHSTOCKLISTS", OwnerID));
        }
    }
}
