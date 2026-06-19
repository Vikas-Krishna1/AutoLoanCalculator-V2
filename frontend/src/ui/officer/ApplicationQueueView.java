package ui.officer;
import db.DatabaseManager;
import models.LoanApplication;
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
public class ApplicationQueueView extends JFrame
{
    //Components/Variables
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
    private DatabaseManager db;
    int pending;
    int approved;
    int denied;
    private int officerId;
//Constructor
    public ApplicationQueueView(int officerId)
    {
        this.officerId = officerId;
        System.out.println("Officer ID :" + officerId);
        welcomeLabel =
        new JLabel(
                "Welcome, " + Session.getCurrentUser().getUsername());

officerIdLabel =
        new JLabel(
                "Officer ID: " + officerId);

roleLabel =
        new JLabel(
                "Role: Loan Officer");

sessionLabel =
        new JLabel(
                "Session: Active");

lastLoginLabel =
        new JLabel(
                "Last Login: " + LocalDateTime.now());
        JPanel statsPanel = new JPanel(new GridLayout(1,4));
        totalLabel = new JLabel("Total: 0");
pendingLabel = new JLabel("Pending: 0");
approvedLabel = new JLabel("Approved: 0");
deniedLabel = new JLabel("Denied: 0");

statsPanel.add(totalLabel);
statsPanel.add(pendingLabel);
statsPanel.add(approvedLabel);
statsPanel.add(deniedLabel);
        //DatabaseManager db = new DatabaseManager();
        db = new DatabaseManager();

        setTitle("Loan Officer - Application Queue");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    //Filters based on status of the loan application
    statusFilter = new JComboBox<>(new String[] { "All", "Pending", "Approved", "Declined" });

     statusFilter.addActionListener(e -> 
        loadApplications()
    );
   

        setLayout(new BorderLayout());

        JLabel title =
                new JLabel(
                        "Loan Application Queue",
                        SwingConstants.CENTER);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22));

        add(title, BorderLayout.NORTH);

        String[] columns =
        {
            "Officer ID",
            "Application ID",
            "Applicant",
            "Vehicle",
            "Loan Amount",
            "Status",
            "Assigned Officer"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0)
                {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column)
                    {
                        return false;
                    }
                };

        applicationTable =
                new JTable(tableModel);
        applicationTable.getColumnModel()
        .getColumn(5)
        .setCellRenderer(new StatusRenderer());

        applicationTable.setRowHeight(25);

        JScrollPane scrollPane =
                new JScrollPane(applicationTable);

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel =
                new JPanel();

        refreshButton =
                new JButton("Refresh");

        openButton =
                new JButton("Open Application");

        buttonPanel.add(refreshButton);
        buttonPanel.add(openButton);
        buttonPanel.add(statusFilter);
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        buttonPanel.add(new JLabel("Search"));      
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
       

        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e ->
        {
            loadApplications();
        });

        openButton.addActionListener(e ->
        {
            openSelectedApplication();
        });

        loadApplications();

        setVisible(true);
    searchButton.addActionListener(e ->
    {
    searchApplications();
    });
    JButton statsButton =
        new JButton("Statistics");

buttonPanel.add(statsButton);

statsButton.addActionListener(e ->
{
    new officerStatsView();
}); 
JButton assignButton =
        new JButton("Assign To Me");

assignButton.addActionListener(e ->
{
    int selectedRow = applicationTable.getSelectedRow();

    if(selectedRow == -1)
    {
        JOptionPane.showMessageDialog(
                this,
                "Select an application first.");
        return;
    }

    int applicationId =
            (Integer) tableModel.getValueAt(
                    selectedRow,
                    1);

    db.assignApplication(
            applicationId,
            officerId);      // <-- logged-in officer

    JOptionPane.showMessageDialog(
            this,
            "Application assigned to Officer #" + officerId);

    loadApplications();
});

buttonPanel.add(assignButton);
JButton reassignButton =
        new JButton("Reassign");

reassignButton.addActionListener(e ->
{
    int row =
            applicationTable.getSelectedRow();

    if(row == -1)
    {
        JOptionPane.showMessageDialog(
                this,
                "Select an application first");

        return;
    }

    int applicationId =
            (Integer)tableModel.getValueAt(
                    row,
                    1);

    String officerIdStr =
            JOptionPane.showInputDialog(
                    this,
                    "Enter new officer ID");

    if(officerIdStr == null)
        return;

    int newOfficerId =
            Integer.parseInt(officerIdStr);

    db.reassignApplication(
            applicationId,
            newOfficerId);

    loadApplications();

    JOptionPane.showMessageDialog(
            this,
            "Application reassigned");
});
buttonPanel.add(reassignButton);

JButton dashboardButton =
        new JButton("Dashboard");
 dashboardButton.addActionListener(e ->
{
        this.setVisible(false);
    new loanOfficerDashboard(officerId,this);
});

buttonPanel.add(dashboardButton);
JButton logoutButton = new JButton("Logout");

logoutButton.addActionListener(e ->
{
    int choice =
            JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);

    if(choice == JOptionPane.YES_OPTION)
    {
         Session.clearSession();

    dispose();

    new LoginView();
    }
});
buttonPanel.add(logoutButton);
    
}



    
    

private void loadApplications()
{
    tableModel.setRowCount(0);

    try
    {
        List<LoanApplication> applications =
                db.getAllApplications();

        String selectedStatus =
                (String) statusFilter.getSelectedItem();


        for(LoanApplication application : applications)
        {
            // Apply filter
            if(selectedStatus != null
                    && !selectedStatus.equalsIgnoreCase("ALL")
                    && !application.getStatus().equalsIgnoreCase(selectedStatus))
            {
                continue;
            }
            int OfficerID = db.getLoanOfficerId(application.getApplicationId());
             int assignedOfficer =
        db.getAssignedOfficerId(
                application.getApplicationId());

            String applicantName =
                    application
                            .getApplicant()
                            .getFullName();

            String vehicleName =
                    application
                            .getVehicle()
                            .getYear()
                            + " "
                            + application
                            .getVehicle()
                            .getMake()
                            + " "
                            + application
                            .getVehicle()
                            .getModel();

            tableModel.addRow(
                    new Object[]
                    {
                        OfficerID,
                        
                        application.getApplicationId(),
                        applicantName,
                        vehicleName,
                        String.format(
                                "$%.2f",
                                application
                                        .getLoan()
                                        .getAutoPrice()),
                        application.getStatus(),
                        assignedOfficer <= 0
                    ? "Unassigned"
                    : "Officer #" + assignedOfficer
                    });
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Unable to load applications.");
    }
}
private void searchApplications()
{
    String keyword =
            searchField.getText().trim();

    if(keyword.isEmpty())
    {
        loadApplications();
        return;
    }

    tableModel.setRowCount(0);

    List<LoanApplication> applications =
            db.searchApplications(keyword);

    for(LoanApplication application : applications)
    {
        tableModel.addRow(
                new Object[]
                {
                    db.getLoanOfficerId(application.getApplicationId()),
                    application.getApplicationId(),
                    application.getApplicant().getFullName(),
                    application.getVehicle().getYear()
                            + " "
                            + application.getVehicle().getMake()
                            + " "
                            + application.getVehicle().getModel(),
                    String.format(
                            "$%.2f",
                            application.getLoan().getAutoPrice()),
                    application.getStatus()
                });
    }
}
    private void openSelectedApplication()
    {
        int selectedRow =
                applicationTable.getSelectedRow();

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
                                1);

     

        //Open loan officer view
        new LoanOfficerView(applicationId);
    }
}
