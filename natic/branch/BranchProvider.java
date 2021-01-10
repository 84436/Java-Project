package natic.branch;

import natic.IDGenerator;
import natic.Log;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class BranchProvider implements Provider<Branch> {
    private ArrayList<Branch> BranchList;
    private IDGenerator IDGen;
    private Connection conn;

    public BranchProvider(Connection conn, IDGenerator idgen) {
        this.conn = conn;
        this.IDGen = idgen;
    }

    public Branch get(Branch o) {
        return null;
    }

    public void add(Branch o) {
        try {
            String query = String.join("\n",
                "INSERTS INTO BRANCHES",
                "(ID, Name, Address)",
                String.format(
                    "VALUES (\"%s\", \"%s\", \"%s\")",
                    o.getID(), 
                    o.getName(), 
                    o.getAddress()
                )
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            BranchList.add(o);
            Log.l.info(String.format("%s: insert into BRANCHES", o.getID()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(Branch o) {
        try {
            String query = String.join("\n",
                "UPDATE BRANCHES",
                "SET",
                String.format(
                    "Name = \"%s\", Address = \"%s\"", 
                    o.getName(),
                    o.getAddress()
                ),
                "WHERE",
                String.format("ID = %s", o.getID())
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            Log.l.info(String.format("%s: update in BRANCHES", o.getID()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void remove(Branch o) {
        try {
            String query = String.join("\n",
                "DELETE FROM BRANCHES",
                "WHERE",
                String.format("ID = %s", o.getID())
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            BranchList.remove(o);
            Log.l.info(String.format("%s: deleted from BRANCHES", o.getID()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String ID) {
        try {
            String query = String.join("\n",
                "DELETE FROM BRANCHES",
                "WHERE",
                String.format("ID = %s", ID)
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            Log.l.info(String.format("%s: deleted from BRANCHES", ID));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean checkExistBranch(String ID) {
        try {
            String query = String.join("\n",
                "SELECT * FROM BRANCH",
                "WHERE",
                String.format("ID = %s", ID)
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                Log.l.info(String.format("%s: exist", ID));
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
