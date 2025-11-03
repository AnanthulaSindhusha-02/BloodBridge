package com.bloodbridge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class DonorService {

    public void addDonor(Donor donor) {
        String sql = "INSERT INTO donors (name, blood_group, age, contact) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, donor.getName());
            ps.setString(2, donor.getBloodGroup());
            ps.setInt(3, donor.getAge());
            ps.setString(4, donor.getContact());
            ps.executeUpdate();
            System.out.println("✅ Donor registered successfully!");
        } catch (SQLException e) {
            System.err.println("Error registering donor: " + e.getMessage());
        }
    }

    public List<Donor> getAllDonors() {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT donor_id, name, blood_group, age, contact FROM donors";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Donor(
                    rs.getInt("donor_id"),
                    rs.getString("name"),
                    rs.getString("blood_group"),
                    rs.getInt("age"),
                    rs.getString("contact")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching donors: " + e.getMessage());
        }
        return list;
    }

    public Donor getDonorById(int id) {
        String sql = "SELECT donor_id, name, blood_group, age, contact FROM donors WHERE donor_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Donor(
                        rs.getInt("donor_id"),
                        rs.getString("name"),
                        rs.getString("blood_group"),
                        rs.getInt("age"),
                        rs.getString("contact")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching donor: " + e.getMessage());
        }
        return null;
    }

    // find first compatible donor for recipient blood group (returns donor or null)
    public Donor findCompatibleDonor(String recipientBloodGroup) {
        String sql = "SELECT donor_id, name, blood_group, age, contact FROM donors";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String donorBg = rs.getString("blood_group");
                if (BloodGroupMatcher.isCompatible(donorBg, recipientBloodGroup)) {
                    return new Donor(
                        rs.getInt("donor_id"),
                        rs.getString("name"),
                        donorBg,
                        rs.getInt("age"),
                        rs.getString("contact")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching compatible donor: " + e.getMessage());
        }
        return null;
    }

    public void exportDonorsCSV(String path) {
        String sql = "SELECT donor_id, name, blood_group, age, contact FROM donors";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileWriter fw = new FileWriter(path)) {

            fw.append("donor_id,name,blood_group,age,contact\n");
            while (rs.next()) {
                fw.append(rs.getInt("donor_id") + ",");
                fw.append(escapeCsv(rs.getString("name")) + ",");
                fw.append(rs.getString("blood_group") + ",");
                fw.append(rs.getInt("age") + ",");
                fw.append(escapeCsv(rs.getString("contact")) + "\n");
            }
            System.out.println("✅ Donors exported to " + path);
        } catch (SQLException | IOException e) {
            System.err.println("Error exporting donors: " + e.getMessage());
        }
    }

    private String escapeCsv(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        } else return s;
    }
}
