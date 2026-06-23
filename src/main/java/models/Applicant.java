package models;
import com.fasterxml.jackson.annotation.JsonProperty;
//APPLICANT CLASS====================================
//This class is used to create an applicant object that 
//represents a person applying for a loan
//====================================================
public class Applicant
{
    @JsonProperty("user_id")
    private int user_id;
    @JsonProperty("applicant_id")
    private int applicant_id;
    @JsonProperty("full_name")
    private String full_name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address")
    private String address;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private String ssn;
    @JsonProperty("employer_name")
    private String employerName;
    @JsonProperty("created_at")
    private String created_at;
    public Applicant() {}

    public Applicant(int user_id,int applicant_id, String full_name, String email, String phone, String address, String dateOfBirth, String ssn, String employerName, String created_at)
    {
        //Private  instance variables
        this.user_id = user_id;
        this.applicant_id = applicant_id;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
        this.employerName = employerName;
        this.created_at = created_at;
    }
    //Constructor for Applicant without user_id
    public Applicant(String full_name,String email,String phone, String address,String dateOfBirth, String ssn,String employerName)
{
    this.full_name = full_name;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.employerName = employerName;
}
//Constructor for Applicant with user_id
public Applicant(int user_id,String full_name,String email, String phone,  String address, String dateOfBirth,  String ssn, String employerName)
{
    this.user_id = user_id;
    this.full_name = full_name;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.employerName = employerName;
}

    //Getters
    @JsonProperty("user_id")
    public int getUserId() {
        return user_id;
    }
    @JsonProperty("applicant_id")
    public int getApplicantId()    {
        return applicant_id;
    }
    @JsonProperty("full_name")
    public String getFullName() {
        return full_name;
    }
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }
    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    @JsonProperty("ssn")
    public String getSSN() {
        return ssn;
    }
    @JsonProperty("employer_name")
    public String getEmployerName() {
        return employerName;
    }
    public String getCreated_at() {
        return created_at;
    }
        //Setters
    public void setApplicantId(int applicant_id) {
        this.applicant_id = applicant_id;
    }
    public void setFullName(String full_name) {
        this.full_name = full_name;
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
    public void setSSN(String ssn) {
        this.ssn = ssn;
    }
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    @Override
    public String toString() {
        return "Applicant ID: " + applicant_id +
                "\nName: " + full_name +
                "\nEmail: " + email +
                "\nPhone: " + phone +
                "\nAddress: " + address;
    } 

   
    

    
}
