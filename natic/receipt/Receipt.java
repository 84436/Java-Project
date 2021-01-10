package natic.receipt;

import java.time.LocalDate;

public class Receipt {
    private String ID;
    private String ISBN;
    private String StaffID;
    private String CustomerID;
    private LocalDate Date;
    private float Price;
    private LocalDate ReturnOn;

    public float getPrice() {
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

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        this.CustomerID = customerID;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        this.Date = date;
    }

    public void setPrice(float price) {
        this.Price = price;
    }

    public LocalDate getReturnOn() {
        return ReturnOn;
    }

    public void setReturnOn(LocalDate returnOn) {
        this.ReturnOn = returnOn;
    }
}
