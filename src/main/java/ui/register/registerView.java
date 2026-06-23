package ui.register;

import api.UserApiClient;
import ui.login.LoginView;

import javax.swing.*;
import java.awt.*;

public class registerView extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    private JPanel dynamicPanel;

    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JTextField addressField;
    private JTextField dobField;
    private JTextField ssnField;
    private JTextField employerField;

    private JTextField employeeNumberField;

    private JButton registerButton;
    private JButton backButton;

    public registerView()
    {
        setTitle("Register");
        setSize(550, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0,2,10,10));

        formPanel.add(new JLabel("Username"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Role"));
        roleBox = new JComboBox<>(
                new String[]
                {
                        "APPLICANT",
                        "OFFICER"
                });
        formPanel.add(roleBox);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new GridLayout(0,2,10,10));

        mainPanel.add(dynamicPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        registerButton =
                new JButton("Register");

        backButton =
                new JButton("Back");

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH);

        add(mainPanel);

        updateRoleFields();

        roleBox.addActionListener(
                e -> updateRoleFields());

        registerButton.addActionListener(
                e -> registerUser());

        backButton.addActionListener(
                e ->
                {
                    dispose();
                    new LoginView();
                });

        setVisible(true);
    }

    private void updateRoleFields()
    {
        dynamicPanel.removeAll();

        fullNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        dynamicPanel.add(
                new JLabel("Full Name"));
        dynamicPanel.add(fullNameField);

        dynamicPanel.add(
                new JLabel("Email"));
        dynamicPanel.add(emailField);

        dynamicPanel.add(
                new JLabel("Phone"));
        dynamicPanel.add(phoneField);

        String role =
                roleBox.getSelectedItem()
                        .toString();

        if(role.equals("APPLICANT"))
        {
            addressField =
                    new JTextField();

            dobField =
                    new JTextField();

            ssnField =
                    new JTextField();

            employerField =
                    new JTextField();

            dynamicPanel.add(
                    new JLabel("Address"));
            dynamicPanel.add(addressField);

            dynamicPanel.add(
                    new JLabel("Date Of Birth"));
            dynamicPanel.add(dobField);

            dynamicPanel.add(
                    new JLabel("SSN"));
            dynamicPanel.add(ssnField);

            dynamicPanel.add(
                    new JLabel("Employer"));
            dynamicPanel.add(employerField);
        }
        else
        {
            employeeNumberField =
                    new JTextField();

            dynamicPanel.add(
                    new JLabel("Employee Number"));
            dynamicPanel.add(
                    employeeNumberField);
        }

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void registerUser()
    {
        try
        {
            String username =
                    usernameField.getText().trim();

            String password =
                    new String(
                            passwordField.getPassword());

            String role =
                    roleBox.getSelectedItem()
                            .toString();

            String fullName =
                    fullNameField.getText().trim();

            String email =
                    emailField.getText().trim();

            String phone =
                    phoneField.getText().trim();

            if(username.isEmpty()
                    || password.isEmpty()
                    || fullName.isEmpty()
                    || email.isEmpty())
            {
                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all required fields.");
                return;
            }

            if(role.equals("APPLICANT"))
            {
                UserApiClient.registerApplicant(
                        username,
                        password,
                        fullName,
                        email,
                        phone,
                        addressField.getText(),
                        dobField.getText(),
                        ssnField.getText(),
                        employerField.getText()
                );
            }
            else
            {
                UserApiClient.registerOfficer(
                        username,
                        password,
                        fullName,
                        email,
                        phone,
                        employeeNumberField.getText()
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!");

            dispose();
            new LoginView();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed:\n"
                            + ex.getMessage());
        }
    }
}