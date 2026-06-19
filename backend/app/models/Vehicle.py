# models/Vehicle.py

from sqlalchemy import Column, Integer, String, Float
from app.database import Base
from sqlalchemy.sql.expression import text
from sqlalchemy.sql.sqltypes import TIMESTAMP

class Vehicle(Base):
    __tablename__ = "vehicle"

    vehicle_id = Column(Integer, primary_key=True)
    make = Column(String(50))
    model = Column(String(50))
    year = Column(Integer)
    vin = Column(String(17))
    created_at = Column(
        TIMESTAMP,
        server_default=text("CURRENT_TIMESTAMP")
    )
