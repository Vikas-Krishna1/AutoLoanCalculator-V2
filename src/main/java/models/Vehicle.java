package models;

public class Vehicle
{
    private int vehicle_id;
    private String make;
    private String model;
    private int year;
    private String vin;

    public Vehicle()
    {

    }

    public Vehicle(
            int vehicle_id,
            String make,
            String model,
            int year,
            String vin)
    {
        this.vehicle_id = vehicle_id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
    }

    public int getVehicle_id()
    {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id)
    {
        this.vehicle_id = vehicle_id;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public String getVin()
    {
        return vin;
    }

    public void setVin(String vin)
    {
        this.vin = vin;
    }
}