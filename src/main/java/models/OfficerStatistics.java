package models;

public class OfficerStatistics
{
    private int totalApplications;
    private int pendingApplications;
    private int approvedApplications;
    private int deniedApplications;
    private double averageLoanAmount;

    public int getTotalApplications()
    {
        return totalApplications;
    }

    public int getPendingApplications()
    {
        return pendingApplications;
    }

    public int getApprovedApplications()
    {
        return approvedApplications;
    }

    public int getDeniedApplications()
    {
        return deniedApplications;
    }

    public double getAverageLoanAmount()
    {
        return averageLoanAmount;
    }
}