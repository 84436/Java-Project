package Book;

import java.time.Year;
import java.util.Currency;
import Book.BookEnums.*;

public class Book {
    private String ISBN;
    private String BookID;
    private String VersionID;
    private String Title;
    private String Author;
    private Year Year;
    private String Publisher;
    private BookGenre Genre;
    private BookRating Rating;
    private BookFormat Format;
    private Currency Price;

    public Currency getPrice() {
        return Price;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        this.ISBN = iSBN;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        this.BookID = bookID;
    }

    public String getVersionID() {
        return VersionID;
    }

    public void setVersionID(String versionID) {
        this.VersionID = versionID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public Year getYear() {
        return Year;
    }

    public void setYear(Year year) {
        this.Year = year;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        this.Publisher = publisher;
    }

    public BookGenre getGenre() {
        return Genre;
    }

    public void setGenre(BookGenre genre) {
        this.Genre = genre;
    }

    public BookRating getRating() {
        return Rating;
    }

    public void setRating(BookRating rating) {
        this.Rating = rating;
    }

    public BookFormat getFormat() {
        return Format;
    }

    public void setFormat(BookFormat format) {
        this.Format = format;
    }

    public void setPrice(Currency price) {
        this.Price = price;
    }
}
