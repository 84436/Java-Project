package Account;

import java.sql.*;
import Core.Provider;
import Core.IDGenerator;
import java.util.ArrayList;

public class AccountProvider implements Provider<Account> {
    private ArrayList<Account> AccountList;
    private IDGenerator IDGen;
    private Connection conn;

    @Override
    public Account get(Account o) {
        return null;
    }

    @Override
    public void add(Account o) {
    }

    @Override
    public void edit(Account o) {
    }

    @Override
    public void remove(Account o) {
    }
}
