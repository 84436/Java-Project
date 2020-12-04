package natic.book;

/**
 * Specific constants (and their string representations) for book genre, rating and format.
 */

// RULES (aka. "What to keep in mind while editing this file")
// 1. Only append: Keep the order of what's already in here.
// 2. Punctuate: End ALL lines with a colon, and keep the semicolon in a standalone line.

public class BookEnums {
    // Book genres (adapted from the subject list in Amazon Advanced Books Search)
    public enum BookGenre {
        //#region: Values
        EDUCATION("Education"),
        SCIENCE("Science"),
        REFERENCES("References"),
        BUSINESS("Business & Finance"),
        LIFESTYLE("Health & Lifestyle"),
        BIO("Biography"),
        RELIGIONS("Religions"),
        POLITICS("Politics"),
        CHILDREN("For Children"),
        ROMANCE("Romance"),
        FICTION("Fiction & Fantasy"),
        WIBU("Japanese Literature"),
        ;
        //#endregion

        //#region: String enum magic
        private final String fullname;
        BookGenre(String fullname) { this.fullname = fullname; }
        public String toString() { return this.fullname; }
        //#endregion
    }
    
    public enum BookRating {
        //#region: Values
        EVERYONE("Everyone"),
        CHILDREN("Children"),
        PRETEEN("Pre-teen"),
        R18("18+"),
        ;
        //#endregion

        //#region: String enum magic
        private final String fullname;
        BookRating(String fullname) { this.fullname = fullname; }
        public String toString() { return this.fullname; }
        //#endregion
    }
    
    public enum BookFormat {
        //#region: Values
        PAPER("Paperback"),
        HARD("Hardcover"),
        EBOOK("Ebook"),
        AUDIO("Audiobook"),
        ;
        //#endregion

        //#region: String enum magic
        private final String fullname;
        BookFormat(String fullname) { this.fullname = fullname; }
        public String toString() { return this.fullname; }
        //#endregion
    }   
}
