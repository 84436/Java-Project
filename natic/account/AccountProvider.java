package natic.account;

import natic.Log;
import natic.IDGenerator;
import natic.Provider;
import natic.account.AccountEnums.AccountType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import natic.*;

public class AccountProvider implements Provider<Account> {
    private IDGenerator IDGen;
    private Connection conn;

    public AccountProvider(Connection conn, IDGenerator idgen) {
        this.conn = conn;
        this.IDGen = idgen;
    }

    public boolean getEmailforLogin(String email) throws SQLException {
        String query = String.join("\n", 
            "SELECT * from ACCOUNTS",
            "WHERE Email = ?"
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            Log.l.info(String.format("%s: found!", email));
            return true;
        }

        return false;
    }

    public boolean checkPassword(String email, String password) throws SQLException {
        String query = String.join("\n", 
            "SELECT Email, Pass",
            "FROM ACCOUNTS",
            "WHERE Email = ?"  
        );
        
        PreparedStatement stmt = conn.prepareStatement(query);
        if (email != null) {
            stmt.setString(1, email);
        } else {
            stmt.setNull(1, java.sql.Types.NULL);
        }
        ResultSet rs = stmt.executeQuery();
        
        boolean result = false;
        if (rs.next()) {
            result = BCrypt.checkpw(password, rs.getString("Pass"));
        }
        Log.l.info("Password check: " + result);
        return result;
    }

    // public AccountType getType(String email, String password) {
        
    //     try {
    //         String query = String.join("\n", 
    //             "SELECT * from Accounts",
    //             "WHERE", 
    //             String.format("Email = %s", email),
    //             "AND",
    //             String.format("Pass = %s", BCrypt.hashpw(password, BCrypt.gensalt()))
    //         );

    //         Statement stmt = conn.createStatement();
    //         ResultSet rs = stmt.executeQuery(query);

    //         int result = rs.getInt("Type");
    //         switch (result) {
    //             case 0 : AccountType ac = AccountType.CUSTOMER; return ac;
    //             case 1 : AccountType as = AccountType.STAFF;    return as;
    //             case 2 : AccountType ad = AccountType.ADMIN;    return ad;
    //             default: AccountType un = AccountType.UNKNOWN;  return un;
    //         }
    //     }
    //     catch (SQLException e) {
    //         e.printStackTrace();
    //     }
        
    //     return null;
    // }

    public AccountType getType(String email) throws SQLException {
        
        String query = String.join("\n", 
            "SELECT * from Accounts",
            "WHERE Email = ?"
        );

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        
        rs.next();
        int result = rs.getInt("Type");
        switch (result) {
            case 0 : AccountType ac = AccountType.CUSTOMER; return ac;
            case 1 : AccountType as = AccountType.STAFF;    return as;
            case 2 : AccountType ad = AccountType.ADMIN;    return ad;
            default: AccountType un = AccountType.UNKNOWN;  return un;
        }
    }

    public Account get(Account o) {
        return null;
    }

