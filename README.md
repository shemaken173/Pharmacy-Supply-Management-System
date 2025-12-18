# Pharmacy Supply Management System

## 1. Project Overview
This Pharmacy Supply Management System is a Java-based solution designed to automate inventory tracking, supplier coordination, and order processing. It addresses real-life challenges such as manual record-keeping errors and stock management inefficiencies.

## 2. System Architecture and Design
The system is designed using several architectural views to ensure a robust and clean software structure.

### Data Flow Diagram (DFD) - Level 1
The DFD illustrates the movement of data between processes and data stores.
* **User Authentication (P1)**: Manages login credentials and authentication status with the Users Database (D1).
* **Order & Inventory Control (P3, P4, P6)**: Handles the creation of orders and tracking of inventory transactions.
* **Reporting (P8)**: Generates alerts and reports for administrators.



### Activity Diagram - Order Processing
This diagram details the step-by-step logic for fulfilling a medicine order.
* **Validation**: The system validates order details (budget and quantity) before processing.
* **Stock Update**: Upon completion, the system automatically records the transaction and updates medicine stock quantities.



### Sequence Diagram - Purchase Workflow
The sequence diagram shows object interactions for stock verification and order placement.
* **Stock Check**: The Order object queries the Inventory to verify medicine availability.
* **Conditional Logic**: If stock is available, it confirms with the Supplier; if not, it notifies the user of unavailability.



## 3. Technical Requirements & Implementation
As per the final exam requirements, the following standards were applied:
* **Design Pattern**: Implements the **DAO (Data Access Object)** pattern (e.g., `UserDao`, `MedicineDao`) to separate data access from business logic.
* **Clean Code**: Adheres to Google's Java coding standards for readability and maintenance.
* **Version Control**: Managed using **Git** to track changes and project history.
* **Containerization**: The application is fully **Dockerized**.

## 4. Setup and Deployment

### Database Configuration
The application connects to a PostgreSQL database named `PharmacySupplyManagementSystem`.
* **Local Environment**: Set the URL to `jdbc:postgresql://localhost:5432/...`.
* **Docker Environment**: Set the URL to `jdbc:postgresql://host.docker.internal:5432/...` to allow the container to communicate with the host database.

### Docker Commands
To build and run the application in a containerized environment:

1. **Build the Image**:
   ```bash
   docker build -t pharmacysupplymanagementsystem .
   docker run -e DISPLAY=host.docker.internal:0 pharmacysupplymanagementsystem
