public class ContactManagementApp {
    public static void main(String[] args) {
        ContactManager contactManager = new ContactManager();
        DataManager dataManager = new DataManager();
        UIManager uiManager = new UIManager();

        // Load contacts from file
        contactManager.getContacts().addAll(dataManager.loadContacts());

        boolean running = true;

        while (running) {
            uiManager.displayMenu();
            int choice = uiManager.getUserChoice();

            switch (choice) {
                case 1:
                    String name = uiManager.getInput("Enter contact name: ");
                    String phone = uiManager.getInput("Enter contact phone: ");
                    contactManager.addContact(name, phone);
                    break;
                case 2:
                    String searchName = uiManager.getInput("Enter name to search: ");
                    Contact foundContact = contactManager.searchContact(searchName);
                    if (foundContact != null) {
                        System.out.println("Contact found: " + foundContact.getName() + " - " + foundContact.getPhone());
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 3:
                    String deleteName = uiManager.getInput("Enter name to delete: ");
                    contactManager.deleteContact(deleteName);
                    break;
                case 4:
                    contactManager.displayAllContacts();
                    break;
                case 5:
                    // Save contacts to file before exiting
                    dataManager.saveContacts(contactManager.getContacts());
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Goodbye!");
    }
}
