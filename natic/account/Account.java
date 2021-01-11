package natic.account;

import natic.Mediator;
import natic.account.AccountEnums.*;

public abstract class Account {
    protected Mediator M;
    private String ID;
    private String Name;
    private String Email;
    private String Phone;
    private AccountType Type;
    private String Pass;

    public AccountType getType() {
        return this.Type;
    }

    public void setType(AccountType Type) {
        this.Type = Type;
    }

    public String getPass() {
        return this.Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public Account() {
        this.ID = "";
        this.Name = "";
        this.Email = "";
        this.Phone = "";
        this.Type = AccountType.UNKNOWN;
        this.Pass = "";
    }

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
