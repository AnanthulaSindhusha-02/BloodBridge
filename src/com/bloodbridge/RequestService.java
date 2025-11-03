package com.bloodbridge;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

public class RequestService {

    // create request: checks recipient exists, finds compatible donor, inserts request
    public void createRequest(int recipientId) {
        try (Connection con = DatabaseConnection.getConnection()) {
            // get recipient blood group
            String getRec = "SELECT blood_group FROM recipients WHERE recipient_id = ?";
            try (PreparedStatement ps = con.prepareStatement(getRec)) {
                ps.setInt(1, recipientId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("⚠️ Recipient not found with ID: " + recipientId);
                        return;
                    }
                    String recipientBg = rs.getString("blood_group");

                    // find compatible donor
                    DonorService donorService = new DonorService();
                    Donor donor = donorService.findCompatibleDonor(recipientBg);
                    if (donor == null) {
                        System.out.println("⚠️ No compatible donor available for " + recipientBg);
                        return;
                    }

                    String insert = "INSERT INTO requests (recipient_id, donor_id, status) VALUES (?, ?, 'Pending')";
                    try (PreparedStatement ins = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                        ins.setInt(1, recipientId);
                        ins.setInt(2, donor.getId());
                        ins.executeUpdate();
                        try (ResultSet gk = ins.getGeneratedKeys()) {
                            if (gk.next()) {
                                int requestId = gk.getInt(1);
                                System.out.println("✅ Request created with ID: " + requestId + " — Donor matched: " + donor.getName() + " (" + donor.getBloodGroup() + ")");
                            } else {
                                System.out.println("✅ Request created — Donor matched: " + donor.getName());
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating request: " + e.getMessage());
        }
    }

    public void approveRequest(int requestId) {
        String sql = "UPDATE requests SET status='Approved' WHERE request_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Request " + requestId + " approved.");
            } else {
                System.out.println("⚠️ Request not found: " + requestId);
            }
        } catch (SQLException e) {
            System.err.println("Error approving request: " + e.getMessage());
        }
    }

    public void viewRequests() {
        String sql = "SELECT r.request_id, r.status, r.request_time, rec.recipient_id, rec.name as recipient_name, rec.blood_group as recipient_bg, " +
                     "d.donor_id, d.name as donor_name, d.blood_group as donor_bg " +
                     "FROM requests r " +
                     "JOIN recipients rec ON r.recipient_id = rec.recipient_id " +
                     "JOIN donors d ON r.donor_id = d.donor_id " +
                     "ORDER BY r.request_time DESC";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Requests ---");
            while (rs.next()) {
                System.out.println("Request ID: " + rs.getInt("request_id") +
                        " | Recipient: " + rs.getString("recipient_name") + " (" + rs.getString("recipient_bg") + ")" +
                        " | Donor: " + rs.getString("donor_name") + " (" + rs.getString("donor_bg") + ")" +
                        " | Status: " + rs.getString("status") +
                        " | Time: " + rs.getTimestamp("request_time"));
            }
        } catch (SQLException e) {
            System.err.println("Error viewing requests: " + e.getMessage());
        }
    }

    public void exportRequestsCSV(String path) {
        String sql = "SELECT r.request_id, r.status, r.request_time, rec.name AS recipient_name, rec.blood_group AS recipient_bg, " +
                     "d.name AS donor_name, d.blood_group AS donor_bg " +
                     "FROM requests r " +
                     "JOIN recipients rec ON r.recipient_id = rec.recipient_id " +
                     "JOIN donors d ON r.donor_id = d.donor_id";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileWriter fw = new FileWriter(path)) {

            fw.append("request_id,status,request_time,recipient_name,recipient_bg,donor_name,donor_bg\n");
            while (rs.next()) {
                fw.append(rs.getInt("request_id") + ",");
                fw.append(rs.getString("status") + ",");
                fw.append(rs.getTimestamp("request_time") + ",");
                fw.append(escapeCsv(rs.getString("recipient_name")) + ",");
                fw.append(rs.getString("recipient_bg") + ",");
                fw.append(escapeCsv(rs.getString("donor_name")) + ",");
                fw.append(rs.getString("donor_bg") + "\n");
            }
            System.out.println("✅ Requests exported to " + path);
        } catch (SQLException | IOException e) {
            System.err.println("Error exporting requests: " + e.getMessage());
        }
    }

    private String escapeCsv(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        } else return s;
    }
}
