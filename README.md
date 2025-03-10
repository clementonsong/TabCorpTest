# Tabcorp - TransactionProcessing TEST Project

## Overview
The `Tabcorp - TransactionProcessing TEST` is a microservices-based application built using Java Spring Boot, Kafka, JWT and MySQL. It demonstrates a transaction processing system with authentication and role-based access control.

EVERY record created here works via API and EVENT MESSAGE BROKER approach (FOLLOWED AN EVENT DRIVEN APPROACH):

(a) A Transaction Rest request is received from a REST client.
(b) An event is emitted to a KAFKA TOPIC (transaction-event). For scaling and high availability KAFKA is used to register transactional events. 
	PARTITIONS and CONSUMER GROUPS are used for scaling horizontally in future
(c)	Once the transaction arrives, a VALIDATOR component validates whether total txn is not more than 5000, Date is not a past date and product must be active
(d) Once successfully validated, the data is emitted to KAFKA topic using a DTO
(e) This data is consumed from the partition by product and customer consumer groups and persisted for future reports
(f) After this step the transaction is saved in the orders table with a Tabcorp order ID like TC1, TC2... so on and so forth
(g) Now, the recorded transactions are persisted in orders table along with some order and customer data pulled from the incoming transaction request
(h) The transaction's total value is stored against each Product_code and Customer_code as and when the messages are consumed.
(i) Now, all the data recorded can be pulled as reports using the below mentioned APIs
(j) For this interview assessment due to limited time availability AUTHENTICATION is provided only for REPORT API on transaction

NOTE: THE GATEWAY TO THIS APPLICATION IS BY SENDING THIS BELO REST REQUEST from a UI layer or from any REST caller (TRANSACTION DATA)

POST /transactions/create  (FOR THIS ASSESSMENT IT IS NOT AUTHENTICATE. BUT CAN EXTEND AUTHENTICATION BY JUST INCLUDING THE URL PATTERN)
Content-Type: application/json

{
    "customerCode": "10005",
    "productCode": "PRODUCT_003",
    "quantity": 3,
    "transactionTime": "2025-03-10T10:30:00"
}


## Microservices
The project is structured into four microservices and a common DTO module:

Tweak the application.properties of these services to run in ports of your choice

1. **AuthMicroservice** (Port: `8083`)  
   Handles authentication and authorization using JWT tokens.

2. **TransactionMicroservice** (Port: `8080`)  
   Accepts transaction data and publishes events to Kafka.

3. **CustomerMicroservice** (Port: `8081`)  
   Consumes Kafka events to update customer-related data and provides reporting APIs.

4. **ProductMicroservice** (Port: `8082`)  
   Consumes Kafka events to update product-related data and provides reporting APIs.

5. **Common DTO Module**  
   Contains shared Data Transfer Objects (DTOs) for communication between services.

## Technologies Used
- **Backend:** Java 17, Spring Boot 3.4.3, Spring Security, Spring JDBC, Spring Kafka
- **Database:** MySQL
- **Messaging:** Apache Kafka
- **Security:** JWT Authentication
- **Tools:** Eclipse IDE, Postman, Notepad++, MySQL Workbench

## Prerequisites
Ensure the following software is installed:
- Java 17
- MySQL Server
- Apache Kafka & Zookeeper
- Eclipse IDE
- Postman (for API testing)

## HIGH LEVEL SETUP (WORKFLOW):

THE APPLICATION.PROPERTIES files is kept as is I used during development

# Before running the transaction request from a REST client, please make sure zookeeper and kafka server are started first(Zookeeper First)


PRE ASSUMPTIONS: There are 4 tables that need to be created in separate databases 
create these Databases in MYSQL: customer_db, product_db, order_db, auth_db 
Create these Tables: customers, products, orders, users

For the tables customers and products - PREPOPULATE with PRODUCT AND CUSTOMER DATA

** A DDL DML file (DDLDMLTabCorp.sql) is placed for easy setup in the root project 

# JWT Secret Key and Expiration
jwt.secret=VGhlU2VjcmV0S2V5TWFyMjAyNUZvckNsZW1lbnRUaG9tYXNDb2RpbmdQcm9qZWN0cw==
jwt.expirationMs=3600000

# THIS key is a Base64-encoded version of TheSecretKeyMar2025ForClementThomasCodingProjects


## Under each microservice's resources folder update the application.properties in each microservice with your MySQL credentials.


## API SECURITY is for now introduced ONLY in TransactionMicroservice which uses the JWT authentication provided from AuthMicroserice.

How AUTH Microservice works:
============================
There are 2 APIs in AuthMicroservice
1) POST /auth/register - Register a new user account in TABCORP servers

SAMPLE REQUEST:

{
  "username": "tabcorp1",
  "password": "newtabcorppassword",
  "role": "USER"
}

RESPONSE: 
User registered successfully

2) POST /auth/login - Authenticates the user account created earlier in TABCORP database and CREATES a JWT token for use in TRANSACTION MICROSERTICE (REPORTS ONLY)

{
  "username": "tabcorp1",
  "password": "newtabcorppassword"
}
RESPONSE: 
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwic3ViIjoidGFiY29ycDEiLCJpYXQiOjE3NDE1ODc0MTAsImV4cCI6MTc0MTU5MTAxMH0.wwPcLYClQOczD_oztByGcTbN6myfbtn-9EHvpGyt4Vs"
}


APIS IN USAGE:

TransactionMicroservice
=======================
POST /api/transactions/reports/count/{COUNTRY} - Get transaction count along with the order details of the country(Secured)
Extra Header required here
- Authorization: Bearer <your_jwt_token_generated_from_auth>

Troubleshooting
403 Forbidden: Ensure your JWT token is valid and you have created the user with the correct role(just USER here for this test project).
Database Connection Errors: Verify MySQL credentials and database configuration in application.properties.

CustomerMicroservice
====================
GET /api/customers/reports - Get customer reports

ProductMicroservice
====================
GET /api/products/reports - Get product reports

TabcorpProject/
├── AuthMicroservice/
├── TransactionMicroservice/
├── CustomerMicroservice/
├── ProductMicroservice/
└── common-dto/


APART FROM THE REPORT APIs PROVIDED ABOVE THERE ARE FEW OTHER APIs PLYING BETWEEN FOR INTERPROCESS COMMUNICATION AND FOR ORCHESTRATION 

(a) Product Details API - http://localhost:8082/products/{product_code}
(b) Customer Details API - http://localhost:8081/customers/{customer_code}

BUT THE CALLS TO THESE APIs ARE MINIMALISED TO AVOID REPETITIOUS CALLS IN ONE TRANSACTION. FOR THIS PUPOSE THE VALIDATOR COMPONENT PERSISTS DATA USING CALLBACKS



License
This project is for demonstration/assessment purposes and is not intended for production use.


