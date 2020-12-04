package Review;

public class Review {
    private String ID;
    private String CustomerID;
    private String BookID;
    private Double ReviewScore;
    private String ReviewText;

    public String getReviewText() {
        return ReviewText;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        this.CustomerID = customerID;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        this.BookID = bookID;
    }

    public Double getReviewScore() {
        return ReviewScore;
    }

    public void setReviewScore(Double reviewScore) {
        this.ReviewScore = reviewScore;
    }

    public void setReviewText(String reviewText) {
        this.ReviewText = reviewText;
    }
}
