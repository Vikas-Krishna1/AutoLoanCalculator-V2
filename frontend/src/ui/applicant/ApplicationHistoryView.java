package ui.applicant;

import db.DatabaseManager;
import models.LoanApplication;
import services.LoanCalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ApplicationHistoryView extends JFrame
{
    private int userId;
    private JFrame dashboard;

    private JTable historyTable;
    private DefaultTableModel tableModel;

    private JButton refreshButton;
    private JButton viewButton;
    private JButton backButton;

    private JComboBox<String> statusFilter;

    private DatabaseManager db;

    public ApplicationHistoryView(int userId,JFrame dashboard)
    {
        this.userId = userId;
        this.dashboard = dashboard; 
        this.db = new DatabaseManager();

        setTitle("Application History");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel title =
                new JLabel(
                        "My Loan Applications",
                        SwingConstants.CENTER);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22));

        add(title, BorderLayout.NORTH);

        String[] columns =
        {
            "Date",
            "Application ID",
            "Applicant",
            "Vehicle",
            "Loan Amount",
            "Monthly Payment",
            "Status"
        };

        tableModel =
                new DefaultTableModel(columns, 0)
                {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column)
                    {
                        return false;
                    }
                };

        historyTable =
                new JTable(tableModel);

        historyTable.setRowHeight(25);

        add(
                new JScrollPane(historyTable),
                BorderLayout.CENTER);

        JPanel bottomPanel =
                new JPanel();

        statusFilter =
                new JComboBox<>(
                        new String[]
                        {
                            "ALL",
                            "PENDING",
                            "APPROVED",
                            "DENIED"
                        });

        refreshButton =
                new JButton("Refresh");

        viewButton =
                new JButton("View Details");
        backButton =
                new JButton("Back");

        bottomPanel.add(backButton);

        bottomPanel.add(
                new JLabel("Status:"));

        bottomPanel.add(statusFilter);

        bottomPanel.add(refreshButton);

        bottomPanel.add(viewButton);

        add(bottomPanel, BorderLayout.SOUTH);

        statusFilter.addActionListener(e ->
        {
            loadApplications();
        });

        refreshButton.addActionListener(e ->
        {
            loadApplications();
        });

        viewButton.addActionListener(e ->
        {
            openSelectedApplication();
        });

        loadApplications();

        setVisible(true);
        backButton.addActionListener(e->
        {
            dashboard.setVisible(true);
            this.dispose();
        }
                
        );
    }

    private void loadApplications()
    {
        tableModel.setRowCount(0);

        try
        {
            List<LoanApplication> applications =
                    db.getApplicationsByUserId(
                            userId);

            String selectedStatus =
                    (String)
                            statusFilter.getSelectedItem();

            for(LoanApplication application
                    : applications)
            {
                if(selectedStatus != null
                        && !selectedStatus.equalsIgnoreCase("ALL")
                        && !application.getStatus()
                        .equalsIgnoreCase(selectedStatus))
                {
                    continue;
                }

                String vehicleName =
                        application.getVehicle().getYear()
                                + " "
                                + application.getVehicle().getMake()
                                + " "
                                + application.getVehicle().getModel();

                tableModel.addRow(
                        new Object[]
                        {
                            db.getLoanApplicationDate(application.getApplicationId()),
                            application.getApplicationId(),

                            application.getApplicant().getFullName(),

                            vehicleName,

                            String.format(
                                    "$%.2f",
                                    LoanCalculator
                                            .calculateLoanAmount(
                                                    application.getLoan())),

                            String.format(
                                    "$%.2f",
                                    LoanCalculator
                                            .calculateMonthlyPayment(
                                                    application.getLoan())),

                            application.getStatus()
                        });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load application history.");
        }
    }

    private void openSelectedApplication()
    {
        int selectedRow =
                historyTable.getSelectedRow();

        if(selectedRow == -1)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an application first.");

            return;
        }

        int applicationId =
                (Integer)
                        tableModel.getValueAt(
                                selectedRow,
                                0);

        new ViewForm(applicationId);
    }
}