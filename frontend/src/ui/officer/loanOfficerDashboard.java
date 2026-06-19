package ui.officer;


import db.DatabaseManager;
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


    public loanOfficerDashboard(int officerId, JFrame QueueView)
    {
        this.officerId = officerId;
        this.QueueView = QueueView;
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
            // TODO
            new ApplicationQueueView(officerId);
        });

        myApplicationsButton.addActionListener(e ->
        {
            this.setVisible(false);
            new MyApplicationsView(1,this);
        });

       JButton exportButton = new JButton("Export PDF");

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
        DatabaseManager db =
                new DatabaseManager();

        PDFExporter.exportAllApplications(
                db.getAllApplications(),
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
        DatabaseManager db = new DatabaseManager();


    int assigned =
            db.getAssignedApplicationsCount(officerId);

    int pending =
            db.getPendingApplicationsCount(officerId);

    int approved =
            db.getApprovedApplicationsCount(officerId);

    int denied =
            db.getDeniedApplicationsCount(officerId);
        

        

        double approvalRate =
                approved + denied == 0
                        ? 0
                        : ((double) approved /
                        (approved + denied)) * 100;

        assignedLabel.setText(String.valueOf(assigned));
        pendingLabel.setText(String.valueOf(pending));
        approvedLabel.setText(String.valueOf(approved));
        deniedLabel.setText(String.valueOf(denied));

        approvalRateLabel.setText(
                String.format(
                        "Approval Rate: %.1f%%",
                        approvalRate));
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
