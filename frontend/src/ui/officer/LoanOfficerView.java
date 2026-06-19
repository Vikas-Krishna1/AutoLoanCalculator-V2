package ui.officer;
import db.DatabaseManager;
import models.Applicant;
import models.AutoLoan;
import models.LoanApplication;
import models.Vehicle;
import services.LoanCalculator;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
//LOAN OFFICER VIEW
//This class represents the loan officer view
//It allows the loan officer to view and manage loan applications
//and assign loan officers to applications
public class LoanOfficerView extends JFrame
{
    // UI Components
    private JTextField applicationIdField;
    private JLabel applicantNameLabel;
    private JLabel applicantEmailLabel;
    private JLabel applicantPhoneLabel;
    private JLabel applicantEmployerLabel;

    private JLabel vehicleLabel;

    private JLabel loanAmountLabel;
    private JLabel monthlyPaymentLabel;
    private JLabel interestRateLabel;

    private JTextArea notesArea;

    private JButton loadButton;
    private JButton approveButton;
    private JButton rejectButton;

    private LoanApplication currentApplication;

    private DatabaseManager db;

    public LoanOfficerView()
    {
        db = new DatabaseManager();

        setTitle("Loan Officer Dashboard");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // ==========================
        // Search Section
        // ==========================
        JPanel searchPanel = new JPanel(new FlowLayout());

        searchPanel.setBorder(
                new TitledBorder("Application Lookup"));

        applicationIdField = new JTextField(15);

        loadButton = new JButton("Load Application");

        searchPanel.add(new JLabel("Application ID"));
        searchPanel.add(applicationIdField);
        searchPanel.add(loadButton);

        mainPanel.add(searchPanel);

        // ==========================
        // Applicant Section
        // ==========================
        JPanel applicantPanel =
                new JPanel(new GridLayout(4,2,5,5));

        applicantPanel.setBorder(
                new TitledBorder("Applicant Information"));

        applicantPanel.add(new JLabel("Name"));
        applicantNameLabel = new JLabel("-");
        applicantPanel.add(applicantNameLabel);

        applicantPanel.add(new JLabel("Email"));
        applicantEmailLabel = new JLabel("-");
        applicantPanel.add(applicantEmailLabel);

        applicantPanel.add(new JLabel("Phone"));
        applicantPhoneLabel = new JLabel("-");
        applicantPanel.add(applicantPhoneLabel);

        applicantPanel.add(new JLabel("Employer"));
        applicantEmployerLabel = new JLabel("-");
        applicantPanel.add(applicantEmployerLabel);

        mainPanel.add(applicantPanel);

        // ==========================
        // Vehicle Section
        // ==========================
        JPanel vehiclePanel =
                new JPanel(new GridLayout(1,2,5,5));

        vehiclePanel.setBorder(
                new TitledBorder("Vehicle Information"));

        vehiclePanel.add(new JLabel("Vehicle"));

        vehicleLabel = new JLabel("-");
        vehiclePanel.add(vehicleLabel);

        mainPanel.add(vehiclePanel);

        // ==========================
        // Loan Section
        // ==========================
        JPanel loanPanel =
                new JPanel(new GridLayout(3,2,5,5));

        loanPanel.setBorder(
                new TitledBorder("Loan Information"));

        loanPanel.add(new JLabel("Loan Amount"));
        loanAmountLabel = new JLabel("-");
        loanPanel.add(loanAmountLabel);

        loanPanel.add(new JLabel("Monthly Payment"));
        monthlyPaymentLabel = new JLabel("-");
        loanPanel.add(monthlyPaymentLabel);

        loanPanel.add(new JLabel("Interest Rate"));
        interestRateLabel = new JLabel("-");
        loanPanel.add(interestRateLabel);

        mainPanel.add(loanPanel);

        // ==========================
        // Notes Section
        // ==========================
        JPanel notesPanel =
                new JPanel(new BorderLayout());

        notesPanel.setBorder(
                new TitledBorder("Review Notes"));

        notesArea = new JTextArea(6,40);

        notesPanel.add(
                new JScrollPane(notesArea),
                BorderLayout.CENTER);

        mainPanel.add(notesPanel);

        // ==========================
        // Buttons
        // ==========================
        JPanel buttonPanel =
                new JPanel();

        approveButton =
                new JButton("Approve");

        rejectButton =
                new JButton("Reject");

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);

