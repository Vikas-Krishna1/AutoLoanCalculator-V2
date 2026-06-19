from sqlalchemy.orm import Session
from app.models.Vehicle import Vehicle
from app.schemas import  vehicle_schema
from app.repositories import vehicle_repository as vehicle_repository


class VehicleRepository:
    def __init__(self, session: Session):
        self.session = session

    @staticmethod
    def create_vehicle(
        db: Session,
        make: str,
        model: str,
        year: int,
        vin: str
    ):
        vehicle = Vehicle(
            make=make,
            model=model,
            year=year,
            vin=vin

        )

        db.add(vehicle)
        db.commit()
        db.refresh(vehicle)

        return vehicle

    def get_vehicle_by_id(self, vehicle_id: int):
        return self.session.query(Vehicle).filter(Vehicle.vehicle_id == vehicle_id).first()
    def get_all_vehicles(self):
        
        return self.session.query(Vehicle).all()