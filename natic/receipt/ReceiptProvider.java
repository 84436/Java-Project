package natic.receipt;

import natic.IDGenerator;
import natic.Provider;
import java.sql.*;
import java.util.ArrayList;

public class ReceiptProvider implements Provider<Receipt> {
    private ArrayList<Receipt> ReceiptList;
    private IDGenerator IDGen;
    private Connection conn;

    public ReceiptProvider(Connection conn) {
        this.conn = conn;
        // TODO: initialize IDGen
    }

    /**
     * Get a receipt with a specific ID from the receipt store.
     * @param ID ID of receipt to be feteched.
     * @return A copy of the receipt, or null if not found
     */
    public Receipt get(String ID) {
        Receipt r = null;
        for (var each: this.ReceiptList) {
            if (each.getID().equals(ID)) {
                return each;
            }
        }
        return r;
    }

    /**
     * Add a new receipt to the receipt store.
     * @param x Receipt to be added.
     */
    public void add(Receipt x) {
        this.ReceiptList.add(x);
    }

    /**
     * Remove a receipt from the receipt store.
     * @param ID ID of receipt to be removed.
     */
    public void remove(String ID) {
        for (var each: this.ReceiptList) {
            if (each.getID().equals(ID)) {
                this.ReceiptList.remove(each);
                break;
            }
        }
    }

    public Receipt get(Receipt o) {
        // TODO Auto-generated method stub
        return null;
    }

    public void edit(Receipt o) {
        // TODO Auto-generated method stub

    }
    
    public void remove(Receipt o) {
        // TODO Auto-generated method stub

    }
}
