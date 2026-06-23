from sqlalchemy.orm import Session
from app.models.Applicant import Applicant
from app.models.LoanApplication import LoanApplication

class ApplicantRepository:

    @staticmethod
    def create(db: Session,
               user_id: int,
               full_name: str,
               email: str,
               phone: str,
               address: str,
               date_of_birth: str,
               ssn: str,
               employer_name: str

               ):
                
        applicant = Applicant(
            user_id=user_id,
            full_name=full_name,    
            email=email,
            phone=phone,
            address=address,
            date_of_birth=date_of_birth,
            ssn=ssn,
            employer_name=employer_name
        )

        db.add(applicant)
        db.commit()
        db.refresh(applicant)

        return applicant
    ##Get Applications by Applicant ID
    @staticmethod
    @staticmethod
    def get_applications_by_applicant_id(db: Session, applicant_id: int):
        return (
        db.query(LoanApplication)
        .filter(LoanApplication.applicant_id == applicant_id)
        .all()
    )
    def get_all(db: Session):
        return db.query(Applicant).all()
    ##Get Applicant by ID
    def get_by_id(db: Session, id: int):
        return db.query(Applicant).filter(Applicant.applicant_id == id).first()
    ##Get Applicant by User ID
    def get_by_user_id(db: Session, id: int):
        return db.query(Applicant).filter(Applicant.user_id == id).first()
    def get_by_id(db: Session, id: int):
        return db.query(Applicant).filter(Applicant.applicant_id == id).first()