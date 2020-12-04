package Receipt;

import java.time.LocalDate;
import java.util.Currency;

public abstract class Receipt {
    private String ID;
    private String ISBN;
    private String StaffID;
    private String BranchID;
    private LocalDate Date;
    private Currency Price;

    public Currency getPrice() {
        return Price;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        this.ISBN = iSBN;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        this.StaffID = staffID;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        this.BranchID = branchID;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        this.Date = date;
    }

    public void setPrice(Currency price) {
        this.Price = price;
    }
}
