import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "," + phoneNumber + "," + email;
    }
}

class ContactManager {
    private List<Contact> contacts;
    private final String filePath = "contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContactsFromFile();
    }

    public ContactManager(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContactsToFile();
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Contact list is empty.");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                Contact contact = contacts.get(i);
                System.out.println((i + 1) + ". " + contact.getName() + " - " + contact.getPhoneNumber() + " - " + contact.getEmail());
            }
        }
    }

    public void editContact(int index, Contact newContact) {
        if (index >= 0 && index < contacts.size()) {
            contacts.set(index, newContact);
            saveContactsToFile();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            saveContactsToFile();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    private void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 3) {
                    contacts.add(new Contact(details[0], details[1], details[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    private void saveContactsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                writer.write(contact.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}

public class ContactManagementApp {
    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nContact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    manager.addContact(new Contact(name, phoneNumber, email));
                    System.out.println("Contact added.");
                    break;

                case 2:
                    manager.viewContacts();
                    break;

                case 3:
                    System.out.print("Enter the contact number to edit: ");
                    int editIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    manager.editContact(editIndex, new Contact(newName, newPhoneNumber, newEmail));
                    System.out.println("Contact updated.");
                    break;

                case 4:
                    System.out.print("Enter the contact number to delete: ");
                    int deleteIndex = scanner.nextInt() - 1;
                    manager.deleteContact(deleteIndex);
                    System.out.println("Contact deleted.");
                    break;

                case 5:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
