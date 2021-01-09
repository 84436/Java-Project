package natic.review;

public class Review {
    private String CustomerID;
    private String ISBN;
    private int ReviewScore;
    private String ReviewText;
    private String CustomerName;

    public String getReviewText() {
        return ReviewText;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        this.CustomerID = customerID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getReviewScore() {
        return ReviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.ReviewScore = reviewScore;
    }

    public void setReviewText(String reviewText) {
        this.ReviewText = reviewText;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerName() {
        return CustomerName;
    }
}
