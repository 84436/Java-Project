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
}
