from sqlalchemy.orm import Session
from app.models.LoanOfficer import LoanOfficer


class LoanOfficerRepository:

    @staticmethod
    def create(
        db: Session,
        user_id: int,
        full_name: str,
        email: str,
        phone: str,
        employee_number: str
    ):

        officer = LoanOfficer(
            user_id=user_id,
            full_name=full_name,
            email=email,
            phone=phone,
            employee_number=employee_number
        )

        db.add(officer)
        db.commit()
        db.refresh(officer)

        return officer