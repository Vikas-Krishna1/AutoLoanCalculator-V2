from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.loan_officer_schema import LoanOfficerCreate
from app.services.loan_officer_service import create_loan_officer, get_all_applications_by_officer

router = APIRouter()


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/officer")
def add_officer(
        officer: LoanOfficerCreate,
        db: Session = Depends(get_db)
):

    return create_loan_officer(
        db,
        officer.user_id,
        officer.full_name,
        officer.email,
        officer.phone,
        officer.employee_number
    )
##Get Applications by Officer
@router.get("/officer/{loan_officer_id}/applications")
def get_applications_by_officer(loan_officer_id: int, db: Session = Depends(get_db)):
    return get_all_applications_by_officer(db, loan_officer_id)