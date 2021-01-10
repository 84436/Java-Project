package natic.receipt;

import natic.IDGenerator;
import natic.Log;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class ReceiptProvider implements Provider<Receipt> {
    private IDGenerator IDGen;
    private Connection conn;

    public ReceiptProvider(Connection conn, IDGenerator idgen) {
        this.conn = conn;
        this.IDGen = idgen;
    }

    public Receipt get(Receipt o) {
        return null;
    }

    public ArrayList<Receipt> get(String CustomerID) {
        return null;
    }

    public void add(Receipt o) {
        try {
            o.setID(IDGen.next());
            String query;
            if (o.getClass().getName() == "BuyReceipt") {
                query = String.format(
                        "INSERT INTO Receipts (ID, ISBN, StaffID, CustomerID, ReceiptDate, Price, ReturnOn) VALUES ('%s', '%s', '%s', '%s', '%d-%d-%d', %2f, '%d-%d-%d')",
                        o.getID(), o.getISBN(), o.getStaffID(), o.getCustomerID(), o.getDate().getYear(),
                        o.getDate().getMonth(), o.getDate().getDayOfMonth(), 0000, 01, 01);
            } else {
                RentReceipt r = (RentReceipt) o;
                query = String.format(
                        "INSERT INTO Receipts (ID, ISBN, StaffID, CustomerID, ReceiptDate, Price, ReturnOn) VALUES ('%s', '%s', '%s', '%s', '%d-%d-%d', %2f, '%d-%d-%d')",
                        r.getID(), r.getISBN(), r.getStaffID(), r.getCustomerID(), r.getDate().getYear(),
                        r.getDate().getMonth(), r.getDate().getDayOfMonth(), r.getReturnOn().getYear(),
                        r.getReturnOn().getMonth(), r.getReturnOn().getDayOfMonth());
            }
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            Log.l.info(String.format("%s: inserted into RECEIPTS", o.getID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DO WE EVEN DO THIS?
    public void edit(Receipt o) {}

    // ALSO THIS
    public void remove(Receipt o) {}
}
