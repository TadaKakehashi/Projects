package Management;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private LoginFrame loginFrame;

    public DashboardFrame(String username) {
        setTitle("Hotel Management Dashboard");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton bookRoomBtn = new JButton("Book Room");
        JButton viewBookingsBtn = new JButton("View Bookings");
        JButton addCustomerBtn = new JButton("Add Customer");
        JButton viewCustomersBtn = new JButton("View Customers"); // NEW BUTTON
        JButton logoutBtn = new JButton("Logout");
        JButton exitProgramBtn = new JButton("Exit Program");

        bookRoomBtn.addActionListener(e -> openBookRoomFrame(username));
        viewBookingsBtn.addActionListener(e -> openViewBookingsFrame());
        addCustomerBtn.addActionListener(e -> openAddCustomerFrame());
        viewCustomersBtn.addActionListener(e -> openViewCustomersFrame()); // NEW ACTION
        // logoutBtn.addActionListener(e -> logout());
        exitProgramBtn.addActionListener(e -> exitProgram());

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10)); // Adjusted layout size
        buttonPanel.add(bookRoomBtn);
        buttonPanel.add(viewBookingsBtn);
        buttonPanel.add(addCustomerBtn);
        buttonPanel.add(viewCustomersBtn); // NEW
        // buttonPanel.add(logoutBtn);
        buttonPanel.add(exitProgramBtn);

        setLayout(new BorderLayout(10, 10));
        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void openBookRoomFrame(String username) {
        BookRoomFrame bookRoomFrame = new BookRoomFrame(username);
        bookRoomFrame.setVisible(true);
        this.setVisible(false);
    }

    private void openViewBookingsFrame() {
        ViewBookingFrame viewBookingFrame = new ViewBookingFrame();
        viewBookingFrame.setVisible(true);
        this.setVisible(false);
    }

    private void openAddCustomerFrame() {
        AddCustomerFrame addCustomerFrame = new AddCustomerFrame();
        addCustomerFrame.setVisible(true);
        this.setVisible(false);
    }

    private void openViewCustomersFrame() {
        ViewCustomersFrame viewCustomersFrame = new ViewCustomersFrame();
        viewCustomersFrame.setVisible(true);
        this.setVisible(false);
    }

    // private void logout() {
    //     int response = JOptionPane.showConfirmDialog(
    //         this,
    //         "Are you sure you want to logout?",
    //         "Logout Confirmation",
    //         JOptionPane.YES_NO_OPTION
    //     );

    //     if (response == JOptionPane.YES_OPTION) {
    //         this.setVisible(false);
    //         if (loginFrame != null) {
    //             loginFrame.setVisible(true);
    //         }
    //     }
    // }

    private void exitProgram() {
        System.exit(0);
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }
}
