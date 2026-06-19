package ui.applicant;
import javax.swing .*;

import java.awt.*;
import java.io.IOException;

import db.DatabaseManager;
import models.Applicant;
import models.AutoLoan;
import models.LoanApplication;
import models.Vehicle;
import utils.PDFExporter;

public class ViewForm extends JFrame {
    public ViewForm(int applicationId)
{
    DatabaseManager db = new DatabaseManager();

    LoanApplication application =
            db.getLoanApplicationByIdWithOfficerData(applicationId);

    if(application == null)
    {
        JOptionPane.showMessageDialog(
                this,
                "Application not found.");

        dispose();
        return;
    }

    setTitle("Application Details");
    setSize(900, 850);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Applicant applicant =
            application.getApplicant();

    Vehicle vehicle =
            application.getVehicle();

    AutoLoan loan =
            application.getLoan();


    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(
            new BoxLayout(
                    mainPanel,
                    BoxLayout.Y_AXIS));

    mainPanel.setBorder(
            BorderFactory.createEmptyBorder(
                    15,15,15,15));

    // =====================================
    // STATUS BANNER
    // =====================================

    JLabel statusBanner =
            new JLabel(
                    application.getStatus(),
                    SwingConstants.CENTER);

    statusBanner.setOpaque(true);
    statusBanner.setFont(
            new Font(
                    "Arial",
                    Font.BOLD,
                    20));

    statusBanner.setForeground(Color.WHITE);

    if(application.getStatus()
            .equalsIgnoreCase("APPROVED"))
    {
        statusBanner.setBackground(
                new Color(46,125,50));
    }
    else if(application.getStatus()
            .equalsIgnoreCase("DENIED"))
    {
        statusBanner.setBackground(
                new Color(198,40,40));
    }
    else
    {
        statusBanner.setBackground(
                new Color(245,124,0));
    }

    mainPanel.add(statusBanner);
    mainPanel.add(Box.createVerticalStrut(10));

    // =====================================
    // APPLICANT PANEL
    // =====================================

    JPanel applicantPanel =
            new JPanel(
                    new GridLayout(7,2,10,10));

    applicantPanel.setBorder(
            BorderFactory.createTitledBorder(
                    "Applicant Information"));

    applicantPanel.add(new JLabel("Full Name"));
    applicantPanel.add(
            createReadOnlyField(
                    applicant.getFullName()));

    applicantPanel.add(new JLabel("Email"));
    applicantPanel.add(
            createReadOnlyField(
                    applicant.getEmail()));

    applicantPanel.add(new JLabel("Phone"));
    applicantPanel.add(
            createReadOnlyField(
                    applicant.getPhone()));

    applicantPanel.add(new JLabel("Address"));
    applicantPanel.add(
            createReadOnlyField(
                    applicant.getAddress()));

    applicantPanel.add(new JLabel("Date Of Birth"));
    applicantPanel.add(
            createReadOnlyField(
                    applicant.getDateOfBirth()));

    applicantPanel.add(new JLabel("SSN"));
    applicantPanel.add(
            createReadOnlyField(
                    applicant.getSSN()));

    applicantPanel.add(new JLabel("Employer"));
    applicantPanel.add(
            createReadOnlyField(
                    applicant.getEmployerName()));

    // =====================================
    // VEHICLE PANEL
    // =====================================

    JPanel vehiclePanel =
            new JPanel(
                    new GridLayout(3,2,10,10));

    vehiclePanel.setBorder(
            BorderFactory.createTitledBorder(
                    "Vehicle Information"));

    vehiclePanel.add(new JLabel("Make"));
    vehiclePanel.add(
            createReadOnlyField(
                    vehicle.getMake()));

    vehiclePanel.add(new JLabel("Model"));
    vehiclePanel.add(
            createReadOnlyField(
                    vehicle.getModel()));

    vehiclePanel.add(new JLabel("Year"));
    vehiclePanel.add(
            createReadOnlyField(
                    String.valueOf(
                            vehicle.getYear())));

    // =====================================
    // LOAN PANEL
    // =====================================

    JPanel loanPanel =
            new JPanel(
                    new GridLayout(7,2,10,10));

    loanPanel.setBorder(
            BorderFactory.createTitledBorder(
                    "Loan Information"));

    loanPanel.add(new JLabel("Auto Price"));
    loanPanel.add(
            createReadOnlyField(
                    "$" + loan.getAutoPrice()));

    loanPanel.add(new JLabel("Down Payment"));
    loanPanel.add(
            createReadOnlyField(
                    "$" + loan.getDownPayment()));

    loanPanel.add(new JLabel("Loan Term"));
    loanPanel.add(
            createReadOnlyField(
                    loan.getLoanTerm()
                            + " months"));

    loanPanel.add(new JLabel("Interest Rate"));
    loanPanel.add(
            createReadOnlyField(
                    loan.getInterestRate()
                            + "%"));

    loanPanel.add(new JLabel("Sales Tax"));
    loanPanel.add(
            createReadOnlyField(
                    loan.getSalesTax()
                            + "%"));

    loanPanel.add(new JLabel("Fees"));
    loanPanel.add(
            createReadOnlyField(
                    "$" + loan.getFees()));

    loanPanel.add(new JLabel("Cash Incentive"));
    loanPanel.add(
            createReadOnlyField(
                    "$" + loan.getCashIncentive()));

    // =====================================
    // REVIEW PANEL
    // =====================================

    JPanel reviewPanel =
            new JPanel(
                    new BorderLayout());

    reviewPanel.setBorder(
            BorderFactory.createTitledBorder(
                    "Officer Review"));

    JPanel reviewInfo =
            new JPanel(
                    new GridLayout(2,2,10,10));

    reviewInfo.add(
            new JLabel("Status"));

    reviewInfo.add(
            createReadOnlyField(
                    application.getStatus()));

    reviewInfo.add(
            new JLabel("Reviewed By"));

    reviewInfo.add(
            createReadOnlyField(
                    application.getReviewedBy() > 0
                            ? "Loan Officer"
                            : "Not Reviewed"));

    reviewPanel.add(
            reviewInfo,
            BorderLayout.NORTH);

    JTextArea notesArea =
            new JTextArea(
                    application.getReviewNotes());

    notesArea.setEditable(false);
    notesArea.setLineWrap(true);
    notesArea.setWrapStyleWord(true);

    reviewPanel.add(
            new JScrollPane(notesArea),
            BorderLayout.CENTER);

    // =====================================
    // BUTTONS
    // =====================================

    JButton closeButton =
            new JButton("Close");

    closeButton.addActionListener(
            e -> dispose());

    JPanel buttonPanel =
            new JPanel();

    buttonPanel.add(closeButton);
    JButton exportButton = new JButton("Export");
    exportButton.addActionListener(e ->
{
    JFileChooser chooser =
            new JFileChooser();

    chooser.setSelectedFile(
            new java.io.File(
                    "Application_"
                            + application.getApplicationId()
                            + ".pdf"));

    int result =
            chooser.showSaveDialog(this);

    if(result == JFileChooser.APPROVE_OPTION)
    {
        utils.PDFExporter.exportApplication(
                application,
                chooser
                        .getSelectedFile()
                        .getAbsolutePath());

        JOptionPane.showMessageDialog(
                this,
                "PDF exported successfully.");
    }
});
    buttonPanel.add(exportButton);

    // =====================================
    // ADD COMPONENTS
    // =====================================

    mainPanel.add(applicantPanel);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(vehiclePanel);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(loanPanel);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(reviewPanel);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(buttonPanel);

    add(new JScrollPane(mainPanel));

    setVisible(true);
}
private JTextField createReadOnlyField(
        String value)
{
    JTextField field =
            new JTextField(value);

    field.setEditable(false);

    return field;
}
}