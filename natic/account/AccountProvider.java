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

    public boolean getEmailforLogin(String email) {
        try {
            String query = String.join("\n", 
                "SELECT * from ACCOUNTS",
                "WHERE",
                String.format("Email = \"%s\"", email)
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            if (rs != null) {
                Log.l.info(String.format("%s: found!", email));
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPassword(String email, String password) {
        boolean isFound = false;
        try {
            String query = String.join("\n", 
                "SELECT ac.Email, ac.Pass from ACCOUNTS as ac",
                "WHERE",
                String.format("Email = %s", email)
            );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs != null) {
                while (rs.next()) {
                    if (BCrypt.checkpw(password, rs.getString("Pass"))) {
                        isFound = true;
                        Log.l.info("ACCOUNT: Found!");
                        return true;
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (!isFound) {
            Log.l.info(String.format("%s: not found!", password));
        }
        return false;
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

    public AccountType getType(String email) {
        
        try {
            String query = String.join("\n", 
                "SELECT * from Accounts",
                "WHERE", 
                String.format("Email = %s", email)
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
            switch (o.getType().toString()) {
                case "Customer" : oType = AccountType.CUSTOMER; break;
                case "Staff"    : oType = AccountType.STAFF;    break;
                case "Admin"    : oType = AccountType.ADMIN;    break;
                default         : oType = AccountType.UNKNOWN;  break;
            }
            Log.l.info(String.format("New %s", oType.toString()));

            // TODO: Set account ID

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

                    stmt.setString(1, oc.getID());

                    if (oc.getDoB() != null) {
                        stmt.setDate(2, Date.valueOf(LocalDate.of(oc.getDoB().getYear(), oc.getDoB().getMonth().getValue(), oc.getDoB().getDayOfMonth())));
                    } else {
                        stmt.setNull(2, java.sql.Types.NULL);
                    }

                    if (oc.getAddress() != null) {
                        stmt.setString(3, oc.getAddress());
                    } else {
                        stmt.setNull(3, java.sql.Types.NULL);
                    }

                    if (oc.getSignUpDate() != null) {
                        stmt.setDate(4, Date.valueOf(LocalDate.of(oc.getSignUpDate().getYear(), oc.getSignUpDate().getMonth().getValue(), oc.getSignUpDate().getDayOfMonth())));
                    } else {
                        stmt.setNull(4, java.sql.Types.NULL);
                    }

                    if (oc.getBookListID() != null) {
                        stmt.setString(5, oc.getBookListID());
                    } else {
                        stmt.setNull(5, java.sql.Types.NULL);
                    }
                    
                    stmt.executeUpdate(queryc);
                    Log.l.info(String.format("%s: inserted into CUSTOMERS", o.getID()));
                    break;
                
                case STAFF:
                    Staff os = (Staff) o;
                    String querys = String.join("\n",
                        "INSERT INTO STAFF",
                        "(ID, BranchID)",
                        "VALUES (?, ?)"
                    );

                    stmt.setString(1, os.getID());

                    if (os.getBranchID() != null) {
                        stmt.setString(2, os.getBranchID());
                    } else {
                        stmt.setNull(2, java.sql.Types.NULL);
                    }

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
            AccountType oType = o.getType();
            switch (o.getType().toString()) {
                case "Customer" : oType = AccountType.CUSTOMER; break;
                case "Staff"    : oType = AccountType.STAFF;    break;
                case "Admin"    : oType = AccountType.ADMIN;    break;
                default         : oType = AccountType.UNKNOWN;  break;
            }
            Log.l.info(String.format("Update %s", oType.toString()));
    
            String query = String.join("\n", 
                "UPDATE ACCOUNTS", 
                "SET",
                "NAME = ?, Phone = ?", 
                "WHERE", 
                "ID = ?"
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

            // WHERE statement
            stmt.setString(3, o.getID());

            stmt.executeUpdate(query);
            Log.l.info(String.format("%s: update in ACCOUNTS", o.getID()));
    
            switch (oType) {
                case CUSTOMER:
                    Customer oc = (Customer) o;
                    String queryc = String.join("\n",
                        "UPDATE CUSTOMERS",
                        "SET",
                        "DOB = ?, Address = ?",
                        "WHERE",
                        "ID = ?"   
                    );

                    if (oc.getDoB() != null) {
                        stmt.setDate(1, Date.valueOf(LocalDate.of(oc.getDoB().getYear(), oc.getDoB().getMonth().getValue(), oc.getDoB().getDayOfMonth())));
                    } else {
                        stmt.setNull(1, java.sql.Types.NULL);
                    }
        
                    if (oc.getAddress() != null) {
                        stmt.setString(2, oc.getAddress());
                    } else {
                        stmt.setNull(2, java.sql.Types.NULL);
                    }
        
                    // WHERE statement
                    stmt.setString(3, oc.getID());

                    stmt.executeUpdate(queryc);
                    Log.l.info(String.format("%s: update in CUSTOMER", o.getID()));
                    break;

                case STAFF:
                    Staff os = (Staff) o;
                    String querys = String.join("\n", 
                        "UPDATE STAFF", 
                        "SET",
                        "BranchID = ?", 
                        "WHERE", 
                        "ID = ?"
                    );

                    if (os.getBranchID() != null) {
                        stmt.setString(1, os.getBranchID());
                    } else {
                        stmt.setNull(1, java.sql.Types.NULL);
                    }

                    // WHERE statement
                    stmt.setString(3, os.getID());

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

    public ArrayList<Account> getAllStaffAtBranch(String BranchID) {
        ArrayList<Account> staffInBranch = new ArrayList<>();

        try {
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
        catch (SQLException exec) {
            exec.printStackTrace();
        }

        return null;
    }

    public void setBranchForStaff(String oldBranchID, String BranchID) {
        try {
            String query = String.join("\n",
                "UPDATE STAFF",
                "SET",
                String.format("BranchID = \"%s\"", BranchID),
                "WHERE",
                String.format("BranchID = \"%s\"", oldBranchID)
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeQuery();
        }
        catch (SQLException exec) {
            exec.printStackTrace();
        }
    }

    public void removeStaffFromBranch(String ID) {
        try {
            String query = String.join("\n",
                "DELETE FROM STAFF",
                "WHERE",
                String.format("ID = \"%s\"", ID)
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
            Log.l.info(String.format("%s: REMOVE from BRANCHES", ID));
        }
        catch (SQLException exec) {
            exec.printStackTrace();
        }
    }

    public boolean checkStaffInBranch(String StaffID, String BranchID) {
        try {
            String query = String.join("\n",
                "SELECT * FROM STAFF",
                "WHERE",
                String.format("ID = \"%s\"", StaffID),
                "AND",
                String.format("BranchID = \"%s\"", BranchID)
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                Log.l.info(String.format("%s: STAFF in %s", StaffID, BranchID));
                return true;
            }
        } catch (SQLException exec) {
            exec.printStackTrace();
        }
        return false;
    }

    public void changePasswordinDB(String email, String newPassword) {
        try {
            String query = String.join("\n",
                "UPDATE ACCOUNTS",
                "SET",
                String.format("Pass = %s", BCrypt.hashpw(newPassword, BCrypt.gensalt())),
                "WHERE",
                String.format("Email = %s", email)
            );
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeQuery();
        }
        catch (SQLException exec) {
            exec.printStackTrace();
        }
    }

    public ArrayList<Account> searchCustomer(String match) {
        try {
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

        } catch (SQLException exec) {
            exec.printStackTrace();
        }

        return null;
    }
}
