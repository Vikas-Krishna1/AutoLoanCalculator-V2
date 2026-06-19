# schemas/loan_schema.py

from pydantic import BaseModel


class AutoLoanRequest(BaseModel):
    auto_price: float
    sales_tax: float
    fees: float
    cash_incentive: float
    down_payment: float

    interest_rate: float
    loan_term: int