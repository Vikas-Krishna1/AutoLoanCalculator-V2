package ui.applicant;
import db.DatabaseManager;
import models.Applicant;
import models.AutoLoan;
import models.Vehicle;
import services.LoanCalculator;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
//LOAN FORM
//This class represents the loan form
//It allows the user to apply for a loan
//and view the results
public class LoanForm extends JFrame
{
    // Applicant Fields
    private int user_id ;
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

   public LoanForm(int user_id)
{
    this.user_id = user_id;

    setTitle("New Loan Application");
    setSize(800,700);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(
            new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(
            new EmptyBorder(10,10,10,10));

    JLabel title =
            new JLabel(
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

    JScrollPane scrollPane =
            new JScrollPane(mainPanel);

    add(scrollPane);

    setVisible(true);
}

    private JPanel createApplicantPanel()
    {
        JPanel panel =
                new JPanel(new GridLayout(7,2,5,5));

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

    private JPanel createVehiclePanel()
    {
        JPanel panel =
                new JPanel(new GridLayout(3,2,5,5));

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

        return panel;
    }

    private JPanel createLoanPanel()
    {
        JPanel panel =
                new JPanel(new GridLayout(7,2,5,5));

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Loan Information"));

        panel.add(new JLabel("Auto Price"));
        autoPriceField = new JTextField();
        panel.add(autoPriceField);

        panel.add(new JLabel("Down Payment"));
        downPaymentField = new JTextField();
        panel.add(downPaymentField);

        panel.add(new JLabel("Loan Term (Years)"));
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

    private JPanel createResultsPanel()
    {
        JPanel panel =
                new JPanel(new GridLayout(2,2,5,5));

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Loan Summary"));

        panel.add(new JLabel("Loan Amount"));

        loanAmountLabel =
                new JLabel("$0.00");

        panel.add(loanAmountLabel);

        panel.add(new JLabel("Monthly Payment"));

        monthlyPaymentLabel =
                new JLabel("$0.00");

        panel.add(monthlyPaymentLabel);

        return panel;
    }

    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel();

        JButton calculateButton =
                new JButton("Calculate");

        JButton saveButton =
                new JButton("Save Application");

        JButton clearButton =
                new JButton("Clear");

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

    private void calculateLoan()
    {
        try
        {
        validateForm();
            currentApplicant =
                    new Applicant(  
                            user_id, 
                            fullNameField.getText(),
                            emailField.getText(),
                            phoneField.getText(),
                            addressField.getText(),
                            dobField.getText(),
                            ssnField.getText(),
                            employerField.getText());


            currentVehicle =
                    new Vehicle(
                            0,
                            makeField.getText(),
                            modelField.getText(),
                            Integer.parseInt(
                                    yearField.getText()));

            currentLoan =
                    new AutoLoan(
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

            double loanAmount =
                    LoanCalculator.calculateLoanAmount(
                            currentLoan);

            double monthlyPayment =
                    LoanCalculator.calculateMonthlyPayment(
                            currentLoan);

            loanAmountLabel.setText(
                    String.format("$%.2f",
                            loanAmount));

            monthlyPaymentLabel.setText(
                    String.format("$%.2f",
                            monthlyPayment));
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveApplication()
    {
        try
        {
            if (currentLoan == null)
            {
                JOptionPane.showMessageDialog(
                        this,
                        "Calculate first.");

                return;
            }


            DatabaseManager db =
                    new DatabaseManager();

            int applicationId =
                    db.saveLoanApplication(
                            currentApplicant,
                            currentVehicle,
                            currentLoan);

            JOptionPane.showMessageDialog(
                    this,
                    "Application Saved!\nID: "
                            + applicationId);

            clearForm();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm()
    {
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
    private void validateForm() throws Exception
{
    // Required fields
    if(fullNameField.getText().trim().isEmpty() ||
       emailField.getText().trim().isEmpty() ||
       phoneField.getText().trim().isEmpty() ||
       addressField.getText().trim().isEmpty() ||
       dobField.getText().trim().isEmpty() ||
       ssnField.getText().trim().isEmpty() ||
       employerField.getText().trim().isEmpty() ||
       makeField.getText().trim().isEmpty() ||
       modelField.getText().trim().isEmpty() ||
       yearField.getText().trim().isEmpty() ||
       autoPriceField.getText().trim().isEmpty() ||
       downPaymentField.getText().trim().isEmpty() ||
       loanTermField.getText().trim().isEmpty() ||
       interestRateField.getText().trim().isEmpty())
    {
        throw new Exception("All required fields must be completed.");
    }

    // Email
    if(!emailField.getText().trim()
            .matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    {
        throw new Exception("Invalid email address.");
    }

    // Phone
    if(!phoneField.getText().trim()
            .matches("\\d{10}"))
    {
        throw new Exception(
                "Phone number must contain 10 digits.");
    }

    // SSN
    if(!ssnField.getText().trim()
            .matches("\\d{9}"))
    {
        throw new Exception(
                "SSN must contain 9 digits.");
    }

    int year =
            Integer.parseInt(yearField.getText());

    if(year < 1900 || year > 2035)
    {
        throw new Exception(
                "Vehicle year must be between 1900 and 2035.");
    }

    double autoPrice =
            Double.parseDouble(
                    autoPriceField.getText());

    if(autoPrice <= 0)
    {
        throw new Exception(
                "Vehicle price must be greater than 0.");
    }

    double downPayment =
            Double.parseDouble(
                    downPaymentField.getText());

    if(downPayment < 0)
    {
        throw new Exception(
                "Down payment cannot be negative.");
    }

    if(downPayment > autoPrice)
    {
        throw new Exception(
                "Down payment cannot exceed vehicle price.");
    }

    int term =
            Integer.parseInt(
                    loanTermField.getText());

    if(term < 1 || term > 7)
    {
        throw new Exception(
                "Loan term must be between 1 and 7 years.");
    }

    double rate =
            Double.parseDouble(
                    interestRateField.getText());

    if(rate < 0 || rate > 30)
    {
        throw new Exception(
                "Interest rate must be between 0 and 30%.");
    }

    double tax =
            Double.parseDouble(
                    salesTaxField.getText());

    if(tax < 0)
    {
        throw new Exception(
                "Sales tax cannot be negative.");
    }

    double fees =
            Double.parseDouble(
                    feesField.getText());

    if(fees < 0)
    {
        throw new Exception(
                "Fees cannot be negative.");
    }
    if(!dobField.getText().matches("\\d{4}-\\d{2}-\\d{2}"))
{
    throw new Exception(
            "Date must be in YYYY-MM-DD format.");
}
}
}