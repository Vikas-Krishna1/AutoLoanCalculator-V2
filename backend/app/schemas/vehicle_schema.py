# schemas/vehicle_schema.py

from pydantic import BaseModel

class VehicleCreate(BaseModel):
    make: str
    model: str
    year: int
    vin: str