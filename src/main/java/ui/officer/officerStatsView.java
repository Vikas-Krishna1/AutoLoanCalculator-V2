package ui.officer;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import api.*;
import models.*;
import java.awt.*;

public class officerStatsView extends JFrame
{
        private int officerId;
    public officerStatsView(int officerId)
    {
        this.officerId = officerId;
        setTitle("Loan Officer Statistics");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        int total = 0;
int pending = 0;
int approved = 0;
int denied = 0;
double avgLoan = 0;

try
{
    OfficerStatistics stats =
            LoanOfficerApiClient.getOfficerStatistics(
                    officerId);

    total =
            stats.getTotalApplications();

    pending =
            stats.getPendingApplications();

    approved =
            stats.getApprovedApplications();

    denied =
            stats.getDeniedApplications();

    avgLoan =
            stats.getAverageLoanAmount();
}
catch(Exception e)
{
    e.printStackTrace();

    JOptionPane.showMessageDialog(
            this,
            "Failed to load statistics");
}

        double approvalRate = 0;

        if(total > 0)
        {
            approvalRate =
                    ((double) approved / total)
                            * 100;
        }

        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS));

        // ==========================
        // Title
        // ==========================

        JLabel title =
                new JLabel(
                        "Loan Officer Statistics",
                        SwingConstants.CENTER);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22));

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));

        // ==========================
        // Application Stats
        // ==========================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10));

        statsPanel.setBorder(
                new TitledBorder(
                        "Application Statistics"));

        statsPanel.add(
                new JLabel("Total Applications"));

        statsPanel.add(
                new JLabel(
                        String.valueOf(total)));

        statsPanel.add(
                new JLabel("Pending"));

        statsPanel.add(
                new JLabel(
                        String.valueOf(pending)));

        statsPanel.add(
                new JLabel("Approved"));

        statsPanel.add(
                new JLabel(
                        String.valueOf(approved)));

        statsPanel.add(
                new JLabel("Denied"));

        statsPanel.add(
                new JLabel(
                        String.valueOf(denied)));

        mainPanel.add(statsPanel);

        mainPanel.add(
                Box.createVerticalStrut(15));

        // ==========================
        // Performance Stats
        // ==========================

        JPanel performancePanel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10));

        performancePanel.setBorder(
                new TitledBorder(
                        "Performance Metrics"));

        performancePanel.add(
                new JLabel(
                        "Approval Rate"));

        performancePanel.add(
                new JLabel(
                        String.format(
                                "%.2f%%",
                                approvalRate)));

        performancePanel.add(
                new JLabel(
                        "Average Loan Amount"));

        performancePanel.add(
                new JLabel(
                        String.format(
                                "$%,.2f",
                                avgLoan)));

        mainPanel.add(performancePanel);

        mainPanel.add(
                Box.createVerticalStrut(20));

        // ==========================
        // Close Button
        // ==========================

        JButton closeButton =
                new JButton("Close");

        closeButton.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        closeButton.addActionListener(
                e -> dispose());

        mainPanel.add(closeButton);

        add(mainPanel);

        setVisible(true);
    }
}
