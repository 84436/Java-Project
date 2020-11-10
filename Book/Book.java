package Book;

public class Book {
    private String ISBN;
    private String BookID;
    private String VersionID;
    private String Title;
    private String Author;
    // Year
    private String Publisher;
    private String Genre; //  <-- 1 Genre or Many Genres??
    // Rating
    // Format
    // private Double Price;

    // Getter-setters 
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public void setBookID(String BookID) {
        this.BookID = BookID;
    }
    public void setVersionID(String VersionID) {
        this.VersionID = VersionID;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }
    public void setAuthor(String Author) {
        this.Author = Author;
    }
    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }
    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public String getISBN() {
        return ISBN;
    }
    public String getBookID() {
        return BookID;
    }
    public String getVersionID() {
        return VersionID;
    }
    public String getTitle() {
        return Title;
    }
    public String getAuthor() {
        return Author;
    }
    public String getPublisher() {
        return Publisher;
    }
    public String Genre() {
        return Genre;
    }
}
