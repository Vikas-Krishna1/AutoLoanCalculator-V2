package ui.applicant;

import models.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import api.*;

//LOAN FORM
//This class represents the loan form
//It allows the user to apply for a loan
//and view the results
public class LoanForm extends JFrame {
        // Applicant Fields
        private int user_id;
        private JTextField fullNameField;
        private JTextField emailField;
        private JTextField phoneField;
        private JTextField addressField;
        private JTextField dobField;
        private JTextField ssnField;
        private JTextField employerField;

        // Vehicle Fields
        private JTextField makeField;
        private JTextField modelField;
        private JTextField yearField;
        private JTextField vinField;

        // Loan Fields
        private JTextField autoPriceField;
        private JTextField downPaymentField;
        private JTextField loanTermField;
        private JTextField interestRateField;
        private JTextField salesTaxField;
        private JTextField feesField;
        private JTextField cashIncentiveField;

        // Results
        private JLabel loanAmountLabel;
        private JLabel monthlyPaymentLabel;

        private Applicant currentApplicant;
        private Vehicle currentVehicle;
        private AutoLoan currentLoan;

        public LoanForm(int user_id) {
                this.user_id = user_id;

                setTitle("New Loan Application");
                setSize(800, 700);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(
                                new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                mainPanel.setBorder(
                                new EmptyBorder(10, 10, 10, 10));

                JLabel title = new JLabel(
                                "AUTO LOAN APPLICATION",
                                SwingConstants.CENTER);

                title.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                22));

                title.setAlignmentX(Component.CENTER_ALIGNMENT);

                mainPanel.add(title);
                mainPanel.add(Box.createVerticalStrut(10));

                mainPanel.add(createApplicantPanel());
                mainPanel.add(Box.createVerticalStrut(10));

                mainPanel.add(createVehiclePanel());
                mainPanel.add(Box.createVerticalStrut(10));

                mainPanel.add(createLoanPanel());
                mainPanel.add(Box.createVerticalStrut(10));

                mainPanel.add(createResultsPanel());
                mainPanel.add(Box.createVerticalStrut(10));

                mainPanel.add(createButtonPanel());

                JScrollPane scrollPane = new JScrollPane(mainPanel);

                add(scrollPane);

