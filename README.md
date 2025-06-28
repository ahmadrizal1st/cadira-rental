# Car Rental Management System

This is a simple Car Rental Management System developed in Java using Swing for the GUI, MySQL for the database, and following the Model-View-Controller (MVC) architectural pattern.

## Features
- **Car Management**: Add, update, delete, and view car details.
- **Customer Management**: Add, update, delete, and view customer details.
- **Rental Management**: Create, update, delete, and view car rental records.

## Technologies Used
- Java (JDK 8 or higher)
- Swing (for GUI)
- MySQL Database
- JDBC (Java Database Connectivity)
- MVC Architecture

## Setup Instructions

### 1. Database Setup

1.  Ensure you have MySQL installed and running.
2.  Open your MySQL client (e.g., MySQL Workbench, command line).
3.  Execute the SQL script located at `car_rental_program/db/car_rental_db.sql` to create the database and tables:

    ```sql
    CREATE DATABASE IF NOT EXISTS car_rental_db;
    USE car_rental_db;

    CREATE TABLE IF NOT EXISTS cars (
        car_id INT AUTO_INCREMENT PRIMARY KEY,
        make VARCHAR(50) NOT NULL,
        model VARCHAR(50) NOT NULL,
        year INT NOT NULL,
        license_plate VARCHAR(20) UNIQUE NOT NULL,
        daily_rate DECIMAL(10, 2) NOT NULL,
        available BOOLEAN NOT NULL DEFAULT TRUE
    );

    CREATE TABLE IF NOT EXISTS customers (
        customer_id INT AUTO_INCREMENT PRIMARY KEY,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        phone_number VARCHAR(20)
    );

    CREATE TABLE IF NOT EXISTS rentals (
        rental_id INT AUTO_INCREMENT PRIMARY KEY,
        car_id INT NOT NULL,
        customer_id INT NOT NULL,
        rental_date DATE NOT NULL,
        return_date DATE,
        total_cost DECIMAL(10, 2),
        FOREIGN KEY (car_id) REFERENCES cars(car_id),
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
    );
    ```

4.  Update the `DatabaseConnection.java` file (`car_rental_program/src/main/java/com/carrental/model/DatabaseConnection.java`) with your MySQL username and password:

    ```java
    private static final String USER = "your_mysql_username"; // e.g., "root"
    private static final String PASSWORD = "your_mysql_password"; // e.g., "password"
    ```

### 2. Project Setup (NetBeans IDE)

1.  Open NetBeans IDE.
2.  Go to `File` -> `Open Project...`.
3.  Navigate to the `car_rental_program` directory and click `Open Project`.
4.  **Add MySQL JDBC Driver**: 
    - Right-click on `Libraries` in the `Projects` window.
    - Select `Add JAR/Folder...`.
    - Navigate to where you downloaded the MySQL Connector/J (JDBC driver) JAR file (e.g., `mysql-connector-java-x.x.x.jar`). If you don't have it, you can download it from the official MySQL website.
    - Select the JAR file and click `Open`.

### 3. Build and Run

1.  Clean and Build the project: `Run` -> `Clean and Build Project`.
2.  Run the application: `Run` -> `Run Project` or press `F6`.

## Project Structure

```
car_rental_program/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── carrental/
│                   ├── controller/
│                   │   ├── CarController.java
│                   │   ├── CustomerController.java
│                   │   └── RentalController.java
│                   ├── model/
│                   │   ├── Car.java
│                   │   ├── Customer.java
│                   │   ├── DatabaseConnection.java
│                   │   └── Rental.java
│                   └── view/
│                       ├── CarPanel.java
│                       ├── CustomerPanel.java
│                       ├── MainFrame.java
│                       └── RentalPanel.java
│                   └── App.java
├── lib/ (for JDBC driver)
├── db/
│   └── car_rental_db.sql
└── README.md
```



