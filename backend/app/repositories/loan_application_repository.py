from sqlalchemy.orm import Session
from app.models.LoanApplication import LoanApplication
from app.schemas.loan_applicaation_schema import LoanApplicationCreate


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
    
    @staticmethod
    def get_all(db: Session):
        return db.query(LoanApplication).all()
    @staticmethod
    def get_by_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.loan_id == id
        ).first()
    @staticmethod
    def get_by_applicant_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.applicant_id == id
        ).first()
    @staticmethod
    def get_by_pending(db: Session):
        return db.query(LoanApplication).filter(
            LoanApplication.status == "Pending"
        ).first()
    @staticmethod
    def get_by_officer_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
        LoanApplication.officer_id == id
    ).first()
    @staticmethod
    def assign_officer(db: Session, application_id: int, officer_id: int):
        loan = db.query(LoanApplication).filter(
        LoanApplication.application_id == application_id
    ).first()
        loan.officer_id = officer_id
        db.commit()
        db.refresh(loan)
        return loan