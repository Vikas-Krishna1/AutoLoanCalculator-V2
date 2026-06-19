package models;
//Vehicle Class==================
//Represents a vehicle with associated 
// make, model, and year
//======================================    
public class Vehicle
{
    // Private instance variables
    private int applicationId;
    private String make;
    private String model;
    private int year;
// Constructor
    public Vehicle(int applicationId, String make,String model,int year)
    {
        this.applicationId = applicationId;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    // Getters

    public int getApplicationId()
    {
        return applicationId;
    }

    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public int getYear()
    {
        return year;
    }

    // Setters

    public void setApplicationId(int applicationId)
    {
        this.applicationId = applicationId;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    @Override
    public String toString()
    {
        return "Application ID: " + applicationId +
               "\nMake: " + make +
               "\nModel: " + model +
               "\nYear: " + year;
    }
}