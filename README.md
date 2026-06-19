# Auto Loan Management System

## Overview

The Auto Loan Management System is a Java desktop application designed to streamline the automobile loan application process for applicants, loan officers, and administrators. The system provides role-based access, allowing users to securely submit, review, and manage loan applications through an intuitive graphical interface built with Java Swing.

The application follows an object-oriented design and integrates with a MySQL database to maintain persistent records of users, applicants, vehicles, loans, and application statuses. Passwords are securely stored using BCrypt hashing to enhance authentication security.

---

## Features

### Applicant Portal

* Secure registration and login
* Submit new auto loan applications
* Automatic loan amount and monthly payment calculations
* View current applications
* Access application history
* Review detailed application information

### Loan Officer Portal

* View application queue
* Assign applications to themselves
* Manage only assigned applications
* Monitor approval statistics
* Dashboard with performance metrics
* Export reports to PDF

### Authentication and Security

* BCrypt password hashing
* Session management
* Role-based access control
* Secure login validation

### Application Processing

* Applicant information management
* Vehicle information management
* Auto loan calculations
* Status tracking
* Search and filtering capabilities

---

## Tech Stack

### Language

* Java

### GUI Framework

* Java Swing

### Database

* MySQL

### Database Connectivity

* JDBC

### Security

* jBCrypt

### PDF Reporting

* iText PDF

### Build Tool

* Maven

### Architecture

* Object-Oriented Programming (OOP)
* MVC-inspired design

---

## Project Structure

```
src
├── db
│   └── DatabaseManager.java
├── models
│   ├── Applicant.java
│   ├── AutoLoan.java
│   ├── LoanApplication.java
│   ├── User.java
│   └── Vehicle.java
├── services
│   └── LoanCalculator.java
├── ui
│   ├── login
│   ├── register
│   ├── applicant
│   ├── officer
│   ├── admin
│   └── table
├── utils
│   ├── passwordUtils.java
│   └── PDFExporter.java
└── Main
    └── Main.java
```

---

## Database Tables

* users
* applicant
* vehicle
* auto_loan
* loan_application
* loan_officer
* admin

---

## Major Concepts Implemented

* Encapsulation
* Inheritance
* Composition
* Separation of concerns
* Event-driven programming
* JDBC database operations
* Password hashing
* Session management
* Input validation
* PDF generation

---

## Running the Project

### Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/AutoLoanCalc.git
```

### Navigate to the Project

```bash
cd AutoLoanCalc
```

### Install Dependencies

```bash
mvn clean install
```

### Run the Application

```bash
mvn exec:java -Dexec.mainClass="src.Main.Main"
```

---

## Future Enhancements

* Admin dashboard and user management
* Reassign applications between loan officers
* Email notifications
* Credit score integration
* Advanced reporting and analytics
* Search optimization
* REST API support
* Migration to JavaFX or web-based frontend

---

## Author

**Vikas S. Krishna**

B.S. Computer Science
Stony Brook University

---

## License

This project is intended for educational purposes and portfolio demonstration.
