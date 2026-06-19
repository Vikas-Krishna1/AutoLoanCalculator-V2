from fastapi import FastAPI
##Routers
from app.api.user_routes import router as user_router
from app.api.applicant_routes import router as applicant_router
from app.api.loan_application_routes import router as loan_application_router
from app.api.vehicle_routes import router as vehicle_router
from app.api.officer_routes import router as officer_router
##Services
from app.services.loan_calculator_service import LoanCalculatorService
from app.schemas.loan_schema import AutoLoanRequest
from app.schemas.vehicle_schema import VehicleCreate
from app.services.vehicle_service import create_vehicle



app = FastAPI()
app.include_router(user_router)
app.include_router(applicant_router)
app.include_router(loan_application_router)
app.include_router(vehicle_router)
app.include_router(officer_router)

@app.get("/")
def root():
    return {"message": "Auto Loan API running"}
@app.post("/calculate-loan")
def calculate_loan(loan: AutoLoanRequest):

    return {
        "loan_amount":
            LoanCalculatorService.calculate_loan_amount(loan),

        "monthly_payment":
            LoanCalculatorService.calculate_monthly_payment(loan),

        "sales_tax":
            LoanCalculatorService.calculate_sales_tax_amount(loan),

        "upfront_costs":
            LoanCalculatorService.calculate_upfront_costs(loan),

        "total_payment":
            LoanCalculatorService.calculate_total_payment(loan),

        "total_interest":
            LoanCalculatorService.calculate_total_interest(loan),

        "out_the_door_price":
            LoanCalculatorService.calculate_out_the_door_price(loan)
    }