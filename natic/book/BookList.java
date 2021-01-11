package natic.book;

public abstract class BookList {
    private String OwnerID;
    private Book Book;

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String iD) {
        this.OwnerID = iD;
    }

    public Book getBook() {
        return Book;
    }

    public void setBook(Book book) {
        Book = book;
    }
}