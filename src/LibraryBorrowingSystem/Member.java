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
 * Represents a library member who can borrow, return, and search for items.
 *
 * Each member has a unique ID and a name. A member can hold up to MAX_BORROW
 * items at the same time. The items currently borrowed are stored in an array.
 * This class also provides the initial member data used at system startup.
 *
 * Attributes:
 *   - memberId      : unique identifier for the member (e.g. "M001")
 *   - name          : full name of the member
 *   - borrowedItems : array of items (Books/Multimedia) the member is currently borrowing
 *   - borrowCount   : number of items currently borrowed
 */
public class Member extends Person {

    // Array to hold the items the member has borrowed
    private LibraryItem[] borrowedItems;

    // FIXED: Changed 'borrowcount' to 'borrowCount' to match Java's strict case sensitivity.
    private int borrowCount;

    private static final int MAX_BORROW = 5;

    /**
     * Creates a new member with the given ID and name.
     * The borrowed items array is initialized empty.
     *
     * @param memberId unique member identifier
     * @param name     full name of the member
     */
    public Member(String memberId, String name) {
        // Call the parent Person
        super(memberId, name);

        // FIXED: Instantiated as LibraryItem[] to support polymorphism (both Books and Multimedia).
        this.borrowedItems = new LibraryItem[MAX_BORROW];

        // No items are borrowed yet, so the count starts at 0
        this.borrowCount = 0;
    }

    /**
     * Returns the pre-defined initial member data for the library.
     * Keeping this data here ensures the Member class owns its own defaults.
     *
     * @return array of Member objects pre-filled with default members
     */
    public static Member[] getInitialMembers() {
        // Create an array that can hold 3 Member objects
        Member[] initial = new Member[3];

        // Fill each slot with a pre-defined member using their ID and name
        initial[0] = new Member("M001", "Alice");
        initial[1] = new Member("M002", "Bob");
        initial[2] = new Member("M003", "Charlie");

        // Return the completed array to whoever called this method
        return initial;
    }

    /**
     * Borrows a library item for this member.
     * 
     * @param item the LibraryItem to borrow
     * @return true if the borrow was successful, false otherwise
     */
    public boolean borrowItem(LibraryItem item) {
        // Check if the item is available — if not, reject the request
        if (!item.isAvailable()) {
            System.out.println("  [FAILED] \"" + item.getTitle() + "\" is currently not available.");
            return false; // Stop here and report failure
        }

        // Check if this member has already reached their borrowing limit
        if (borrowCount >= MAX_BORROW) {
            System.out.println("  [FAILED] " + name + " has reached the borrow limit (" + MAX_BORROW + ").");
            return false; // Stop here and report failure
        }

        // Add the Item to the member's borrowedItems array at the next open slot
        // borrowCount++ adds the item at index borrowCount, then increases borrowCount by 1
        borrowedItems[borrowCount++] = item;

        // Mark the item as no longer available in the system
        item.setAvailable(false);

        // Confirm the successful borrow to the console
        System.out.println("  [SUCCESS] " + name + " borrowed \"" + item.getTitle() + "\".");

        // Return true to indicate the borrow was successful
        return true;
    }

    /**
     * Returns a borrowed Item back to the library.
     * Fails if this member does not currently have the given Item.
     *
     * @param item the LibraryItem to return
     * @return true if the return was successful, false otherwise
     */
    public boolean returnItem(LibraryItem item) {
        // Loop through all items this member is currently borrowing
        for (int i = 0; i < borrowCount; i++) {

            // Check if this slot has an Item and its ID matches the Item to return
            if (borrowedItems[i] != null
                    && borrowedItems[i].getItemId().equals(item.getItemId())) {

                // Mark the item as available again in the system
                item.setAvailable(true);

                // FIXED: Changed the phantom variable 'borrowedBooks' back to 'borrowedItems'.
                // Remove the item from the array by replacing it with the last entry
                // --borrowCount decreases the count first, then uses that value as the index
                borrowedItems[i] = borrowedItems[--borrowCount];

                // Clear the last slot to avoid keeping a duplicate reference
                borrowedItems[borrowCount] = null;

                // Confirm the successful return to the console
                System.out.println("  [SUCCESS] " + name + " returned \"" + item.getTitle() + "\".");

                // Return true to indicate the return was successful
                return true;
            }
        }

        // If the loop finishes without finding the item, report failure
        System.out.println("  [FAILED] " + name + " does not have \"" + item.getTitle() + "\".");
        return false;
    }

