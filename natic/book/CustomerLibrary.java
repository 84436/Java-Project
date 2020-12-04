package natic.book;

import java.time.LocalDate;
import java.util.HashMap;

public class CustomerLibrary extends BookList {
    private HashMap<Book, LocalDate> Library;

    public void removeAllExpired() {}
}
