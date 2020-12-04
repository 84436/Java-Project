package natic.book;

import natic.IDGenerator;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class BookListProvider implements Provider<BookList> {
    private ArrayList<BookList> BookListList;
    private IDGenerator IDGen;
    private Connection conn;

    public BookListProvider(Connection conn) {
        this.conn = conn;
        // TODO: initialize IDGen
    }
    
    public BookList get(BookList o) {
        return null;
    }

    public void add(BookList o) {
    }

    public void edit(BookList o) {
    }
    
    public void remove(BookList o) {
    }
}
