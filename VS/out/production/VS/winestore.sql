DROP DATABASE IF EXISTS winestore;
CREATE DATABASE IF NOT EXISTS winestore;
SHOW DATABASES;
USE winestore;

DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer(
                         CustomerID VARCHAR (6),
                         CustomerTitle VARCHAR (5),
                         CustomerName VARCHAR (30),
                         CustomerAddress VARCHAR (30),
                         City VARCHAR (20),
                         Province VARCHAR (20),
                         PostCode VARCHAR (9),
                         Date DATE,
                         Time TIME,
                         CONSTRAINT PRIMARY KEY (CustomerID)
);

SHOW TABLES ;
DESCRIBE Customer;
#----------------------------------------------------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    OrderID VARCHAR (6),
    CustomerID VARCHAR (6),
    OrderDate DATE ,
    OrderTime TIME,
    CONSTRAINT PRIMARY KEY (OrderID),
    CONSTRAINT FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE CASCADE ON UPDATE CASCADE
    );

SHOW TABLES ;
DESCRIBE `Order`;

#-------------------------------------------------------------

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    ItemCode VARCHAR (6),
    Description VARCHAR (50),
    PackSize VARCHAR (20),
    UnitPrice DOUBLE DEFAULT 0.00,
    QtyOnHand INT DEFAULT 0,
    Date DATE,
    CONSTRAINT PRIMARY KEY (ItemCode)
    );
INSERT INTO Item VALUES('I-001','Mango','small/medium',60.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-002','Orange','small/medium',80.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-003','pearce','small/medium',100.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-004','Strawberry','small/medium',20.0,1000,'2022-03-19');
INSERT INTO Item VALUES('I-005','Berry','small/medium',120.0,1000,'2022-03-19');

SHOW TABLES ;
DESCRIBE Item;

#-----------------------------------------------
DROP TABLE IF EXISTS `Order Details`;
CREATE TABLE IF NOT EXISTS `Order Details`(
    OrderID VARCHAR(6),
    ItemCode VARCHAR(6),
    Orderqty INT,
    Discount DOUBLE,
    Price DOUBLE,
    CONSTRAINT PRIMARY KEY (OrderID,ItemCode),
    CONSTRAINT FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (ItemCode) REFERENCES Item(ItemCode) ON DELETE CASCADE ON UPDATE CASCADE

    );

SHOW TABLES ;
DESCRIBE `Order Details`;

INSERT INTO Customer VALUES('C001','mr','Danapala','Panadura','Panadura','SP',8000,'2021-09-10','21:12:12');
INSERT INTO Customer VALUES('C002','mr','Gunapala','Matara','Matara','WP',4000,'2021-09-10','21:12:12');
USE winestore;

USE winestore;
DROP TABLE IF EXISTS Cashier;
CREATE TABLE Cashier
(
    CashierId          VARCHAR(6),
    CashierName        VARCHAR(30),
    NIC                VARCHAR(15),
    Contact			   VARCHAR(15),
    Salary             DOUBLE,
    CPassword          VARCHAR(15),
    CONSTRAINT PRIMARY KEY (CashierId)
);

SHOW TABLES;
DESCRIBE Cashier;

USE winestore;
CREATE TABLE IF NOT EXISTS Login(
                                    UserName VARCHAR (10),
                                    Password VARCHAR(10),
                                    CONSTRAINT PRIMARY KEY(UserName)
);
SHOW TABLES;
DESCRIBE Login;
INSERT INTO Login (UserName,PassWord)VALUES ('Admin',1234);