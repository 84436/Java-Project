package Book;

import java.sql.*;
import Core.IDGenerator;
import Core.Provider;
import java.util.ArrayList;

public class BookProvider implements Provider<Book>{
    private ArrayList<Book> BookList;
    private IDGenerator IDGen;
    private Connection conn;

    @Override
    public Book get(Book o) {
        return null;
    }

    @Override
    public void add(Book o) {
    }

    @Override
    public void edit(Book o) {
    }

    @Override
    public void remove(Book o) {
    }
}