        mainPanel.add(buttonPanel);

        add(new JScrollPane(mainPanel));

        // ==========================
        // Load Application
        // ==========================
        loadButton.addActionListener(e ->
        {
            try
            {
                int applicationId =
                        Integer.parseInt(
                                applicationIdField.getText());
                currentApplication =
                        db.getLoanApplicationById(applicationId);                


                if(currentApplication == null)
                {
                    JOptionPane.showMessageDialog(
                            this,
                            "Application not found.");

                    return;
                }

                populateApplication(
                        currentApplication);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Application ID");
            }
        });

        // ==========================
        // Approve
        // ==========================
approveButton.addActionListener(e ->
{
    if(currentApplication == null)
    {
        JOptionPane.showMessageDialog(
                this,
                "Load an application first.");
        return;
    }

    boolean success =db.approveApplication(currentApplication.getApplicationId(),1, notesArea.getText()); // officer id

    if(success)
    {
        JOptionPane.showMessageDialog(
                this,
                "Application Approved");

        clearView();
    }
    else
    {
        JOptionPane.showMessageDialog(
                this,
                "Approval failed");
    }
});

        // ==========================
        // Reject
        // ==========================
rejectButton.addActionListener(e ->
{
    if(currentApplication == null)
    {
        JOptionPane.showMessageDialog(
                this,
                "Load an application first.");
        return;
    }

    boolean success = db.denyApplication(currentApplication.getApplicationId(),1, // officer id
 notesArea.getText());

    if(success)
    {
        JOptionPane.showMessageDialog(
                this,
                "Application Rejected");

        clearView();
    }
    else
    {
        JOptionPane.showMessageDialog(
                this,
                "Rejection failed");
    }
});
}
    private void populateApplication(
            LoanApplication application)
    {
        Applicant applicant =
                application.getApplicant();

        Vehicle vehicle =
                application.getVehicle();

        AutoLoan loan =
                application.getLoan();

        applicantNameLabel.setText(
                applicant.getFullName());

        applicantEmailLabel.setText(
                applicant.getEmail());

        applicantPhoneLabel.setText(
                applicant.getPhone());

        applicantEmployerLabel.setText(
                applicant.getEmployerName());

        vehicleLabel.setText(
                vehicle.getYear()
                        + " "
                        + vehicle.getMake()
                        + " "
                        + vehicle.getModel());

        loanAmountLabel.setText(
                String.format(
                        "$%.2f",
                        LoanCalculator.calculateLoanAmount(
                                loan)));

        monthlyPaymentLabel.setText(
                String.format(
                        "$%.2f",
                        LoanCalculator.calculateMonthlyPayment(
                                loan)));

        interestRateLabel.setText(
                loan.getInterestRate() + "%");
    }

    private void clearView()
    {
        applicationIdField.setText("");

        applicantNameLabel.setText("-");
        applicantEmailLabel.setText("-");
        applicantPhoneLabel.setText("-");
        applicantEmployerLabel.setText("-");

        vehicleLabel.setText("-");

        loanAmountLabel.setText("-");
        monthlyPaymentLabel.setText("-");
        interestRateLabel.setText("-");

        notesArea.setText("");

        currentApplication = null;
    }
    public LoanOfficerView(int applicationId) {
        this();
        System.out.println(
            "Constructor received "
                    + applicationId);
        applicationIdField.setText(String.valueOf(applicationId));
        loadButton.doClick();
        setVisible(true);
    }
}