    public Staff getStaff(String ID) throws SQLException {
        String query = String.join("\n",
            "SELECT * FROM STAFF join ACCOUNTS on STAFF.ID = ACCOUNTS.ID",
            "WHERE",
            "STAFF.ID = ?"
        );
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, ID);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff();
            staff.setID(rs.getString("ID"));
            staff.setType(AccountType.values()[rs.getInt("Type")]);
            staff.setName(rs.getString("Name"));
            staff.setEmail(rs.getString("Email"));
            staff.setPhone(rs.getString("Phone"));
            staff.setBranchID(rs.getString("BranchID"));
            return staff;
        }
        return null;
    }

    public Admin getAdmin(String ID) throws SQLException {
        String query = String.join("\n",
            "SELECT * FROM ACCOUNTS",
            "WHERE",
            "ID = ?"
        );
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, ID);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Admin admin = new Admin();
            admin.setID(rs.getString("ID"));
            admin.setEmail(rs.getString("Email"));
            admin.setType(AccountType.values()[rs.getInt("Type")]);
            admin.setName(rs.getString("Name"));
            admin.setPhone(rs.getString("Phone"));
            return admin;
        }
        return null;
    }

    public Customer getCustomer(String ID) throws SQLException {
        String query = String.join("\n",
            "SELECT * FROM CUSTOMERS join ACCOUNTS on CUSTOMERS.ID = ACCOUNTS.ID",
            "WHERE",
            "CUSTOMERS.ID = ?"
        );
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, ID);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setID(rs.getString("ID"));
            customer.setType(AccountType.values()[rs.getInt("Type")]);
            customer.setName(rs.getString("Name"));
            customer.setEmail(rs.getString("Email"));
            customer.setPhone(rs.getString("Phone"));
            customer.setDoB(LocalDate.of(rs.getDate("DoB").getYear() + 1900, rs.getDate("DoB").getMonth() + 1, rs.getDate("DoB").getDate()));
            customer.setAddress(rs.getString("Address"));
            customer.setSignUpDate(LocalDate.of(rs.getDate("SignUpDate").getYear() + 1900, rs.getDate("SignUpDate").getMonth() + 1, rs.getDate("SignUpDate").getDate()));
            return customer;
        }
        return null;
    }

    public void add(Account o) throws SQLException {
        o.setID(IDGen.next());
        // Who is o?
        AccountType oType;
        switch (o.getType().toString()) {
            case "Customer" : oType = AccountType.CUSTOMER; break;
            case "Staff"    : oType = AccountType.STAFF;    break;
            case "Admin"    : oType = AccountType.ADMIN;    break;
            default         : oType = AccountType.UNKNOWN;  break;
        }
        Log.l.info(String.format("New %s", oType.toString()));

        String query = String.join("\n",
            "INSERT INTO ACCOUNTS",
            "(ID, Name, Email, Phone, Type, Pass)",
            "VALUES (?, ?, ?, ?, ?, ?)"
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setString(1, o.getID());

        if (o.getName() != null) {
            stmt.setString(2, o.getName());
        } else {
            stmt.setNull(2, java.sql.Types.NULL);
        }

        if (o.getEmail() != null) {
            stmt.setString(3, o.getEmail());
        } else {
            stmt.setNull(3, java.sql.Types.NULL);
        }

        if (o.getPhone() != null) {
            stmt.setString(4, o.getPhone());
        } else {
            stmt.setNull(4, java.sql.Types.NULL);
        }

        if (o.getType() != null) {
            stmt.setInt(5, o.getType().ordinal());
        } else {
            stmt.setNull(5, java.sql.Types.NULL);
        }

        if (o.getPass() != null) {
            stmt.setString(6, BCrypt.hashpw(o.getPass(), BCrypt.gensalt()));
        } else {
            stmt.setNull(6, java.sql.Types.NULL);
        }

        stmt.executeUpdate();
        Log.l.info(String.format("%s: inserted into ACCOUNTS", o.getID()));

        // Who is o? (Customer/Staff/Admin)
        switch (oType) {
            case CUSTOMER:
                Customer oc = (Customer) o;
                String queryc = String.join("\n",
                    "INSERT INTO CUSTOMERS",
                    "(ID, DoB, Address, SignUpDate, BookListID)",
                    "VALUES (?, ?, ?, ?, ?)"
                );

                PreparedStatement stmtc = conn.prepareStatement(queryc);

                stmtc.setString(1, oc.getID());

                if (oc.getDoB() != null) {
                    stmtc.setDate(2, Date.valueOf(LocalDate.of(oc.getDoB().getYear(), oc.getDoB().getMonth().getValue(), oc.getDoB().getDayOfMonth())));
                } else {
                    stmtc.setNull(2, java.sql.Types.NULL);
                }

                if (oc.getAddress() != null) {
                    stmtc.setString(3, oc.getAddress());
                } else {
                    stmtc.setNull(3, java.sql.Types.NULL);
                }

                if (oc.getSignUpDate() != null) {
                    stmtc.setDate(4, Date.valueOf(LocalDate.of(oc.getSignUpDate().getYear(), oc.getSignUpDate().getMonth().getValue(), oc.getSignUpDate().getDayOfMonth())));
                } else {
                    stmtc.setNull(4, java.sql.Types.NULL);
                }

                if (oc.getBookListID() != null) {
                    stmtc.setString(5, oc.getBookListID());
                } else {
                    stmtc.setNull(5, java.sql.Types.NULL);
                }
                
                stmtc.executeUpdate();
                Log.l.info(String.format("%s: inserted into CUSTOMERS", o.getID()));
                break;
            
            case STAFF:
                Staff os = (Staff) o;
                String querys = String.join("\n",
                    "INSERT INTO STAFF",
                    "(ID, BranchID)",
                    "VALUES (?, ?)"
                );

                PreparedStatement stmts = conn.prepareStatement(querys);

                stmts.setString(1, os.getID());

                if (os.getBranchID() != null) {
                    stmts.setString(2, os.getBranchID());
                } else {
                    stmts.setNull(2, java.sql.Types.NULL);
                }

                stmts.executeUpdate();
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

    public void edit(Account o) throws SQLException {
        AccountType oType = o.getType();
        // switch (o.getClass().getName()) {
        //     case "Customer" : oType = AccountType.CUSTOMER; break;
        //     case "Staff"    : oType = AccountType.STAFF;    break;
        //     case "Admin"    : oType = AccountType.ADMIN;    break;
        //     default         : oType = AccountType.UNKNOWN;  break;
        // }
        Log.l.info(String.format("Update %s", oType.toString()));

        String query = String.join("\n", 
            "UPDATE ACCOUNTS", 
            "SET Name = ?, Phone = ?, Email = ?",
            "WHERE ID = ?"
        );
        PreparedStatement stmt = conn.prepareStatement(query);

        if (o.getName() != null) {
            stmt.setString(1, o.getName());
        } else {
            stmt.setNull(1, java.sql.Types.NULL);
        }

        if (o.getPhone() != null) {
            stmt.setString(2, o.getPhone());
        } else {
            stmt.setNull(2, java.sql.Types.NULL);
        }

        if (o.getEmail() != null) {
            stmt.setString(3, o.getEmail());
        } else {
            stmt.setNull(3, java.sql.Types.NULL);
        }

        // WHERE statement
        stmt.setString(4, o.getID());

        stmt.executeUpdate();
        Log.l.info(String.format("%s: update in ACCOUNTS", o.getID()));
        stmt.close();

        switch (oType.toString()) {
            case "Customer":
                Customer oc = (Customer) o;
                String queryc = String.join("\n",
                    "UPDATE CUSTOMERS",
                    "SET",
                    "DOB = ?, Address = ?",
                    "WHERE",
                    "ID = ?"   
                );

                PreparedStatement stmtc = conn.prepareStatement(queryc);

                if (oc.getDoB() != null) {
                    stmtc.setDate(1, Date.valueOf(LocalDate.of(oc.getDoB().getYear(), oc.getDoB().getMonth().getValue(), oc.getDoB().getDayOfMonth())));
                } else {
                    stmtc.setNull(1, java.sql.Types.NULL);
                }
    
                if (oc.getAddress() != null) {
                    stmtc.setString(2, oc.getAddress());
                } else {
                    stmtc.setNull(2, java.sql.Types.NULL);
                }
    
                // WHERE statement
                stmtc.setString(3, oc.getID());

                stmtc.executeUpdate();
                Log.l.info(String.format("%s: update in CUSTOMER", o.getID()));
                break;

            case "Staff":
                Staff os = (Staff) o;
                String querys = String.join("\n", "UPDATE STAFF", "SET", "BranchID = ?", "WHERE", "ID = ?");

                PreparedStatement stmts = conn.prepareStatement(querys);

                if (os.getBranchID() != null) {
                    stmts.setString(1, os.getBranchID());
                } else {
                    stmts.setNull(1, java.sql.Types.NULL);
                }

                // WHERE statement
                stmts.setString(2, os.getID());

                stmts.executeUpdate();
                Log.l.info(String.format("%s: update in STAFF", o.getID()));
                break;

            case "Admin":
                Log.l.info(String.format("%s: account is ADMIN, no further actions taken", o.getID()));
                break;

            case "Unknown":
                Log.l.warning(String.format("%s: something's off. The account type is UNKNOWN.", o.getID()));
                break;
        }
        stmt.close();
    }
    
    public void remove(Account o) throws SQLException {
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
                PreparedStatement stmtc = conn.prepareStatement(queryc);
                stmtc.executeUpdate();
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
                PreparedStatement stmts = conn.prepareStatement(querys);
                stmts.executeUpdate();
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

    public ArrayList<Account> getAllStaffAtBranch(String BranchID) throws SQLException {
        ArrayList<Account> staffInBranch = new ArrayList<>();

        String query = String.join("\n",
            "SELECT * FROM STAFF",
            "WHERE",
            String.format("BranchID = \"%s\"", BranchID)
        );

        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Staff staff = new Staff();
            staff.setID(rs.getString("ID"));
            staff.setBranchID(rs.getString("BranchID"));
            staffInBranch.add(staff);
        }
        Log.l.info("Get all staff in branch");
        return staffInBranch;
    }

    public void setBranchForStaff(String oldBranchID, String BranchID) throws SQLException {
        String query = String.join("\n",
            "UPDATE STAFF",
            "SET",
            String.format("BranchID = \"%s\"", BranchID),
            "WHERE",
            String.format("BranchID = \"%s\"", oldBranchID)
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
    }

    public void removeStaffFromBranch(String ID) throws SQLException {
        String query = String.join("\n",
            "DELETE FROM STAFF",
            "WHERE",
            String.format("ID = \"%s\"", ID)
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        Log.l.info(String.format("%s: REMOVE from BRANCHES", ID));
    }

    public boolean checkStaffInBranch(String StaffID, String BranchID) throws SQLException {
        String query = String.join("\n",
            "SELECT * FROM STAFF",
            "WHERE",
            String.format("ID = \"%s\"", StaffID),
            "AND",
            String.format("BranchID = \"%s\"", BranchID)
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Log.l.info(String.format("%s: STAFF in %s", StaffID, BranchID));
            return true;
        }

        return false;
    }

    public void changePasswordinDB(String email, String newPassword) throws SQLException {
        String query = String.join("\n",
            "UPDATE ACCOUNTS",
            "SET",
            "Pass = ?",
            "WHERE Email = ?"
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        stmt.setString(2, email);

        stmt.executeUpdate();
    }

    public ArrayList<Account> searchCustomer(String match) throws SQLException {
        String query = String.join("\n",
            "SELECT *",
            "FROM ACCOUNTS as ac JOIN CUSTOMERS as cus on ac.ID = cus.ID",
            "WHERE",
            String.format("Name LIKE '%%%s%%' OR Email LIKE '%%%s%%'", match, match)
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Account> accountList = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setEmail(rs.getString("Email"));
            customer.setName(rs.getString("Name"));
            customer.setPhone(rs.getString("Phone"));
            customer.setAddress(rs.getString("Address"));
            accountList.add(customer);
        }
        Log.l.info("All Customer found!");
        return accountList;
    }

    public ArrayList<Account> getAllCustomer() throws SQLException {
        String query = String.join("\n",
            "SELECT *",
            "FROM ACCOUNTS as ac JOIN CUSTOMERS as cus on ac.ID = cus.ID"
        );
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Account> accountList = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("Type") == 0) {
                Customer customer = new Customer();
                customer.setEmail(rs.getString("Email"));
                customer.setName(rs.getString("Name"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                accountList.add(customer);
            }
        }
        Log.l.info("All Customer found!");
        return accountList;
    }

    public ArrayList<Staff> getALlStaff() throws SQLException {
        String query = "SELECT * FROM ACCOUNTS join STAFF on ACCOUNTS.ID = STAFF.ID";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Staff> accountList = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("Type") == 1) {
                Staff staff = new Staff();
                staff.setID(rs.getString("ID"));
                staff.setPhone(rs.getString("Phone"));
                staff.setEmail(rs.getString("Email"));
                staff.setName(rs.getString("Name"));
                staff.setBranchID(rs.getString("BranchID"));
                accountList.add(staff);
            }
        }
        Log.l.info("All Staff found!");
        return accountList;
    }

    public String getIDByEmail(String email) throws SQLException {
        String query = String.format("SELECT * FROM ACCOUNTS WHERE Email = '%s'", email);
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("ID");
        }

        return null;
    }
}
