package natic.account;

import natic.Log;
import natic.IDGenerator;
import natic.Provider;
import natic.account.AccountEnums.AccountType;

import java.sql.*;
import java.util.ArrayList;
import natic.*;

public class AccountProvider implements Provider<Account> {
    private ArrayList<Account> AccountList;
    private IDGenerator IDGen;
    private Connection conn;

    public AccountProvider(Connection conn, IDGenerator idgen) {
        this.conn = conn;
        this.IDGen = idgen;
    }

    public boolean getEmailforLogin(String email) {
        boolean isFound = false;
        try {
            String query = String.join("\n", 
                "SELECT * from ACCOUNTS",
                "WHERE",
                String.format("Email = %s", email)
            );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs != null) {
                isFound = true;
                Log.l.info(String.format("%s: found!", email));
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (!isFound) {
            Log.l.info(String.format("%s: not found!", email));
        }
        return false;
    }

    public boolean getHashPassword(String password) {
        String hassPwd = BCrypt.hashpw(password, BCrypt.gensalt(1));
        boolean isFound = false;
        try {
            String query = String.join("\n", 
                "SELECT * from ACCOUNTS",
                "WHERE",
                String.format("Pass = %s", hassPwd)
            );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs != null) {
                isFound = true;
                Log.l.info(String.format("%s: found!", hassPwd));
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (!isFound) {
            Log.l.info(String.format("%s: not found!", hassPwd));
        }
        return false;
    }

    public AccountType getType(String email, String password) {
        
        try {
            String query = String.join("\n", 
                "SELECT * from Accounts",
                "WHERE", 
                String.format("Email = %s", email),
                "AND",
                String.format("Pass = %s", BCrypt.hashpw(password, BCrypt.gensalt(1)))
            );

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int result = rs.getInt("Type");
            switch (result) {
                case 0 : AccountType ac = AccountType.CUSTOMER; return ac;
                case 1 : AccountType as = AccountType.STAFF;    return as;
                case 2 : AccountType ad = AccountType.ADMIN;    return ad;
                default: AccountType un = AccountType.UNKNOWN;  return un;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public Account get(Account o) {
        return null;
    }

    public void add(Account o) {
        try {
            // Who is o?
            AccountType oType;
            switch (o.getClass().getName()) {
                case "Customer" : oType = AccountType.CUSTOMER; break;
                case "Staff"    : oType = AccountType.STAFF;    break;
                case "Admin"    : oType = AccountType.ADMIN;    break;
                default         : oType = AccountType.UNKNOWN;  break;
            }
            Log.l.info(String.format("New %s", oType.toString()));

            // TODO: Set account ID

            String query = String.join("\n",
                "INSERT INTO ACCOUNTS",
                "(ID, AccName, Email, Phone, AccType)",
                String.format(
                    "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %s, \"%s\")",
                    o.getID(), o.getName(), o.getEmail(), o.getPhone(), oType.ordinal(), BCrypt.hashpw(o.getPass(), BCrypt.gensalt(1))
                )
            );
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            AccountList.add(o);
            Log.l.info(String.format("%s: inserted into ACCOUNTS", o.getID()));

            // Who is o? (Customer/Staff/Admin)
            switch (oType) {
                case CUSTOMER:
                    Customer oc = (Customer) o;
                    String queryc = String.join("\n",
                        "INSERT INTO CUSTOMER",
                        "(ID, DoB, Address, SignUpDate, BookListID)",
                        String.format(
                            "VALUES (\"%s\", \"%s-%s-%s\", \"%s\", \"%s-%s-%s\", %s)",
                            oc.getID(),
                            oc.getDoB().getYear(), oc.getDoB().getMonth().getValue(), oc.getDoB().getDayOfMonth(),
                            oc.getAddress(),
                            oc.getSignUpDate().getYear(), oc.getSignUpDate().getMonth().getValue(), oc.getSignUpDate().getDayOfMonth(),
                            "null"
                        )
                    );
                    stmt.executeUpdate(queryc);
                    Log.l.info(String.format("%s: inserted into CUSTOMERS", o.getID()));
                    break;
                
                case STAFF:
                    Staff os = (Staff) o;
                    String querys = String.join("\n",
                        "INSERT INTO STAFF",
                        "(ID, BranchID)",
                        String.format(
                            "VALUES (\"%s\", \"%s\")",
                            os.getID(),
                            os.getBranchID()
                        )
                    );
                    stmt.executeUpdate(querys);
                    Log.l.info(String.format("%s: inserted into STAFF", o.getID()));
                    break;
                
                case ADMIN:
                    Log.l.info(String.format("%s: account is ADMIN, no further actions taken", o.getID()));
                    break;
                
                default:
                    Log.l.warning(String.format("%s: something's off. The account type is UNKNOWN.", o.getID()));
                    break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(Account o) {
        try {
            AccountType oType;
            switch (o.getClass().getName()) {
                case "Customer" : oType = AccountType.CUSTOMER; break;
                case "Staff"    : oType = AccountType.STAFF;    break;
                case "Admin"    : oType = AccountType.ADMIN;    break;
                default         : oType = AccountType.UNKNOWN;  break;
            }
            Log.l.info(String.format("Update %s", oType.toString()));
    
            String query = String.join("\n", 
                "UPDATE ACCOUNTS", 
                "SET",
                String.format(
                    "NAME = \"%s\", Phone = \"%s\"", 
                    o.getName(), 
                    o.getPhone()
                ),
                "WHERE", 
                String.format(
                    "ID = \"%s\"", o.getID()
                )
            );
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            Log.l.info(String.format("%s: update in ACCOUNTS", o.getID()));
    
            switch (oType) {
                case CUSTOMER:
                    Customer oc = (Customer) o;
                    String queryc = String.join("\n",
                        "UPDATE CUSTOMERS",
                        "SET",
                        String.format(
                            "DOB = \"%s-%s-%S\", Address = \"%s\"",
                            oc.getDoB().getYear(), oc.getDoB().getMonth().getValue(), oc.getDoB().getDayOfMonth(),
                            oc.getAddress()
                        ),
                        "WHERE",
                        String.format(
                            "ID = %s", oc.getID()    
                        )
                    );
                    stmt.executeUpdate(queryc);
                    Log.l.info(String.format("%s: update in CUSTOMER", o.getID()));
                    break;

                case STAFF:
                    Staff os = (Staff) o;
                    String querys = String.join("\n", 
                        "UPDATE STAFF", 
                        "SET",
                        String.format(
                            "BranchID = %s", 
                            os.getBranchID()
                        ),
                        "WHERE", 
                        String.format(
                            "ID = \"%s\"", os.getID()
                        )
                    );
                    stmt.executeUpdate(querys);
                    Log.l.info(String.format("%s: update in STAFF", o.getID()));

                case ADMIN:
                    Log.l.info(String.format("%s: account is ADMIN, no further actions taken", o.getID()));
                    break;

                case UNKNOWN:
                    Log.l.warning(String.format("%s: something's off. The account type is UNKNOWN.", o.getID()));
                    break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void remove(Account o) {
        try {
            AccountType oType;
            switch (o.getClass().getName()) {
                case "Customer" : oType = AccountType.CUSTOMER; break;
                case "Staff"    : oType = AccountType.STAFF;    break;
                case "Admin"    : oType = AccountType.ADMIN;    break;
                default         : oType = AccountType.UNKNOWN;  break;
            }
            Log.l.info(String.format("New %s", oType.toString()));

            String query = String.join("\n",
                "DELETE FROM ACCOUNTS", 
                "WHERE",
                String.format(
                    "ID = \"%s\"", 
                    o.getID()
                )
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate(query);
            AccountList.remove(o);
            Log.l.info(String.format("%s: deleted from ACCOUNTS", o.getID()));

            switch (oType) {
                case CUSTOMER:
                    Customer oc = (Customer) o;
                    String queryc = String.join("\n",
                        "DELETE FROM CUSTOMER",
                        "WHERE",
                        String.format(
                            "ID = %s", 
                            oc.getID()
                        )
                    );
                    stmt.executeUpdate(queryc);
                    Log.l.info(String.format("%s: delete from CUSTOMER", o.getID()));
                    break;

                case STAFF:
                    Staff os = (Staff) o;
                    String querys = String.join("\n",
                        "DELETE FROM STAFF",
                        "WHERE",
                        String.format(
                            "ID = \"%s\"",
                            os.getID()
                        )
                    );
                    stmt.executeUpdate(querys);
                    Log.l.info(String.format("%s: deleted from STAFF", o.getID()));
                    break;

                case ADMIN:
                    Log.l.info(String.format("%s: account is ADMIN, no further actions taken", o.getID()));
                    break;

                case UNKNOWN:
                    Log.l.warning(String.format("%s: something's off. The account type is UNKNOWN.", o.getID()));
                    break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
