package models;
import com.fasterxml.jackson.annotation.*;
public class RegisterOfficerRequest
{
    private String username;
    private String password;
    @JsonProperty("full_name")
    private String fullName;
    private String email;
    private String phone;
    @JsonProperty("employee_number")
    private String employeeNumber;

    public RegisterOfficerRequest() {}

    public RegisterOfficerRequest(
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            String employeeNumber)
    {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.employeeNumber = employeeNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }
    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @JsonProperty("employee_number")
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    @JsonProperty("employee_number")
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}