package natic.book;

import natic.Log;
import natic.Provider;
import natic.book.BookEnums.BookFormat;
import natic.book.BookEnums.BookGenre;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

public class BookProvider implements Provider<Book> {
    private Connection conn;

    public BookProvider(Connection conn) {
        this.conn = conn;
    }

    public Book get(Book o) {
        return null;
    }

    public ArrayList<Book> getAll() throws SQLException {
        String query = "SELECT * FROM Books";
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            resList.add(res);
        }
        Log.l.info("None: Get all books returned");
        return resList;
    }

    public Book get(String ISBN) throws SQLException {
        String query = String.format("SELECT * FROM Books WHERE ISBN = '%s'", ISBN);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Book res = new Book();
        while (rs.next()) {
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
        }
        Log.l.info(String.format("%s: Get by ISBN returned", ISBN));
        return res;
    }

    public ArrayList<Book> searchBook(String match) throws SQLException {
        String query = String.format("SELECT * FROM Books WHERE Title LIKE '%%%s%%' OR Author LIKE '%%%s%%' OR ISBN = '%s'", match,
                match, match);
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
            res.setBuyPrice(rs.getFloat("BuyPrice"));
            res.setRentPrice(rs.getFloat("RentPrice"));

            resList.add(res);
        }
        Log.l.info(String.format("%s: Searching by query returned", match));
        return resList;
    }

    public void add(Book o) throws SQLException {
        String query = "INSERT INTO BOOKS (ISBN, VersionID, Title, Author, BookYear, Publisher, Genre, Rating, BookFormat, BuyPrice, RentPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        // Primary key
        stmt.setString(1, o.getISBN());

        if (o.getVersionID() != null)
            stmt.setInt(2, o.getVersionID());
        else {
            stmt.setNull(2, java.sql.Types.NULL);
        }

        if (o.getTitle() != null)
            stmt.setString(3, o.getTitle());
        else {
            stmt.setNull(3, java.sql.Types.NULL);
        }

        if (o.getAuthor() != null)
            stmt.setString(4, o.getAuthor());
        else {
            stmt.setNull(4, java.sql.Types.NULL);
        }

        if (o.getYear() != null)
            stmt.setDate(5, Date.valueOf(LocalDate.of(o.getYear().getValue(), 1, 1)));
        else {
            stmt.setNull(5, java.sql.Types.NULL);
        }

        if (o.getPublisher() != null)
            stmt.setString(6, o.getPublisher());
        else {
            stmt.setNull(6, java.sql.Types.NULL);
        }

        if (o.getGenre() != null)
            stmt.setInt(7, o.getGenre().ordinal());
        else {
            stmt.setNull(7, java.sql.Types.NULL);
        }

        // Default rating
        stmt.setFloat(8, 0);

        if (o.getFormat() != null)
            stmt.setInt(9, o.getFormat().ordinal());
        else {
            stmt.setNull(9, java.sql.Types.NULL);
        }

        if (o.getBuyPrice() != null)
            stmt.setFloat(10, o.getBuyPrice());
        else {
            stmt.setNull(10, java.sql.Types.NULL);
        }

        if (o.getRentPrice() != null)
            stmt.setFloat(11, o.getRentPrice());
        else {
            stmt.setNull(11, java.sql.Types.NULL);
        }

        stmt.executeUpdate();
        Log.l.info(String.format("%s: inserted into BOOKS", o.getISBN()));
    }

    public void edit(Book o) throws SQLException {
        String query = "UPDATE BOOKS SET VersionID = ?, Title = ?, Author = ?, BookYear = ?, Publisher = ?, Genre = ?, Rating = ?, BookFormat = ?, BuyPrice = ?, RentPrice = ? WHERE ISBN = ?";
        PreparedStatement stmt = conn.prepareStatement(query);

        if (o.getVersionID() != null)
            stmt.setInt(1, o.getVersionID());
        else {
            stmt.setNull(1, java.sql.Types.NULL);
        }

        if (o.getTitle() != null)
            stmt.setString(2, o.getTitle());
        else {
            stmt.setNull(2, java.sql.Types.NULL);
        }

        if (o.getAuthor() != null)
            stmt.setString(3, o.getAuthor());
        else {
            stmt.setNull(3, java.sql.Types.NULL);
        }

        if (o.getYear() != null)
            stmt.setDate(4, Date.valueOf(LocalDate.of(o.getYear().getValue(), 1, 1)));
        else {
            stmt.setNull(4, java.sql.Types.NULL);
        }

        if (o.getPublisher() != null)
            stmt.setString(5, o.getPublisher());
        else {
            stmt.setNull(5, java.sql.Types.NULL);
        }

        if (o.getGenre() != null)
            stmt.setInt(6, o.getGenre().ordinal());
        else {
            stmt.setNull(6, java.sql.Types.NULL);
        }

        if (o.getRating() != null) {
            stmt.setFloat(7, o.getRating());
        }
        else {
            stmt.setFloat(7, 0);
        }

        if (o.getFormat() != null)
            stmt.setInt(8, o.getFormat().ordinal());
        else {
            stmt.setNull(8, java.sql.Types.NULL);
        }

        if (o.getBuyPrice() != null)
            stmt.setFloat(9, o.getBuyPrice());
        else {
            stmt.setNull(9, java.sql.Types.NULL);
        }

        if (o.getRentPrice() != null)
            stmt.setFloat(10, o.getRentPrice());
        else {
            stmt.setNull(10, java.sql.Types.NULL);
        }

        // WHERE condition
        stmt.setString(11, o.getISBN());

        stmt.executeUpdate();
        Log.l.info(String.format("%s: updated in BOOKS", o.getISBN()));
    }

    public void remove(Book o) throws SQLException {
        String query = String.join("\n", "DELETE FROM BOOKS", "WHERE", String.format("ISBN = %s", o.getISBN()));
        Statement stmt = conn.createStatement();
        stmt.executeQuery(query);
        Log.l.info(String.format("%s: deleted from BOOKS", o.getISBN()));
    }

    // DO WE NEED THIS
    public void remove(String ISBN) {
        // try {
        //     String query = String.join("\n", "DELETE FROM BOOKS", "WHERE", String.format("ISBN = %s", ISBN));
        //     Statement stmt = conn.createStatement();
        //     stmt.executeQuery(query);
        //     Log.l.info(String.format("%s: deleted from BOOKS", ISBN));
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }
}
