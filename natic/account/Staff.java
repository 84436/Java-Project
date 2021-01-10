package natic.account;

import natic.Mediator;

public class Staff extends Account {
    private String BranchID;

    public Staff() {
        BranchID = "BR00000000";
    }

    public Staff(Mediator M) {
        super(M);
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        this.BranchID = branchID;
    }
}
