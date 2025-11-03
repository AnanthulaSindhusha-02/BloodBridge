/** 
package com.bloodbridge;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Start GUI console
        GuiConsole.createGui();

        Scanner sc = new Scanner(System.in);
        DonorService donorService = new DonorService();
        RecipientService recipientService = new RecipientService();
        RequestService requestService = new RequestService();

        while (true) {
            System.out.println("\n=== BLOOD BRIDGE SYSTEM ===");
            System.out.println("1. Register Donor");
            System.out.println("2. Register Recipient");
            System.out.println("3. Create Blood Request (auto-match)");
            System.out.println("4. Approve Blood Request");
            System.out.println("5. View All Requests");
            System.out.println("6. List Donors");
            System.out.println("7. List Recipients");
            System.out.println("8. Export Donors to CSV");
            System.out.println("9. Export Requests to CSV");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");
            String line = sc.nextLine().trim();
            int choice;
            try { choice = Integer.parseInt(line); } catch (NumberFormatException e) { choice = -1; }

            switch (choice) {
                case 1:
                    System.out.print("Name: "); String dName = sc.nextLine();
                    System.out.print("Blood Group (e.g., A+, O-): "); String dBg = sc.nextLine().toUpperCase();
                    System.out.print("Age: "); int dAge = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Contact: "); String dContact = sc.nextLine();
                    donorService.addDonor(new Donor(dName, dBg, dAge, dContact));
                    break;

                case 2:
                    System.out.print("Name: "); String rName = sc.nextLine();
                    System.out.print("Blood Group (e.g., A+, O-): "); String rBg = sc.nextLine().toUpperCase();
                    System.out.print("Age: "); int rAge = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Contact: "); String rContact = sc.nextLine();
                    recipientService.addRecipient(new Recipient(rName, rBg, rAge, rContact));
                    break;

                case 3:
                    System.out.print("Recipient ID: "); int recId = Integer.parseInt(sc.nextLine().trim());
                    requestService.createRequest(recId);
                    break;

                case 4:
                    System.out.print("Request ID to approve: "); int reqId = Integer.parseInt(sc.nextLine().trim());
                    requestService.approveRequest(reqId);
                    break;

                case 5:
                    requestService.viewRequests();
                    break;

                case 6:
                    List<Donor> donors = donorService.getAllDonors();
                    System.out.println("\n--- Donors ---");
                    donors.forEach(System.out::println);
                    break;

                case 7:
                    List<Recipient> recs = recipientService.getAllRecipients();
                    System.out.println("\n--- Recipients ---");
                    recs.forEach(System.out::println);
                    break;

                case 8:
                    System.out.print("Enter CSV path (e.g., donors.csv): ");
                    String dPath = sc.nextLine().trim();
                    donorService.exportDonorsCSV(dPath);
                    break;

                case 9:
                    System.out.print("Enter CSV path (e.g., requests.csv): ");
                    String rPath = sc.nextLine().trim();
                    requestService.exportRequestsCSV(rPath);
                    break;

                case 10:
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}*/

package com.bloodbridge;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Start GUI console
        GuiConsole.createGui();

        DonorService donorService = new DonorService();
        RecipientService recipientService = new RecipientService();
        RequestService requestService = new RequestService();

        while (true) {
            String choiceStr = GuiConsole.getInput(
                    "=== BLOOD BRIDGE SYSTEM ===\n" +
                    "1. Register Donor\n" +
                    "2. Register Recipient\n" +
                    "3. Create Blood Request (auto-match)\n" +
                    "4. Approve Blood Request\n" +
                    "5. View All Requests\n" +
                    "6. List Donors\n" +
                    "7. List Recipients\n" +
                    "8. Export Donors to CSV\n" +
                    "9. Export Requests to CSV\n" +
                    "10. Exit\n" +
                    "Enter choice:"
            );

            if (choiceStr == null) break; // User pressed Cancel
            int choice;
            try { choice = Integer.parseInt(choiceStr.trim()); } catch (NumberFormatException e) { choice = -1; }

            switch (choice) {
                case 1:
                    String dName = GuiConsole.getInput("Name:");
                    String dBg = GuiConsole.getInput("Blood Group (e.g., A+, O-):").toUpperCase();
                    int dAge = Integer.parseInt(GuiConsole.getInput("Age:"));
                    String dContact = GuiConsole.getInput("Contact:");
                    donorService.addDonor(new Donor(dName, dBg, dAge, dContact));
                    GuiConsole.showMessage("Donor registered successfully!");
                    break;

                case 2:
                    String rName = GuiConsole.getInput("Name:");
                    String rBg = GuiConsole.getInput("Blood Group (e.g., A+, O-):").toUpperCase();
                    int rAge = Integer.parseInt(GuiConsole.getInput("Age:"));
                    String rContact = GuiConsole.getInput("Contact:");
                    recipientService.addRecipient(new Recipient(rName, rBg, rAge, rContact));
                    GuiConsole.showMessage("Recipient registered successfully!");
                    break;

                case 3:
                    int recId = Integer.parseInt(GuiConsole.getInput("Recipient ID:"));
                    requestService.createRequest(recId);
                    GuiConsole.showMessage("Blood request created!");
                    break;

                case 4:
                    int reqId = Integer.parseInt(GuiConsole.getInput("Request ID to approve:"));
                    requestService.approveRequest(reqId);
                    GuiConsole.showMessage("Request approved!");
                    break;

                case 5:
                    requestService.viewRequests();
                    break;

                case 6:
                    List<Donor> donors = donorService.getAllDonors();
                    System.out.println("\n--- Donors ---");
                    donors.forEach(System.out::println);
                    break;

                case 7:
                    List<Recipient> recs = recipientService.getAllRecipients();
                    System.out.println("\n--- Recipients ---");
                    recs.forEach(System.out::println);
                    break;

                case 8:
                    String dPath = GuiConsole.getInput("Enter CSV path (e.g., donors.csv):");
                    donorService.exportDonorsCSV(dPath);
                    GuiConsole.showMessage("Donors exported successfully!");
                    break;

                case 9:
                    String rPath = GuiConsole.getInput("Enter CSV path (e.g., requests.csv):");
                    requestService.exportRequestsCSV(rPath);
                    GuiConsole.showMessage("Requests exported successfully!");
                    break;

                case 10:
                    GuiConsole.showMessage("Goodbye!");
                    System.exit(0);

                default:
                    GuiConsole.showMessage("Invalid choice. Try again.");
            }
        }
    }
}