    /**
     * Searches the library catalog for items whose title contains the given keyword.
     * The search is case-insensitive. All matching items are printed to the console.
     *  
     * @param catalog     the full array of LibraryItems in the library
     * @param catalogSize the number of valid items in the catalog array
     * @param keyword     the search keyword to match against item titles
     */
    public void searchItem(LibraryItem[] catalog, int catalogSize, String keyword) {
        // Print a header showing what keyword is being searched
        System.out.println("  Search results for \"" + keyword + "\":");

        // found tracks whether at least one matching item was found
        boolean found = false;

        // Loop through every valid item in the catalog
        for (int i = 0; i < catalogSize; i++) {

            // Convert both strings to lowercase so the search is case-insensitive
            // contains() checks if the title includes the keyword anywhere inside it
            if (catalog[i] != null
                    && catalog[i].getTitle().toLowerCase().contains(keyword.toLowerCase())) {

                // Print the matching item's details using its toString() method
                System.out.println("    -> " + catalog[i]);

                // Mark that we found at least one result
                found = true;
            }
        }

        // If no match was found after checking all items, print a message
        if (!found) {
            System.out.println("    No items found matching \"" + keyword + "\".");
        }
    }

    /**
     * OVERLOADED METHOD (without keyword) —
     * Lists all items currently available to borrow from the library.
     * This is the second version of searchItem — it takes NO keyword.
     * Instead of filtering by title, it shows every item that is available.
     *
     * @param catalog     the full array of LibraryItems in the library
     * @param catalogSize the number of valid items in the catalog array
     */
    public void searchItem(LibraryItem[] catalog, int catalogSize) {
        // Print a header for the browse view
        System.out.println("  All available items in the library:");

        // found tracks whether at least one available item was found
        boolean found = false;

        // Loop through every valid item in the catalog
        for (int i = 0; i < catalogSize; i++) {

            // Only show items that are currently available to borrow
            if (catalog[i] != null && catalog[i].isAvailable()) {

                // Print the item's details using its toString() method
                System.out.println("    -> " + catalog[i]);

                // Mark that we found at least one available item
                found = true;
            }
        }

        // If no available items were found, print a message
        if (!found) {
            System.out.println("    No items are currently available.");
        }
    }

    /**
     * Returns a detailed description of this member.
     * OVERRIDES the base getInfo() in Person with member-specific details.
     *
     * @return a formatted string with member ID, name, and borrow count
     */
    @Override
    public String getInfo() {
        // Build a string that includes the member-specific borrow count
        // id and name are accessed directly because they are protected in Person
        return "Member[" + id + "] " + name + " (borrowing: " + borrowCount + " item(s))";
    }

    /**
     * Returns a readable summary of this member.
     * Calls the overridden getInfo() method from this class.
     */
    @Override
    public String toString() {
        // Delegates to getInfo() — which is the overridden version in Member
        return getInfo();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /**
     * Returns the member ID.
     * Delegates to getId() inherited from Person.
     */
    public String getMemberId() { return id; }

    /** Returns the array of items currently borrowed by this member. */
    public LibraryItem[] getBorrowedItems() { return borrowedItems; }

    /** Returns the number of items currently borrowed by this member. */
    public int getBorrowCount() { return borrowCount; }

    // ── Setters ───────────────────────────────────────────────────────────────

    /** Updates the member ID. Delegates to Person's id field. */
    public void setMemberId(String memberId) { this.id = memberId; }
}