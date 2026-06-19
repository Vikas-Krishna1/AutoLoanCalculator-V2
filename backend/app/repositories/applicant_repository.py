from sqlalchemy.orm import Session
from app.models.Applicant import Applicant

class ApplicantRepository:

    @staticmethod
    def create(db: Session,
               user_id: int,
               full_name: str,
               email: str,
               phone: str,
               adress: str,
               date_of_birth: str,
               ssn: str,
               employer_name: str

               ):

        applicant = Applicant(
            user_id=user_id,
            full_name=full_name,    
            email=email,
            phone=phone,
            adress=adress,
            date_of_birth=date_of_birth,
            ssn=ssn,
            employer_name=employer_name
        )

        db.add(applicant)
        db.commit()
        db.refresh(applicant)

        return applicant