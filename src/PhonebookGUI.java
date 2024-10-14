import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PhonebookGUI {
    private JFrame frame;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JButton addButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton viewAllButton; // Button to view all contacts
    private JTextArea outputArea;
    private Phonebook phonebook; // Phonebook instance
    private boolean showingAllContacts = false; // To track visibility state

    public PhonebookGUI() {
        phonebook = new Phonebook(100); // Initialize the phonebook
        frame = new JFrame("Phonebook Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Name:"), gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        frame.add(nameField, gbc);

        // Phone Number Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Phone Number:"), gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        frame.add(phoneField, gbc);

        // Email Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        frame.add(emailField, gbc);

        // Address Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Address:"), gbc);

        addressField = new JTextField(20);
        gbc.gridx = 1;
        frame.add(addressField, gbc);

        // Buttons
        addButton = new JButton("Add Contact");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(addButton, gbc);

        searchButton = new JButton("Search");
        gbc.gridx = 1;
        frame.add(searchButton, gbc);

        deleteButton = new JButton("Delete Contact");
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(deleteButton, gbc);

        updateButton = new JButton("Update Contact");
        gbc.gridx = 1;
        frame.add(updateButton, gbc);

        // Output Area
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Span across both columns
        frame.add(scrollPane, gbc);

        // Button to view all contacts
        viewAllButton = new JButton("View All Contacts");
        gbc.gridy = 7; // Place it below the output area
        frame.add(viewAllButton, gbc);

        // Add action listener to view all contacts button
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingAllContacts) {
                    // Hide contacts
                    outputArea.setText(""); // Clear the output area
                    showingAllContacts = false; // Update the state
                    viewAllButton.setText("View All Contacts"); // Change button text
                } else {
                    // Display all contacts
                    List<Contact> allContacts = phonebook.getAllContacts();
                    displayContacts(allContacts);
                    showingAllContacts = true; // Update the state
                    viewAllButton.setText("Hide Contacts"); // Change button text
                }
            }
        });

        // Action listener for Add Contact button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();

                if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) {
                    // Create and insert the contact
                    Contact contact = new Contact(name, phone, email, address);
                    phonebook.insertContact(contact);
                    outputArea.append("Contact added: " + contact + "\n");

                    // Clear fields after adding
                    nameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    addressField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Contact contact = phonebook.searchContact(name);
                if (contact != null) {
                    outputArea.setText("Contact found: " + contact);
                } else {
                    outputArea.setText("Contact not found.");
                }
            }
        });

        // Action listener for Delete Contact button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (phonebook.deleteContact(name)) {
                    outputArea.setText("Contact deleted: " + name);
                } else {
                    outputArea.setText("Contact not found.");
                }
                nameField.setText(""); // Clear the name field after deletion
            }
        });

        // Action listener for Update Contact button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();

                if (phonebook.updateContact(name, phone, email, address)) {
                    outputArea.setText("Contact updated: " + name);
                } else {
                    outputArea.setText("Contact not found or update failed.");
                }
                nameField.setText(""); // Clear fields after update
                phoneField.setText("");
                emailField.setText("");
                addressField.setText("");
            }
        });

        // Final setup
        frame.pack();
        frame.setVisible(true);
    }

    public void displayContacts(List<Contact> contacts) {
        // Display all contacts in the output area
        for (Contact contact : contacts) {
            outputArea.append(contact.toString() + "\n"); // Display each contact
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhonebookGUI());
    }
}
