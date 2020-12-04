package natic.branch;

public class Branch {
    private String ID;
    private String Name;
    private String Address;
    private String BookListID;

    public String getBookListID() {
        return BookListID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setBookListID(String bookListID) {
        this.BookListID = bookListID;
    }
}
