package models;
import java.sql.Date;
//LoanOfficer
//This class represents a loan officer.
public class LoanOfficer
{
    private int loan_officer_id;
    private int user_id;
    private String full_name;
    private String email;
    private String phone;
    private String employee_number;
    public LoanOfficer(int loan_officer_id, int user_id, String full_name, String email, String phone, String employee_number)
    {
        this.loan_officer_id = loan_officer_id;
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.employee_number = employee_number;
    }

    public int getLoan_officer_id()
    {
        return loan_officer_id;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public String getFull_name()
    {
        return full_name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getEmployee_number()
    {
        return employee_number;
    }

    public void setLoan_officer_id(int loan_officer_id)
    {
        this.loan_officer_id = loan_officer_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setEmployee_number(String employee_number)
    {
        this.employee_number = employee_number;
    }
}
