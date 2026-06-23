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
##Get Vehicle by ID

def get_vehicle_by_id_service(vehicle_id: int, db: Session):
    return VehicleRepository.get_vehicle_by_id(vehicle_id, db)
##Get all vehicles

def get_all_vehicles_service(db: Session):
    return VehicleRepository.get_all_vehicles(db)