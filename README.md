# Car Rental Management System - CADIRA RENTAL

**Experience the Future of Driving Today**

![Cover Image](assets/cover.png)

## Project Overview

Car Rental Management System is a simple desktop application developed using Java. This system simplifies the management of car rentals, customers, and vehicle data, complete with validation to prevent double-booking of rented cars.

## Main Features

- **Login**
  - User authentication.

- **Rental Management (Transactions)**
  - Add, update, delete, and view rental transactions.
  - Validation to prevent renting a car that is already rented.

- **Car Management**
  - Add, update, delete, and view car data.

- **Customer Management**
  - Add, update, delete, and view customer data.

## Technology Stack

- **Backend & Logic**
  - Java: Main programming language.
  - JDBC Driver MySQL: Connector between Java application and MySQL database.

- **Database**
  - MySQL: Relational database management system.

- **GUI / Frontend**
  - Swing: Standard Java library for building GUIs.
  - SwingX 1.6.0: Swing extension providing advanced components like JXDatePicker.
  - JFrame & JPanel: Core components for window and panel structures.

- **IDE**
  - NetBeans IDE: Integrated Development Environment for coding, debugging, and GUI design.

## Project Structure

```
CAR_RENTAL/
├── assets/                    # Assets folder (images, etc)
├── build/                     # Build output
├── db/                        # Database
├── lib/                       # External libraries (JDBC driver, SwingX, etc)
├── nbproject/                 # NetBeans project configuration
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── carrental/
│                   ├── controller/         # MVC: Controller layer
│                   │   ├── CarController.java
│                   │   ├── CustomerController.java
│                   │   ├── LoginController.java
│                   │   └── RentalController.java
│                   ├── model/              # MVC: Model layer
│                   │   ├── Car.java
│                   │   ├── Customer.java
│                   │   ├── DatabaseConnection.java
│                   │   ├── Rental.java
│                   │   └── User.java
│                   └── view/               # MVC: View layer
│                       ├── CarPanel.java
│                       ├── CustomerPanel.java
│                       ├── LoginFrame.java
│                       ├── MainFrame.java
│                       ├── RentalPanel.java
│                       ├── Theme.java
│                       └── App.java        # Application entry point
├── test/                      # Unit tests
├── .gitignore                 # Git ignore configuration
├── build.xml                  # Build configuration
└── manifest.mf                # Manifest configuration
```

## Architecture

The system follows the **Model-View-Controller (MVC)** architecture to separate logic, view, and data.

## Graphical User Interface (GUI)

- Login
- Car Management
- Customer Management
- Rental Management

## Contributors

- [Dinul Hayat (23105826)](https://github.com/DinulHyt)
- [Nasya Kemal Giffari (231057018)](https://github.com/KemalNasya)
- [Ahmad Rizal (231057014)](https://github.com/ahmadrizal1st)

---

Thank you for using this application.

**CADIRA RENTAL**