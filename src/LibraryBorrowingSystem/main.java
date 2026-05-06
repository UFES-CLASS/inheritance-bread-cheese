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

import java.util.Scanner;

/**
 * Entry point for the Library Borrowing System.
 *
 * This class uses a single, flat menu-driven interface for the user 
 * to interact with the system without complex nested loops.
 */
public class main {

    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("  Library Borrowing System");
        System.out.println("============================================");
        System.out.println("  Author      : masjohncook X Bread & Cheese");
        System.out.println("  Version     : 0.0.2");
        System.out.println("  Copyright   : (C) Copyright 2026");
        System.out.println("============================================\n");

        Scanner sc = new Scanner(System.in);
        Librarian librarian = new Librarian("L001", "Mrs. Smith");

        System.out.println("=== Welcome, " + librarian.getName() + "! ===\n");

        int choice = -1;

        // Flat menu loop — runs until user enters 0
        while (choice != 0) {

            System.out.println("\n============================================");
            System.out.println("          LIBRARY BORROWING SYSTEM         ");
            System.out.println("============================================");
            System.out.println(" --- Book Management ---");
            System.out.println("  1. Add Book");
            System.out.println("  2. Remove Book");
            System.out.println("  3. View All Books");
            System.out.println(" --- Multimedia Management ---");
            System.out.println("  4. Add Multimedia");
            System.out.println("  5. Remove Multimedia");
            System.out.println("  6. View All Multimedia");
            System.out.println(" --- Member Management ---");
            System.out.println("  7. Register Member");
            System.out.println("  8. Remove Member");
            System.out.println("  9. View All Members");
            System.out.println(" --- Borrow & Return ---");
            System.out.println(" 10. Borrow Item (Book/Multimedia)");
            System.out.println(" 11. Return Item (Book/Multimedia)");
            System.out.println(" 12. Search Catalog by Keyword");
            System.out.println(" 13. View All Borrow Records");
            System.out.println("--------------------------------------------");
            System.out.println("  0. Exit");
            System.out.println("============================================");
            System.out.print("Enter choice: ");

            // Inline validation so it doesn't look like 0.0.1's helper method
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Clear the buffer
            } else {
                sc.nextLine(); // Discard the garbage input
                System.out.println("  [ERROR] Please enter a valid number.");
                continue; // Restart the loop immediately
            }

            switch (choice) {
                // ── BOOKS ──
                case 1:
                    System.out.print("  Book ID  : ");
                    String bId = sc.nextLine().trim();
                    System.out.print("  Title    : ");
                    String bTitle = sc.nextLine().trim();
                    System.out.print("  Author   : ");
                    String bAuthor = sc.nextLine().trim();
                    
                    if (bId.isEmpty() || bTitle.isEmpty() || bAuthor.isEmpty()) {
                        System.out.println("  [FAILED] All fields required.");
                    } else {
                        librarian.addBook(bId, bTitle, bAuthor);
                    }
                    break;
                case 2:
                    librarian.displayCatalog();
                    System.out.print("  Enter Book ID to remove: ");
                    String rBookId = sc.nextLine().trim();
                    librarian.removeBook(rBookId);
                    break;
                case 3:
                    librarian.displayCatalog();
                    break;

                // ── MULTIMEDIA ──
                case 4:
                    System.out.print("  Item ID  : ");
                    String mId = sc.nextLine().trim();
                    System.out.print("  Title    : ");
                    String mTitle = sc.nextLine().trim();
                    System.out.print("  Type     : ");
                    String mType = sc.nextLine().trim();
                    
                    if (mId.isEmpty() || mTitle.isEmpty() || mType.isEmpty()) {
                        System.out.println("  [FAILED] All fields required.");
                    } else {
                        librarian.addMultimedia(mId, mTitle, mType);
                    }
                    break;
                case 5:
                    librarian.displayMultimedia();
                    System.out.print("  Enter Item ID to remove: ");
                    String rItemId = sc.nextLine().trim();
                    librarian.removeMultimedia(rItemId);
                    break;
                case 6:
                    librarian.displayMultimedia();
                    break;

                // ── MEMBERS ──
                case 7:
                    System.out.print("  Member ID : ");
                    String memId = sc.nextLine().trim();
                    System.out.print("  Name      : ");
                    String memName = sc.nextLine().trim();
                    
                    if (memId.isEmpty() || memName.isEmpty()) {
                        System.out.println("  [FAILED] All fields required.");
                    } else {
                        librarian.registerMember(memId, memName);
                    }
                    break;
                case 8:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID to remove: ");
                    String rMemId = sc.nextLine().trim();
                    librarian.removeMember(rMemId);
                    break;
                case 9:
                    librarian.displayMembers();
                    break;

                // ── TRANSACTIONS ──
                case 10:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String borrowMemId = sc.nextLine().trim();
                    Member borrower = librarian.findMemberById(borrowMemId);

                    if (borrower == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else {
                        librarian.displayAllItems();
                        System.out.print("  Enter Item ID to Borrow: ");
                        String borrowItemId = sc.nextLine().trim();
                        LibraryItem itemToBorrow = librarian.findItemById(borrowItemId);

                        if (itemToBorrow == null) {
                            System.out.println("  [FAILED] Item not found.");
                        } else {
                            if (borrower.borrowItem(itemToBorrow)) {
                                librarian.recordBorrow(borrower, itemToBorrow);
                            }
                        }
                    }
                    break;

                case 11:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String retMemId = sc.nextLine().trim();
                    Member returner = librarian.findMemberById(retMemId);

                    if (returner == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else if (returner.getBorrowCount() == 0) {
                        System.out.println("  This member has no items to return.");
                    } else {
                        System.out.println("  Borrowed Items:");
                        LibraryItem[] borrowed = returner.getBorrowedItems();
                        for (int i = 0; i < returner.getBorrowCount(); i++) {
                            System.out.println("    " + borrowed[i]);
                        }
                        System.out.print("  Enter Item ID to Return: ");
                        String retItemId = sc.nextLine().trim();
                        LibraryItem itemToReturn = librarian.findItemById(retItemId);

                        if (itemToReturn == null) {
                            System.out.println("  [FAILED] Item not found in system.");
                        } else {
                            if (returner.returnItem(itemToReturn)) {
                                librarian.recordReturn(returner, itemToReturn);
                            }
                        }
                    }
                    break;

                case 12:
                    System.out.print("  Enter Search Keyword: ");
                    String keyword = sc.nextLine().trim();
                    
                    // We can use a temporary dummy member to utilize the search logic cleanly
                    Member tempSearcher = new Member("TEMP", "Searcher");
                    tempSearcher.searchItem(librarian.getAllItems(), librarian.getAllItemsCount(), keyword);
                    break;

                case 13:
                    librarian.displayAllRecords();
                    break;

                case 0:
                    System.out.println("\n  Shutting down system. Goodbye!");
                    break;

                default:
                    System.out.println("  [ERROR] Unrecognized menu option.");
            }
        }
        
        sc.close();
    }
}