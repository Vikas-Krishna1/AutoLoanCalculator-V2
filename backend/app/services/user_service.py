from sqlalchemy.orm import Session
from app.repositories.user_repository import UserRepository
import app.utils.security as security
from fastapi import HTTPException



def register_user(db: Session, username: str, password: str, role: str):

    existing = UserRepository.get_by_username(db, username)

    if existing:
        raise Exception("Username already exists")
    hashed_password = security.hash_password(password)
    print("Username")
    print(username)
    print("Password")
    print(password)
    print(hashed_password)

    return UserRepository.create_user(
        db,
        username,
        hashed_password,
        role
    )
def login_user(db: Session, username: str, password: str):
    user = UserRepository.get_by_username(db, username)
    print("RAW PASSWORD:", repr(password))
    print("RAW USER:", repr(username))

    if not user:
        raise HTTPException(status_code=401, detail="Invalid username or password")

    print("Input username:", username)
    print("Input password:", password)
    print("Stored hash:", user.password)

    if not security.verify_password(password, user.password):
        raise HTTPException(status_code=401, detail="Invalid username or password")

    return user
def get_all_users_service(db: Session):
    return UserRepository.get_all_users(db)
def get_by_role_service(db: Session, role: str):
    return UserRepository.get_by_role(db, role)
def get_by_id_service(db: Session, id: int):
    return UserRepository.get_by_id(db, id)
def create_applicant_service(db: Session,user_id: int, full_name: str,email: str, phone: str,address: str, date_of_birth: str,ssn: str, employer_name: str):
    return UserRepository.create_applicant(db,user_id, full_name,email, phone,address, date_of_birth,ssn, employer_name)
def create_loan_officer_service(db: Session,user_id: int, full_name: str,email: str, phone: str,employee_number: str):
    return UserRepository.create_loan_officer(db,user_id, full_name,email, phone,employee_number)