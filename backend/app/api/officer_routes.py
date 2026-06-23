from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.loan_officer_schema import LoanOfficerCreate
from app.services.loan_officer_service import create_loan_officer, get_all_applications_by_officer, get_by_id_service, get_by_user_id_service, get_all_service, get_officer_statistics_service

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
@router.get("/officer/{loan_officer_id}")
def get_officer_by_id(loan_officer_id: int, db: Session = Depends(get_db)):
    return get_by_id_service(db, loan_officer_id)
@router.get("/officer/user/{user_id}")
def get_officer_by_user_id(user_id: int, db: Session = Depends(get_db)):
    return get_by_user_id_service(db, user_id)
@router.get("/officer")
def get_all_officers(db: Session = Depends(get_db)):
    return get_all_service(db)
@router.get("/officer/{loan_officer_id}/statistics")
def get_officer_statistics(loan_officer_id: int, db: Session = Depends(get_db)):
    return get_officer_statistics_service(db, loan_officer_id)