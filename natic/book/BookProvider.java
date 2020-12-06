package natic.book;

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
    }

    public void edit(Book o) {
    }
    
    public void remove(Book o) {
    }
}
