package ui.officer;
import api.*;
import models.LoanApplication;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import models.*;
public class MyApplicationsView extends JFrame
{
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;

    private int officerId;
    private JFrame dashbaord;

    public MyApplicationsView(int officerId,JFrame dashboard)
    {
        this.officerId = officerId;
        this.dashbaord = dashboard;

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
                LoanApplicationApi.getApplicationsByOfficer(officerId);

        for(LoanApplication application : applications)
        {
                Applicant applicant = null;
                Vehicle vehicle = null;
                LoanApplication loan = null;
                try{
                applicant =
                        ApplicantApiClient.getApplicantById(
                                application.getApplicant_id());

                vehicle =
                        VehicleApiClient.getVehicleById(
                                application.getVehicle_id());

                loan = LoanApplicationApi.getApplicationById(
                        application.getApplication_id());
                }
                catch(Exception ex)
                {
                ex.printStackTrace();
                }
            tableModel.addRow(
                    new Object[]
                    {
                            applicant.getApplicantId(),

                            applicant.getFullName(),

                            vehicle.getYear()
                                    + " "
                                    + vehicle.getMake()
                                    + " "
                                    + vehicle.getModel(),

                            String.format(
                                    "$%.2f",
                                    loan.getAuto_price()),

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

        new LoanOfficerView(officerId,applicationId);
    }
}
