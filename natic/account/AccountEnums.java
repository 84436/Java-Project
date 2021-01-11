package natic.account;

/**
 * Specific constants (and their string representations) for account-related stuff.
 */

// See the RULES at BookEnums for more details.
public class AccountEnums {
    public enum AccountType {
        //#region: Values
        CUSTOMER("Customer"),
        STAFF("Staff"),
        ADMIN("Admin"),
        UNKNOWN("unknown")
        ;
        //#endregion

        //#region: String enum magic
        private final String fullname;
        AccountType(String fullname) { this.fullname = fullname; }
        public String toString() { return this.fullname; }
        //#endregion
    }
}
