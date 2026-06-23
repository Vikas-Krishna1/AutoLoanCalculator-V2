package models;
import com.fasterxml.jackson.annotation.*;
public class RegisterApplicantRequest
{
    
    private String username;
    private String password;

    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String dateOfBirth;
    private String ssn;
    private String employerName;

    public RegisterApplicantRequest(
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            String address,
            String dateOfBirth,
            String ssn,
            String employerName)
    {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
        this.employerName = employerName;
    }

    public RegisterApplicantRequest() {}
    //Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getSsn() {
        return ssn;
    }
    public String getEmployerName() {
        return employerName;
    }
    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

}