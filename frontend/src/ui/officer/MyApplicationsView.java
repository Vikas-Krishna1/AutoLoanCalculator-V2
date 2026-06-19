package ui.officer;
import db.DatabaseManager;
import models.LoanApplication;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class MyApplicationsView extends JFrame
{
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private DatabaseManager db;

    private int officerId;
    private JFrame dashbaord;

    public MyApplicationsView(int officerId,JFrame dashboard)
    {
        this.officerId = officerId;
        this.dashbaord = dashboard;

        db = new DatabaseManager();

        setTitle("My Assigned Applications");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel title =
                new JLabel(
                        "My Assigned Applications",
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

        table =
                new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER);

        JButton openButton =
                new JButton(
                        "Open Application");

        JButton refreshButton =
                new JButton(
                        "Refresh");

        JPanel buttonPanel =
                new JPanel();

        
        

        add(
                buttonPanel,
                BorderLayout.SOUTH);

        refreshButton.addActionListener(
                e -> loadApplications());

        openButton.addActionListener(
                e -> openApplication());

         backButton = new JButton("Back");
        backButton.addActionListener(e -> 
        {
        dashbaord.setVisible(true);
        dispose();
        });

        buttonPanel.add(backButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(openButton);


        loadApplications();

        setVisible(true);
    }

    private void loadApplications()
    {
        tableModel.setRowCount(0);

        List<LoanApplication> applications =
                db.getAssignedApplications(officerId);

        for(LoanApplication application : applications)
        {
            tableModel.addRow(
                    new Object[]
                    {
                            application.getApplicationId(),

                            application.getApplicant()
                                    .getFullName(),

                            application.getVehicle()
                                    .getYear()
                                    + " "
                                    + application.getVehicle()
                                    .getMake()
                                    + " "
                                    + application.getVehicle()
                                    .getModel(),

                            String.format(
                                    "$%.2f",
                                    application.getLoan()
                                            .getAutoPrice()),

                            application.getStatus()
                    });
        }
    }

    private void openApplication()
    {
        int row =
                table.getSelectedRow();

        if(row == -1)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an application first");

            return;
        }

        int applicationId =
                (Integer)
                        tableModel.getValueAt(
                                row,
                                0);

        new LoanOfficerView(applicationId);
    }
}
