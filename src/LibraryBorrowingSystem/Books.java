/**
 * @author      masjohncook X Bread & Cheese
 * @version     0.0.2
 * @copyright   (C) Copyright 2026
 * @license     None
 * @maintainer  masjohncook X Bread & Cheese
 * @email       mas.john.cook@gmail.com
 * @status      None
 */
package LibraryBorrowingSystem;

/**
 * Represents a book in the library collection.
 *
 * This class stores the details of a single book including its ID, title,
 * author, and availability status. It also provides the initial book data
 * used to pre-populate the library catalog on startup.
 *
 * Attributes:
 *   - author    : the name of the book's author
 *   - genre : the genre/category of the book
 */
public class Books extends LibraryItem {

    // private means only this class can access it directly
    // genre stores the genre of the book
    private String genre;

    // author stores the name of the person who wrote the book
    private String author;

    /**
     * Creates a new book with all details provided.
     * Calls the LibraryItem constructor (super) to set itemId, title, available.
     *
     * @param bookId unique book identifier (e.g. "B001")
     * @param title  title of the book
     * @param author author of the book
     * @param genre  genre/category of the book
     */
    public Books(String bookId, String title, String author, String genre) {
        // Call the parent
        super(bookId, title);

        // Assign the given title to this object's title attribute
        this.genre = genre;

        // Assign the given author to this object's author attribute
        this.author = author;

    }

    /**
     * Returns the pre-defined initial book data for the library.
     * This method keeps the starting data inside the Books class
     * so each class is responsible for its own data.
     *
     * @return array of Books objects pre-filled with default catalog entries
     */
    public static Books[] getInitialBooks() {
        // Create an array that can hold 5 Books objects
        Books[] initial = new Books[5];

        // Fill each slot with a pre-defined book using its ID, title, and author
        initial[0] = new Books("B001", "The Great Gatsby",       "F. Scott Fitzgerald", "Classic");
        initial[1] = new Books("B002", "To Kill a Mockingbird",  "Harper Lee",  "Fiction");
        initial[2] = new Books("B003", "1984",                   "George Orwell", "Dystopian");
        initial[3] = new Books("B004", "Brave New World",        "Aldous Huxley", "Sci-Fi");
        initial[4] = new Books("B005", "The Catcher in the Rye", "J.D. Salinger", "Fiction");

        // Return the completed array to whoever called this method
        return initial;
    }

    // Check if the book available or no
    public String getInfo() {
        // Determine the availability status as a word using if-else
        String status;
        if (available == true)
            status = "available";
        else {
            status = "borrowed";
        }

        // call the specific of book
        return "[" + itemId + "] \"" + title + "\" by " + author
                + " | Genre: " + genre
                + " (" + status + ")";
    }

        // Calls the overridden getInfo() method from this class.
       public String toString() {
        // Delegates to getInfo()
        return getInfo();
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    // Getters allow other classes to read private attributes safely
    // without being able to change them directly

    /** Returns the unique book ID. */
    public String getBookId() { 
        return itemId; 
    }

    /** Returns the title of the book. */
    public String getGenre() { 
        return genre; 
    }

    /** Returns the author of the book. */
    public String getAuthor() { 
        return author; 
    }

    // ── Setters ───────────────────────────────────────────────────────────────
    // Setters allow other classes to update private attributes in a controlled way

    /** Updates the book ID. */
    public void setBookId(String bookId) { 
        this.itemId = bookId; 
    }

    /** Updates the title of the book. */
    public void setGenre(String genre) { 
        this.genre = genre; 
    }

    /** Updates the author of the book. */
    public void setAuthor(String author) { 
        this.author = author; 
    }
}