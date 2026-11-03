package dao;

import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public void addMember(Member member) {
        String sql = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.executeUpdate();
            System.out.println("Member added successfully.");
        } catch (SQLException e) {
            System.out.println("Could not add member: " + e.getMessage());
        }
    }

    public List<Member> viewAllMembers() {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY member_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapMember(rs));
            }
        } catch (SQLException e) {
            System.out.println("Could not load members: " + e.getMessage());
        }
        return list;
    }

    public Member searchById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapMember(rs);
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
        return null;
    }

    public void updateMember(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, phone = ? WHERE member_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.setInt(4, member.getMemberId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Member updated.");
            } else {
                System.out.println("No member found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("Could not update member: " + e.getMessage());
        }
    }

    public void deleteMember(int memberId) {
        String sql = "DELETE FROM members WHERE member_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Member deleted.");
            } else {
                System.out.println("No member found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("Could not delete member. They may have issued books: " + e.getMessage());
        }
    }

    private Member mapMember(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setMemberId(rs.getInt("member_id"));
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        m.setPhone(rs.getString("phone"));
        return m;
    }
}
