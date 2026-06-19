package db;
import io.github.cdimascio.dotenv.Dotenv;
// ==========================
// Database Manager
// ==========================
// This class handles all database interactions, including saving and retrieving
// applicant, vehicle, and loan application data. It uses JDBC to connect to a MySQL database and performs SQL operations securely with prepared statements. The class
// includes methods to save applicants and vehicles,
//  as well as to save and retrieve complete 
// loan applications, ensuring that all related data is properly
//  stored and retrieved.
//=============================
//Imports
import io.github.cdimascio.dotenv.Dotenv;
import models.Applicant;
import models.Vehicle;
import models.AutoLoan;
import models.LoanApplication;
import services.LoanCalculator;
import models.User;
import java.io.InputStream;
import java.util.Properties;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.swing.JOptionPane;
import utils.passwordUtils;
// ==========================
public class DatabaseManager
{
private static Properties props = new Properties();
static
{
    try
    {
        InputStream input =
                DatabaseManager.class.getResourceAsStream("/config.properties");
        if (input == null)
        {
            throw new RuntimeException(
                    "config.properties not found!");
        }
        props.load(input);
    }
    catch (Throwable e)
    {
        e.printStackTrace();

        JOptionPane.showMessageDialog(
                null,
                e.toString(),
                "STATIC ERROR",
                JOptionPane.ERROR_MESSAGE);
    }
}

private static final String URL =
        "jdbc:mysql://"
        + props.getProperty("MYSQL_HOST")
        + ":"
        + props.getProperty("MYSQL_PORT")
        + "/"
        + props.getProperty("MYSQL_DATABASE")
        + "?sslMode=REQUIRED";

private static final String USER =
        props.getProperty("MYSQL_USER");

private static final String PASSWORD =
        props.getProperty("MYSQL_PASSWORD");
//Check if env variables are loaded correctly
// ==========================
// Connect to Database
// ==========================
private Connection connect() throws SQLException
{
    return DriverManager.getConnection(URL, USER, PASSWORD);
}
//Initalize Tables
public void initializeTables() {
    try (Connection conn = connect();
         Statement stmt = conn.createStatement()) {

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS users (
                user_id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(100) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(20) NOT NULL
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS applicant (
                applicant_id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                full_name VARCHAR(100),
                email VARCHAR(100),
                phone VARCHAR(20),
                address VARCHAR(255),
                date_of_birth VARCHAR(20),
                ssn VARCHAR(20),
                employer_name VARCHAR(100),
                FOREIGN KEY (user_id)
                    REFERENCES users(user_id)
                    ON DELETE CASCADE
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS vehicle (
                vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
                make VARCHAR(50),
                model VARCHAR(50),
                year INT
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS loan_officer (
                loan_officer_id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT,
                full_name VARCHAR(100),
                FOREIGN KEY (user_id)
                    REFERENCES users(user_id)
                    ON DELETE CASCADE
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS loan_application (
                application_id INT AUTO_INCREMENT PRIMARY KEY,
                applicant_id INT NOT NULL,
                vehicle_id INT NOT NULL,
                auto_price DOUBLE,
                down_payment DOUBLE,
                loan_term INT,
                interest_rate DOUBLE,
                sales_tax DOUBLE,
                fees DOUBLE,
                cash_incentive DOUBLE,
                loan_amount DOUBLE,
                monthly_payment DOUBLE,
                status VARCHAR(20) DEFAULT 'PENDING',
                application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                assigned_officer_id INT,
                reviewed_by INT,
                review_notes TEXT,

                FOREIGN KEY (applicant_id)
                    REFERENCES applicant(applicant_id)
                    ON DELETE CASCADE,

                FOREIGN KEY (vehicle_id)
                    REFERENCES vehicle(vehicle_id)
                    ON DELETE CASCADE,

                FOREIGN KEY (assigned_officer_id)
                    REFERENCES loan_officer(loan_officer_id),

                FOREIGN KEY (reviewed_by)
                    REFERENCES loan_officer(loan_officer_id)
            )
        """);

        System.out.println("Tables initialized successfully.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
//APPICANT  SIDE METHODS=====================================================
//Get applicnat form Applicant ID=========================
//Retrieves an applicant's information from the database using their applicant ID
//Uses SQL prepared statements to securely query the database for an applicant's information based on their ID
//Returns an Applicant object containing the applicant's information
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging purposes
//===================================
public Applicant getApplicantByUserId(
        int userId)
{
    String sql =
            "SELECT * FROM applicant WHERE user_id=?";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, userId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return new Applicant(
                    rs.getInt("user_id"),
                    rs.getInt("applicant_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("date_of_birth"),
                    rs.getString("ssn"),
                    rs.getString("employer_name")
            );
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return null;
}

// ==========================
// Save Applicant
//Uses SQL prepared statements to securely insert applicant data into 
// the database and retrieves the generated applicant ID
//  for use in loan application records.
//Uses smtmt.executeUpdate() to execute the insert and stmt.getGeneratedKeys() 
// to get the new applicant ID.
// ==========================
public int saveApplicant(Applicant applicant) throws SQLException
{
    // Insert applicant into database, the SQL statement is parameterized
    //BY USER, APP_ID, NAME, EMAIL, PHONE, ADDRESS, DOB, SSN, EMPLOYER
    String sql =
            "INSERT INTO applicant " +
            "(user_id,full_name, email, phone, address, date_of_birth, ssn, employer_name) " +
            "VALUES (?,?, ?, ?, ?, ?, ?, ?)";
    //Connect to DB
    try (Connection conn = connect();
         PreparedStatement stmt =
                 conn.prepareStatement(
                         sql,
                         Statement.RETURN_GENERATED_KEYS))
    {
        stmt.setInt(1, applicant.getUserId());
        stmt.setString(2, applicant.getFullName());
        stmt.setString(3, applicant.getEmail());
        stmt.setString(4, applicant.getPhone());
        stmt.setString(5, applicant.getAddress());
        stmt.setString(6, applicant.getDateOfBirth());
        stmt.setString(7, applicant.getSSN());
        stmt.setString(8, applicant.getEmployerName());
        //Execute the SQL statement
        stmt.executeUpdate();
        //REsult set
        ResultSet rs = stmt.getGeneratedKeys();
        //Get the new applicant ID
        if (rs.next())
        {
            return rs.getInt(1);
        }
    }
    //Exception handling
    catch (SQLException e)
    {
        e.printStackTrace();
    }
    //If there is an error saving the applicant, return -1

    return -1;
}

// ==========================
// Save Vehicle
//Uses SQL prepared statements to securely insert vehicle data into 
// the database and retrieves the generated vehicle ID
//  for use in loan application records.
//Uses smtmt.executeUpdate() to execute the insert and stmt.getGeneratedKeys() 
// to get the new vehicle ID.
// ==========================
public int saveVehicle(Vehicle vehicle) throws SQLException
{
    // Insert vehicle into database, with SQL statement parameterized
    //BY MAKE, MODEL, YEAR
    String sql =
            "INSERT INTO vehicle " +
            "(make, model, year) " +
            "VALUES (?, ?, ?)";
    //Connect to DB
    try (Connection conn = connect();
         PreparedStatement stmt =
                 conn.prepareStatement(
                         sql,
                         Statement.RETURN_GENERATED_KEYS))
    {
        stmt.setString(1, vehicle.getMake());
        stmt.setString(2, vehicle.getModel());
        stmt.setInt(3, vehicle.getYear());
        //Execute the SQL statement
        stmt.executeUpdate();
        //Result set
        ResultSet rs = stmt.getGeneratedKeys();
        //Get the new vehicle ID
        if (rs.next())
        {
            return rs.getInt(1);
        }
    }
    //Exception handling
    catch (SQLException e)
    {
        e.printStackTrace();
    }
    //If there is an error saving the vehicle, return -1

    return -1;
}

// ==========================
// Save Loan Application
//Uses SQL prepared statements to securely insert loan application data into 
// the database and retrieves the generated loan application ID
//  for use in loan application records.
//Uses smtmt.executeUpdate() to execute the insert and stmt.getGeneratedKeys() 
// to get the new loan application ID.
// ==========================
public int saveLoanApplication(
        Applicant applicant,
        Vehicle vehicle,
        AutoLoan loan)
{
        // First save applicant and vehicle to get their IDs
    try
    {
        int applicantId = saveApplicant(applicant);

        if(applicantId == -1)
    {
            throw new SQLException("Failed to save applicant");
    }
       
        int vehicleId = saveVehicle(vehicle);

        double loanAmount =
                LoanCalculator.calculateLoanAmount(loan);

        double monthlyPayment =
                LoanCalculator.calculateMonthlyPayment(loan);
        // Now save the loan application with the foreign keys
        //BY APPLICANT_ID, VEHICLE_ID
        //, AUTO_PRICE, DOWN_PAYMENT, LOAN_TERM, INTEREST_RATE, SALES_TAX, FEES, CASH_INCENTIVE
        String sql =
                "INSERT INTO loan_application " +
                "(applicant_id, vehicle_id, auto_price, down_payment, " +
                "loan_term, interest_rate, sales_tax, fees, " +
                "cash_incentive, loan_amount, monthly_payment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //Connect to DB
        try (Connection conn = connect();
             PreparedStatement stmt =
                     conn.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, applicantId);
            stmt.setInt(2, vehicleId);

            stmt.setDouble(3, loan.getAutoPrice());
            stmt.setDouble(4, loan.getDownPayment());
            stmt.setInt(5, loan.getLoanTerm());
            stmt.setDouble(6, loan.getInterestRate());
            stmt.setDouble(7, loan.getSalesTax());
            stmt.setDouble(8, loan.getFees());
            stmt.setDouble(9, loan.getCashIncentive());

            stmt.setDouble(10, loanAmount);
            stmt.setDouble(11, monthlyPayment);
            //Execute the SQL statement
            stmt.executeUpdate();
            //Result set
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next())
            {
                return rs.getInt(1);
            }
        }
    }
    //Error handling for SQL exceptions during the save process
    catch (SQLException e)
    {
        e.printStackTrace();
    }
    //If there is an error saving the loan application, return -1

    return -1;
}

// ==========================
// Retrieve Loan Application
//Retrieves a loan application by its ID from the database 
// and returns it as a LoanApplication object
//Uses SQL prepared statements to retrieve loan application data 
// from the database and maps it to a LoanApplication object
//Joins the loan_application, applicant, and vehicle tables 
// to get all related data in a single query
//Returns the loan application as a LoanApplication object
//Handles SQL exceptions that may occur during
//  the retrieval process and prints the stack trace for debugging
// ==========================
public LoanApplication getLoanApplicationById(int applicationId)
{
        // SQL query to join loan_application, applicant, and vehicle tables
    String sql =
            "SELECT * " +
            "FROM loan_application " +
            "JOIN applicant " +
            "ON loan_application.applicant_id = applicant.applicant_id " +
            "JOIN vehicle " +
            "ON loan_application.vehicle_id = vehicle.vehicle_id " +
            "WHERE loan_application.application_id = ?";
        //Prepared statement to execute the query securely and map the result to a LoanApplication object
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql))
    {
        stmt.setInt(1, applicationId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next())
        {
            Applicant applicant =
                    new Applicant(
                            rs.getInt("user_id"),
                            rs.getInt("applicant_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("date_of_birth"),
                            rs.getString("ssn"),
                            rs.getString("employer_name")
                    );


            Vehicle vehicle =
                    new Vehicle(
                            rs.getInt("application_id"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getInt("year")
                    );
            AutoLoan loan =
                    new AutoLoan(
                            rs.getDouble("auto_price"),
                            rs.getDouble("down_payment"),
                            rs.getInt("loan_term"),
                            rs.getDouble("interest_rate"),
                            rs.getDouble("sales_tax"),
                            rs.getDouble("fees"),
                            rs.getDouble("cash_incentive")
                    );



            return new LoanApplication(
                    rs.getString("status"),
                    applicationId,
                    applicant,
                    vehicle,
                    loan
            );
        }
    }
    //Error handling for SQL exceptions during the retrieval process
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    return null;
}
//Get LaonApplicationFrom ID With Officer Data
//Retrieves a loan application by its ID from the database
// and returns it as a LoanApplication object and its officer data
//Uses SQL prepared statements to retrieve loan application data
// from the database and maps it to a LoanApplication object
//Joins the loan_application, applicant, and vehicle tables
// to get all related data in a single query
//Returns the loan application as a LoanApplication object
//Handles SQL exceptions that may occur during the retrieval process
// and prints the stack trace for debugging
public LoanApplication getLoanApplicationByIdWithOfficerData(int applicationId)
{
    String sql =
            "SELECT * " +
            "FROM loan_application " +
            "JOIN applicant " +
            "ON loan_application.applicant_id = applicant.applicant_id " +
            "JOIN vehicle " +
            "ON loan_application.vehicle_id = vehicle.vehicle_id " +
            "WHERE loan_application.application_id = ?";

    try(Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(sql))
    {
        stmt.setInt(1, applicationId);

        ResultSet rs = stmt.executeQuery();

        if(rs.next())
        {
            Applicant applicant =
                    new Applicant(
                            rs.getInt("user_id"),
                            rs.getInt("applicant_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("date_of_birth"),
                            rs.getString("ssn"),
                            rs.getString("employer_name")
                    );

            Vehicle vehicle =
                    new Vehicle(
                            rs.getInt("vehicle_id"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getInt("year")
                    );

            AutoLoan loan =
                    new AutoLoan(
                            rs.getDouble("auto_price"),
                            rs.getDouble("down_payment"),
                            rs.getInt("loan_term"),
                            rs.getDouble("interest_rate"),
                            rs.getDouble("sales_tax"),
                            rs.getDouble("fees"),
                            rs.getDouble("cash_incentive")
                    );

            return new LoanApplication(
                    0, // application_date for now
                    rs.getString("status"),
                    rs.getInt("application_id"),
                    applicant,
                    vehicle,
                    loan,
                    rs.getString("review_notes"),
                    rs.getInt("reviewed_by")
            );
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return null;
}
public Date getLoanApplicationDate(int applicationId) {
    String sql = "SELECT application_date FROM loan_application WHERE application_id = ?";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, applicationId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDate("application_date");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public List<LoanApplication> getApplicationsByUserId(int userId)
{
    // SQL query to join loan_application, applicant, and vehicle tables
    String sql =
            "SELECT * " +
            "FROM loan_application " +
            "JOIN applicant " +
            "ON loan_application.applicant_id = applicant.applicant_id " +
            "JOIN vehicle " +
            "ON loan_application.vehicle_id = vehicle.vehicle_id " +
            "WHERE applicant.user_id = ?";
    //Prepared statement to execute the query securely and map the result to a LoanApplication object
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql))
    {
        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();

        List<LoanApplication> applications = new ArrayList<>();

        while (rs.next())
        {
            Applicant applicant =
                    new Applicant(
                            rs.getInt("user_id"),
                            rs.getInt("applicant_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("date_of_birth"),
                            rs.getString("ssn"),
                            rs.getString("employer_name")
                    );
   

            Vehicle vehicle =
                    new Vehicle(
                            rs.getInt("vehicle_id"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getInt("year")
                    );

            AutoLoan loan =
                    new AutoLoan(
                            rs.getDouble("auto_price"),
                            rs.getDouble("down_payment"),
                            rs.getInt("loan_term"),
                            rs.getDouble("interest_rate"),
                            rs.getDouble("sales_tax"),
                            rs.getDouble("fees"),
                            rs.getDouble("cash_incentive")
                    );

            LoanApplication application =
                    new LoanApplication(
                            rs.getString("status"),
                            rs.getInt("application_id"),
                            applicant,
                            vehicle,
                            loan
                    );

            applications.add(application);
        }

        return applications;
    }
    //Error handling for SQL exceptions during the retrieval process
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();            
}
//================================================
//Loan Officer Side Methods
//Get Application Status================================
//Retrieves the status of a loan application from the database
//Uses SQL prepared statements to query the database for the status of a loan application
// based on the application ID
//Returns the status of the loan application as a String
//Handles SQL exceptions that may occur during the retrieval process and prints the 
// stack trace for debugging
// ==========================
public String getApplicationStatus(int applicationId)
{
    String sql =
            "SELECT status " +
            "FROM loan_application " +
            "WHERE application_id = ?";

    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql))
    {
        stmt.setInt(1, applicationId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next())
        {
            return rs.getString("status");
        }
    }
    //Error handling for SQL exceptions during the retrieval process
    catch (SQLException e)
    {
        e.printStackTrace();    
    }

    return null;
}
//Get all Applications===============
//Retrieves a list of all loan applications from the database
//Uses SQL prepared statements to query the database for all loan applications
// and maps the results to a list of LoanApplication objects
//Returns a list of all loan applications as LoanApplication objects
//Handles SQL exceptions that may occur during the retrieval process and prints the 
// stack trace for debugging
// ==========================
public List<LoanApplication> getAllApplications()
{
    List<LoanApplication> applications = new ArrayList<>();

    // SQL query to retrieve all loan applications
    //By joining loan_application, applicant, and vehicle tables
    String sql =
            "SELECT application_id " +
            "FROM loan_application";
    //Connect to the database and execute the query
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql))
    {
        //Execute the query
        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            int applicationId =
                    rs.getInt("application_id");
            //Get the loan application by its ID
            LoanApplication app =
                    getLoanApplicationById(applicationId);

            if (app != null)
            {
                applications.add(app);
            }
        }
    }
    //Error handling for SQL exceptions during the retrieval process
    catch (SQLException e)
    {
        e.printStackTrace();
    }
    //Return the list of loan applications

    return applications;
}

//================================================
//Get Pending Applications================================
//Retrieves a list of pending loan applications from the database
//Uses SQL prepared statements to query the database for loan applications with a 
// status of 'PENDING' and maps the results to a list of LoanApplication objects
//Returns a list of pending loan applications as LoanApplication objects
//Handles SQL exceptions that may occur during the retrieval process and prints the 
// stack trace for debugging
// ==========================
public List<LoanApplication> getPendingApplications()
{
    List<LoanApplication> applications = new ArrayList<>();
    // SQL query to retrieve pending loan applications
    //By joining loan_application, applicant, and vehicle tables
    String sql =
            "SELECT application_id " +
            "FROM loan_application " +
            "WHERE status = 'PENDING'";
//Connect to the database and execute the query
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql))
    {
        //Execute the query
        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            //Get the loan application by its ID
            int applicationId =
                    rs.getInt("application_id");
            
            LoanApplication app =
                    getLoanApplicationById(applicationId);

            if (app != null)
            {
                applications.add(app);
            }
        }
    }
    //Error Handling
    catch (SQLException e)
    {
        e.printStackTrace();
    }
    //Return the list of pending loan applications

    return applications;
}
//APPROVE APPLICATION================================
//Updates the status of a loan application to 'APPROVED' in the database
//Uses SQL prepared statements to securely update the status of a loan application
// in the database based on the application ID
//Returns true if the update was successful, false otherwise
//Handles SQL exceptions that may occur during the update process and prints the stack trace for debugging
// ==========================
public boolean approveApplication(int applicationId,int officerId,String notes)
{
    String sql =
            "UPDATE loan_application " +
            "SET status=?, reviewed_by=? ,review_notes=?" +
            "WHERE application_id=?";

    try (Connection conn = connect();
         PreparedStatement stmt =
                 conn.prepareStatement(sql))
    {
        stmt.setString(1, "APPROVED");
        stmt.setInt(2, officerId);
        stmt.setString(3, notes);
        stmt.setInt(4, applicationId);
           

        return stmt.executeUpdate() > 0;
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    return false;
}
//DENY APPLICATION================================
//Updates the status of a loan application to 'DENIED' in the database
//Uses SQL prepared statements to securely update the status of a loan application
// in the database based on the application ID
//Returns true if the update was successful, false otherwise
//Handles SQL exceptions that may occur during the update process and prints the stack trace for debugging
// ==========================
public boolean denyApplication(
        int applicationId,
        int officerId,
        String notes)
{
    String sql =
            "UPDATE loan_application " +
            "SET status='DENIED', reviewed_by=?, review_notes=? " +
            "WHERE application_id=?";

    try (Connection conn = connect();
         PreparedStatement stmt =
                 conn.prepareStatement(sql))
    {
        stmt.setInt(1, officerId);
        stmt.setString(2, notes);
        stmt.setInt(3, applicationId);

        return stmt.executeUpdate() > 0;
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    return false;
}
//================================================
//Get Specific Loan Application================================
//Retrieves a specific loan application by its ID from the database
//Uses SQL prepared statements to retrieve loan application data
// from the database and maps it to a LoanApplication object
//Joins the loan_application, applicant, and vehicle tables to get all related data in a single query
//Returns the loan application as a LoanApplication object
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public List<LoanApplication> getApplicationsForApplicant( int applicantId)
{
    List<LoanApplication> applications =
            new ArrayList<>();

    String sql =
            "SELECT application_id " +
            "FROM loan_application " +
            "WHERE applicant_id=?";

    try (Connection conn = connect();
         PreparedStatement stmt =
                 conn.prepareStatement(sql))
    {
        stmt.setInt(1, applicantId);

        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            LoanApplication app =
                    getLoanApplicationById(
                            rs.getInt("application_id"));

            if (app != null)
            {
                applications.add(app);
            }
        }
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    return applications;
}
//Assign Loan Officer to Application================================
//Updates the loan officer ID for a loan application in the database
//Uses SQL prepared statements to securely update the loan officer ID for a loan application
// in the database based on the application ID
//Returns true if the update was successful, false otherwise
//Handles SQL exceptions that may occur during the update process and prints the stack trace for debugging
// ==========================
public boolean assignLoanOfficer(
        int applicationId,
        int loanOfficerId)
{
   String sql =
    "UPDATE loan_application " +
    "SET reviewed_by=? " +
    "WHERE application_id=?";

    try (Connection conn = connect();
         PreparedStatement stmt =
                 conn.prepareStatement(sql))
    {
        stmt.setInt(1, loanOfficerId);
        stmt.setInt(2, applicationId);

        return stmt.executeUpdate() > 0;
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    return false;       
}
//Search for Applications================================
//Retrieves a list of loan applications from the database based on a search term
//Uses SQL prepared statements to query the database for loan applications
// that match the search term and maps the results to a list of LoanApplication objects
//Returns a list of loan applications as LoanApplication objects
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public List<LoanApplication> searchApplications(
        String keyword)
{
    String sql =
            "SELECT * " +
            "FROM loan_application " +
            "JOIN applicant " +
            "ON loan_application.applicant_id = applicant.applicant_id " +
            "JOIN vehicle " +
            "ON loan_application.vehicle_id = vehicle.vehicle_id " +
            "WHERE CAST(loan_application.application_id AS CHAR) LIKE ? " +
            "OR full_name LIKE ? " +
            "OR make LIKE ? " +
            "OR model LIKE ?";

    List<LoanApplication> applications =
            new ArrayList<>();

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        String search =
                "%" + keyword + "%";

        stmt.setString(1, search);
        stmt.setString(2, search);
        stmt.setString(3, search);
        stmt.setString(4, search);

        ResultSet rs =
                stmt.executeQuery();

       while(rs.next())
{
    Applicant applicant =
            new Applicant(
                    rs.getInt("user_id"),
                    rs.getInt("applicant_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("date_of_birth"),
                    rs.getString("ssn"),
                    rs.getString("employer_name")
            );

    Vehicle vehicle =
            new Vehicle(
                    rs.getInt("vehicle_id"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year")
            );

    AutoLoan loan =
            new AutoLoan(
                    rs.getDouble("auto_price"),
                    rs.getDouble("down_payment"),
                    rs.getInt("loan_term"),
                    rs.getDouble("interest_rate"),
                    rs.getDouble("sales_tax"),
                    rs.getDouble("fees"),
                    rs.getDouble("cash_incentive")
            );

    LoanApplication application =
            new LoanApplication(
                    rs.getString("status"),
                    rs.getInt("application_id"),
                    applicant,
                    vehicle,
                    loan
            );

    applications.add(application);
}
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return applications;
}
//Get Officer ID from Application================================
//Retrieves the loan officer ID associated with a loan application from the database
//Uses SQL prepared statements to query the database for the loan officer ID based on the application ID
//Returns the loan officer ID as an integer
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public int getLoanOfficerId(int applicationId)
{
    String sql =
            "SELECT reviewed_by " +
            "FROM loan_application " +
            "WHERE application_id=?";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, applicationId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt("reviewed_by");
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return 0;
}
//Get OfficerName form ID
//Retrieves the loan officer name associated with a loan officer ID from the database
//Uses SQL prepared statements to query the database for the loan officer name based on the loan officer ID
//Returns the loan officer name as a string
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public String getLoanOfficerName(int loanOfficerId)
{
    String sql =
            "SELECT full_name " +
            "FROM loan_officer " +
            "WHERE loan_officer_id=?";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, loanOfficerId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getString("full_name");
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return "";
}
//Get Tota lApplications
//Retrieves the total number of loan applications from the database
//Uses SQL prepared statements to query the database for the total number of loan applications
//Returns the total number of loan applications as an integer
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public int getTotalApplications()
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return 0;
}
//Get Approved Applications
//Retrieves the total number of approved loan applications from the database
//Uses SQL prepared statements to query the database for the total number of approved loan applications
//Returns the total number of approved loan applications as an integer
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public int getApprovedApplications()
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE status='Approved'";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return 0;   
}
//Get Denied Applications 
//Retrieves the total number of denied loan applications from the database
//Uses SQL prepared statements to query the database for the total number of denied loan applications
//Returns the total number of denied loan applications as an integer
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public int getDeniedApplications()
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE status='Denied'";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return 0;
}
//Get Pending Applications
//Retrieves the total number of pending loan applications from the database
//Uses SQL prepared statements to query the database for the total number of pending loan applications
//Returns the total number of pending loan applications as an integer
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public int getPendingApplicationCount()
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE status='Pending'";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return 0;
}
//Get Average LoanAmount for Approved Applications
//Retrieves the average loan amount for approved loan applications from the database
//Uses SQL prepared statements to query the database for the average loan amount for approved loan applications
//Returns the average loan amount for approved loan applications as a double
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public double getAverageLoanAmount() {
    String sql = "SELECT AVG(loan_amount) FROM loan_application WHERE status = 'Approved'";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
//Get Assigned Officer
//Retrieves the loan officer assigned to a loan application from the database
//Uses SQL prepared statements to query the database for the loan officer assigned to a loan application
//Returns the loan officer assigned to the loan application as a string
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging
// ==========================
public int getAssignedOfficerId(
        int applicationId)
{
    String sql =
            "SELECT assigned_officer_id " +
            "FROM loan_application " +
            "WHERE application_id = ?";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, applicationId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(
                    "assigned_officer_id");
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return 0;
}
//Get Assigned Count
public int getAssignedCount(int officerId)
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE loan_officer=?";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1,officerId);
        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
    return 0;
}
public int getPendingApplicationCount(int officerId) {
    String sql = "SELECT COUNT(*) FROM loan_application WHERE loan_officer = ? AND status = 'Pending'";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, officerId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
//Assign Application to Officer by officerId
//Assigns a loan application to a loan officer by updating the loan_officer column in the loan_application table
//Uses SQL prepared statements to update the loan_officer column in the loan_application table
//Handles SQL exceptions that may occur during the update process and prints the stack trace for debugging
// ==========================
public void assignApplication(
        int applicationId,
        int officerId)
{
    String sql =
            "UPDATE loan_application " +
            "SET assigned_officer_id = ? " +
            "WHERE application_id = ?";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, officerId);
        stmt.setInt(2, applicationId);

        stmt.executeUpdate();
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
}
public List<LoanApplication> getAssignedApplications(
        int officerId)
{
    String sql =
            "SELECT * " +
            "FROM loan_application " +
            "JOIN applicant " +
            "ON loan_application.applicant_id = applicant.applicant_id " +
            "JOIN vehicle " +
            "ON loan_application.vehicle_id = vehicle.vehicle_id " +
            "WHERE assigned_officer_id = ?";

    List<LoanApplication> applications =
            new ArrayList<>();

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, officerId);

        ResultSet rs =
                stmt.executeQuery();

        while(rs.next())
        {
            Applicant applicant =
                    new Applicant(
                            rs.getInt("user_id"),
                            rs.getInt("applicant_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("date_of_birth"),
                            rs.getString("ssn"),
                            rs.getString("employer_name")
                    );

            Vehicle vehicle =
                    new Vehicle(
                            rs.getInt("vehicle_id"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getInt("year")
                    );

            AutoLoan loan =
                    new AutoLoan(
                            rs.getDouble("auto_price"),
                            rs.getDouble("down_payment"),
                            rs.getInt("loan_term"),
                            rs.getDouble("interest_rate"),
                            rs.getDouble("sales_tax"),
                            rs.getDouble("fees"),
                            rs.getDouble("cash_incentive")
                    );

            LoanApplication application =
                    new LoanApplication(
                            rs.getString("status"),
                            rs.getInt("application_id"),
                            applicant,
                            vehicle,
                            loan
                    );

            applications.add(application);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return applications;
}
public int getAssignedApplicationsCount(int officerId)
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE assigned_officer_id = ?";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, officerId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return 0;
}
public int getApprovedApplicationsCount(int officerId)
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE assigned_officer_id = ? " +
            "AND status = 'APPROVED'";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, officerId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return 0;
}
public int getDeniedApplicationsCount(int officerId)
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE assigned_officer_id = ? " +
            "AND status = 'DENIED'";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, officerId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return 0;
}
public int getPendingApplicationsCount(int officerId)
{
    String sql =
            "SELECT COUNT(*) " +
            "FROM loan_application " +
            "WHERE assigned_officer_id = ? " +
            "AND status = 'PENDING'";

    try(Connection conn = connect();
        PreparedStatement stmt =
                conn.prepareStatement(sql))
    {
        stmt.setInt(1, officerId);

        ResultSet rs =
                stmt.executeQuery();

        if(rs.next())
        {
            return rs.getInt(1);
        }
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }

    return 0;
}
//Reassign Application
public void reassignApplication(int applicationId, int officerId) {
    String sql = "UPDATE loan_application SET assigned_officer_id = ? WHERE application_id = ?";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, officerId);
        stmt.setInt(2, applicationId);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
//Get all Officers
public List<User> getAllOfficers() {
    String sql = "SELECT * FROM users WHERE role = 'OFFICER'";
    List<User> officers = new ArrayList<>();
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            officers.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return officers;
}


//================================================
//USER SIDE METHODS
//================================================
//Login Methods =======================
//Retrieves a user's information from the database based on their username and password
//Uses SQL prepared statements to query the database for a user's information
//Returns a User object containing the user's information
//Handles SQL exceptions that may occur during the retrieval process and prints the stack trace for debugging purposes
public User login (String username, String password) {
    String sql = "SELECT * FROM users WHERE username = ? ";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
{
    String storedHash =
            rs.getString("password");

    if(passwordUtils.checkPassword(
            password,
            storedHash))
    {
        return new User(
                rs.getInt("user_id"),
                rs.getString("username"),
                storedHash,
                rs.getString("role"));
    }
}
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
//Register
public boolean registerUser(String username , String password, String role)
{
    String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, role);
        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
    
}
     
}
