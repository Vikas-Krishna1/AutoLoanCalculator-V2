from sqlalchemy.orm import Session
from app.repositories.applicant_repository import ApplicantRepository

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
##Get Applications by Applicant ID
def get_applications_by_applicant_id_service(db: Session, applicant_id: int):
    return ApplicantRepository.get_applications_by_applicant_id(db, applicant_id)
def get_all_applicants_service(db: Session):
    return ApplicantRepository.get_all(db)
##Get Applicant by User ID
def get_applicant_by_user_id_service(db: Session, id: int):
    return ApplicantRepository.get_by_user_id(db, id)
##Get Applicant by ID
def get_applicant_by_id_service(db: Session, id: int):
    return ApplicantRepository.get_by_id(db, id)