package Account;

import Core.Mediator;

public class Staff extends Account {
    private String BranchID;

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
