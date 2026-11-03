package dao;

import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, category, quantity) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());
            ps.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println("Could not add book: " + e.getMessage());
        }
    }

    public List<Book> viewAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY book_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapBook(rs));
            }
        } catch (SQLException e) {
            System.out.println("Could not load books: " + e.getMessage());
        }
        return list;
    }

    public Book searchById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapBook(rs);
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
        return null;
    }

    public List<Book> searchByTitle(String title) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapBook(rs));
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
        return list;
    }

    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, category = ?, quantity = ? WHERE book_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());
            ps.setInt(5, book.getBookId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book updated.");
            } else {
                System.out.println("No book found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("Could not update book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book deleted.");
            } else {
                System.out.println("No book found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("Could not delete book. It may be issued to someone: " + e.getMessage());
        }
    }

    public void checkAvailability(int bookId) {
        Book book = searchById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (book.getQuantity() > 0) {
            System.out.println("Available. Copies left: " + book.getQuantity());
        } else {
            System.out.println("Not available right now. All copies are issued.");
        }
    }

    public List<Book> viewAvailableBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE quantity > 0 ORDER BY book_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapBook(rs));
            }
        } catch (SQLException e) {
            System.out.println("Could not load available books: " + e.getMessage());
        }
        return list;
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setBookId(rs.getInt("book_id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setCategory(rs.getString("category"));
        b.setQuantity(rs.getInt("quantity"));
        return b;
    }
}
