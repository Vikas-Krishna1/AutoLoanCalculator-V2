package ui.login;
import api.UserApiClient;
import models.User;
import ui.*;
import ui.admin.adminDashboard;
import ui.applicant.ApplicationDashboard;
import ui.officer.ApplicationQueueView;
import ui.register.registerView;
import javax.swing.*;
import java.awt.*;
public class LoginView extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private JButton registerButton;
        public LoginView() {
                setTitle("Auto Loan System Login");
                setSize(450, 300);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                JLabel title = new JLabel("AUTO LOAN SYSTEM");
                title.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                20));

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                panel.add(title, gbc);
                gbc.gridwidth = 1;
                // Username
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(
                                new JLabel("Username:"),
                                gbc);
                usernameField = new JTextField(15);
                gbc.gridx = 1;
                panel.add(usernameField,gbc);
                // Password

                gbc.gridx = 0;
                gbc.gridy = 2;

                panel.add(
                                new JLabel("Password:"),
                                gbc);

                passwordField = new JPasswordField(15);

                gbc.gridx = 1;

                panel.add(
                                passwordField,
                                gbc);

                // Login Button

                loginButton = new JButton("Login");

                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(loginButton,gbc);
                // Register Button
                registerButton = new JButton("Register");
                gbc.gridx = 1;
                panel.add(registerButton,gbc);
                add(panel);

                // Login Listener

        loginButton.addActionListener(e -> {
                        loginUser();
                });

                // Register Listener

                registerButton.addActionListener(e -> {
                        new registerView();
                });

                setVisible(true);
        }

        private void loginUser() {
                String username = usernameField.getText().trim();

                String password = String.valueOf(
                                passwordField.getPassword());

                User user;

                try {
                        user = UserApiClient.login(
                                        username,
                                        password);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(
                                        this,
                                        "Cannot connect to server.");

                        return;
                }
                ;

                if (user == null) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "Invalid username or password.");
                        return;
                }

                // Save logged-in user
                Session.setCurrentUser(user);

                JOptionPane.showMessageDialog(
                                this,
                                "Welcome " +
                                                user.getUsername());

                JOptionPane.showMessageDialog(
                                this,
                                "Welcome " +
                                                user.getUsername());

                String role = user.getRole();

                if (role.equalsIgnoreCase("ADMIN")) {
                        new adminDashboard();
                } else if (role.equalsIgnoreCase("OFFICER")) {
                        this.setVisible(false);
                        new ApplicationQueueView(
                                        user.getUser_id());
                } else if (role.equalsIgnoreCase("APPLICANT")) {
                        this.setVisible(false);
                        new ApplicationDashboard(
                                        user.getUser_id());
                }

                dispose();
        }
}
