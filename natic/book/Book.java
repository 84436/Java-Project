package natic.book;

import natic.book.BookEnums.*;
import java.time.Year;

public class Book {
    private String ISBN;
    private Integer VersionID;
    private String Title;
    private String Author;
    private Year Year;
    private String Publisher;
    private BookGenre Genre;
    private Float Rating;
    private BookFormat Format;
    private Float Price;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        this.ISBN = iSBN;
    }

    public Integer getVersionID() {
        return VersionID;
    }

    public void setVersionID(Integer versionID) {
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

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        this.Rating = rating;
    }

    public BookFormat getFormat() {
        return Format;
    }

    public void setFormat(BookFormat format) {
        this.Format = format;
    }

    public Float getPrice() {
        return Price;
    }
    public void setPrice(Float price) {
        this.Price = price;
    }
}
