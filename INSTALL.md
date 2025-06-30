# Implementation Guide - Car Rental Management System - CADIRA RENTAL

**Experience the Future of Driving Today**

---

## System Requirements

- Java Development Kit (JDK) version 8 or higher
- NetBeans IDE (recommended version 12 or above)
- MySQL Server
- Git
- MySQL JDBC Driver
- SwingX 1.6.0

---

## Implementation Steps

### 1. Clone Repository from GitHub

Open terminal or CMD, then run the following command:

```bash
git clone https://github.com/username/cadira-rental.git
```

> **Note:** Replace `username` and the repository link according to your project's GitHub repository.

---

### 2. Import Project to NetBeans

1. Open NetBeans IDE.
2. Go to **File → Open Project...**.
3. Navigate to the `cadira-rental` folder from the cloned repository, then click **Open Project**.
4. Wait for the project to finish loading.

---

### 3. Setup MySQL Database

#### a. Start MySQL Server

Ensure MySQL is running.

#### b. Create New Database

Open a tool like **phpMyAdmin**, **MySQL Workbench**, or terminal, then execute:

```sql
CREATE DATABASE car_rental;
```

#### c. Import Table Structure

Navigate to the `db/` folder in the project, find the SQL file (if available), and run the script to create the necessary tables.

> **Note:** If no SQL file is provided, you need to manually create the required tables based on the application's needs.

---

### 4. Configure Database Connection

Open the following file:

```
src/main/java/com/carrental/model/DatabaseConnection.java
```

Make sure the following configuration matches your MySQL settings:

```java
private static final String DB_CONNECTION = "mysql";
private static final String DB_HOST = "localhost";
private static final int DB_PORT = 3306;
private static final String DB_DATABASE = "car_rental_db";

private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";
// Adjust according to your MySQL username and password
```

---

### 5. Add External Libraries

#### a. MySQL JDBC Driver

1. Download the `mysql-connector-java` file from the official MySQL website.
2. Copy the `.jar` file to the `lib/` folder.
3. Right-click the project in NetBeans → **Properties → Libraries**.
4. Click **Add JAR/Folder**, select the `.jar` file.

#### b. SwingX 1.6.0

1. Download `swingx-all-1.6.0.jar` from the official website.
2. Copy it to the `lib/` folder.
3. Add it to the Libraries as described above.

---

### 6. Run the Application

- Right-click the `App.java` file located in the `view` folder.
- Select **Run File** or click the **Run Project** button in the toolbar.

---

## Troubleshooting

- Ensure database connection settings are correct.
- If there are missing library errors, recheck the Libraries configuration in the project.
- Make sure all `.jar` files are properly linked.

---

## Useful Links

- [JDBC MySQL Download](https://dev.mysql.com/downloads/connector/j/)
- [SwingX 1.6.0 Download](https://mvnrepository.com/artifact/org.swinglabs/swingx/1.6.1)

---

**Thank you for using this application.**

**CADIRA RENTAL**