package natic.branch;

import natic.IDGenerator;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class BranchProvider implements Provider<Branch> {
    private ArrayList<Branch> BranchList;
    private IDGenerator IDGen;
    private Connection conn;

    public BranchProvider(Connection conn) {
        this.conn = conn;
        // TODO: initialize IDGen
    }

    public Branch get(Branch o) {
        return null;
    }

    public void add(Branch o) {
    }

    public void edit(Branch o) {
    }
    
    public void remove(Branch o) {
    }
}
