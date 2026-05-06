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
 * Represents the librarian who manages the library system.
 * Inherits from Person — gains id and name.
 *
 * The Librarian is responsible for managing the book catalog,
 * the multimedia catalog, and the list of registered members.
 * It also keeps a full record of all borrow and return transactions.
 * On creation, initial data is automatically loaded.
 *
 * Associations:
 *   - catalog[]      : array of all Books in the library
 *   - multimedia[]   : array of all Multimedia items in the library
 *   - members[]      : array of all registered Members
 *   - borrowRecords[]: array of all BorrowRecord transactions
 */
public class Librarian extends Person {

    // Arrays to store system data
    private Books[] catalog;
    private int catalogCount;

    private Multimedia[] multimedia;
    private int multimediaCount;

    private Member[] members;
    private int memberCount;

    private BorrowRecord[] borrowRecords;
    private int recordCount;

    // Maximum capacities
    private static final int MAX_BOOKS      = 100;
    private static final int MAX_MULTIMEDIA = 50;
    private static final int MAX_MEMBERS    = 50;
    private static final int MAX_RECORDS    = 200;

    /**
     * Creates a new Librarian and automatically loads initial data.
     * Calls the Person constructor (super) to set id and name.
     * 
     * @param librarianId unique identifier for the librarian
     * @param name        full name of the librarian
     */
    public Librarian(String librarianId, String name) {
        // Call the parent Person constructor to set this.id and this.name
        super(librarianId, name);

        // Initialize the arrays
        this.catalog = new Books[MAX_BOOKS];
        this.catalogCount = 0;

        this.multimedia = new Multimedia[MAX_MULTIMEDIA];
        this.multimediaCount = 0;

        this.members = new Member[MAX_MEMBERS];
        this.memberCount = 0;

        this.borrowRecords = new BorrowRecord[MAX_RECORDS];
        this.recordCount = 0;

        // Call the helper method to fill the arrays with starting data
        loadInitialData();
    }

    /**
     * Loads the starting data for Books, Multimedia, and Members.
     */
    private void loadInitialData() {
        Books[] initialBooks = Books.getInitialBooks();
        for (int i = 0; i < initialBooks.length; i++) {
            catalog[catalogCount++] = initialBooks[i];
        }

        Multimedia[] initialMultimedia = Multimedia.getInitialMultimedia();
        for (int i = 0; i < initialMultimedia.length; i++) {
            multimedia[multimediaCount++] = initialMultimedia[i];
        }

        Member[] initialMembers = Member.getInitialMembers();
        for (int i = 0; i < initialMembers.length; i++) {
            members[memberCount++] = initialMembers[i];
        }
    }

    // ── Book CRUD ─────────────────────────────────────────────────────────────

    public Books addBook(String bookId, String title, String author, String genre) {
        if (catalogCount >= MAX_BOOKS) {
            System.out.println("  [FAILED] Catalog is full.");
            return null;
        }
        if (findBookById(bookId) != null) {
            System.out.println("  [FAILED] Book ID \"" + bookId + "\" already exists.");
            return null;
        }
        Books book = new Books(bookId, title, author, genre);
        catalog[catalogCount++] = book;
        System.out.println("  [ADDED] " + book);
        return book;
    }

    public Books addBook(String bookId, String title, String author) {
        return addBook(bookId, title, author, "General");
    }

    public boolean removeBook(String bookId) {
        for (int i = 0; i < catalogCount; i++) {
            if (catalog[i].getBookId().equals(bookId)) {
                if (!catalog[i].isAvailable()) {
                    System.out.println("  [FAILED] Cannot remove a book that is currently borrowed.");
                    return false;
                }
                for (int j = i; j < catalogCount - 1; j++) {
                    catalog[j] = catalog[j + 1];
                }
                catalog[--catalogCount] = null;
                System.out.println("  [REMOVED] Book ID \"" + bookId + "\" removed from catalog.");
                return true;
            }
        }
        System.out.println("  [FAILED] Book ID \"" + bookId + "\" not found.");
        return false;
    }

