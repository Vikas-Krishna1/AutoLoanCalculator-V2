from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.applicant_schema import ApplicantCreate
from app.services.applicant_service import create_applicant, get_applications_by_applicant_id_service, get_all_applicants_service, get_applicant_by_user_id_service, get_applicant_by_id_service

router = APIRouter()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/applicant")
def add_applicant(
        applicant: ApplicantCreate,
        db: Session = Depends(get_db)):

    return create_applicant(
        db,
        applicant.user_id,
        applicant.full_name,
        applicant.email,
        applicant.phone,
        applicant.address,
        applicant.date_of_birth,
        applicant.ssn,
        applicant.employer_name
    )

@router.get("/applicant/{applicant_id}")
def get_applicant_by_id(applicant_id: int, db: Session = Depends(get_db)):
    return get_applicant_by_id_service(db, applicant_id)
##Get Applications by Applicant ID
@router.get("/applicant/{applicant_id}/applications")
def get_applications_by_applicant_id(applicant_id: int, db: Session = Depends(get_db)):
    
    
    return get_applications_by_applicant_id_service(db, applicant_id)
##Get all Applicants
@router.get("/applicant")
def get_all_applicants(db: Session = Depends(get_db)):
    return get_all_applicants_service(db)
#get Applicant by User ID
@router.get("/applicant/user/{user_id}")
def get_applicant_by_user_id(user_id: int, db: Session = Depends(get_db)):
    return get_applicant_by_user_id_service(db, user_id)