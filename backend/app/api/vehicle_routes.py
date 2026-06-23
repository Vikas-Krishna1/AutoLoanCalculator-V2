from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.schemas.vehicle_schema import VehicleCreate
from app.services.vehicle_service import create_vehicle, get_all_vehicles_service, get_vehicle_by_id_service

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
@router.get("/vehicle")
def get_all_vehicles(db: Session = Depends(get_db)):
    return get_all_vehicles_service(db)
@router.get("/vehicle/{vehicle_id}")
def get_vehicle_by_id(vehicle_id: int, db: Session = Depends(get_db)):
    return get_vehicle_by_id_service(vehicle_id, db)