                setVisible(true);
        }

        private JPanel createApplicantPanel() {
                JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

                panel.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Applicant Information"));

                panel.add(new JLabel("Full Name"));
                fullNameField = new JTextField();
                panel.add(fullNameField);

                panel.add(new JLabel("Email"));
                emailField = new JTextField();

                panel.add(emailField);

                panel.add(new JLabel("Phone"));
                phoneField = new JTextField();
                panel.add(phoneField);

                panel.add(new JLabel("Address"));
                addressField = new JTextField();
                panel.add(addressField);

                panel.add(new JLabel("Date of Birth"));
                dobField = new JTextField();
                panel.add(dobField);

                panel.add(new JLabel("SSN"));
                ssnField = new JTextField();
                panel.add(ssnField);

                panel.add(new JLabel("Employer"));
                employerField = new JTextField();
                panel.add(employerField);

                return panel;
        }

        private JPanel createVehiclePanel() {
                JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

                panel.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Vehicle Information"));

                panel.add(new JLabel("Make"));
                makeField = new JTextField();
                panel.add(makeField);

                panel.add(new JLabel("Model"));
                modelField = new JTextField();
                panel.add(modelField);

                panel.add(new JLabel("Year"));
                yearField = new JTextField();
                panel.add(yearField);

                panel.add(new JLabel("VIN"));
                vinField = new JTextField();
                panel.add(vinField);

                return panel;
        }

        private JPanel createLoanPanel() {
                JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

                panel.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Loan Information"));

                panel.add(new JLabel("Auto Price"));
                autoPriceField = new JTextField();
                panel.add(autoPriceField);

                panel.add(new JLabel("Down Payment"));
                downPaymentField = new JTextField();
                panel.add(downPaymentField);

                panel.add(new JLabel("Loan Term (Months)"));
                loanTermField = new JTextField();
                panel.add(loanTermField);

                panel.add(new JLabel("Interest Rate (%)"));
                interestRateField = new JTextField();
                panel.add(interestRateField);

                panel.add(new JLabel("Sales Tax (%)"));
                salesTaxField = new JTextField();
                panel.add(salesTaxField);

                panel.add(new JLabel("Fees"));
                feesField = new JTextField();
                panel.add(feesField);

                panel.add(new JLabel("Cash Incentive"));
                cashIncentiveField = new JTextField();
                panel.add(cashIncentiveField);

                return panel;
        }

        private JPanel createResultsPanel() {
                JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));

                panel.setBorder(
                                BorderFactory.createTitledBorder(
                                                "Loan Summary"));

                panel.add(new JLabel("Loan Amount"));

                loanAmountLabel = new JLabel("$0.00");

                panel.add(loanAmountLabel);

                panel.add(new JLabel("Monthly Payment"));

                monthlyPaymentLabel = new JLabel("$0.00");

                panel.add(monthlyPaymentLabel);

                return panel;
        }

        private JPanel createButtonPanel() {
                JPanel panel = new JPanel();

                JButton calculateButton = new JButton("Calculate");

                JButton saveButton = new JButton("Save Application");

                JButton clearButton = new JButton("Clear");

                calculateButton.addActionListener(
                                e -> calculateLoan());

                saveButton.addActionListener(
                                e -> saveApplication());

                clearButton.addActionListener(
                                e -> clearForm());

                panel.add(calculateButton);
                panel.add(saveButton);
                panel.add(clearButton);

                return panel;
        }

        private void calculateLoan() {
                try {
                        validateForm();
                        currentApplicant = new Applicant(
                                        user_id,
                                        fullNameField.getText(),
                                        emailField.getText(),
                                        phoneField.getText(),
                                        addressField.getText(),
                                        dobField.getText(),
                                        ssnField.getText(),
                                        employerField.getText());

                        currentVehicle = new Vehicle(
                                        0,
                                        makeField.getText(),
                                        modelField.getText(),
                                        Integer.parseInt(
                                                        yearField.getText()),
                                        vinField.getText());

                        currentLoan = new AutoLoan(
                                        Double.parseDouble(
                                                        autoPriceField.getText()),
                                        Double.parseDouble(
                                                        downPaymentField.getText()),
                                        Integer.parseInt(
                                                        loanTermField.getText()),
                                        Double.parseDouble(
                                                        interestRateField.getText()),
                                        Double.parseDouble(
                                                        salesTaxField.getText()),
                                        Double.parseDouble(
                                                        feesField.getText()),
                                        Double.parseDouble(
                                                        cashIncentiveField.getText()));

                        double loanAmount = currentLoan.getAutoPrice()
                                        - currentLoan.getDownPayment()
                                        - currentLoan.getCashIncentive()
                                        + currentLoan.getFees()
                                        + currentLoan.getAutoPrice()
                                                        * currentLoan.getSalesTax() / 100.0;

                        double principal = loanAmount;

                        double monthlyRate = currentLoan.getInterestRate() / 100.0 / 12.0;

                        int months = currentLoan.getLoanTerm();

                        double monthlyPayment = principal *
                                        (monthlyRate *
                                                        Math.pow(1 + monthlyRate, months))
                                        /
                                        (Math.pow(1 + monthlyRate, months) - 1);

                        loanAmountLabel.setText(
                                        String.format("$%.2f",
                                                        loanAmount));

                        monthlyPaymentLabel.setText(
                                        String.format("$%.2f",
                                                        monthlyPayment));
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        ex.getMessage(),
                                        "Input Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private void saveApplication() {
                try {
                        if (currentLoan == null) {
                                JOptionPane.showMessageDialog(
                                                this,
                                                "Calculate first.");

                                return;
                        }

                        Applicant applicant = ApplicantApiClient.createApplicant(
                                        currentApplicant);

                        Vehicle vehicle = VehicleApiClient.createVehicle(
                                        currentVehicle);

                        LoanApplication loan = new LoanApplication();

                        loan.setApplicant_id(
                                        applicant.getApplicantId());

                        loan.setVehicle_id(
                                        vehicle.getVehicle_id());

                        loan.setAuto_price(
                                        currentLoan.getAutoPrice());

                        loan.setSales_tax(
                                        currentLoan.getSalesTax());

                        loan.setFees(
                                        currentLoan.getFees());

                        loan.setCash_incentive(
                                        currentLoan.getCashIncentive());

                        loan.setDown_payment(
                                        currentLoan.getDownPayment());

                        loan.setInterest_rate(
                                        currentLoan.getInterestRate());

                        loan.setLoan_term(
                                        currentLoan.getLoanTerm());

                        LoanApplication savedLoan = LoanApplicationApi.createLoan(
                                        loan);

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Application Saved!\nID: "
                                                        + savedLoan.getApplication_id());

                        clearForm();
                } catch (Exception ex) {
                        ex.printStackTrace();

                        JOptionPane.showMessageDialog(
                                        this,
                                        ex.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);

                }
        }

        public void clearForm() {
                fullNameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                addressField.setText("");
                dobField.setText("");
                ssnField.setText("");
                employerField.setText("");
                makeField.setText("");
                modelField.setText("");
                yearField.setText("");
                vinField.setText("");
                autoPriceField.setText("");
                downPaymentField.setText("");
                loanTermField.setText("");
                interestRateField.setText("");
                salesTaxField.setText("");
                feesField.setText("");
                cashIncentiveField.setText("");
                loanAmountLabel.setText("$0.00");
                monthlyPaymentLabel.setText("$0.00");

                currentApplicant = null;
                currentVehicle = null;
                currentLoan = null;

        }

        public void validateForm() throws Exception {
                if (fullNameField.getText().isEmpty()) {
                        throw new Exception("Full Name is required.");
                }

                if (emailField.getText().isEmpty()) {
                        throw new Exception("Email is required.");
                }

                if (phoneField.getText().isEmpty()) {
                        throw new Exception("Phone is required.");
                }

                if (addressField.getText().isEmpty()) {
                        throw new Exception("Address is required.");
                }

                if (dobField.getText().isEmpty()) {
                        throw new Exception("Date of Birth is required.");
                }

                if (ssnField.getText().isEmpty()) {
                        throw new Exception("SSN is required.");
                }

                if (employerField.getText().isEmpty()) {
                        throw new Exception("Employer is required.");
                }

                if (makeField.getText().isEmpty()) {
                        throw new Exception("Make is required.");
                }

                if (modelField.getText().isEmpty()) {
                        throw new Exception("Model is required.");
                }

                if (yearField.getText().isEmpty()) {
                        throw new Exception("Year is required.");
                }

                if (vinField.getText().isEmpty()) {
                        throw new Exception("VIN is required.");
                }

                if (autoPriceField.getText().isEmpty()) {
                        throw new Exception("Auto Price is required.");
                }

                if (downPaymentField.getText().isEmpty()) {
                        throw new Exception("Down Payment is required.");
                }

                if (loanTermField.getText().isEmpty()) {
                        throw new Exception("Loan Term is required.");
                }

                if (interestRateField.getText().isEmpty()) {
                        throw new Exception("Interest Rate is required.");
                }

                if (salesTaxField.getText().isEmpty()) {
                        throw new Exception("Sales Tax is required.");
                }

                if (feesField.getText().isEmpty()) {
                        throw new Exception("Fees is required.");
                }

                if (cashIncentiveField.getText().isEmpty()) {
                        throw new Exception("Cash Incentive is required.");
                }

                // Add more form validation checks as needed
        }
}