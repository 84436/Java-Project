package Receipt;

public class Receipt {
    private String ID;
    // BookVersionID;
    // StaffID
    // BranchID;
    // Date
    private Double Price;

    public Receipt() {
        ID = "";
        // BookVersionID
        // StaffID
        // BranchID
        // Date
        Price = 0.0;
    }

    // Getter-setters 
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public String getID() {
        return ID;
    }
    public Double getPrice() {
        return Price;
    }
}
