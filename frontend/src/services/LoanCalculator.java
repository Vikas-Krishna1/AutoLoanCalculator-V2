package services;
import models.AutoLoan;
// ==========================
// Loan Calculator Service
// ==========================
public class LoanCalculator
{
    // Amount financed (principal)
    public static double calculateLoanAmount(
            AutoLoan loan)
    {
        double tax =
                loan.getAutoPrice()
                * (loan.getSalesTax() / 100.0);

        double amount =
                loan.getAutoPrice()
                + tax
                + loan.getFees()
                - loan.getCashIncentive()
                - loan.getDownPayment();

        return Math.max(amount, 0);
    }

    // Monthly payment
    public static double calculateMonthlyPayment(
            AutoLoan loan)
    {
        double principal =
                calculateLoanAmount(loan);

        double monthlyRate =
                loan.getInterestRate()
                / 100.0
                / 12.0;

        int numberOfPayments =
                loan.getLoanTerm();   // already months

        if (numberOfPayments <= 0)
        {
            return 0;
        }

        if (monthlyRate == 0)
        {
            return principal / numberOfPayments;
        }

        double denominator =
                1 - Math.pow(
                        1 + monthlyRate,
                        -numberOfPayments);

        return principal
                * monthlyRate
                / denominator;
    }

    // Sales tax amount
    public static double calculateSalesTaxAmount(
            AutoLoan loan)
    {
        return loan.getAutoPrice()
                * (loan.getSalesTax() / 100.0);
    }

    // Tax + fees
    public static double calculateUpfrontCosts(
            AutoLoan loan)
    {
        return calculateSalesTaxAmount(loan)
                + loan.getFees();
    }

    // Total amount paid over life of loan
    public static double calculateTotalPayment(
            AutoLoan loan)
    {
        return calculateMonthlyPayment(loan)
                * loan.getLoanTerm();
    }

    // Interest paid over life of loan
    public static double calculateTotalInterest(
            AutoLoan loan)
    {
        return calculateTotalPayment(loan)
                - calculateLoanAmount(loan);
    }

    // Vehicle price + tax + fees
    public static double calculateOutTheDoorPrice(
            AutoLoan loan)
    {
        return loan.getAutoPrice()
                + calculateSalesTaxAmount(loan)
                + loan.getFees();
    }
}