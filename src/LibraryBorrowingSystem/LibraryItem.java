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

public abstract class LibraryItem {
    protected String itemId;
    protected String title;
    protected boolean available;

    public LibraryItem(String itemId, String title) {
        this.itemId = itemId;
        this.title = title;
        this.available = true;
    }

    public String getInfo() {
        // Determine the availability status as a word using if-else
        String status;
        if (available == true) {
            status = "Available";
        } else {
            status = "Borrowed";
        }

        // Base implementation — shows item ID, title, and status
        return "[" + itemId + "] \"" + title + "\" (" + status + ")";
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setItemId (String itemId) {
        this.itemId = itemId;
    } 

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return getInfo();
    }
}