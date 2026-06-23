from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.schemas.applicant_schema import ApplicantCreate
from app.schemas.loan_officer_schema import LoanOfficerCreate
from app.schemas.register_schema import RegisterApplicantRequest, RegisterOfficerRequest
from app.database import SessionLocal
from app.schemas.user_schema import UserCreate, UserResponse, UserLogin
from app.services.user_service import register_user, login_user, get_by_id_service, get_all_users_service, get_by_role_service,create_applicant_service, create_loan_officer_service

router = APIRouter()


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/register")
def register(applicant: ApplicantCreate,
             db: Session = Depends(get_db)):

    return create_applicant_service(
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
@router.post("/register/applicant")
def register_applicant(
        request: RegisterApplicantRequest,
        db: Session = Depends(get_db)):

    user = register_user(
        db,
        request.username,
        request.password,
        "APPLICANT"
    )

    return create_applicant_service(
        db,
        user.user_id,
        request.full_name,
        request.email,
        request.phone,
        request.address,
        request.date_of_birth,
        request.ssn,
        request.employer_name
    )
@router.post("/register/loan-officer")
def register_loan_officer(
        request: RegisterOfficerRequest,
        db: Session = Depends(get_db)):

    user = register_user(
        db,
        request.username,
        request.password,
        "OFFICER"
    )

    return create_loan_officer_service(
        db,
        user.user_id,
        request.full_name,
        request.email,
        request.phone,
        request.employee_number
    )
@router.post("/login")
def login(user: UserLogin,
             db: Session = Depends(get_db)):

    return login_user(
        db,
        user.username,
        user.password
    )
@router.get("/user/{user_id}")
def get_user_by_id(user_id: int, db: Session = Depends(get_db)):
    return get_by_id_service(db, user_id)
@router.get("/users")
def get_all_users(db: Session = Depends(get_db)):
    return get_all_users_service(db)