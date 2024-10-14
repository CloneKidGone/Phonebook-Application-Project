import java.util.List;
import java.util.Scanner;

public class UIManager {
    private Scanner scanner;

    public UIManager() {
        this.scanner = new Scanner(System.in);
    }

    // Displays the main menu
    public void displayMenu() {
        System.out.println("1. Add Contact");
        System.out.println("2. Search Contact");
        System.out.println("3. Delete Contact");
        System.out.println("4. View All Contacts");
        System.out.println("5. Exit");
    }

    // Gets user input for menu choices
    public int getUserChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    // Gets user input for contact name and phone number
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    // Displays a list of contacts
    public void showContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact.getName() + ": " + contact.getPhone());
            }
        }
    }
}
