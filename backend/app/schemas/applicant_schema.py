from pydantic import BaseModel

class ApplicantCreate(BaseModel):
    user_id: int
    full_name: str
    email: str
    phone: str
    adress: str
    date_of_birth: str
    ssn: str
    employer_name: str