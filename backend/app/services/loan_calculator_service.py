import math
from app.schemas.loan_schema import AutoLoanRequest


class LoanCalculatorService:

    # Amount financed (principal)
    @staticmethod
    def calculate_loan_amount(loan: AutoLoanRequest):

        tax = (
            loan.auto_price
            * (loan.sales_tax / 100.0)
        )

        amount = (
            loan.auto_price
            + tax
            + loan.fees
            - loan.cash_incentive
            - loan.down_payment
        )

        return max(amount, 0)


    # Monthly payment
    @staticmethod
    def calculate_monthly_payment(loan: AutoLoanRequest):

        principal = (
            LoanCalculatorService.calculate_loan_amount(
                loan
            )
        )

        monthly_rate = (
            loan.interest_rate
            / 100.0
            / 12.0
        )

        number_of_payments = loan.loan_term

        if number_of_payments <= 0:
            return 0

        if monthly_rate == 0:
            return principal / number_of_payments

        denominator = (
            1
            - math.pow(
                1 + monthly_rate,
                -number_of_payments
            )
        )

        return (
            principal
            * monthly_rate
            / denominator
        )


    # Sales tax amount
    @staticmethod
    def calculate_sales_tax_amount(loan: AutoLoanRequest):

        return (
            loan.auto_price
            * (loan.sales_tax / 100.0)
        )


    # Tax + fees
    @staticmethod
    def calculate_upfront_costs(loan: AutoLoanRequest):

        return (
            LoanCalculatorService.calculate_sales_tax_amount(
                loan
            )
            + loan.fees
        )


    # Total amount paid over life of loan
    @staticmethod
    def calculate_total_payment(loan: AutoLoanRequest):

        return (
            LoanCalculatorService.calculate_monthly_payment(
                loan
            )
            * loan.loan_term
        )


    # Interest paid over life of loan
    @staticmethod
    def calculate_total_interest(loan: AutoLoanRequest):

        return (
            LoanCalculatorService.calculate_total_payment(
                loan
            )
            - LoanCalculatorService.calculate_loan_amount(
                loan
            )
        )


    # Vehicle price + tax + fees
    @staticmethod
    def calculate_out_the_door_price(loan: AutoLoanRequest):

        return (
            loan.auto_price
            + LoanCalculatorService.calculate_sales_tax_amount(
                loan
            )
            + loan.fees
        )