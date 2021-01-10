package natic.book;

import natic.Log;
import natic.Provider;
import natic.book.BookEnums.BookFormat;
import natic.book.BookEnums.BookGenre;

import java.sql.*;
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
            if (o.getClass().getName() == "natic.book.BranchStockList") {
                BranchStockList b = (BranchStockList) o;
                String query = "INSERT INTO BranchStockLists (BranchID, ISBN, Stock) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, b.getOwnerID());
                stmt.setString(2, b.getISBN());
                stmt.setInt(3, b.getStock());

                stmt.executeUpdate();
                Log.l.info(String.format("%s: inserted into BRANCHSTOCKLISTS %s", b.getISBN(), b.getOwnerID()));
            } else if (o.getClass().getName() == "natic.book.CustomerLibrary") {
                CustomerLibrary c = (CustomerLibrary) o;
                String query = "INSERT INTO CustomerLibraries (CustomerID, ISBN, ExpiryDate) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, c.getOwnerID());
                stmt.setString(2, c.getISBN());
                stmt.setDate(3, Date.valueOf(c.getExpireDate()));
;
                stmt.executeUpdate();
                Log.l.info(String.format("%s: inserted into CUSTOMERLIBRARIES %s", c.getISBN(), c.getOwnerID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(BookList o) {
        try {
            Log.l.info(o.getClass().getName());
            if (o.getClass().getName() == "natic.book.BranchStockList") {
                BranchStockList b = (BranchStockList) o;
                String query = "UPDATE BranchStockLists SET Stock = ? WHERE BranchID = ? AND ISBN = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setInt(1, b.getStock());
                stmt.setString(2, b.getOwnerID());
                stmt.setString(3, b.getISBN());

                stmt.executeUpdate();
                Log.l.info(String.format("%s: Updated BRANCHSTOCKLISTS %s", b.getISBN(), b.getOwnerID()));

            } else if (o.getClass().getName() == "natic.book.CustomerLibrary") {
                CustomerLibrary c = (CustomerLibrary) o;
                String query = "UPDATE CUSTOMERLIBRARIES SET ExpiryDate = ? WHERE CustomerID = ? AND ISBN = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setDate(1, Date.valueOf(c.getExpireDate()));
                stmt.setString(2, c.getOwnerID());
                stmt.setString(3, c.getISBN());

                stmt.executeUpdate();
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
            String query = "DELETE FROM BRANCHSTOCKLISTS WHERE BranchID = ? AND ISBN = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, BranchID);
            stmt.setString(2, ISBN);

            stmt.executeUpdate();
            Log.l.info(String.format("%s: Delted from BRANCHSTOCKLISTS %s", ISBN, BranchID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAll(String OwnerID, boolean isCustomer) {
        try {
            if (isCustomer) {
                String query = "DELETE FROM CUSTOMERLIBRARIES WHERE CustomerID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, OwnerID);

                stmt.executeUpdate();
                Log.l.info(String.format("%s: Delted from CUSTOMERLIBRARIES", OwnerID));
            }
            else {
                String query = "DELETE FROM BRANCHSTOCKLISTS WHERE BranchID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, OwnerID);

                stmt.executeUpdate();
                Log.l.info(String.format("%s: Delted from BRANCHSTOCKLISTS", OwnerID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
