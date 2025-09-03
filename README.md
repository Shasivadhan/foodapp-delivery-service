# 🍴 Food Ordering Microservices System

This project is a **Food Ordering System** built using **Spring Boot microservices**, **MongoDB**, and **Kafka**.  
It simulates a real-world platform where users can browse food items, place orders, pay through a bank service, and track delivery.

---

## 📌 Microservices Overview

### 1. Vendor Service
- Manages vendors and their menu items.
- Provides APIs to:
  - Add / update menu items.
  - Mark food items available/unavailable.
- Publishes **menu change events** to Kafka (`menu-changes.v1`) for real-time updates.

### 2. Order Service
- Manages user carts and orders.
- Key features:
  - Fetch user cart and place orders.
  - Calls **Bank Service** for fund transfer.
  - Consumes **menu change events** from Kafka.
  - Produces **delivery events** to Kafka (`delivery-requests.v1`).

### 3. Bank Service
- Handles user accounts and transactions.
- Exposes APIs for fund transfer between accounts.
- Integrated with Order Service for payment flow.

### 4. Delivery Service
- Kafka consumer that listens for **delivery events**.
- Stores delivery info into MongoDB.
- Can be extended to handle delivery tracking.

---

## 🛠️ Tech Stack
- **Java 17**, **Spring Boot 3**
- **Spring Data MongoDB**
- **Spring Cloud OpenFeign** (inter-service communication)
- **Apache Kafka** (event-driven messaging)
- **Swagger / SpringDoc OpenAPI** (API documentation)
- **Docker (optional)** for containerization

---

## 🔑 Kafka Topics
- `menu-changes.v1` → Vendor Service publishes, Order Service consumes.  
- `delivery-requests.v1` → Order Service publishes, Delivery Service consumes.  

---

## 🚀 How to Run

### 1. Start Infrastructure
```bash
# Start Zookeeper
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

# Start Kafka
.\bin\windows\kafka-server-start.bat .\config\server.properties

# Create required topics
.\bin\windows\kafka-topics.bat --create --topic menu-changes.v1 --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
.\bin\windows\kafka-topics.bat --create --topic delivery-requests.v1 --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```
Access Swagger UIs

Vendor Service → http://localhost:8081/swagger-ui.html

Order Service → http://localhost:8082/swagger-ui.html

Bank Service → http://localhost:8083/swagger-ui/index.html#/

//Delivery Service → http://localhost:8083/swagger-ui.html
