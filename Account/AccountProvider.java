package Account;

import Core.Provider;
import Core.IDGenerator;
import java.util.ArrayList;

public class AccountProvider implements Provider {
    private ArrayList<Account> AccountList;
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
