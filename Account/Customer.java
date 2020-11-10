package Account;

import java.time.LocalDate;
import java.util.ArrayList;
import Core.Mediator;
import Receipt.Receipt;

public class Customer extends Account {
    private LocalDate DoB;
    private String Address;
    private LocalDate SignUpDate;
    private String BookListID;
    private ArrayList<Receipt> ReceiptList;

    public Customer(Mediator M) {
        super(M);
    }
}
