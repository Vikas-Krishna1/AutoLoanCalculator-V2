from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.vehicle_schema import VehicleCreate
from app.services.vehicle_service import create_vehicle

router = APIRouter()


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/vehicle")
def add_vehicle(
    vehicle: VehicleCreate,
    db: Session = Depends(get_db)
):

    return create_vehicle(
        db,
        vehicle.make,
        vehicle.model,
        vehicle.year,
        vehicle.vin
    )