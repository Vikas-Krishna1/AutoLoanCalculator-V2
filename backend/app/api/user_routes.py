from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.user_schema import UserCreate, UserResponse, UserLogin
from app.services.user_service import register_user, login_user

router = APIRouter()


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/register")
def register(user: UserCreate,
             db: Session = Depends(get_db)):

    return register_user(
        db,
        user.username,
        user.password,
        user.role
    )
@router.post("/login")
def login(user: UserLogin,
             db: Session = Depends(get_db)):

    return login_user(
        db,
        user.username,
        user.password
    )