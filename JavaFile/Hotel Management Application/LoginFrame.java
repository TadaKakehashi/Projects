package Management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginFrame extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Hotel Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(300, 180));
        formPanel.setBorder(BorderFactory.createTitledBorder("Login / Sign Up"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        usernameField = new JTextField();
        formPanel.add(usernameField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        passwordField = new JPasswordField();
        formPanel.add(passwordField, gbc);

        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Sign Up");
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        formPanel.add(buttonPanel, gbc);

        wrapperPanel.add(formPanel);
        add(wrapperPanel);

        setVisible(true);

        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> signup());
    }

    private void login() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && validatePassword(password, parts[1])) {
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                DashboardFrame dashboard = new DashboardFrame(username);
                dashboard.setLoginFrame(this);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading user data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validatePassword(String inputPassword, String storedPassword) {
        return storedPassword.equals(hashPassword(inputPassword));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "Password hashing error!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void signup() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill both fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Reader Error: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + hashPassword(password));
            writer.newLine();
            JOptionPane.showMessageDialog(this, "User registered successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

