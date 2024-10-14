import java.util.HashMap;
import java.util.LinkedList;

public class Trie {
    private class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<>();
        LinkedList<Contact> contacts = new LinkedList<>(); // Contacts that match the prefix
        boolean isEndOfWord = false;
    }

    private TrieNode root;

    // Constructor
    public Trie() {
        root = new TrieNode();
    }

    // Insert contact into the Trie by name
    public void insert(String name, Contact contact) {
        TrieNode current = root;
        name = contact.getName().toLowerCase(); // Convert name to lowercase for consistency

        for (char c : name.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }

        current.isEndOfWord = true;
        current.contacts.add(contact); // Store contact at the end of the name
    }

    // Search contacts by prefix (autocomplete feature)
    public LinkedList<Contact> searchByPrefix(String prefix) {
        TrieNode current = root;
        prefix = prefix.toLowerCase();

        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return new LinkedList<>(); // No contacts with this prefix
            }
            current = current.children.get(c);
        }

        return current.contacts; // Return all contacts matching the prefix
    }

    // Delete contact from the Trie by name
    public boolean delete(String name, Contact contactToDelete) {
        return deleteRecursive(root, name.toLowerCase(), contactToDelete, 0);
    }

    private boolean deleteRecursive(TrieNode current, String name, Contact contactToDelete, int index) {
        if (index == name.length()) {
            if (!current.isEndOfWord) {
                return false; // Not found
            }

            // Remove the contact from the list
            boolean removed = current.contacts.remove(contactToDelete);
            if (current.contacts.isEmpty()) {
                current.isEndOfWord = false; // Mark as not end of word if no contacts remain
            }
            return removed;
        }

        char ch = name.charAt(index);
        TrieNode child = current.children.get(ch);
        if (child == null) {
            return false; // Not found
        }

        boolean shouldDeleteChild = deleteRecursive(child, name, contactToDelete, index + 1);

        // If the child node should be deleted
        if (shouldDeleteChild) {
            current.children.remove(ch);
            return current.children.isEmpty() && !current.isEndOfWord; // Check if current node can be deleted
        }

        return false;
    }
}
