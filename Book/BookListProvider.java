package Book;

import Core.IDGenerator;
import Core.Provider;
import java.util.ArrayList;
import java.sql.*;

public class BookListProvider implements Provider<BookList> {
    private ArrayList<BookList> BookListList;
    private IDGenerator IDGen;

    private Connection conn;
    @Override
    public BookList get(BookList o) {
        return null;
    }

    @Override
    public void add(BookList o) {
    }

    @Override
    public void edit(BookList o) {
    }

    @Override
    public void remove(BookList o) {
    }
}
