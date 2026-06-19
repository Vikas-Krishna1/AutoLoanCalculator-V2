from sqlalchemy import Column, Integer, Float, String
from app.database import Base
from sqlalchemy.sql.expression import text
from sqlalchemy.sql.sqltypes import TIMESTAMP

class LoanApplication(Base):
    __tablename__ = "loan_application"

    
    application_id = Column(Integer, primary_key=True)

    applicant_id = Column(Integer)
    vehicle_id = Column(Integer)
    loan_officer_id = Column(Integer)

    auto_price = Column(Float)
    sales_tax = Column(Float)
    fees = Column(Float)
    cash_incentive = Column(Float)
    down_payment = Column(Float)

    interest_rate = Column(Float)
    loan_term = Column(Integer)

    loan_amount = Column(Float)
    monthly_payment = Column(Float)

    status = Column(String(20))

    created_at = Column(
        TIMESTAMP,
        server_default=text("CURRENT_TIMESTAMP")
    )