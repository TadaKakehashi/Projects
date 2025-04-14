package Management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BookRoomFrame extends JFrame {
    JTextField nameField, roomTypeField, daysField, dateField;
    JButton bookBtn, cancelBtn;

    public BookRoomFrame(String username) {
        setTitle("Book Room");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create form fields
        nameField = new JTextField();
        roomTypeField = new JTextField();
        daysField = new JTextField();
        dateField = new JTextField();

        bookBtn = new JButton("Book Room");
        cancelBtn = new JButton("Cancel");

        // Set Layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Room Type (AC/Non-AC):"));
        panel.add(roomTypeField);
        panel.add(new JLabel("No. of Days:"));
        panel.add(daysField);
        panel.add(new JLabel("Date (DD/MM/YYYY):"));
        panel.add(dateField);

        panel.add(bookBtn);
        panel.add(cancelBtn);

        add(panel);

        // Book Button Action
        bookBtn.addActionListener(e -> bookRoom(username));

        // Cancel Button Action
        cancelBtn.addActionListener(e -> closeFrame());

        setVisible(true);
    }

    private void bookRoom(String username) {
        String name = nameField.getText();
        String roomType = roomTypeField.getText();
        String days = daysField.getText();
        String date = dateField.getText();

        if (name.isEmpty() || roomType.isEmpty() || days.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate room type (AC or Non-AC)
        if (!roomType.equalsIgnoreCase("AC") && !roomType.equalsIgnoreCase("Non-AC")) {
            JOptionPane.showMessageDialog(this, "Room Type must be AC or Non-AC!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate date format (DD/MM/YYYY)
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use DD/MM/YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simple validation for days (should be a number greater than 0)
        try {
            int numberOfDays = Integer.parseInt(days);
            if (numberOfDays <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of days!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save the booking (For simplicity, we'll write to a text file)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt", true))) {
                writer.write(username + "," + name + "," + roomType + "," + numberOfDays + "," + date);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Room booked successfully!");
                closeFrame(); // Close the booking form and return to the dashboard
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving booking!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for days!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeFrame() {
        // Return to the Dashboard
        DashboardFrame dashboardFrame = new DashboardFrame(""); // You can pass username here
        dashboardFrame.setVisible(true);
        this.setVisible(false); // Hide the current frame (Booking)
    }
}

