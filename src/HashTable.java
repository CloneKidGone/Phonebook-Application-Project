import java.util.Arrays;
import java.util.LinkedList;

public class HashTable {
    private LinkedList<Contact>[] table;
    private int size;

    // Constructor
    @SuppressWarnings("unchecked") // Suppress warnings for array of generic types
    public HashTable(int size) {
        this.size = size;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>(); // Initialize each bucket
        }
    }

    // Hash function to compute index
    private int hash(String phoneNumber) {
        return Math.abs(phoneNumber.hashCode() % size); // Using phoneNumber's hash code for indexing
    }

    // Insert a contact
    public void insert(String phoneNumber, Contact contact) {
        int index = hash(contact.getPhoneNumber());
        // Check if the contact already exists
        for (Contact c : table[index]) {
            if (c.getPhoneNumber().equals(contact.getPhoneNumber())) {
                // If found, replace the existing contact
                table[index].remove(c);
                break;
            }
        }
        table[index].add(contact); // Add new contact
    }

    // Search contact by phone number
    public Contact search(String phoneNumber) {
        int index = hash(phoneNumber);
        for (Contact c : table[index]) {
            if (c.getPhoneNumber().equals(phoneNumber)) {
                return c; // Return the contact if found
            }
        }
        return null; // Return null if not found
    }

    // Delete contact by phone number
    public boolean delete(String phoneNumber) {
        int index = hash(phoneNumber);
        for (Contact c : table[index]) {
            if (c.getPhoneNumber().equals(phoneNumber)) {
                table[index].remove(c); // Remove the contact
                return true; // Return true if deleted
            }
        }
        return false; // Return false if not found
    }

    // Update contact by phone number
    public boolean updateContact(String phoneNumber, Contact newContact) {
        int index = hash(phoneNumber);
        for (Contact c : table[index]) {
            if (c.getPhoneNumber().equals(phoneNumber)) {
                // Update the existing contact with new details
                c.setName(newContact.getName());
                c.setEmail(newContact.getEmail());
                return true; // Return true if updated
            }
        }
        return false; // Return false if not found
    }

    // Sort contacts in each bucket (optional)
    public void sortContacts() {
        for (LinkedList<Contact> bucket : table) {
            Contact[] contactsArray = bucket.toArray(new Contact[0]); // Convert to array
            Arrays.sort(contactsArray); // Assuming Contact implements Comparable
            bucket.clear(); // Clear the bucket
            bucket.addAll(Arrays.asList(contactsArray)); // Add sorted contacts back
        }
    }

    // Display all contacts
    public void display() {
        for (LinkedList<Contact> bucket : table) {
            for (Contact c : bucket) {
                System.out.println(c); // Assuming Contact has a proper toString() method
            }
        }
    }

    // Get all contacts
    public Contact[] getAllContacts() {
        LinkedList<Contact> allContacts = new LinkedList<>();
        for (LinkedList<Contact> bucket : table) {
            if (bucket != null) {
                allContacts.addAll(bucket);
            }
        }
        return allContacts.toArray(new Contact[0]); // Convert LinkedList to array
    }


    // Analyze the efficiency of the search algorithm
    public String analyzeSearchEfficiency() {
        // Here, you could provide analysis based on average and worst-case scenarios.
        // For a hash table, average-case search time is O(1), and worst-case is O(n) in case of collisions.
        return "Average-case search time: O(1)\nWorst-case search time: O(n)";
    }
}
