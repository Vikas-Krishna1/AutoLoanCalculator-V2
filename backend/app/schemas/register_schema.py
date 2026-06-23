from pydantic import BaseModel

class RegisterApplicantRequest(BaseModel):
    username: str
    password: str

    full_name: str
    email: str
    phone: str

    address: str
    date_of_birth: str
    ssn: str
    employer_name: str


class RegisterOfficerRequest(BaseModel):
    username: str
    password: str

    full_name: str
    email: str
    phone: str

    employee_number: str