from sqlalchemy.orm import Session
from app.repositories.loan_officer_repository import LoanOfficerRepository


def create_loan_officer(
    db: Session,
    user_id: int,
    full_name: str,
    email: str,
    phone: str,
    employee_number: str
):

    return LoanOfficerRepository.create(
        db,
        user_id,
        full_name,
        email,
        phone,
        employee_number
    )