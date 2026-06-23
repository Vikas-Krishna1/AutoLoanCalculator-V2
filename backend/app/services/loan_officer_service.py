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
##Get allApplications by officer
def get_all_applications_by_officer(db: Session, loan_officer_id: int):
    return LoanOfficerRepository.get_all_applications_by_officer(db, loan_officer_id)
##Get officer by id
def get_by_id_service(db: Session, loan_officer_id: int):
    return LoanOfficerRepository.get_by_id(db, loan_officer_id)
##Get Officer by user id
def get_by_user_id_service(db: Session, user_id: int):
    return LoanOfficerRepository.get_by_user_id(db, user_id)
def get_all_service(db: Session):
    return LoanOfficerRepository.get_all(db)
def get_officer_statistics_service(
    db: Session,
    loan_officer_id: int
):

    return {

        "assigned":
        LoanOfficerRepository.get_total_count_by_officer(
            db,
            loan_officer_id
        ),

        "pending":
        LoanOfficerRepository.get_pending_count_by_officer(
            db,
            loan_officer_id
        ),

        "under_review":
        LoanOfficerRepository.get_under_review_count_by_officer(
            db,
            loan_officer_id
        ),

        "approved":
        LoanOfficerRepository.get_approved_count_by_officer(
            db,
            loan_officer_id
        ),

        "denied":
        LoanOfficerRepository.get_denied_count_by_officer(
            db,
            loan_officer_id
        )

    }