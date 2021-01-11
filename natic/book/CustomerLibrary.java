package natic.book;

import java.time.LocalDate;

public class CustomerLibrary extends BookList {
    private LocalDate expireDate;

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
}
