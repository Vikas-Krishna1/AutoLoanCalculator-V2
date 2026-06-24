# AutoLoanCalculator 🚗

> A full-stack auto loan management platform simulating the end-to-end workflow of a real-world automotive lending system — from application submission to officer review and approval.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![FastAPI](https://img.shields.io/badge/FastAPI-005571?style=for-the-badge&logo=fastapi)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

---

## Overview

AutoLoanCalculator models a complete automotive lending workflow with two distinct user roles: **applicants**, who register, build profiles, submit vehicle and financing information, and track application status; and **loan officers**, who review assigned applications, approve or deny loans, leave review notes, and generate reports. The system is built as a desktop client backed by a cloud-hosted REST API and relational database — a deliberate architecture choice to demonstrate enterprise-style separation of concerns.

---

## Features

### 👤 Applicant Workflow
- Account registration and secure authentication
- Profile and vehicle information management
- Loan application submission
- Real-time application status tracking
- Application history view

### 🧑‍💼 Loan Officer Workflow
- Role-based access to assigned application queues
- Review of applicant and vehicle details
- Approve / deny decision workflow with review notes
- PDF report generation for processed applications

### 🔐 Security
- Industry-standard password hashing and verification
- Role-based access control separating applicant and officer dashboards
- Input validation throughout to prevent invalid or incomplete submissions

---

## Architecture

The system is split into two independently deployable services:

```
┌─────────────────────┐         REST API          ┌──────────────────────┐
│   Java Swing Client   │ ───────────────────────▶ │   FastAPI Backend     │
│  (Desktop Application)│ ◀─────────────────────── │   (Business Logic)    │
└─────────────────────┘                            └──────────┬───────────┘
                                                                │
                                                                ▼
                                                       ┌──────────────────┐
                                                       │   MySQL (Aiven)   │
                                                       │  Relational Store  │
                                                       └──────────────────┘
```

**Frontend** — Java Swing multi-window desktop application with custom API client classes for consuming REST endpoints and performing CRUD operations against users, applicants, vehicles, officers, and loan applications.

**Backend** — FastAPI service organized into modular layers: routes, schemas, services, repositories, models, and database configuration. SQLAlchemy handles ORM-based database interaction with a clean repository pattern.

**Database** — MySQL with normalized relational tables and foreign key constraints linking applicants, vehicles, loan applications, and officers — ensuring referential integrity across multi-step workflows.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Desktop Client | Java, Swing |
| Backend API | FastAPI (Python) |
| ORM | SQLAlchemy |
| Database | MySQL |
| Build Tool (Java) | Maven |
| Backend Deployment | Render |
| Database Hosting | Aiven (managed cloud MySQL) |
| Version Control | Git, GitHub |

---

## Getting Started

### Prerequisites
- Java 17+ and Maven
- Python 3.10+
- MySQL instance (local or cloud)

### Backend Setup

```bash
# Clone the repository
git clone https://github.com/vikaskrishna/autoloancalculator.git
cd autoloancalculator/backend

# Create virtual environment
python -m venv venv
source venv/bin/activate  # or venv\Scripts\activate on Windows

# Install dependencies
pip install -r requirements.txt

# Configure environment variables
cp .env.example .env
# Add your MySQL connection string and secrets

# Run the API
uvicorn main:app --reload
```

### Desktop Client Setup

```bash
cd autoloancalculator/client

# Build with Maven
mvn clean package

# Run the generated JAR
java -jar target/autoloancalculator-client.jar
```

---

## Environment Variables

```env
DATABASE_URL=mysql+pymysql://user:password@host:port/dbname
SECRET_KEY=your_secret_key
ACCESS_TOKEN_EXPIRE_MINUTES=30
```

---

## Project Structure

```
autoloancalculator/
├── backend/
│   ├── routes/          # API endpoint definitions
│   ├── schemas/         # Pydantic request/response models
│   ├── services/        # Business logic layer
│   ├── repositories/    # Database access layer
│   ├── models/          # SQLAlchemy ORM models
│   ├── utils/           # Shared utilities (hashing, auth, etc.)
│   └── main.py
├── client/
│   ├── src/
│   │   ├── ui/          # Swing windows and dashboards
│   │   └── api/         # API client classes (CRUD consumers)
│   └── pom.xml
└── README.md
```

---

## Deployment

- **Backend** — Deployed as a publicly accessible web service on Render
- **Database** — Hosted on Aiven's managed MySQL cloud platform
- **Client** — Packaged as a runnable Maven JAR configured to communicate with production API endpoints

---

## Testing & Validation

End-to-end functionality has been verified across:
- Applicant registration and profile creation
- Vehicle registration and loan submission
- Application history tracking
- Officer review queues and approval/denial workflows
- PDF report generation
- Cloud database operations under production configuration

---

## Author

**Vikas Krishna** — [@vikaskrishna](https://github.com/vikaskrishna)
