from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.loan_applicaation_schema import LoanApplicationCreate
from app.services.loan_application_service import create_loan_application
from app.schemas.loan_assignment_schema import AssignOfficerRequest
from app.services.loan_application_service import assign_officer
from app.schemas.loan_review_schema import ReviewRequest
from app.services.loan_application_service import approve_application, deny_application, get_pending_applications, get_approved, get_denied, get_under_review, get_loanApplication_by_id_service, get_loanApplication_by_vehicle_id_service, get_loanApplication_by_applicant_id_service, get_all_applications_service

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
@router.put("/loan/{application_id}/approve")
def approve_loan(

    application_id: int,
    request: ReviewRequest,
    db: Session = Depends(get_db)

):

    
    return approve_application(

        db,
        application_id,
        request.officer_id,
        request.review_notes

    )
@router.put("/loan/{application_id}/deny")
def deny_loan(

    application_id: int,
    request: ReviewRequest,
    db: Session = Depends(get_db)

):

    return deny_application(
        db,
        application_id,
        request.officer_id,
        request.review_notes
)
## Get all Pending applications
@router.get("/loan/pending")
def getpending_applications(db: Session = Depends(get_db)):
    return get_pending_applications(db)
##Get all Aproved applications
@router.get("/loan/approved")
def getapproved_applications(db: Session = Depends(get_db)):
    return get_approved(db)
##Get all Denied applications
@router.get("/loan/denied")
def getdenied_applications(db: Session = Depends(get_db)):
    return get_denied(db)
##Get all under review applications
@router.get("/loan/review")
def getunderreview_applications(db: Session = Depends(get_db)):
    return get_under_review(db)

@router.get("/loan/{application_id}")
def get_loanApplication_by_id(application_id: int, db: Session = Depends(get_db)):
    return get_loanApplication_by_id_service(db, application_id)

@router.get("/loan/vehicle/{vehicle_id}")
def get_loanApplication_by_vehicle_id(vehicle_id: int, db: Session = Depends(get_db)):
    return get_loanApplication_by_vehicle_id_service(db, vehicle_id)

@router.get("/loan/applicant/{applicant_id}")
def get_loanApplication_by_applicant_id(applicant_id: int, db: Session = Depends(get_db)):
    return get_loanApplication_by_applicant_id_service(db, applicant_id)

@router.get("/loan")
def get_all_loan_applications(db: Session = Depends(get_db)):
    return get_all_applications_service(db)


