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
 * Represents a Person identity.
 *
 * represent name and Id of Person who borrow the book.
 *
 * Attributes:
 *   - Id            : unique identifier for the Person
 *   - name          : full name of the Person
 */
public class Person {
    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

     public String getInfo() {
        // shows ID and name
        return "Person[" + id + "] " + name;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /** Returns the unique ID of this person. */
    public String getId() { 
        return id; 
    }

    /** Returns the full name of this person. */
    public String getName() { 
        return name; 
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    /** Updates the ID of this person. */
    public void setId(String id) { 
        this.id = id; 
    }

    /** Updates the name of this person. */
    public void setName(String name) { 
        this.name = name; 
    }

    public String toString() {
        return getInfo();
    }
}