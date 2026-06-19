from sqlalchemy.orm import Session
from app.repositories.user_repository import UserRepository
import app.utils.security as security


def register_user(db: Session, username: str, password: str, role: str):

    existing = UserRepository.get_by_username(db, username)

    if existing:
        raise Exception("Username already exists")
    hashed_password = security.hash_password(password)

    return UserRepository.create_user(
        db,
        username,
        hashed_password,
        role
    )
def login_user(db: Session, username: str, password: str):
    user = UserRepository.get_by_username(db, username)

    if not user:
        raise Exception("Invalid username or password")

    if not security.verify_password(password, user.password) == password:
        raise Exception("Invalid username or password")

    return user