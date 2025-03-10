-- PLEASE RUN THE SCRIPTS STEP BY STEP AND NOT AT ONE GO AS DDLS-DMLS coexist

--**************************** PRODUCT MICROSERVICE ******************************************************

CREATE DATABASE IF NOT EXISTS product_db;
USE product_db;


CREATE TABLE IF NOT EXISTS products (
    Product_Code VARCHAR(50) PRIMARY KEY,
    Cost DECIMAL(10,2) NOT NULL,
    Status VARCHAR(10) NOT NULL
);


ALTER TABLE products ADD COLUMN Transaction_Value DECIMAL(10,2) DEFAULT 0.00;

-- RUN THE DML BELOW SEPARATELY 
INSERT INTO products (Product_Code, Cost, Status) VALUES 
('PRODUCT_001', 50.00, 'Active'),
('PRODUCT_002', 100.00, 'Inactive'),  
('PRODUCT_003', 200.00, 'Active'),
('PRODUCT_004', 10.00, 'Inactive'), 
('PRODUCT_005', 500.00, 'Active');

--***************************** CUSTOMER MICROSERVICE ******************************************************

CREATE DATABASE IF NOT EXISTS customer_db;
USE customer_db;

CREATE TABLE IF NOT EXISTS customers (
    Customer_Code VARCHAR(20) PRIMARY KEY,
    First_Name VARCHAR(50) NOT NULL,
    Last_Name VARCHAR(50) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Location VARCHAR(100) NOT NULL
);

ALTER TABLE customers ADD COLUMN Transaction_Value DECIMAL(10,2) DEFAULT 0.00;



-- RUN THE DML BELOW SEPARATELY 
INSERT INTO customers (Customer_Code, First_Name, Last_Name, Email, Location)
VALUES
(10001, 'Tony', 'Stark', 'tony.stark@gmail.com', 'Australia'),
(10002, 'Bruce', 'Banner', 'bruce.banner@gmail.com', 'US'),
(10003, 'Steve', 'Rogers', 'steve.rogers@hotmail.com', 'Australia'),
(10004, 'Wanda', 'Maximoff', 'wanda.maximoff@gmail.com', 'US'),
(10005, 'Natasha', 'Romanoff', 'natasha.romanoff@gmail.com', 'Canada');



--***************************** TRANSACTION MICROSERVICE ******************************************************

CREATE DATABASE IF NOT EXISTS order_db;
USE order_db;

CREATE TABLE IF NOT EXISTS orders (
    Order_ID VARCHAR(10) PRIMARY KEY,
    Customer_Code VARCHAR(20) NOT NULL,
    Product_Code VARCHAR(20) NOT NULL,
    Quantity INT NOT NULL,
    Transaction_Cost DECIMAL(10,2) NOT NULL,
    Transaction_Time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

use order_db;
ALTER TABLE orders ADD COLUMN Cust_Location VARCHAR(20);
ALTER TABLE orders CHANGE Cust_Location Customer_Location varchar(20);

--***************************** AUTH MICROSERVICE ******************************************************

CREATE DATABASE IF NOT EXISTS auth_db;
USE auth_db;

CREATE TABLE users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
