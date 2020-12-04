package Account;

import Core.Mediator;

public abstract class Account {
    protected Mediator M;
    private String ID;
    private String Name;
    private String Email;
    private String Phone;

    public Account(Mediator M) {
        this.M = M;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }
}
