package Management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AddCustomerFrame extends JFrame {
    JTextField nameField, ageField, contactField, addressField;
    JButton addBtn, closeBtn;

    public AddCustomerFrame() {
        setTitle("Add Customer");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create form fields
        nameField = new JTextField();
        ageField = new JTextField();
        contactField = new JTextField();
        addressField = new JTextField();

        addBtn = new JButton("Add Customer");
        closeBtn = new JButton("Close");

        // Set Layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        panel.add(addBtn);
        panel.add(closeBtn);

        add(panel);

        // Add Customer Button Action
        addBtn.addActionListener(e -> addCustomer());

        // Close Button Action
        closeBtn.addActionListener(e -> closeFrame());

        setVisible(true);
    }

    private void addCustomer() {
        String name = nameField.getText();
        String age = ageField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || age.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate age (must be a number)
        try {
            Integer.parseInt(age);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save the customer info
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
            writer.write(name + "," + age + "," + contact + "," + address);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            closeFrame(); // Close the add customer form
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving customer!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeFrame() {
        // Close the AddCustomerFrame and return to the Dashboard
        DashboardFrame dashboardFrame = new DashboardFrame(""); // You can pass username here
        dashboardFrame.setVisible(true);
        this.setVisible(false); // Hide the current frame
    }
}
