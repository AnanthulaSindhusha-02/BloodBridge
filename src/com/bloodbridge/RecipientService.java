package com.bloodbridge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipientService {

    public void addRecipient(Recipient r) {
        String sql = "INSERT INTO recipients (name, blood_group, age, contact) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getBloodGroup());
            ps.setInt(3, r.getAge());
            ps.setString(4, r.getContact());
            ps.executeUpdate();
            System.out.println("✅ Recipient registered successfully!");
        } catch (SQLException e) {
            System.err.println("Error registering recipient: " + e.getMessage());
        }
    }

    public Recipient getRecipientById(int id) {
        String sql = "SELECT recipient_id, name, blood_group, age, contact FROM recipients WHERE recipient_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Recipient(
                        rs.getInt("recipient_id"),
                        rs.getString("name"),
                        rs.getString("blood_group"),
                        rs.getInt("age"),
                        rs.getString("contact")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching recipient: " + e.getMessage());
        }
        return null;
    }

    public List<Recipient> getAllRecipients() {
        List<Recipient> list = new ArrayList<>();
        String sql = "SELECT recipient_id, name, blood_group, age, contact FROM recipients";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Recipient(
                    rs.getInt("recipient_id"),
                    rs.getString("name"),
                    rs.getString("blood_group"),
                    rs.getInt("age"),
                    rs.getString("contact")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching recipients: " + e.getMessage());
        }
        return list;
    }
}
