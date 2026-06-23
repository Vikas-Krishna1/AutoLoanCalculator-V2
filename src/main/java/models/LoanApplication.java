package models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanApplication
{
  
    @JsonProperty("application_date")
    private String application_date;
    @JsonProperty("application_id")
    private int application_id;
    @JsonProperty("loan_officer_id")
    private Integer loan_officer_id;
    @JsonProperty("applicant_id")
    private int applicant_id;
    @JsonProperty("vehicle_id")
    private int vehicle_id;
    @JsonProperty("loan_amount")
    private double loan_amount;
    @JsonProperty("monthly_payment")
    private double monthly_payment;
    @JsonProperty("auto_price")
    private double auto_price;
    @JsonProperty("down_payment")
    private double down_payment;
    @JsonProperty("loan_term")
    private int loan_term;
    @JsonProperty("interest_rate")
    private double interest_rate;
    @JsonProperty("sales_tax")
    private double sales_tax;
    @JsonProperty("fees")
    private double fees;
    @JsonProperty("cash_incentive")
    private double cash_incentive;
    @JsonProperty("status")
    private String status;
    @JsonProperty("review_notes")
    private String review_notes;
    @JsonProperty("reviewed_by")
    private Integer reviewed_by;
    @JsonProperty("created_at")
    private String created_at;

    //COnstructors
    public LoanApplication() {
        
    }
    public LoanApplication(String application_date,int application_id, int applicant_id, int vehicle_id, double auto_price, double down_payment, int loan_term, double interest_rate, double sales_tax, double fees, double cash_incentive, String notes, String created_at, double loan_amount, double monthly_payment, Integer reviewed_by, Integer loan_officer_id) {
        this.application_date = application_date;
        this.application_id = application_id;
        this.applicant_id = applicant_id;
        this.vehicle_id = vehicle_id;
        this.auto_price = auto_price;
        this.down_payment = down_payment;
        this.loan_term = loan_term;
        this.interest_rate = interest_rate;
        this.sales_tax = sales_tax;
        this.fees = fees;
        this.cash_incentive = cash_incentive;
        this.status = "Pending";
        this.review_notes = notes;
        this.created_at = created_at;
        this.loan_amount = loan_amount;
        this.monthly_payment = monthly_payment;
        this.reviewed_by = reviewed_by;
        this.loan_officer_id = loan_officer_id;
    }

//Getters
@JsonProperty("created_at")
public String getCreated_at() {
    return created_at;
}
public String getApplication_date() {
    return application_date;
}
public int getApplication_id() {
    return application_id;
}
public Integer getLoan_officer_id() {
    return loan_officer_id;
}

public int getApplicant_id() {
    return applicant_id;
}

public int getVehicle_id() {
    return vehicle_id;
}

public double getLoan_amount() {
    return loan_amount;
}

public double getMonthly_payment() {
    
    return monthly_payment;
}

public double getAuto_price() {
    return auto_price;
}

public double getDown_payment() {
    return down_payment;
}

public int getLoan_term() {
    return loan_term;
}

public double getInterest_rate() {
    return interest_rate;
}

public double getSales_tax() {
    return sales_tax;
}

public double getFees() {
    return fees;
}

public double getCash_incentive() {
    return cash_incentive;
}

public String getStatus() {
    return status;
}

public String getReview_notes() {
    return review_notes;
}

public Integer getReviewed_by() {
    return reviewed_by;
}
//Setters
public void setLoan_officer_id(Integer loan_officer_id) {
    this.loan_officer_id = loan_officer_id;
}
public void setApplication_date(String application_date) {
    this.application_date = application_date;
}
public void setApplication_id(int application_id) {
    this.application_id = application_id;
}
public void setApplicant_id(int applicant_id) {
    this.applicant_id = applicant_id;
}
public void setVehicle_id(int vehicle_id) {
    this.vehicle_id = vehicle_id;
}
public void setLoan_amount(double loan_amount) {
    this.loan_amount = loan_amount;
}
public void setMonthly_payment(double monthly_payment) {
    this.monthly_payment = monthly_payment;
}
public void setAuto_price(double auto_price) {
    this.auto_price = auto_price;
}
public void setDown_payment(double down_payment) {
    this.down_payment = down_payment;
}
public void setLoan_term(int loan_term) {
    this.loan_term = loan_term;
}
public void setInterest_rate(double interest_rate) {
    this.interest_rate = interest_rate;
}
public void setSales_tax(double sales_tax) {
    this.sales_tax = sales_tax;
}
public void setFees(double fees) {
    this.fees = fees;
}
public void setCash_incentive(double cash_incentive) {
    this.cash_incentive = cash_incentive;
}
public void setStatus(String status) {
    this.status = status;
}
public void setReview_notes(String review_notes) {
    this.review_notes = review_notes;
}
public void setReviewed_by(Integer reviewed_by) {
    this.reviewed_by = reviewed_by;
}

public void setCreated_at(String created_at) {
    this.created_at = created_at;
}
}