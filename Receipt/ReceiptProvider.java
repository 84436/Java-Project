package Receipt;

import Core.Provider;
import Core.IDGenerator;
import java.util.ArrayList;

public class ReceiptProvider implements Provider {
    private ArrayList<Receipt> ReceiptList;
    private IDGenerator IDGen;

    /****************************************/

    /**
     * WARNING: THIS IS A STUB METHOD. DO NOT CALL STUB METHODS.
     */
    public Object get(Object o) {return null;}
    
    /**
     * WARNING: THIS IS A STUB METHOD. DO NOT CALL STUB METHODS.
     */
    public void add(Object o) {}

    /**
     * WARNING: THIS IS A STUB METHOD. DO NOT CALL STUB METHODS.
     */
    public void edit(Object o) {}

    /**
     * WARNING: THIS IS A STUB METHOD. DO NOT CALL STUB METHODS.
     */
    public void remove(Object o) {}

    /****************************************/

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
}
