package models;
public class AutoLoan
{
    private double autoPrice;
    private int loanTerm; // months
    private double interestRate;
    private double downPayment;
    private double salesTax;
    private double fees;
    private double cashIncentive;

    public AutoLoan(double autoPrice,double downPayment,int loanTerm,double interestRate,double salesTax, double fees,double cashIncentive)
    {
        this.autoPrice = autoPrice;
        this.downPayment = downPayment;
        this.loanTerm = loanTerm;
        this.interestRate = interestRate;
        this.salesTax = salesTax;
        this.fees = fees;
        this.cashIncentive = cashIncentive;
    }

    // Getters

    public double getAutoPrice()
    {
        return autoPrice;
    }

    public double getDownPayment()
    {
        return downPayment;
    }

    public int getLoanTerm()
    {
        return loanTerm;
    }

    public double getInterestRate()
    {
        return interestRate;
    }

    public double getSalesTax()
    {
        return salesTax;
    }

    public double getFees()
    {
        return fees;
    }

    public double getCashIncentive()
    {
        return cashIncentive;
    }

    // Setters

    public void setAutoPrice(double autoPrice)
    {
        this.autoPrice = autoPrice;
    }

    public void setDownPayment(double downPayment)
    {
        this.downPayment = downPayment;
    }

    public void setLoanTerm(int loanTerm)
    {
        this.loanTerm = loanTerm;
    }

    public void setInterestRate(double interestRate)
    {
        this.interestRate = interestRate;
    }

    public void setSalesTax(double salesTax)
    {
        this.salesTax = salesTax;
    }

    public void setFees(double fees)
    {
        this.fees = fees;
    }

    public void setCashIncentive(double cashIncentive)
    {
        this.cashIncentive = cashIncentive;
    }
    @Override
    public String toString()
    {
        return "Auto Price: $" + autoPrice +
               "\nCash Incentive: $" + cashIncentive +
               "\nDown Payment: $" + downPayment +
                "\nLoan Term: " + loanTerm + " months" +
                "\nInterest Rate: " + interestRate + "%" +
                "\nSales Tax: $" + salesTax +
                "\nFees: $" + fees;
    }
}