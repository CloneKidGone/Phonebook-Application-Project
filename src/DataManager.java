import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FILE_NAME = "contacts.txt"; // File to store contacts

    // Saves the contacts list to a file
    public void saveContacts(List<Contact> contacts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhone());
                writer.newLine();
            }
            System.out.println("Contacts saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    // Loads contacts from a file into the list
    public List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    contacts.add(new Contact(data[0], data[1]));
                }
            }
            System.out.println("Contacts loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
        return contacts;
    }
}
