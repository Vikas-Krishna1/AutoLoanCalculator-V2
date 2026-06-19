from sqlalchemy.orm import Session
from app.models.LoanApplication import LoanApplication

class LoanApplicationRepository:

    @staticmethod
    def create(
        db: Session,
        applicant_id: int,
        vehicle_id: int,
        auto_price: float,
        sales_tax: float,
        fees: float,
        cash_incentive: float,
        down_payment: float,
        interest_rate: float,
        loan_term: int,
        loan_amount: float,
        monthly_payment: float

    ):

        loan = LoanApplication(
    applicant_id=applicant_id,
    vehicle_id=vehicle_id,

    auto_price=auto_price,
    sales_tax=sales_tax,
    fees=fees,
    cash_incentive=cash_incentive,
    down_payment=down_payment,
    interest_rate=interest_rate,
    loan_term=loan_term,

    loan_amount=loan_amount,
    monthly_payment=monthly_payment,

    status="Pending"
)

        db.add(loan)
        db.commit()
        db.refresh(loan)

        return loan