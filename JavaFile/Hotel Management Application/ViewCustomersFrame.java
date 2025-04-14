package Management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ViewCustomersFrame extends JFrame {
    JTextArea customersArea;
    JButton closeBtn;

    public ViewCustomersFrame() {
        setTitle("View Customers");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a text area to display customer information
        customersArea = new JTextArea();
        customersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(customersArea);

        closeBtn = new JButton("Close");

        // Close button action
        closeBtn.addActionListener(e -> closeFrame());

        // Load customers
        loadCustomers();

        // Set layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeBtn, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void loadCustomers() {
        StringBuilder customers = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    customers.append("Name: ").append(parts[0])
                            .append("\nAge: ").append(parts[1])
                            .append("\nContact: ").append(parts[2])
                            .append("\nAddress: ").append(parts[3])
                            .append("\n\n");
                }
            }
            customersArea.setText(customers.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading customer data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeFrame() {
        // Close the ViewCustomersFrame and return to the Dashboard
        DashboardFrame dashboardFrame = new DashboardFrame(""); // You can pass username here
        dashboardFrame.setVisible(true);
        this.setVisible(false); // Close the current frame (View Customers)
    }
}
