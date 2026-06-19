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
            LoanApplication.application_id == id
        ).all()
    @staticmethod
    def get_by_applicant_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.applicant_id == id
        ).all()
    @staticmethod
    def get_by_pending(db: Session):
        return db.query(LoanApplication).filter(
            LoanApplication.status == "Pending"
        ).all()
    @staticmethod
    def get_by_officer_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
        LoanApplication.loan_officer == id
    ).all()
    @staticmethod
    def assign_officer(db: Session, application_id: int, officer_id: int):
        loan = db.query(LoanApplication).filter(
        LoanApplication.application_id == application_id
    ).first()
        loan.loan_officer_id = officer_id

        if loan.status == "Pending":
            loan.status = "UNDER_REVIEW"
        db.commit()
        db.refresh(loan)
        return loan
    @staticmethod
    def approve_application( db: Session,application_id: int,officer_id: int,notes: str):

        application = (
        LoanApplicationRepository.get_by_id(db,application_id))

        if application:

            application.loan_officer_id = officer_id
            application.reviewed_by = officer_id
            application.review_notes = notes
            application.status = "APPROVED"

            db.commit()
            db.refresh(application)

        return application

    @staticmethod
    def deny_application( db: Session,application_id: int,officer_id: int,notes: str):

        application = (
        LoanApplicationRepository.get_by_id(db,application_id))

        if application:

            application.loan_officer_id = officer_id
            application.reviewed_by = officer_id
            application.review_notes = notes
            application.status = "DENIED"

            db.commit()
            db.refresh(application)

        return application
    ##Get Under Review Applications
    def get_under_review(db: Session):
        return db.query(LoanApplication).filter(
            LoanApplication.status == "UNDER_REVIEW"
        ).all()

    ##Get Approved Applications
    def get_approved(db: Session):
        return db.query(LoanApplication).filter(
            LoanApplication.status == "APPROVED"
        ).all()

    ##Get Denied Applications
    def get_denied(db: Session):
        return db.query(LoanApplication).filter(
            LoanApplication.status == "DENIED"
        ).all()
    
    ## Get Application by ID
    def get_by_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.application_id == id
        ).first()

    ## Get Application by ID
    def get_by_vehicle_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.vehicle_id == id
        ).first()

    ## Get Application by ID
    def get_by_applicant_id(db: Session, id: int):
        return db.query(LoanApplication).filter(
            LoanApplication.applicant_id == id
        ).first()