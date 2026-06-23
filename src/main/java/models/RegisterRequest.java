package models;
public class RegisterRequest
{
    private String username;
    private String password;
    private String role;

    private String fullName;
    private String email;
    private String phone;

    // Applicant only
    private String address;
    private String dateOfBirth;
    private String ssn;
    private String employerName;

    // Officer only
    private String employeeNumber;

    // constructors/getters/setters
    public RegisterRequest(String username, String password, String role, String fullName, String email, String phone, String address, String dateOfBirth, String ssn, String employerName, String employeeNumber) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
        this.employerName = employerName;
        this.employeeNumber = employeeNumber;
    }
    public RegisterRequest() {}
    //Getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
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
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    //Setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
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
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}