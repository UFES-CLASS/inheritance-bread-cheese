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


public class Multimedia extends LibraryItem {
    private String type;
    private String duration;

    public Multimedia(String itemId, String title, String type, String duration) {
        super(itemId, title);
        this.type = type;
        this.duration = duration;
    }

    public static Multimedia[] getInitialMultimedia() {
        // Create an array that can hold 3 Multimedia objects
        Multimedia[] initial = new Multimedia[3];

        // Fill each slot with a pre-defined multimedia item
        initial[0] = new Multimedia("MM001", "Inception",          "DVD",       "148 min");
        initial[1] = new Multimedia("MM002", "Dark Side of Moon",  "CD",        "43 min");
        initial[2] = new Multimedia("MM003", "Sapiens Audiobook",  "Audiobook", "15 hrs");

        // Return the completed array
        return initial;
    }

 public String getInfo() {
        // Determine the availability status using if-else
        String status;
        if (available == true) {
            status = "Available";
        } else {
            status = "Borrowed";
        }

        // Build a string that includes the multimedia-specific fields
        // available, itemId, title are accessed directly because they are protected in LibraryItem
        return "[" + itemId + "] \"" + title + "\""
                + " | Type: " + type
                + " | Duration: " + duration
                + " (" + status + ")";
    }

    public String toString() {
        return getInfo();
    }

    public String getType() {
        return type;
    }

    public String getDuration() {
        return duration;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}