from sqlalchemy.orm import Session
from app.repositories.applicant_repository import ApplicantRepository

def create_applicant(db: Session,user_id: int, full_name: str,email: str, phone: str,adress: str, date_of_birth: str,ssn: str, employer_name: str):
    return ApplicantRepository.create( db,user_id,full_name, email, phone,adress,date_of_birth, ssn,employer_name )