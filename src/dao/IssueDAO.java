package dao;

import model.Book;
import model.IssueRecord;
import model.Member;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {
    private BookDAO bookDAO = new BookDAO();
    private MemberDAO memberDAO = new MemberDAO();

    public void issueBook(int bookId, int memberId) {
        Book book = bookDAO.searchById(bookId);
        if (book == null) {
            System.out.println("Book ID not found.");
            return;
        }
        if (book.getQuantity() <= 0) {
            System.out.println("This book is not available.");
            return;
        }

        Member member = memberDAO.searchById(memberId);
        if (member == null) {
            System.out.println("Member ID not found.");
            return;
        }

        String insert = "INSERT INTO issue_records (book_id, member_id, issue_date, status) VALUES (?, ?, ?, 'ISSUED')";
        String updateQty = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(insert);
                 PreparedStatement ps2 = con.prepareStatement(updateQty)) {
                ps1.setInt(1, bookId);
                ps1.setInt(2, memberId);
                ps1.setDate(3, Date.valueOf(LocalDate.now()));
                ps1.executeUpdate();

                ps2.setInt(1, bookId);
                ps2.executeUpdate();

                con.commit();
                System.out.println("Book issued to " + member.getName() + ".");
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Issue failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void returnBook(int issueId) {
        String find = "SELECT book_id, status FROM issue_records WHERE issue_id = ?";
        String updateIssue = "UPDATE issue_records SET return_date = ?, status = 'RETURNED' WHERE issue_id = ?";
        String updateQty = "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psFind = con.prepareStatement(find)) {
                psFind.setInt(1, issueId);
                ResultSet rs = psFind.executeQuery();
                if (!rs.next()) {
                    System.out.println("Issue ID not found.");
                    con.rollback();
                    return;
                }
                String status = rs.getString("status");
                int bookId = rs.getInt("book_id");
                if ("RETURNED".equals(status)) {
                    System.out.println("This book is already returned.");
                    con.rollback();
                    return;
                }

                try (PreparedStatement ps1 = con.prepareStatement(updateIssue);
                     PreparedStatement ps2 = con.prepareStatement(updateQty)) {
                    ps1.setDate(1, Date.valueOf(LocalDate.now()));
                    ps1.setInt(2, issueId);
                    ps1.executeUpdate();

                    ps2.setInt(1, bookId);
                    ps2.executeUpdate();
                }

                con.commit();
                System.out.println("Book returned successfully.");
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Return failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public List<IssueRecord> viewIssuedBooks() {
        List<IssueRecord> list = new ArrayList<>();
        String sql = "SELECT i.issue_id, i.book_id, i.member_id, b.title, m.name, "
                + "i.issue_date, i.return_date, i.status "
                + "FROM issue_records i "
                + "JOIN books b ON i.book_id = b.book_id "
                + "JOIN members m ON i.member_id = m.member_id "
                + "WHERE i.status = 'ISSUED' "
                + "ORDER BY i.issue_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRecord(rs));
            }
        } catch (SQLException e) {
            System.out.println("Could not load issued books: " + e.getMessage());
        }
        return list;
    }

    public List<IssueRecord> viewAllIssueHistory() {
        List<IssueRecord> list = new ArrayList<>();
        String sql = "SELECT i.issue_id, i.book_id, i.member_id, b.title, m.name, "
                + "i.issue_date, i.return_date, i.status "
                + "FROM issue_records i "
                + "JOIN books b ON i.book_id = b.book_id "
                + "JOIN members m ON i.member_id = m.member_id "
                + "ORDER BY i.issue_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRecord(rs));
            }
        } catch (SQLException e) {
            System.out.println("Could not load issue history: " + e.getMessage());
        }
        return list;
    }

    private IssueRecord mapRecord(ResultSet rs) throws SQLException {
        IssueRecord rec = new IssueRecord();
        rec.setIssueId(rs.getInt("issue_id"));
        rec.setBookId(rs.getInt("book_id"));
        rec.setMemberId(rs.getInt("member_id"));
        rec.setBookTitle(rs.getString("title"));
        rec.setMemberName(rs.getString("name"));
        rec.setIssueDate(rs.getDate("issue_date"));
        rec.setReturnDate(rs.getDate("return_date"));
        rec.setStatus(rs.getString("status"));
        return rec;
    }
}
