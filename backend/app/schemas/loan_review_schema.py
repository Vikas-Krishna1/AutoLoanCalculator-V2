# loan_review_schema.py

from pydantic import BaseModel

class ReviewRequest(BaseModel):

    officer_id: int
    review_notes: str