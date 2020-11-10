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
}
