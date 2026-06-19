from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
import os

load_dotenv()

MY_SQL_HOST = os.getenv("MYSQL_HOST")
MY_SQL_USER = os.getenv("MYSQL_USER")
MY_SQL_PASSWORD = os.getenv("MYSQL_PASSWORD")
MY_SQL_DATABASE = os.getenv("MYSQL_DATABASE")
MY_SQL_PORT = os.getenv("MYSQL_PORT")

DATABASE_URL = f"mysql+mysqlconnector://{MY_SQL_USER}:{MY_SQL_PASSWORD}@{MY_SQL_HOST}:{MY_SQL_PORT}/{MY_SQL_DATABASE}"
print(DATABASE_URL)

engine = create_engine(
    DATABASE_URL,
    pool_pre_ping=True,
    connect_args={
        "ssl_disabled": False
    }
)


SessionLocal = sessionmaker(
    autocommit=False,
    autoflush=False,
    bind=engine
)

Base = declarative_base()


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()