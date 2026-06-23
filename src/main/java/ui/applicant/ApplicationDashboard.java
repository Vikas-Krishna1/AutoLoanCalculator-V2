package ui.applicant;
//======================================

//APPLICANT APPLICATION DASHBOARD
//This class represents the applicant application dashboard
//It allows the applicant to view their loan applications
//and manage their loan applications
//======================================

//======================================

//Imports
import ui.*;
import ui.login.LoginView;
import api.*;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import ui.table.*;

public class ApplicationDashboard extends JFrame {
        // Feilds, and variables and UI components
        private int userId;
        // Table Components
        private JTable applicationTable;
        private DefaultTableModel tableModel;
        // Button Components
        // Refresh Button
        private JButton refreshButton;
        // New Application Button
        private JButton newApplicationButton;
        // View Button
        private JButton viewButton;
        // Application History Button
        private JButton historyButton;
        // Back Button
        private JButton backButton;
        private JButton logoutButton;

        // Database Manager connection

        public ApplicationDashboard(int userId) {
                // Constructor-> Takes in user_id as a parameter
                // and sets it to the userId field
                // Sets the title, size, location, and close operation
                this.userId = userId;

                setTitle("Applicant Dashboard");
                setSize(900, 600);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                setLayout(new BorderLayout());

                JLabel title = new JLabel(
                                "My Loan Applications",
                                SwingConstants.CENTER);

                title.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                22));

                add(title, BorderLayout.NORTH);

                String[] columns = {
                                "Application ID",
                                "Applicant",
                                "Vehicle",
                                "Loan Amount",
                                "Monthly Payment",
                                "Status"
                };

                tableModel = new DefaultTableModel(columns, 0) {
                        @Override
                        public boolean isCellEditable(
                                        int row,
                                        int column) {
                                return false;
                        }
                };

                applicationTable = new JTable(tableModel);
                applicationTable.getColumnModel().getColumn(1).setCellRenderer(new StatusRenderer());

                add(
                                new JScrollPane(applicationTable),
                                BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();

                refreshButton = new JButton("Refresh");

                newApplicationButton = new JButton("New Application");

                viewButton = new JButton("View Details");
                logoutButton = new JButton("Logout");

                buttonPanel.add(refreshButton);
                buttonPanel.add(newApplicationButton);
                buttonPanel.add(viewButton);
                buttonPanel.add(logoutButton);

                historyButton = new JButton("Application History");
                buttonPanel.add(historyButton);
                historyButton.addActionListener(e ->

                new ApplicationHistoryView(userId, this));

                add(buttonPanel, BorderLayout.SOUTH);

                refreshButton.addActionListener(
                                e -> loadApplications());
                logoutButton.addActionListener(e -> {

                        dispose();
                        new LoginView();
                });

                newApplicationButton.addActionListener(e -> {
                        new LoanForm(userId);
                });

                viewButton.addActionListener(
                                e -> viewSelectedApplication());

                loadApplications();

                setVisible(true);
        }

        private void loadApplications() {
                tableModel.setRowCount(0);

                try {
                        // userId -> applicant
                        Applicant applicant = ApplicantApiClient.getApplicantByUserId(userId);

                        if (applicant == null) {
                                JOptionPane.showMessageDialog(
                                                this,
                                                "Applicant profile not found.");

                                return;
                        }

                        // applicantId -> applications
                        List<LoanApplication> applications = LoanApplicationApi
                                        .getApplicationsByApplicant(applicant.getApplicantId());

                        if (applications == null) {
                                applications = new ArrayList<>();
                        }

                        for (LoanApplication application : applications) {
                                Vehicle vehicle = VehicleApiClient.getVehicleById(
                                                application.getVehicle_id());

                                String vehicleString = vehicle.getYear()
                                                + " "
                                                + vehicle.getMake()
                                                + " "
                                                + vehicle.getModel();

                                tableModel.addRow(
                                                new Object[] {
                                                                application.getApplication_id(),
                                                                applicant.getFullName(),
                                                                vehicleString,
                                                                String.format("$%.2f", application.getLoan_amount()),
                                                                String.format("$%.2f",
                                                                                application.getMonthly_payment()),
                                                                application.getStatus()
                                                });
                        }
                } catch (Exception e) {
                        e.printStackTrace();

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Unable to load applications.");
                }
        }

        private void viewSelectedApplication() {
                int row = applicationTable.getSelectedRow();

                if (row == -1) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "Select an application.");

                        return;
                }

                int applicationId = (Integer) tableModel.getValueAt(
                                row,
                                0);
                // Open ViewForm
                new ViewForm(applicationId);

        }
}
