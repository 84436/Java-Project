package natic.receipt;

import natic.IDGenerator;
import natic.Log;
import natic.Provider;
import java.sql.*;
import java.time.LocalDate;
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

    public Receipt getByID(String ReceiptID) throws SQLException {
        String query = String.format("SELECT * FROM Receipts WHERE ID = '%s'", ReceiptID);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Receipt res = null;
        if (rs.next()) {
            res = new Receipt();
            res.setID(rs.getString("ID"));
            res.setISBN(rs.getString("ISBN"));
            res.setStaffID(rs.getString("StaffID"));
            res.setCustomerID(rs.getString("CustomerID"));
            Date reDate = rs.getDate("ReceiptDate");
            res.setDate(LocalDate.of(reDate.getYear() + 1900, reDate.getMonth() + 1, reDate.getDate()));
            res.setPrice(rs.getFloat("Price"));
            Date returnDate = rs.getDate("ReturnOn");
            res.setReturnOn(LocalDate.of(returnDate.getYear() + 1900, returnDate.getMonth() + 1, returnDate.getDate()));
        }
        Log.l.info(String.format("%s: Get RECEIPTS", ReceiptID));
        return res;
    }

    public ArrayList<Receipt> get(String CustomerID) throws SQLException {
        String query = String.format("SELECT * FROM Receipts WHERE CustomerID = '%s'", CustomerID);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<Receipt> resList = new ArrayList<>();
        while (rs.next()) {
            Receipt res = new Receipt();
            res.setID(rs.getString("ID"));
            res.setISBN(rs.getString("ISBN"));
            res.setStaffID(rs.getString("StaffID"));
            res.setCustomerID(rs.getString("CustomerID"));
            Date reDate = rs.getDate("ReceiptDate");
            res.setDate(LocalDate.of(reDate.getYear() + 1900, reDate.getMonth() + 1, reDate.getDate()));
            res.setPrice(rs.getFloat("Price"));
            Date returnDate = rs.getDate("ReturnOn");
            res.setReturnOn(LocalDate.of(returnDate.getYear() + 1900, returnDate.getMonth() + 1, returnDate.getDate()));

            resList.add(res);
        }
        Log.l.info(String.format("%s: Get all RECEIPTS", CustomerID));
        return resList;
    }

    public ArrayList<Receipt> getAtBranch(String BranchID) throws SQLException {
        String query = String.format(
                "SELECT * FROM receipts, branches, staff where receipts.StaffID = staff.id and staff.BranchID = branches.id and branches.id = '%s'",
                BranchID);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<Receipt> resList = new ArrayList<>();
        while (rs.next()) {
            Receipt res = new Receipt();
            res.setID(rs.getString("ID"));
            res.setISBN(rs.getString("ISBN"));
            res.setStaffID(rs.getString("StaffID"));
            res.setCustomerID(rs.getString("CustomerID"));
            Date reDate = rs.getDate("ReceiptDate");
            res.setDate(LocalDate.of(reDate.getYear() + 1900, reDate.getMonth() + 1, reDate.getDate()));
            res.setPrice(rs.getFloat("Price"));
            Date returnDate = rs.getDate("ReturnOn");
            res.setReturnOn(LocalDate.of(returnDate.getYear() + 1900, returnDate.getMonth() + 1, returnDate.getDate()));

            resList.add(res);
        }
        Log.l.info(String.format("%s: Get all RECEIPTS", BranchID));
        return resList;
    }

    public void add(Receipt o) throws SQLException {
        System.out.println(o.getClass().getName());
        o.setID(IDGen.next());
        String query = "INSERT INTO Receipts (ID, ISBN, StaffID, CustomerID, ReceiptDate, Price, ReturnOn) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, o.getID());
        stmt.setString(2, o.getISBN());
        stmt.setString(3, o.getStaffID());
        stmt.setString(4, o.getCustomerID());
        stmt.setDate(5, Date.valueOf(o.getDate()));
        stmt.setFloat(6, o.getPrice());
        stmt.setDate(7, Date.valueOf(o.getReturnOn()));

        stmt.executeUpdate();
        Log.l.info(String.format("%s: inserted into RECEIPTS", o.getID()));
    }

    public void bypassReceiptForVirtualAcc(String VirtualID, String currentStaff) throws SQLException {
        String query = String.join("\n", "UPDATE RECEIPTS", "SET", String.format("StaffID = \"%s\"", VirtualID),
                "WHERE", String.format("StaffID = \"%s\"", currentStaff));
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        Log.l.info(String.format("Bypass RECEIPTS from \"%s\" to \"%s\"", currentStaff, VirtualID));
    }

    // DO WE EVEN DO THIS?
    public void edit(Receipt o) {
    }

    public void remove(Receipt o) {
    }
}
