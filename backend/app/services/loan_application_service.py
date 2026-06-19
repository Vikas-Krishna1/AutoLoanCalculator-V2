from sqlalchemy.orm import Session
from app.repositories.loan_application_repository import LoanApplicationRepository
from app.schemas.loan_schema import AutoLoanRequest
from app.services.loan_calculator_service import LoanCalculatorService


def create_loan_application(
    db: Session,
    applicant_id: int,
    vehicle_id: int,
    auto_price: float,
    sales_tax: float,
    fees: float,
    cash_incentive: float,
    down_payment: float,
    interest_rate: float,
    loan_term: int

):

    loan = AutoLoanRequest(
        auto_price=auto_price,
        sales_tax=sales_tax,
        fees=fees,
        cash_incentive=cash_incentive,
        down_payment=down_payment,
        interest_rate=interest_rate,
        loan_term=loan_term
    )

    loan_amount = LoanCalculatorService.calculate_loan_amount(loan)

    monthly_payment = LoanCalculatorService.calculate_monthly_payment(loan)

    return LoanApplicationRepository.create(
        db,
        applicant_id,
        vehicle_id,
        auto_price,
        sales_tax,
        fees,
        cash_incentive,
        down_payment,
        interest_rate,
        loan_term,
        loan_amount,
        monthly_payment
    )
##Officer Services
def assign_officer(db: Session, application_id: int, officer_id: int):

    return LoanApplicationRepository.assign_officer(db, application_id, officer_id)

def approve_application( db: Session,application_id: int, officer_id: int, notes: str):

    return (LoanApplicationRepository.approve_application( db,application_id,officer_id,  notes))

def deny_application( db: Session,application_id: int, officer_id: int, notes: str):

    return (LoanApplicationRepository.deny_application( db,application_id,officer_id,  notes))
##Get all  Pending Applications
def get_pending_applications(db: Session):

    return LoanApplicationRepository.get_by_pending(db)

def get_under_review(db: Session):

    return LoanApplicationRepository.get_under_review(db)
def get_approved(db: Session):

    return LoanApplicationRepository.get_approved(db)

def get_denied(db: Session):

    return LoanApplicationRepository.get_denied(db)
##get_by_id
def get_loanApplication_by_id_service(db: Session, id: int):

    return LoanApplicationRepository.get_by_id(db, id)
##get_by_applicant_id
def get_loanApplication_by_applicant_id_service(db: Session, id: int):

    return LoanApplicationRepository.get_by_applicant_id(db, id)

##get_by_vehicle_id
def get_loanApplication_by_vehicle_id_service(db: Session, id: int):

    return LoanApplicationRepository.get_by_vehicle_id(db, id)

