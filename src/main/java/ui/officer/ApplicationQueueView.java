package ui.officer;

import api.*;
import models.Applicant;
import models.LoanApplication;
import models.LoanOfficer;
import models.Vehicle;
import api.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import ui.table.*;
import ui.login.LoginView;
import ui.officer.*;
import ui.Session;

//LOAN OFFICER APPLICATION QUEUE
//This class represents the loan officer application queue view
//It allows the loan officer to view and manage loan applications   
//and assign loan officers to applications
public class ApplicationQueueView extends JFrame {
        // Components/Variables
        private JTextField searchField;
        private JButton searchButton;
        private JTable applicationTable;
        private DefaultTableModel tableModel;
        private JButton refreshButton;
        private JButton openButton;
        private JComboBox<String> statusFilter;
        private JLabel totalLabel;
        private JLabel pendingLabel;
        private JLabel approvedLabel;
        private JLabel deniedLabel;
        private JButton StatsButton;
        private JLabel welcomeLabel;
        private JLabel officerIdLabel;
        private JLabel roleLabel;
        private JLabel sessionLabel;
        private JLabel lastLoginLabel;
        int pending;
        int approved;
        int denied;
        private int officerId;
        private int user_id;

        // Constructor
        public ApplicationQueueView(int user_id) {
                this.user_id = user_id;

                LoanOfficer officerID = null;

                try {
                        officerID = LoanOfficerApiClient.getLoanOfficerByUserId(user_id);

                } catch (Exception e) {
                        e.printStackTrace();
                }

                if (officerID == null) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "No loan officer found for user ID " + user_id);
                        return;
                }

                officerId = officerID.getLoan_officer_id();
                welcomeLabel = new JLabel(
                                "Welcome, " + Session.getCurrentUser().getUsername());

                officerIdLabel = new JLabel(
                                "Officer ID: " + officerId);

                roleLabel = new JLabel(
                                "Role: Loan Officer");

                sessionLabel = new JLabel(
                                "Session: Active");

                lastLoginLabel = new JLabel(
                                "Last Login: " + LocalDateTime.now());
                JPanel statsPanel = new JPanel(new GridLayout(1, 4));
                totalLabel = new JLabel("Total: 0");
                pendingLabel = new JLabel("Pending: 0");
                approvedLabel = new JLabel("Approved: 0");
                deniedLabel = new JLabel("Denied: 0");

                statsPanel.add(totalLabel);
                statsPanel.add(pendingLabel);
                statsPanel.add(approvedLabel);
                statsPanel.add(deniedLabel);
                // DatabaseManager db = new DatabaseManager();

                setTitle("Loan Officer - Application Queue");
                setSize(900, 600);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Filters based on status of the loan application
                statusFilter = new JComboBox<>(new String[] {
                                "ALL",
                                "PENDING",
                                "UNDER_REVIEW",
                                "APPROVED",
                                "DENIED"
                });

                statusFilter.addActionListener(e -> loadApplications());

                setLayout(new BorderLayout());

                JLabel title = new JLabel(
                                "Loan Application Queue",
                                SwingConstants.CENTER);

                title.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                22));

                add(title, BorderLayout.NORTH);

                String[] columns = {
                                "Officer ID",
                                "Application ID",
                                "Applicant",
                                "Vehicle",
                                "Loan Amount",
                                "Status",
                                "Assigned Officer"
                };

                tableModel = new DefaultTableModel(
                                columns,
                                0) {
                        @Override
                        public boolean isCellEditable(
                                        int row,
                                        int column) {
                                return false;
                        }
                };

                applicationTable = new JTable(tableModel);
                applicationTable.getColumnModel()
                                .getColumn(5)
                                .setCellRenderer(new StatusRenderer());

                applicationTable.setRowHeight(25);

                JScrollPane scrollPane = new JScrollPane(applicationTable);

                add(scrollPane, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();

                refreshButton = new JButton("Refresh");

                openButton = new JButton("Open Application");

                buttonPanel.add(refreshButton);
                buttonPanel.add(openButton);
                buttonPanel.add(statusFilter);
                searchField = new JTextField(20);
                searchButton = new JButton("Search");
                buttonPanel.add(new JLabel("Search"));
                buttonPanel.add(searchField);
                buttonPanel.add(searchButton);

                add(buttonPanel, BorderLayout.SOUTH);

                refreshButton.addActionListener(e -> {
                        loadApplications();
                });

                openButton.addActionListener(e -> {
                        openSelectedApplication();
                });

                loadApplications();

                setVisible(true);
                searchButton.addActionListener(e -> {
                        searchApplications();
                });
                JButton statsButton = new JButton("Statistics");

                buttonPanel.add(statsButton);

                statsButton.addActionListener(e -> {
                        new officerStatsView(officerId);
                });
                JButton assignButton = new JButton("Assign To Me");

                assignButton.addActionListener(e -> {
                        int selectedRow = applicationTable.getSelectedRow();

                        if (selectedRow == -1) {
                                JOptionPane.showMessageDialog(
                                                this,
                                                "Select an application first.");
                                return;
                        }

                        int applicationId = (Integer) tableModel.getValueAt(
                                        selectedRow,
                                        1); // Application ID column

                        try {
                                LoanApplicationApi.assignOfficer(
                                                applicationId,
                                                officerId);

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Application assigned to Officer #"
                                                                + officerId);

                                // Refresh table immediately
                                loadApplications();
                        } catch (Exception ex) {
                                ex.printStackTrace();

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Unable to assign application.");
                        }
                });

                buttonPanel.add(assignButton);
                JButton reassignButton = new JButton("Reassign");

                reassignButton.addActionListener(e -> {
                        int row = applicationTable.getSelectedRow();

                        if (row == -1) {
                                JOptionPane.showMessageDialog(
                                                this,
                                                "Select an application first");

                                return;
                        }

                        int applicationId = (Integer) tableModel.getValueAt(
                                        row,
                                        1);

                        String officerIdStr = JOptionPane.showInputDialog(
                                        this,
                                        "Enter new officer ID");

                        if (officerIdStr == null)
                                return;

                        int newOfficerId = Integer.parseInt(officerIdStr);

                        LoanApplicationApi.assignOfficer(
                                        applicationId,
                                        newOfficerId);

                        loadApplications();

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Application reassigned");
                });
                buttonPanel.add(reassignButton);

                JButton dashboardButton = new JButton("Dashboard");
                dashboardButton.addActionListener(e -> {
                        this.setVisible(false);
                        new loanOfficerDashboard(user_id, officerId, this);
                });

                buttonPanel.add(dashboardButton);
                JButton logoutButton = new JButton("Logout");

                logoutButton.addActionListener(e -> {
                        int choice = JOptionPane.showConfirmDialog(
                                        this,
                                        "Are you sure you want to logout?",
                                        "Logout",
                                        JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.YES_OPTION) {
                                Session.clearSession();

                                dispose();

                                new LoginView();
                        }
                });
                buttonPanel.add(logoutButton);

        }

        private void loadApplications() {
                tableModel.setRowCount(0);

                try {
                        List<LoanApplication> applications = LoanApplicationApi.getAlLoanApplications();

                        String selectedStatus = (String) statusFilter.getSelectedItem();

                        for (LoanApplication application : applications) {
                                String status = application.getStatus();

                                // Status filter
                                if (selectedStatus != null
                                                && !selectedStatus.equalsIgnoreCase("ALL")
                                                && status != null
                                                && !status.equalsIgnoreCase(selectedStatus)) {
                                        continue;
                                }

                                Integer assignedOfficer = application.getLoan_officer_id();

                                // Show applications assigned to me OR unassigned
                                if (assignedOfficer != null &&
                                                !assignedOfficer.equals(officerId)) {
                                        continue;
                                }

                                Applicant applicant = ApplicantApiClient.getApplicantById(
                                                application.getApplicant_id());

                                Vehicle vehicle = VehicleApiClient.getVehicleById(
                                                application.getVehicle_id());

                                String vehicleName = vehicle.getYear()
                                                + " "
                                                + vehicle.getMake()
                                                + " "
                                                + vehicle.getModel();

                                String assignedOfficerStr = (assignedOfficer == null)
                                                ? "Unassigned"
                                                : "Officer #" + assignedOfficer;

                                tableModel.addRow(
                                                new Object[] {
                                                                assignedOfficer, // Officer ID
                                                                application.getApplication_id(), // Application ID
                                                                applicant.getFullName(), // Applicant
                                                                vehicleName, // Vehicle
                                                                String.format(
                                                                                "$%.2f",
                                                                                application.getLoan_amount()),
                                                                status, // Status
                                                                assignedOfficerStr // Assigned Officer
                                                });
                        }
                } catch (Exception e) {
                        e.printStackTrace();

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Unable to load applications.");
                }
        }

        private void searchApplications() {
                String keyword = searchField.getText().trim();

                if (keyword.isEmpty()) {
                        loadApplications();
                        return;
                }

                tableModel.setRowCount(0);

                try {
                        List<LoanApplication> applications = LoanApplicationApi.getAlLoanApplications();

                        for (LoanApplication application : applications) {
                                Integer assignedOfficer = application.getLoan_officer_id();

                                // Show applications assigned to me OR unassigned
                                if (assignedOfficer != null &&
                                                !assignedOfficer.equals(officerId)) {
                                        continue;
                                }

                                Applicant applicant = ApplicantApiClient.getApplicantById(
                                                application.getApplicant_id());

                                Vehicle vehicle = VehicleApiClient.getVehicleById(
                                                application.getVehicle_id());

                                String applicantName = applicant.getFullName();

                                // Search by applicant name
                                if (!applicantName.toLowerCase()
                                                .contains(keyword.toLowerCase())) {
                                        continue;
                                }

                                String vehicleName = vehicle.getYear()
                                                + " "
                                                + vehicle.getMake()
                                                + " "
                                                + vehicle.getModel();

                                String assignedOfficerStr = assignedOfficer == null
                                                ? "Unassigned"
                                                : "Officer #" + assignedOfficer;

                                tableModel.addRow(
                                                new Object[] {
                                                                assignedOfficer, // Officer ID
                                                                application.getApplication_id(), // Application ID
                                                                applicantName, // Applicant
                                                                vehicleName, // Vehicle
                                                                String.format(
                                                                                "$%.2f",
                                                                                application.getLoan_amount()),
                                                                application.getStatus(), // Status
                                                                assignedOfficerStr // Assigned Officer
                                                });
                        }
                } catch (Exception e) {
                        e.printStackTrace();

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Search failed.");
                }
        }

        private void openSelectedApplication() {
                int selectedRow = applicationTable.getSelectedRow();

                if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "Select an application first.");

                        return;
                }

                int applicationId = (Integer) tableModel.getValueAt(
                                selectedRow,
                                1);

                // Open loan officer view
                new LoanOfficerView(officerId, applicationId);
        }
}
