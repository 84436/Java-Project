package natic.receipt;

import java.time.LocalDate;

public class RentReceipt extends Receipt {
    private LocalDate ReturnOn;

    public LocalDate getReturnOn() {
        return ReturnOn;
    }

    public void setReturnOn(LocalDate returnOn) {
        this.ReturnOn = returnOn;
    }
}
