package natic.account;

import natic.Mediator;
import natic.receipt.Receipt;
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends Account {
    private LocalDate DoB;
    private String Address;
    private LocalDate SignUpDate;
    private String BookListID;
    private ArrayList<Receipt> ReceiptList;

    public Customer() {
        
    }

    public Customer(Mediator M) {
        super(M);
    }

    public ArrayList<Receipt> getReceiptList() {
        return ReceiptList;
    }

    public void setReceiptList(ArrayList<Receipt> receiptList) {
        this.ReceiptList = receiptList;
    }

    public String getBookListID() {
        return BookListID;
    }

    public void setBookListID(String bookListID) {
        this.BookListID = bookListID;
    }

    public LocalDate getSignUpDate() {
        return SignUpDate;
    }

    public void setSignUpDate(LocalDate signUpDate) {
        this.SignUpDate = signUpDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public LocalDate getDoB() {
        return DoB;
    }

    public void setDoB(LocalDate doB) {
        this.DoB = doB;
    }
}
