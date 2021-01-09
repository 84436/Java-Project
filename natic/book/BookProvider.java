package natic.book;

import natic.Log;
import natic.IDGenerator;
import natic.Provider;
import natic.book.BookEnums.BookFormat;
import natic.book.BookEnums.BookGenre;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;

public class BookProvider implements Provider<Book>{
    private ArrayList<Book> BookList;
    private IDGenerator IDGen;
    private Connection conn;

    public BookProvider(Connection conn, IDGenerator idgen) {
        this.conn = conn;
        this.IDGen = idgen;
    }

    public Book get(Book o) {
        return null;
    }

    public ArrayList<Book> getAll() {
        try {
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
                res.setPrice(rs.getFloat("Price"));
                resList.add(res);
            }
            Log.l.info("None: Get all books returned");
            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book get(String ISBN) {
        try {
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
                res.setPrice(rs.getFloat("Price"));
            }
            Log.l.info(String.format("%s: Get by ISBN returned", ISBN));
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Book> searchBook(String match) {
        try {
            String query = String.format("SELECT * FROM Books WHERE Title LIKE '%%%s%%' OR Author LIKE '%%%s%%'", match, match);
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
            Log.l.info(String.format("%s: Searching by query returned", match));
            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(Book o) {
        try {
            String query = String.join("\n",
                "INSERT INTO BOOKS",
                "(ISBN, BookID, VersionID, Title, Author, BookYear, Publisher, Genre, Rating, BookFormat, Price)",
                "VALUES",
                String.format(
                    "(\"%s\", \"%s\", \"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%.2f\", \"%s\", \"%.2f\")",
                    o.getISBN(),
                    o.getVersionID(),
                    o.getTitle(),
                    o.getAuthor(),
                    o.getYear(),
                    o.getPublisher(),
                    o.getGenre().getClass().getName(),
                    o.getRating(),
                    o.getFormat().getClass().getName(),
                    o.getPrice()
                )    
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            BookList.add(o);
            Log.l.info(String.format("%s: inserted into BOOKS", o.getISBN()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(Book o) {
        try {
            String query = String.join("\n",
                "UPDATE BOOKS",
                "SET",
                String.format(
                    "(\"%s\", \"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%.2f\", \"%s\", \"%.2f\")",
                    o.getVersionID(),
                    o.getTitle(),
                    o.getAuthor(),
                    o.getYear(),
                    o.getPublisher(),
                    o.getGenre().getClass().getName(),
                    o.getRating(),
                    o.getFormat().getClass().getName(),
                    o.getPrice()
                ),
                "WHERE",
                String.format("ISBN = %s", o.getISBN())
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            Log.l.info(String.format("%s: updated in BOOKS", o.getISBN()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void remove(Book o) {
        try {
            String query = String.join("\n", "DELETE FROM BOOKS", "WHERE", String.format("ISBN = %s", o.getISBN()));
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            BookList.remove(o);
            Log.l.info(String.format("%s: deleted from BOOKS", o.getISBN()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String ISBN) {
        try {
            String query = String.join("\n", "DELETE FROM BOOKS", "WHERE", String.format("ISBN = %s", ISBN));
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            Log.l.info(String.format("%s: deleted from BOOKS", ISBN));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
