from sqlalchemy import Column, Integer, String
from app.database import Base
from sqlalchemy.sql.expression import text
from sqlalchemy.sql.sqltypes import TIMESTAMP

class Applicant(Base):
    __tablename__ = "applicant"

    applicant_id = Column(Integer, primary_key=True)
    user_id = Column(Integer)
    full_name = Column(String(100))
    email = Column(String(100))
    phone = Column(String(20))
    address = Column(String(100))
    date_of_birth = Column(String(20))
    ssn = Column(String(20))
    employer_name = Column(String(100))

    created_at = Column(
        TIMESTAMP,
        server_default=text("CURRENT_TIMESTAMP")
    )