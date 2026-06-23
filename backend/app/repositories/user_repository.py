from sqlalchemy.orm import Session
from app.models.User import User
from app.models.Applicant import Applicant
from app.repositories.applicant_repository import ApplicantRepository
from app.models.LoanOfficer import LoanOfficer
from app.repositories.loan_officer_repository import LoanOfficerRepository

class UserRepository:

    @staticmethod
    def get_by_username(db: Session, username: str):
        return db.query(User).filter(
            User.username == username
        ).first()

    @staticmethod
    def get_by_id(db: Session, id: int):
        return db.query(User).filter(
            User.user_id == id
        ).first()
    @staticmethod
    def create_user(db: Session, username: str, password: str, role: str):
        user = User(
            username=username,
            password=password,
            role=role
        )
        db.add(user)
        db.commit()
        db.refresh(user)
        
        return user
    @staticmethod
    def get_all_users(db: Session):
        return db.query(User).all()
    @staticmethod
    def get_by_role(db: Session, role: str):
        return db.query(User).filter(
            User.role == role
        ).all()

    @staticmethod
    def get_by_id(db: Session, id: int):
        return db.query(User).filter(
            User.user_id == id
        ).first()
    @staticmethod
    def create_applicant(db: Session,user_id: int, full_name: str,email: str, phone: str,address: str, date_of_birth: str,ssn: str, employer_name: str):
        existing = ApplicantRepository.get_by_user_id(
            db,
            user_id
        )

        if existing:
            return existing

        return ApplicantRepository.create(
            db,
            user_id,
            full_name,
            email,
            phone,
            address,
            date_of_birth,
            ssn,
            employer_name
        )
    @staticmethod
    def create_loan_officer(db: Session,user_id: int, full_name: str,email: str, phone: str,employee_number: str):
        existing = LoanOfficerRepository.get_by_user_id(
            db,
            user_id
        )

        if existing:
            return existing

        return LoanOfficerRepository.create(
            db,
            user_id,
            full_name,
            email,
            phone,
            employee_number
        )