package natic.branch;

import natic.IDGenerator;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class BranchProvider implements Provider<Branch> {
    private ArrayList<Branch> BranchList;
    private IDGenerator IDGen;
    private Connection conn;

    @Override
    public Branch get(Branch o) {
        return null;
    }

    @Override
    public void add(Branch o) {
    }

    @Override
    public void edit(Branch o) {
    }

    @Override
    public void remove(Branch o) {
    }
}
