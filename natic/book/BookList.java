package natic.book;

public abstract class BookList {
    private String OwnerID;
    private String ISBN;

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String iD) {
        this.OwnerID = iD;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }
}