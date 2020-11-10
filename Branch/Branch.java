package Branch;

public class Branch {
    private String ID;
    private String Name;
    private String Address;
    // BookListID

    // getter-setters
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getID() {
        return ID;
    }
    public String getName() {
        return Name;
    }
    public String getAddress() {
        return Address;
    }
}