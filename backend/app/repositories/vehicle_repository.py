from sqlalchemy.orm import Session
from app.models.Vehicle import Vehicle
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
    
    @staticmethod
    def get_vehicle_by_id( vehicle_id: int, session: Session):
        return session.query(Vehicle).filter(Vehicle.vehicle_id == vehicle_id).first()
    @staticmethod
    def get_all_vehicles(db: Session):
        return db.query(Vehicle).all()