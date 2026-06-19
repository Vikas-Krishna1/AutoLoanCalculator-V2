from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.loan_applicaation_schema import LoanApplicationCreate
from app.services.loan_application_service import create_loan_application
from app.schemas.loan_assignment_schema import AssignOfficerRequest
from app.services.loan_application_service import assign_officer

router = APIRouter()


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/loan")
def add_loan(
    loan: LoanApplicationCreate,
    db: Session = Depends(get_db)
):

    return create_loan_application(
    db,
    loan.applicant_id,
    loan.vehicle_id,
    loan.auto_price,
    loan.sales_tax,
    loan.fees,
    loan.cash_incentive,
    loan.down_payment,
    loan.interest_rate,
    loan.loan_term
    )
@router.put("/loan/{application_id}/assign")
def assign_loan_officer(

    application_id: int,
    request: AssignOfficerRequest,
    db: Session = Depends(get_db)

):

    return assign_officer(

        db,
        application_id,
        request.officer_id

    )
