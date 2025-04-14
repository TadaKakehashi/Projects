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
        setTitle("Hotel Login (File Based)");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Sign Up");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginBtn);
        panel.add(signupBtn);

        add(panel);
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
                dashboard.setLoginFrame(this); // pass reference
                this.setVisible(false); // hide login
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
        new LoginFrame();
    }
}
