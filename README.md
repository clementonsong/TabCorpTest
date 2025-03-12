# Tabcorp - TransactionProcessing TEST Project

## Overview
The `Tabcorp - TransactionProcessing TEST` is a microservices-based application built using Java Spring Boot, Kafka, JWT and MySQL. It demonstrates a transaction processing system with authentication (for now in limited APIs for interview purpose) and role-based access control.
As this is a MICROSERVICE environment, the product, customer and orders table are in separate DBs and has no relation.

EVERY record created and inserted in the tables works via API and EVENT MESSAGE BROKER approach (FOLLOWED AN EVENT DRIVEN MICROSERVICES APPROACH):

## HIGH LEVEL FLOW
(a) A Transaction Rest request (exactly as is in the problem statement) is received from a REST client.

(b) An event is emitted to a KAFKA TOPIC (transaction-event). For scaling and high availability KAFKA is used to register transactional events. 
	PARTITIONS and CONSUMER GROUPS are already in place. These can be further increased for scaling Transaction Microservice horizontally.
 
(c) Once the transaction arrives, a VALIDATOR component (per the requirements in problem statement) validates whether total txn is not more than 5000, Date is not a past date and checks product is active.

(d) Once successfully validated, the data is emitted to KAFKA topic using a DTO. [If any validation error, the validator sends a menaininful error back to the caller]

(e) This data is consumed from the TOPIC by product and customer consumer groups and persisted for future reports.

(f) After this step, the transaction is saved in the orders table with a Tabcorp prefixed order ID like TC1, TC2... so on and so forth.

(g) Finally, the successful transactions are persisted in orders table along with some product and customer data pulled from the incoming transaction request.

(h) The transaction's total value is stored against each Product_code and Customer_code as and when the messages are consumed..

(i) Now, all the data recorded can be pulled as reports using the below mentioned APIs.

(j) For this interview assessment due to limited time availability AUTHENTICATION is provided only for REPORT API on Transaction Microservice.

(k) TEST CLASSES are added in the Transacion Microservice which is the main Microservice in this whole project covering all concerns like Controllers, Service, Repository, Config, DTO etc.


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


## INSTRUCTIONS FOR RUNNING END TO END FLOW OF THIS PROJECT INVOLVING ALL THE MICROSERVICES AND MODULES:
(a) Download all the code artifacts into an IDE of your choice and should look like the below

## FINAL PROJECT STRUCTURE / PROJECT EXPLORER
TabcorpProject/
├── common-dto/
├── AuthMicroservice/
├── CustomerMicroservice/
├── ProductMicroservice/
└── TransactionMicroservice/

(b) Do a mvn clean install in each of the 5 projects starting with common-dto first and in every module after that. (only common-dto is referred by other modules but the rest of them are all independent microservices)

(c) Once successfully built, as you might guess the common-dto is just a common project and not a microserivce. So run the other 4 microservices.

(d) Also, create the database and tables according to the below instructions:
PRE ASSUMPTIONS: There are 4 tables that need to be created in separate databases as these follow the Microservice Architecture - So each of the 4 can Microservices can work individually for certain functionalities.  
create these Databases in MYSQL: customer_db, product_db, order_db, auth_db 
Create these Tables: customers, products, orders, users

## For the tables customers and products - PREPOPULATE with PRODUCT AND CUSTOMER DATA as given in the Challenge and use the scripts enclosed in the root directory for easy DDL and DML scripts (orders and users table get populated as the application runs, so no data insertion required)

** SCHEMA: SQL scripts (DDLDML.sql) are placed for easy DB setup in the root directory

(e) As an important step also download Kafka related binaries and start the zookeeper and kafka server as TransactionMicroservice emits transaction details as events for Customer and Product Microservices.

# Before running the below enclosed transaction request from a REST client, please make sure zookeeper and kafka server are started (start Zookeeper first)

## NOTE: THE GATEWAY OR STARRING POINT to this application is by sending the below REST REQUEST from a UI layer or from any REST caller (TRANSACTION DATA)

POST /transactions/create  

Content-Type: application/json

[THIS API IS NOT INCLUDED FOR JWT AUTHENTICATION here. BUT THE AUTHENTICATION MICROSERVICE CAN BE EXTENDED easily just by including a single line change with the URI pattern in Spring Security Config - requestMatchers()]

