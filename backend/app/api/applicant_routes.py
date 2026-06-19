from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.applicant_schema import ApplicantCreate
from app.services.applicant_service import create_applicant, get_applications_by_applicant_id_service

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
        applicant.adress,
        applicant.date_of_birth,
        applicant.ssn,
        applicant.employer_name
    )

@router.get("/applicant/{applicant_id}")
def get_applicant_by_id(applicant_id: int, db: Session = Depends(get_db)):
    return get_applications_by_applicant_id_service(db, applicant_id)