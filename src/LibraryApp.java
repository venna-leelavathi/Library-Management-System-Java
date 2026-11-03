import dao.BookDAO;
import dao.IssueDAO;
import dao.MemberDAO;
import model.Book;
import model.IssueRecord;
import model.Member;
import util.InputHelper;

import java.util.List;

// Main class - menu driven console app
public class LibraryApp {
    private static BookDAO bookDAO = new BookDAO();
    private static MemberDAO memberDAO = new MemberDAO();
    private static IssueDAO issueDAO = new IssueDAO();

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("   Library Management System");
        System.out.println("======================================");

        int choice = 0;
        while (choice != 4) {
            System.out.println();
            System.out.println("1. Book Menu");
            System.out.println("2. Member Menu");
            System.out.println("3. Issue / Return Menu");
            System.out.println("4. Exit");
            choice = InputHelper.readInt("Enter choice: ");

            if (choice == 1) {
                bookMenu();
            } else if (choice == 2) {
                memberMenu();
            } else if (choice == 3) {
                issueMenu();
            } else if (choice == 4) {
                System.out.println("Thank you. Bye!");
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void bookMenu() {
        int choice = 0;
        while (choice != 7) {
            System.out.println();
            System.out.println("--- Book Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Check Availability");
            System.out.println("7. Back");
            choice = InputHelper.readInt("Enter choice: ");

            if (choice == 1) {
                addBook();
            } else if (choice == 2) {
                printBooks(bookDAO.viewAllBooks());
            } else if (choice == 3) {
                searchBook();
            } else if (choice == 4) {
                updateBook();
            } else if (choice == 5) {
                int id = InputHelper.readInt("Book ID to delete: ");
                bookDAO.deleteBook(id);
            } else if (choice == 6) {
                int id = InputHelper.readInt("Book ID: ");
                bookDAO.checkAvailability(id);
            } else if (choice != 7) {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void memberMenu() {
        int choice = 0;
        while (choice != 6) {
            System.out.println();
            System.out.println("--- Member Menu ---");
            System.out.println("1. Add Member");
            System.out.println("2. View Members");
            System.out.println("3. Search Member");
            System.out.println("4. Update Member");
            System.out.println("5. Delete Member");
            System.out.println("6. Back");
            choice = InputHelper.readInt("Enter choice: ");

            if (choice == 1) {
                addMember();
            } else if (choice == 2) {
                List<Member> members = memberDAO.viewAllMembers();
                if (members.isEmpty()) {
                    System.out.println("No members found.");
                } else {
                    System.out.println("ID | Name | Email | Phone");
                    for (Member m : members) {
                        System.out.println(m);
                    }
                }
            } else if (choice == 3) {
                int id = InputHelper.readInt("Member ID: ");
                Member m = memberDAO.searchById(id);
                if (m == null) {
                    System.out.println("Member not found.");
                } else {
                    System.out.println(m);
                }
            } else if (choice == 4) {
                updateMember();
            } else if (choice == 5) {
                int id = InputHelper.readInt("Member ID to delete: ");
                memberDAO.deleteMember(id);
            } else if (choice != 6) {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void issueMenu() {
        int choice = 0;
        while (choice != 6) {
            System.out.println();
            System.out.println("--- Issue / Return Menu ---");
            System.out.println("1. Issue Book");
            System.out.println("2. Return Book");
            System.out.println("3. Track Issued Books");
            System.out.println("4. View Available Books");
            System.out.println("5. Issue History");
            System.out.println("6. Back");
            choice = InputHelper.readInt("Enter choice: ");

            if (choice == 1) {
                int bookId = InputHelper.readInt("Book ID: ");
                int memberId = InputHelper.readInt("Member ID: ");
                issueDAO.issueBook(bookId, memberId);
            } else if (choice == 2) {
                int issueId = InputHelper.readInt("Issue ID: ");
                issueDAO.returnBook(issueId);
            } else if (choice == 3) {
                List<IssueRecord> issued = issueDAO.viewIssuedBooks();
                if (issued.isEmpty()) {
                    System.out.println("No books are currently issued.");
                } else {
                    for (IssueRecord rec : issued) {
                        System.out.println(rec);
                    }
                }
            } else if (choice == 4) {
                printBooks(bookDAO.viewAvailableBooks());
            } else if (choice == 5) {
                List<IssueRecord> all = issueDAO.viewAllIssueHistory();
                if (all.isEmpty()) {
                    System.out.println("No issue records yet.");
                } else {
                    for (IssueRecord rec : all) {
                        System.out.println(rec);
                    }
                }
            } else if (choice != 6) {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void addBook() {
        String title = InputHelper.readLine("Title: ");
        String author = InputHelper.readLine("Author: ");
        String category = InputHelper.readLine("Category: ");
        int qty = InputHelper.readInt("Quantity: ");

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Title and author cannot be empty.");
            return;
        }
        if (qty < 1) {
            System.out.println("Quantity must be at least 1.");
            return;
        }

        bookDAO.addBook(new Book(title, author, category, qty));
    }

    private static void searchBook() {
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Title");
        int opt = InputHelper.readInt("Choice: ");
        if (opt == 1) {
            int id = InputHelper.readInt("Book ID: ");
            Book b = bookDAO.searchById(id);
            if (b == null) {
                System.out.println("Book not found.");
            } else {
                System.out.println(b);
            }
        } else if (opt == 2) {
            String title = InputHelper.readLine("Title: ");
            printBooks(bookDAO.searchByTitle(title));
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void updateBook() {
        int id = InputHelper.readInt("Book ID to update: ");
        Book old = bookDAO.searchById(id);
        if (old == null) {
            System.out.println("Book not found.");
            return;
        }
        System.out.println("Current: " + old);
        String title = InputHelper.readLine("New title: ");
        String author = InputHelper.readLine("New author: ");
        String category = InputHelper.readLine("New category: ");
        int qty = InputHelper.readInt("New quantity: ");
        if (title.isEmpty() || author.isEmpty() || qty < 0) {
            System.out.println("Invalid data.");
            return;
        }
        bookDAO.updateBook(new Book(id, title, author, category, qty));
    }

    private static void addMember() {
        String name = InputHelper.readLine("Name: ");
        String email = InputHelper.readLine("Email: ");
        String phone = InputHelper.readLine("Phone: ");
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (!email.isEmpty() && !email.contains("@")) {
            System.out.println("Please enter a valid email.");
            return;
        }
        memberDAO.addMember(new Member(name, email, phone));
    }

    private static void updateMember() {
        int id = InputHelper.readInt("Member ID to update: ");
        Member old = memberDAO.searchById(id);
        if (old == null) {
            System.out.println("Member not found.");
            return;
        }
        System.out.println("Current: " + old);
        String name = InputHelper.readLine("New name: ");
        String email = InputHelper.readLine("New email: ");
        String phone = InputHelper.readLine("New phone: ");
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        memberDAO.updateMember(new Member(id, name, email, phone));
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("ID | Title | Author | Category | Qty");
        for (Book b : books) {
            System.out.println(b);
        }
    }
}
