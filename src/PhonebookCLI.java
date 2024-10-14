import java.util.LinkedList;
import java.util.Scanner;

public class PhonebookCLI {
    private Phonebook phonebook;

    public PhonebookCLI() {
        phonebook = new Phonebook(100); // Initialize phonebook with a hash table size of 100
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Phonebook Menu ---");
            System.out.println("1. Add Contact");
            System.out.println("2. Search by Phone Number");
            System.out.println("3. Search by Name Prefix");
            System.out.println("4. Delete Contact");
            System.out.println("5. Display All Contacts");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    searchByPhoneNumber(scanner);
                    break;
                case 3:
                    searchByName(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    phonebook.displayContacts();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private void addContact(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        try {
            Contact contact = new Contact(name, phoneNumber, email, address);
            phonebook.insertContact(contact);
            System.out.println("Contact added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchByPhoneNumber(Scanner scanner) {
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        Contact contact = phonebook.searchByPhoneNumber(phoneNumber);
        if (contact != null) {
            System.out.println("Contact Found: " + contact);
        } else {
            System.out.println("No contact found with this phone number.");
        }
    }

    private void searchByName(Scanner scanner) {
        System.out.print("Enter Name Prefix: ");
        String namePrefix = scanner.nextLine();
        LinkedList<Contact> contacts = phonebook.searchByName(namePrefix);
        if (!contacts.isEmpty()) {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        } else {
            System.out.println("No contacts found with this name prefix.");
        }
    }

    private void deleteContact(Scanner scanner) {
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        if (phonebook.deleteContact(phoneNumber)) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("No contact found with this phone number.");
        }
    }

    public static void main(String[] args) {
        Phonebook phonebook = new Phonebook(100); // No arguments

        // Create a new contact
        Contact contact = new Contact("John Doe", "1234567890", "john@example.com", "123 Main St");
        phonebook.insertContact(contact);

        // Display all contacts
        System.out.println("All Contacts:");
        phonebook.displayContacts();

        // Search for a contact
        Contact foundContact = phonebook.searchByPhoneNumber("1234567890");
        if (foundContact != null) {
            System.out.println("Contact found: " + foundContact);
        } else {
            System.out.println("Contact not found.");
        }

        // Delete a contact
        boolean isDeleted = phonebook.deleteContact("1234567890");
        System.out.println(isDeleted ? "Contact deleted." : "Contact not found.");

        // Get all contacts again
        System.out.println("All Contacts after deletion:");
        phonebook.displayContacts();

        // Search by name prefix
        LinkedList<Contact> searchResults = phonebook.searchByName("John");
        System.out.println("Search results for 'John':");
        for (Contact c : searchResults) {
            System.out.println(c);
        }
    }
}