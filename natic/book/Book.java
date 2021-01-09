package natic.book;

import natic.book.BookEnums.*;
import java.time.Year;
import java.util.Currency;

public class Book {
    private String ISBN;
    private int VersionID;
    private String Title;
    private String Author;
    private Year Year;
    private String Publisher;
    private BookGenre Genre;
    private float Rating;
    private BookFormat Format;
    private float Price;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        this.ISBN = iSBN;
    }

    public int getVersionID() {
        return VersionID;
    }

    public void setVersionID(int versionID) {
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

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        this.Rating = rating;
    }

    public BookFormat getFormat() {
        return Format;
    }

    public void setFormat(BookFormat format) {
        this.Format = format;
    }

    public float getPrice() {
        return Price;
    }
    public void setPrice(float price) {
        this.Price = price;
    }
}
