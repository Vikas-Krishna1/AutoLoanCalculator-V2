from pydantic import BaseModel


class LoanOfficerCreate(BaseModel):
    user_id: int
    full_name: str
    email: str
    phone: str
    employee_number: str