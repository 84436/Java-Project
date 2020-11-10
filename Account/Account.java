package Account;

public class Account {
    private String ID;
    private String Name;
    private String Email;
    private String Phone;

    // Constructors
    public Account() {

    }

    // Getter-setters
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    
    public String getID() {
        return ID;
    }
    public String getName() {
        return Name;
    }
    public String getEmail() {
        return Email;
    }
    public String getPhone() {
        return Phone;
    }
}
