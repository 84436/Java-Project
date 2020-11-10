package Review;

public class Review {
    private String ID;
    // CustomerID;
    // BookID;
    private Double ReviewScore;
    private String ReviewText;
    
    // Constructors
    
    // Getter-setters
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setReviewScore(Double ReviewScore) {
        this.ReviewScore = ReviewScore;
    }
    public void setReviewText(String ReviewText) {
        this.ReviewText = ReviewText;
    }

    public String getID() {
        return ID;
    }
    public Double getReviewScore() {
        return ReviewScore;
    }
    public String getReviewText() {
        return ReviewText;
    }
}
