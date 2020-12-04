package natic.account;

import natic.Log;
import natic.IDGenerator;
import natic.Provider;
import natic.account.AccountEnums.AccountType;

import java.sql.*;
import java.util.ArrayList;

public class AccountProvider implements Provider<Account> {
    private ArrayList<Account> AccountList;
    private IDGenerator IDGen;
    private Connection conn;

    public AccountProvider(Connection conn) {
        this.conn = conn;
        // TODO: initialize IDGen
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
            Log.i(String.format("New %s", oType.toString()));

            // TODO: Set account ID

            String query = String.join("\n",
                "INSERT INTO ACCOUNTS",
                "(ID, AccName, Email, Phone, AccType)",
                String.format(
                    "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %s)",
                    o.getID(), o.getName(), o.getEmail(), o.getPhone(), oType.ordinal()
                )
            );
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            Log.i(String.format("%s: inserted into ACCOUNTS", o.getID()));

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
                    Log.i(String.format("%s: inserted into CUSTOMERS", o.getID()));
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
                    Log.i(String.format("%s: inserted into STAFF", o.getID()));
                    break;
                
                case ADMIN:
                    Log.i(String.format("%s: account is ADMIN, no further actions taken", o.getID()));
                    break;
                
                default:
                    Log.w(String.format("%s: something's off. The account type is UNKNOWN.", o.getID()));
                    break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(Account o) {
    }
    
    public void remove(Account o) {
        try {
            String query = String.join("\n",
                ""
            );
            PreparedStatement stmt = conn.prepareStatement(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hello() {
        Log.i("AccountProvider says hi.");
    }
}
