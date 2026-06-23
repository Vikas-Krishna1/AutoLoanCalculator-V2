package ui.officer;
import api.*;
import models.*;

import java.util.List;
import javax.swing.*;
import javax.xml.crypto.Data;
import utils.PDFExporter;
import java.io.File;
import java.awt.*;

public class loanOfficerDashboard extends JFrame
{
    private JLabel assignedLabel;
    private JLabel pendingLabel;
    private JLabel approvedLabel;
    private JLabel deniedLabel;
    private JLabel approvalRateLabel;

    private JButton queueButton;
    private JButton myApplicationsButton;
    private JButton exportButton;
    private JButton refreshButton;
    private JFrame QueueView;
    private int officerId;
    private int user_id;


    public loanOfficerDashboard(int user_id,int officerId, JFrame QueueView)
    {
        this.user_id = user_id;
        this.officerId = officerId;
        this.QueueView = QueueView;
        try{
                System.out.println("User ID: " + user_id);

                LoanOfficer officerID =
                 LoanOfficerApiClient.getLoanOfficerByUserId(user_id);
                System.out.println("Officer object: " + officerID);
                int loan_officer_id = officerID.getLoan_officer_id();
                this.officerId = loan_officer_id;
        }catch(Exception e){
        System.out.println(e.getMessage());

        }

        setTitle("Loan Officer Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10,10));

        // ==========================
        // TITLE
        // ==========================

        JLabel title =
                new JLabel(
                        "Loan Officer Dashboard",
                        SwingConstants.CENTER);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24));

        add(title, BorderLayout.NORTH);

        // ==========================
        // STATS PANEL
        // ==========================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                15,
                                15));

        assignedLabel =
                createStatCard(
                        "Assigned Applications",
                        "0");

        pendingLabel =
                createStatCard(
                        "Pending",
                        "0");

        approvedLabel =
                createStatCard(
                        "Approved",
                        "0");

        deniedLabel =
                createStatCard(
                        "Denied",
                        "0");

        statsPanel.add(wrapPanel(assignedLabel));
        statsPanel.add(wrapPanel(pendingLabel));
        statsPanel.add(wrapPanel(approvedLabel));
        statsPanel.add(wrapPanel(deniedLabel));

        add(statsPanel, BorderLayout.CENTER);

        // ==========================
        // BOTTOM PANEL
        // ==========================

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS));

        approvalRateLabel =
                new JLabel(
                        "Approval Rate: 0%",
                        SwingConstants.CENTER);

        approvalRateLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18));

        approvalRateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(approvalRateLabel);
        bottomPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel =
                new JPanel();

        queueButton =
                new JButton("Open Queue");

        myApplicationsButton =
                new JButton("My Applications");

        exportButton =
                new JButton("Export Report");

        refreshButton =
                new JButton("Refresh");

       

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // ==========================
        // BUTTON LISTENERS
        // ==========================
        JButton backButton
                = new JButton("Back");
        backButton.addActionListener(e ->
        {
            this.setVisible(false);
            QueueView.setVisible(true);
            dispose();
        });

        queueButton.addActionListener(e ->
        {

            new ApplicationQueueView(user_id);
        });

        myApplicationsButton.addActionListener(e ->
        {
            this.setVisible(false);
            new MyApplicationsView(officerId,this);
        });

        exportButton = new JButton("Export PDF");

exportButton.addActionListener(e ->
{
    JFileChooser chooser =
            new JFileChooser();

    chooser.setSelectedFile(
            new File(
                    "All_Applications.pdf"));

    if(chooser.showSaveDialog(this)
            == JFileChooser.APPROVE_OPTION)
    {


        PDFExporter.exportAllApplications(
                 LoanApplicationApi.getAlLoanApplications(),
                chooser.getSelectedFile()
                        .getAbsolutePath());
    }
});

        refreshButton.addActionListener(e ->
        {
            loadStats();
        });
         buttonPanel.add(queueButton);
        buttonPanel.add(myApplicationsButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        loadStats();

        setVisible(true);
    }

    // ==========================
    // LOAD STATS
    // ==========================

    private void loadStats()
{
    try
    {
        // Get all applications assigned to this officer
        List<LoanApplication> applications =
                LoanApplicationApi.getApplicationsByOfficer(
                        officerId);

        int assigned = applications.size();

        int pending = 0;
        int approved = 0;
        int denied = 0;

        for (LoanApplication application : applications)
        {
            String status = application.getStatus();

            if (status == null)
            {
                continue;
            }

            if (status.equalsIgnoreCase("Pending"))
            {
                pending++;
            }
            else if (status.equalsIgnoreCase("Approved"))
            {
                approved++;
            }
            else if (status.equalsIgnoreCase("Denied"))
            {
                denied++;
            }
        }

        double approvalRate =
                (approved + denied == 0)
                        ? 0
                        : ((double) approved /
                           (approved + denied)) * 100;

        assignedLabel.setText(
                String.valueOf(assigned));

        pendingLabel.setText(
                String.valueOf(pending));

        approvedLabel.setText(
                String.valueOf(approved));

        deniedLabel.setText(
                String.valueOf(denied));

        approvalRateLabel.setText(
                String.format(
                        "Approval Rate: %.1f%%",
                        approvalRate));
    }
    catch (Exception e)
    {
        JOptionPane.showMessageDialog(
                this,
                "Unable to load statistics:\n"
                        + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

    // ==========================
    // HELPER METHODS
    // ==========================

    private JLabel createStatCard(
            String title,
            String value)
    {
        JLabel label =
                new JLabel(
                        value,
                        SwingConstants.CENTER);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24));

        label.setBorder(
                BorderFactory.createTitledBorder(
                        title));

        return label;
    }

    private JPanel wrapPanel(
            JLabel label)
    {
        JPanel panel =
                new JPanel(
                        new BorderLayout());

        panel.add(label);

        return panel;
    }
}
