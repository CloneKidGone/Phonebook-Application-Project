import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Phonebook {
    private List<Contact> contacts;

    public Phonebook(int i) {

    }

    // Insert a contact into the database
    public void insertContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhoneNumber());
            pstmt.setString(3, contact.getEmail());
            pstmt.setString(4, contact.getAddress());

            // Log the phone number being inserted
            System.out.println("Inserting contact: " + contact.getPhoneNumber());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Search contact by phone number
    public Contact searchByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM contacts WHERE phone = ?";
        Contact contact = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                contact = new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                System.out.println("Contact found: " + contact); // Log found contact
            } else {
                System.out.println("Contact not found for phone: " + phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return contact;
    }


    // Delete contact by phone number
    public boolean deleteContact(String phoneNumber) {
        String sql = "DELETE FROM contacts WHERE phone = ?";
        boolean deleted = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            deleted = pstmt.executeUpdate() > 0; // returns true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return deleted;
    }

    // Retrieve all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return contacts;
    }

    // Display all contacts (useful for debugging or console output)
    public void displayContacts() {
        List<Contact> contacts = getAllContacts();
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    // Search for contacts by name prefix
    public LinkedList<Contact> searchByName(String namePrefix) {
        LinkedList<Contact> matchingContacts = new LinkedList<>();
        String sql = "SELECT * FROM contacts WHERE name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namePrefix + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                matchingContacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return matchingContacts;
    }

    // Update contact details
    public boolean updateContact(String phone, String newName, String newEmail, String newAddress) {
        String sql = "UPDATE contacts SET name = ?, email = ?, address = ? WHERE phone = ?";
        boolean updated = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, newAddress);
            pstmt.setString(4, phone);
            updated = pstmt.executeUpdate() > 0; // returns true if a row was updated
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return updated;
    }

    // Search for a contact by name
    public Contact searchContact(String name) {
        String sql = "SELECT * FROM contacts WHERE name = ?";
        Contact contact = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                contact = new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return contact;
    }
}
