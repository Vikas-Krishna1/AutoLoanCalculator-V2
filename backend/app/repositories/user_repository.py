from sqlalchemy.orm import Session
from app.models.User import User

class UserRepository:

    @staticmethod
    def get_by_username(db: Session, username: str):
        return db.query(User).filter(
            User.username == username
        ).first()

    @staticmethod
    def get_by_id(db: Session, id: int):
        return db.query(User).filter(
            User.user_id == id
        ).first()
    @staticmethod
    def create_user(db: Session, username: str, password: str, role: str):
        user = User(
            username=username,
            password=password,
            role=role
        )
        db.add(user)
        db.commit()
        db.refresh(user)
        return user