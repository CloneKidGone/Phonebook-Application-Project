import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private List<Contact> contacts;

    public ContactManager() {
        this.contacts = new ArrayList<>(); // Initialize an empty list of contacts
    }

    // Adds a new contact to the list
    public void addContact(String name, String phone) {
        Contact contact = new Contact(name, phone);
        contacts.add(contact);
        System.out.println("Contact added: " + contact.getName());
    }

    // Deletes a contact from the list by name
    public void deleteContact(String name) {
        boolean removed = contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Contact deleted: " + name);
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Searches for a contact by name
    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null; // Return null if not found
    }

    // Displays all contacts
    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact.getName() + ": " + contact.getPhone());
            }
        }
    }

    // Returns the list of all contacts
    public List<Contact> getContacts() {
        return contacts;
    }
}
