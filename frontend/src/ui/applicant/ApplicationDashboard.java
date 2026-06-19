package ui.applicant;
//======================================
//APPLICANT APPLICATION DASHBOARD
//This class represents the applicant application dashboard
//It allows the applicant to view their loan applications
//and manage their loan applications
//======================================

//======================================

//Imports
import db.DatabaseManager;
import models.LoanApplication;
import services.LoanCalculator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import ui.table.*;
public class ApplicationDashboard extends JFrame
{
    //Feilds, and variables and UI components
    private int userId;
    //Table Components
    private JTable applicationTable;
    private DefaultTableModel tableModel;
    //Button Components
    //Refresh Button
    private JButton refreshButton;
    //New Application Button
    private JButton newApplicationButton;
    //View Button
    private JButton viewButton;
    //Application History Button
    private JButton historyButton;
    //Back Button
    private JButton backButton;
    //Database Manager connection
    private DatabaseManager db;

    public ApplicationDashboard(int userId)
    {
        //Constructor-> Takes in user_id as a parameter 
        //and sets it to the userId field
        //Sets the title, size, location, and close operation
        this.userId = userId;
        this.db = new DatabaseManager();

        setTitle("Applicant Dashboard");
        setSize(900,600);
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
            "Application ID",
            "Applicant",
            "Vehicle",
            "Loan Amount",
            "Monthly Payment",
            "Status"
        };

        tableModel =
                new DefaultTableModel(columns,0)
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
        applicationTable.getColumnModel().getColumn(1).setCellRenderer(new StatusRenderer());
        

        add(
                new JScrollPane(applicationTable),
                BorderLayout.CENTER);

        JPanel buttonPanel =
                new JPanel();

        refreshButton =
                new JButton("Refresh");

        newApplicationButton =
                new JButton("New Application");

        viewButton =
                new JButton("View Details");

        buttonPanel.add(refreshButton);
        buttonPanel.add(newApplicationButton);
        buttonPanel.add(viewButton);

        historyButton = new JButton("Application History");
        buttonPanel.add(historyButton);
        historyButton.addActionListener(e -> 
            
            new ApplicationHistoryView(userId,this)
        );

        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(
                e -> loadApplications());

        newApplicationButton.addActionListener(e -> 
                {
                System.out.println("Opening new application form");
                new LoanForm(userId);
                System.out.println("New application form opened");
                });

        viewButton.addActionListener(
                e -> viewSelectedApplication());

        loadApplications();

        setVisible(true);
    }
    

    private void loadApplications()
    {
        String applicant = "";
        tableModel.setRowCount(0);

        try
        {
            List<LoanApplication> applications =
                    db.getApplicationsByUserId(userId);
            for (LoanApplication application : applications)
            {
                 applicant =
                        application.getApplicant().getFullName();
            }

            for(LoanApplication application : applications)
            {
                String vehicle =
                        application.getVehicle().getYear()
                        + " "
                        + application.getVehicle().getMake()
                        + " "
                        + application.getVehicle().getModel();

                tableModel.addRow(
                        new Object[]
                        {
                            application.getApplicationId(),
                            applicant,
                            vehicle,
                            String.format(
                                    "$%.2f",
                                    LoanCalculator.calculateLoanAmount(
                                            application.getLoan())
                                    ),
                            String.format(
                                    "$%.2f",
                                    LoanCalculator.calculateMonthlyPayment(
                                            application.getLoan())
                                    ),
                            application.getStatus()
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

    private void viewSelectedApplication()
    {
        int row =
                applicationTable.getSelectedRow();

        if(row == -1)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an application.");

            return;
        }

        int applicationId =
                (Integer)
                        tableModel.getValueAt(
                                row,
                                0);
            //Open ViewForm
            new ViewForm(applicationId);
        
    }
}
