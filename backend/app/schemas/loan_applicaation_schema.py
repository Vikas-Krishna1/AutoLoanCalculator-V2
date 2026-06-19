from pydantic import BaseModel

class LoanApplicationCreate(BaseModel):
    applicant_id: int
    vehicle_id: int
    auto_price: float
    sales_tax: float
    fees: float
    cash_incentive: float
    down_payment: float
    interest_rate: float
    loan_term: int
