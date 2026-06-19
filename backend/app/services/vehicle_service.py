from sqlalchemy.orm import Session
from app.repositories.vehicle_repository import VehicleRepository

def create_vehicle(
    db: Session,
    make: str,
    model: str,
    year: int,
    vin: str

):

    return VehicleRepository.create_vehicle(
        db,
        make,
        model,
        year,
        vin

    )