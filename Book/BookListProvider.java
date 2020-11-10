package Book;

import Core.IDGenerator;
import Core.Provider;
import java.util.ArrayList;

public class BookListProvider implements Provider {
    private ArrayList<BookList> BookListList;
    private IDGenerator IDGen;

    @Override
    public Object get(Object o) {
        return null;
    }

    @Override
    public void add(Object o) {
    }

    @Override
    public void edit(Object o) {
    }

    @Override
    public void remove(Object o) {
    }
}