    public boolean updateBook(String bookId, String newTitle, String newAuthor, String newGenre) {
        Books book = findBookById(bookId);
        if (book == null) {
            System.out.println("  [FAILED] Book ID \"" + bookId + "\" not found.");
            return false;
        }
        if (!newTitle.trim().isEmpty()) book.setTitle(newTitle.trim());
        if (!newAuthor.trim().isEmpty()) book.setAuthor(newAuthor.trim());
        if (!newGenre.trim().isEmpty()) book.setGenre(newGenre.trim());
        System.out.println("  [UPDATED] " + book);
        return true;
    }

    public Books findBookById(String bookId) {
        for (int i = 0; i < catalogCount; i++) {
            if (catalog[i].getBookId().equals(bookId)) {
                return catalog[i];
            }
        }
        return null;
    }

    // ── Multimedia CRUD ───────────────────────────────────────────────────────

    public Multimedia addMultimedia(String itemId, String title, String type, String duration) {
        if (multimediaCount >= MAX_MULTIMEDIA) {
            System.out.println("  [FAILED] Multimedia catalog is full.");
            return null;
        }
        if (findMultimediaById(itemId) != null) {
            System.out.println("  [FAILED] Item ID \"" + itemId + "\" already exists.");
            return null;
        }
        Multimedia item = new Multimedia(itemId, title, type, duration);
        multimedia[multimediaCount++] = item;
        System.out.println("  [ADDED] " + item);
        return item;
    }

    public Multimedia addMultimedia(String itemId, String title, String type) {
        return addMultimedia(itemId, title, type, "Unknown");
    }

    public boolean removeMultimedia(String itemId) {
        for (int i = 0; i < multimediaCount; i++) {
            if (multimedia[i].getItemId().equals(itemId)) {
                if (!multimedia[i].isAvailable()) {
                    System.out.println("  [FAILED] Cannot remove an item that is currently borrowed.");
                    return false;
                }
                for (int j = i; j < multimediaCount - 1; j++) {
                    multimedia[j] = multimedia[j + 1];
                }
                multimedia[--multimediaCount] = null;
                System.out.println("  [REMOVED] Item ID \"" + itemId + "\" removed from multimedia catalog.");
                return true;
            }
        }
        System.out.println("  [FAILED] Item ID \"" + itemId + "\" not found.");
        return false;
    }

    public boolean updateMultimedia(String itemId, String newTitle, String newType, String newDuration) {
        Multimedia item = findMultimediaById(itemId);
        if (item == null) {
            System.out.println("  [FAILED] Item ID \"" + itemId + "\" not found.");
            return false;
        }
        if (!newTitle.trim().isEmpty()) item.setTitle(newTitle.trim());
        if (!newType.trim().isEmpty()) item.setType(newType.trim());
        if (!newDuration.trim().isEmpty()) item.setDuration(newDuration.trim());
        System.out.println("  [UPDATED] " + item);
        return true;
    }

    public Multimedia findMultimediaById(String itemId) {
        for (int i = 0; i < multimediaCount; i++) {
            if (multimedia[i].getItemId().equals(itemId)) {
                return multimedia[i];
            }
        }
        return null;
    }

    public LibraryItem findItemById(String itemId) {
        Books book = findBookById(itemId);
        if (book != null) return book;
        
        Multimedia item = findMultimediaById(itemId);
        if (item != null) return item;
        
        return null;
    }

    // ── Member CRUD ───────────────────────────────────────────────────────────

