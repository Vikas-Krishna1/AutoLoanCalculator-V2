package ui.register;



import db.DatabaseManager;
import ui.login.LoginView;
import utils.passwordUtils;

import javax.swing.*;
import java.awt.*;

public class registerView extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton registerButton;
    private JButton backButton;

    private DatabaseManager db;

    public registerView()
    {
        db = new DatabaseManager();

        setTitle("Register");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2,10,10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        roleBox = new JComboBox<>(
                new String[]
                {
                        "APPLICANT",
                        "OFFICER",
                        "ADMIN"
                });
        panel.add(roleBox);

        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        registerButton.addActionListener(e ->
        {
            registerUser();
        });

        backButton.addActionListener(e ->
        {
            dispose();
            new LoginView();
        });

        setVisible(true);
    }

    private void registerUser()
    {
        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        password = passwordUtils.hashPassword(password);

        String role =
                roleBox.getSelectedItem().toString();

        if(username.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(
                    this,
                    "All fields are required.");
            return;
        }

        boolean success =
                db.registerUser(
                        username,
                        password,
                        role);

        if(success)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!");

            dispose();
            new LoginView();
        }
        else
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed.");
        }
    }
}
