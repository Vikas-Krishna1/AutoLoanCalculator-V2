package models;
//APPLICANT CLASS====================================
//This class is used to create an applicant object that 
//represents a person applying for a loan
//====================================================
public class Applicant
{
    private int user_id;
    private int applicant_id;
    private String full_name;
    private String email;
    private String phone;
    private String address;
    private String dateOfBirth;
    private String ssn;
    private String employerName;

    public Applicant(int user_id,int applicant_id, String full_name, String email, String phone, String address, String dateOfBirth, String ssn, String employerName)
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
    public int getUserId() {
        return user_id;
    }
    public int getApplicantId()    {
        return applicant_id;
    }
    public String getFullName() {
        return full_name;
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
    public String getSSN() {
        return ssn;
    }
    public String getEmployerName() {
        return employerName;
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
    @Override
    public String toString() {
        return "Applicant ID: " + applicant_id +
                "\nName: " + full_name +
                "\nEmail: " + email +
                "\nPhone: " + phone +
                "\nAddress: " + address;
    } 

   
    

    
}