{
    "customerCode": "10005",
    "productCode": "PRODUCT_003",
    "quantity": 3,
    "transactionTime": "2025-03-15T10:30:00"
}


## ADDITIONAL SETUP & PROPERITES
Tweak the application.properties of these services to run in ports of your choice ()

1. **AuthMicroservice** (Port: `8083`)  
   Handles authentication and authorization using JWT tokens.

2. **TransactionMicroservice** (Port: `8080`)  
   Accepts transaction data and publishes events to Kafka.

3. **CustomerMicroservice** (Port: `8081`)  
   Consumes Kafka events to update customer-related data and provides reporting APIs.

4. **ProductMicroservice** (Port: `8082`)  
   Consumes Kafka events to update product-related data and provides reporting APIs.

5. **Common DTO Module**  
   Contains shared Data Transfer Objects (DTOs) for communication between services


# JWT KEY REQUIRED BY AUTHENTICATION MICROSERVICE AND TRANSACTION MICROSERVICE (for now only in Transaction Microservice's REPORT API. But can be easily extended to all Microservices making a config entry in that particular microservice that needs authentication)
** JWT Secret Key and Expiration (already present in the appplicaiton.properties of AuthMicroservice and Transaction Microservice)
jwt.secret=VGhlU2VjcmV0S2V5TWFyMjAyNUZvckNsZW1lbnRUaG9tYXNDb2RpbmdQcm9qZWN0cw==
jwt.expirationMs=3600000

# THIS above key is a Base64-encoded version of 'TheSecretKeyMar2025ForClementThomasCodingProjects'


## Under each microservice's resources folder, update the application.properties with your MySQL credentials. (already has development settings I used)


## API SECURITY is for now introduced ONLY in TransactionMicroservice's REPORT API which uses the JWT authentication provided from AuthMicroserice.

How AUTH Microservice works:
============================
There are 2 APIs in AuthMicroservice
1) POST /auth/register - Register a new user account in TABCORP servers
Content-Type: application/json

SAMPLE USER REGISTRATION REQUEST BODY:

{
  "username": "tabcorp1",
  "password": "newtabcorppassword",
  "role": "USER"
}

RESPONSE: 
User registered successfully

2) POST /auth/login - Authenticates the user account created earlier in TABCORP database and CREATES a JWT token for use in TRANSACTION MICROSERTICE (REPORTS ONLY)
Content-Type: application/json

SAMPLE REGISTERD USER LOGIN REQUEST BODY: (or access token generation)
{
  "username": "tabcorp1",
  "password": "newtabcorppassword"
}
RESPONSE: 
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwic3ViIjoidGFiY29ycDEiLCJpYXQiOjE3NDE1ODc0MTAsImV4cCI6MTc0MTU5MTAxMH0.wwPcLYClQOczD_oztByGcTbN6myfbtn-9EHvpGyt4Vs"
}



REPORTING APIs:
===============

TransactionMicroservice
=======================
POST /api/transactions/reports/count/{COUNTRY} - Get transaction count along with the order details of the country(Secured)
Content-Type: application/json
[An extra Header required here for this service as it need authentication]
- Authorization: Bearer <your_jwt_token_generated_from_auth>

Troubleshooting (if needed)
403 Forbidden: Ensure your JWT token is valid and you have created the user with the correct role(just USER here for this test project).
Database Connection Errors: Verify MySQL credentials and database configuration in application.properties are matching
ACCESS TOKEN ERRORS: Have a long SECRET added (under jwt.secret) and expiration duration of the SECRET (under jwt.expirationMs) added in TransationMicroservice's properties.

CustomerMicroservice
====================
GET /api/customers/reports - Get customer reports

ProductMicroservice
====================
GET /api/products/reports - Get product reports


# APART FROM THE REPORT APIs THERE ARE A FEW OTHER APIs FOR INTERPROCESS COMMUNICATION AND FOR ORCHESTRATION IN THIS PROJECT:

(a) Product Details API - http://localhost:8082/products/{product_code}
(b) Customer Details API - http://localhost:8081/customers/{customer_code}

BUT THE CALLS TO THESE APIs ARE MADE ONLY ONCE DURING A SINGLE TRANSACTION FLOW. TO AVOID REPETITIOUS CALLS, THE TRANSACITON VALIDATOR COMPONENT PERSISTS DATA INTO OBJECTS USING CALLBACKS.



##License
This project is for demonstration/assessment purposes and is not intended for production use.


