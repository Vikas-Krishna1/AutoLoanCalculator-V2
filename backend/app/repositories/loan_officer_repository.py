from sqlalchemy.orm import Session
from app.models.LoanOfficer import LoanOfficer
from app.models.LoanApplication import LoanApplication


class LoanOfficerRepository:

    @staticmethod
    def create(
        db: Session,
        user_id: int,
        full_name: str,
        email: str,
        phone: str,
        employee_number: str
    ):

        officer = LoanOfficer(
            user_id=user_id,
            full_name=full_name,
            email=email,
            phone=phone,
            employee_number=employee_number
        )

        db.add(officer)
        db.commit()
        db.refresh(officer)

        return officer
    ## Get Loan Officer
    @staticmethod
    def get_by_id(db: Session, loan_officer_id: int):
        return db.query(LoanOfficer).filter(
            LoanOfficer.loan_officer_id == loan_officer_id
        ).first()
    ##Get all Loan Application form a Loan Officer
    @staticmethod
    def get_all_applications_by_officer(db: Session, loan_officer_id: int):
        r = db.query(LoanApplication).filter(
            LoanApplication.loan_officer_id == loan_officer_id
        ).all()

        return r
    ##Get Officer by ID
    @staticmethod
    def get_by_id(db: Session, loan_officer_id: int):
        return db.query(LoanOfficer).filter(
            LoanOfficer.loan_officer_id == loan_officer_id
        ).first()

    @staticmethod
    def get_by_user_id(db: Session, user_id: int):
        return db.query(LoanOfficer).filter(
            LoanOfficer.user_id == user_id
        ).first()
    @staticmethod
    def get_all(db: Session):
        return db.query(LoanOfficer).all()
    @staticmethod
    def get_pending_count_by_officer(db: Session, loan_officer_id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.loan_officer_id == loan_officer_id,
            LoanApplication.status == "Pending"
        ).count()

    @staticmethod
    def get_under_review_count_by_officer(db: Session, loan_officer_id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.loan_officer_id == loan_officer_id,
            LoanApplication.status == "UNDER_REVIEW"
        ).count()
    @staticmethod
    def get_approved_count_by_officer(db: Session, loan_officer_id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.loan_officer_id == loan_officer_id,
            LoanApplication.status == "APPROVED"
        ).count()

    @staticmethod
    def get_denied_count_by_officer(db: Session, loan_officer_id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.loan_officer_id == loan_officer_id,
            LoanApplication.status == "DENIED"
        ).count()
    @staticmethod
    def get_total_count_by_officer(db: Session, loan_officer_id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.loan_officer_id == loan_officer_id
        ).count