    public Member registerMember(String memberId, String name) {
        if (memberCount >= MAX_MEMBERS) {
            System.out.println("  [FAILED] Member limit reached.");
            return null;
        }
        if (findMemberById(memberId) != null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" already exists.");
            return null;
        }
        Member member = new Member(memberId, name);
        members[memberCount++] = member;
        System.out.println("  [REGISTERED] " + member);
        return member;
    }

    public boolean removeMember(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {
                if (members[i].getBorrowCount() > 0) {
                    System.out.println("  [FAILED] Cannot remove a member who still has borrowed items.");
                    return false;
                }
                for (int j = i; j < memberCount - 1; j++) {
                    members[j] = members[j + 1];
                }
                members[--memberCount] = null;
                System.out.println("  [REMOVED] Member ID \"" + memberId + "\" removed.");
                return true;
            }
        }
        System.out.println("  [FAILED] Member ID \"" + memberId + "\" not found.");
        return false;
    }

    public boolean updateMember(String memberId, String newName) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" not found.");
            return false;
        }
        if (!newName.trim().isEmpty()) {
            member.setName(newName.trim());
            System.out.println("  [UPDATED] " + member);
            return true;
        }
        System.out.println("  [FAILED] New name cannot be empty.");
        return false;
    }

    public Member findMemberById(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {
                return members[i];
            }
        }
        return null;
    }

    // ── Borrow Record Management ──────────────────────────────────────────────

    /**
     * Creates and stores a new borrow record.
     * Uses Polymorphism (LibraryItem) so it works for both Books and Multimedia.
     */
    public void recordBorrow(Member member, LibraryItem item) {
        if (recordCount >= MAX_RECORDS) return;
        String recordId = "REC" + String.format("%03d", recordCount + 1);
        borrowRecords[recordCount++] = new BorrowRecord(recordId, member, item, "2026-05-05");
    }

    /**
     * Marks the open borrow record as returned.
     * Uses Polymorphism (LibraryItem) so it works for both Books and Multimedia.
     */
    public void recordReturn(Member member, LibraryItem item) {
        for (int i = 0; i < recordCount; i++) {
            BorrowRecord r = borrowRecords[i];
            if (!r.isReturned()
                    && r.getMember().getMemberId().equals(member.getMemberId())
                    && r.getItem().getItemId().equals(item.getItemId())) {
                r.setReturnDate("2026-05-06");
                r.setReturned(true);
                return;
            }
        }
    }

    // ── Display Helpers ───────────────────────────────────────────────────────

    public void displayCatalog() {
        System.out.println("  ---- Book Catalog (" + catalogCount + " book(s)) ----");
        if (catalogCount == 0) System.out.println("  (empty)");
        else for (int i = 0; i < catalogCount; i++) System.out.println("  " + catalog[i]);
    }

    public void displayMultimedia() {
        System.out.println("  ---- Multimedia Catalog (" + multimediaCount + " item(s)) ----");
        if (multimediaCount == 0) System.out.println("  (empty)");
        else for (int i = 0; i < multimediaCount; i++) System.out.println("  " + multimedia[i]);
    }

    public void displayAllItems() {
        displayCatalog();
        displayMultimedia();
    }

    public void displayMembers() {
        System.out.println("  ---- Registered Members (" + memberCount + " member(s)) ----");
        if (memberCount == 0) System.out.println("  (empty)");
        else for (int i = 0; i < memberCount; i++) System.out.println("  " + members[i]);
    }

    public void displayAllRecords() {
        System.out.println("  ---- Borrow Records (" + recordCount + " total) ----");
        if (recordCount == 0) System.out.println("  (none yet)");
        else for (int i = 0; i < recordCount; i++) System.out.println("  " + borrowRecords[i]);
    }

    public LibraryItem[] getAllItems() {
        LibraryItem[] all = new LibraryItem[catalogCount + multimediaCount];
        for (int i = 0; i < catalogCount; i++) all[i] = catalog[i];
        for (int i = 0; i < multimediaCount; i++) all[catalogCount + i] = multimedia[i];
        return all;
    }

    public int getAllItemsCount() {
        return catalogCount + multimediaCount;
    }

    // ── Getters, Setters, and Overrides ───────────────────────────────────────

    @Override
    public String getInfo() {
        return "Librarian[" + id + "] " + name; // FIXED: Using inherited 'id' and 'name'
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public String getLibrarianId() { return id; } // FIXED: Returns inherited 'id'
    public void setLibrarianId(String librarianId) { this.id = librarianId; } // FIXED: Sets inherited 'id'

    public Books[] getCatalog() { return catalog; }
    public int getCatalogCount() { return catalogCount; }

    public Multimedia[] getMultimedia() { return multimedia; }
    public int getMultimediaCount() { return multimediaCount; }

    public Member[] getMembers() { return members; }
    public int getMemberCount() { return memberCount; }

    public BorrowRecord[] getBorrowRecords() { return borrowRecords; }
    public int getRecordCount() { return recordCount; }
}