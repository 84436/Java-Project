package natic.book;

import natic.Log;
import natic.IDGenerator;
import natic.Provider;
import java.sql.*;
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

    public void add(Book o) {
        try {
            String query = String.join("\n",
                "INSERT INTO BOOKS",
                "(ISBN, BookID, VersionID, Title, Author, BookYear, Publisher, Genre, Rating, BookFormat, Price)",
                "VALUES",
                String.format(
                    "(\"%s\", \"%s\", \"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%.2f\", \"%s\", \"%.2f\")",
                    o.getISBN(),
                    o.getBookID(),
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
            Log.l.info(String.format("%s: inserted into BOOKS", o.getBookID()));
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
                    o.getBookID(),
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
            String query = String.join("\n",
                "DELETE FROM BOOKS",
                "WHERE",
                String.format("ISBN  = %s", o.getISBN())
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            Log.l.info(String.format("%s: deleted from BOOKS", o.getISBN()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
