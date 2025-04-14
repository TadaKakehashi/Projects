package Management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ViewBookingFrame extends JFrame {
    JTextArea bookingsArea;
    JButton closeBtn;

    public ViewBookingFrame() {
        setTitle("View Bookings");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a text area to display bookings
        bookingsArea = new JTextArea();
        bookingsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingsArea);

        closeBtn = new JButton("Close");

        // Close button action
        closeBtn.addActionListener(e -> closeFrame());

        // Load bookings
        loadBookings();

        // Set layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeBtn, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void loadBookings() {
        StringBuilder bookings = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    bookings.append("Username: ").append(parts[0])
                            .append("\nName: ").append(parts[1])
                            .append("\nRoom Type: ").append(parts[2])
                            .append("\nDays: ").append(parts[3])
                            .append("\nDate: ").append(parts[4])
                            .append("\n\n");
                }
            }
            bookingsArea.setText(bookings.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading bookings!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeFrame() {
        // Return to the Dashboard
        DashboardFrame dashboardFrame = new DashboardFrame(""); // You can pass username here
        dashboardFrame.setVisible(true);
        this.setVisible(false); // Close the current frame (View Bookings)
    }
}

