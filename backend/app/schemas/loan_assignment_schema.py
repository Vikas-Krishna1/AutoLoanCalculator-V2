# loan_assignment_schema.py

from pydantic import BaseModel


class AssignOfficerRequest(BaseModel):

    officer_id: int