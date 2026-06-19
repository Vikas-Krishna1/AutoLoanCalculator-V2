package models;
//LoanApplication Class==================
//Represents a loan application with associated 
// applicant, vehicle, and loan details
//======================================
public class LoanApplication
{
    // instance variables
    private int  application_date;
    private int applicationId;
    private String status;
    private Applicant applicant;
    private Vehicle vehicle;
    private AutoLoan loan;
    private String review_notes;
    private int review_by;
    
    // constructor
    public LoanApplication(
            int application_date,
        String status,
            int applicationId,
            Applicant applicant,
            Vehicle vehicle,
            AutoLoan loan)
    {
        this.application_date = application_date;
        this.status = status;
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.vehicle = vehicle;
        this.loan = loan;
    }
    
    // constructor
    public LoanApplication(
           
        String status,
            int applicationId,
            Applicant applicant,
            Vehicle vehicle,
            AutoLoan loan)
    {
        this.status = status;
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.vehicle = vehicle;
        this.loan = loan;
    }
    public LoanApplication(
            int application_date,
        String status,
            int applicationId,
            Applicant applicant,
            Vehicle vehicle,
            AutoLoan loan,
            String review_notes,
            int review_by)
    {
        this.application_date = application_date;
        this.status = status;
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.vehicle = vehicle;
        this.loan = loan;
        this.review_notes = review_notes;
        this.review_by = review_by;
    }

    // getters
    public String getReviewNotes() {
        return review_notes;
    }

    public int getReviewedBy() {
        return review_by;
    }
    public int getApplicationDate() {
        return application_date;
    }
    public String getStatus() {
        return status;
    }
    public int getApplicationId()
    {
        return applicationId;
    }

    public Applicant getApplicant()
    {
        return applicant;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public AutoLoan getLoan()
    {
        return loan;
    }
        // setters
    public void setApplicationId(int applicationId)
    {
        this.applicationId = applicationId;
    }

    public void setApplicant(Applicant applicant)
    {
        this.applicant = applicant;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public void setLoan(AutoLoan loan)
    {
        this.loan = loan;
    }
    public void setStatus(String status) {
        this.status = status;
    }   
    public void setApplication_date(int application_date) {
        this.application_date = application_date;
    }
    public void setReview_notes(String review_notes) {
        this.review_notes = review_notes;
    }
    public void setReview_by(int review_by) {
        this.review_by = review_by;
    }

     @Override
    public String toString() {
        return "Loan Application ID: " + applicationId +
                "\n\nApplicant Information:\n" + applicant +
                "\n\nVehicle Information:\n" + vehicle +
                "\n\nLoan Details:\n" + loan;
    }   
}
