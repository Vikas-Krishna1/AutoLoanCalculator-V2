import sqlalchemy
from sqlalchemy import Column, Integer, String
from app.database import Base
from sqlalchemy.sql.expression import text
from sqlalchemy.sql.sqltypes import TIMESTAMP
class LoanOfficer(Base):
    __tablename__ = "loan_officer"

    loan_officer_id = Column(Integer, primary_key=True)
    user_id = Column(Integer)
    full_name = Column(String(100))
    email = Column(String(100))
    phone = Column(String(20))
    employee_number = Column(String(20), unique=True)

    created_at = Column(
        TIMESTAMP,
        server_default=text("CURRENT_TIMESTAMP")
    )
