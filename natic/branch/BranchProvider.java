package natic.branch;

import natic.IDGenerator;
import natic.Log;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class BranchProvider implements Provider<Branch> {
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
            o.setID(IDGen.next());
            String query = String.join("\n",
                "INSERT INTO BRANCHES",
                "(ID, Name, Address)",
                "VALUES (?, ?, ?)"
            );

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, o.getID());

            if (o.getName() != null) {
                stmt.setString(2, o.getName());
            } else {
                stmt.setNull(2, java.sql.Types.NULL);
            }

            if (o.getAddress() != null) {
                stmt.setString(3, o.getAddress());
            } else {
                stmt.setNull(3, java.sql.Types.NULL);
            }

            stmt.executeUpdate();
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
                String.format("ID = \"%s\"", o.getID())
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
                String.format("ID = \"%s\"", o.getID())
            );
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
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
                "ID = ?"
            );
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, ID);

            stmt.executeUpdate();
            Log.l.info(String.format("%s: deleted from BRANCHES", ID));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean checkExistBranch(String ID) {
        try {
            String query = String.join("\n",
                "SELECT * FROM BRANCHES",
                "WHERE",
                String.format("ID = \"%s\"", ID)
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                Log.l.info(String.format("%s: exist", ID));
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<Branch> getAll() {
        
        try {
            String query = "SELECT * FROM BRANCHES";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Branch> branchList = new ArrayList<>();

            while (rs.next()) {
                Branch branch = new Branch();
                branch.setID(rs.getString("ID"));
                branch.setName(rs.getString("Name"));
                branch.setAddress(rs.getString("Address"));
                branchList.add(branch);
            }
            Log.l.info("All branch get");
            return branchList;
        }
        catch (SQLException exec) {
            exec.printStackTrace();
        }

        return null;
    }
}
