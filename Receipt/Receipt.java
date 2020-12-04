package Receipt;

import java.time.LocalDate;
import java.util.Currency;

public abstract class Receipt {
    private String ID;
    private String BookID;
    private String StaffID;
    private String BranchID;
    private LocalDate Date;
    private Currency Price;

    public Currency getPrice() {
        return Price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        this.BookID = bookID;
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
