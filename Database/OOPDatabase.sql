CREATE SCHEMA IF NOT EXISTS OOP;
USE OOP;

DROP TABLE IF EXISTS trades;
DROP TABLE IF EXISTS portfolios;
DROP TABLE IF EXISTS stocks;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
 user_id INT NOT NULL PRIMARY KEY,
 first_name VARCHAR(64) NOT NULL,
 last_name VARCHAR(64) NOT NULL,
 email VARCHAR(64) NOT NULL,
 -- HashedPassword length depends on algorithm used,
 -- CHAR(60) data type assumes use of Bcrypt
 password CHAR(60) NOT NULL,
 role enum('ADMIN','USER') NOT NULL
);

CREATE TABLE IF NOT EXISTS stocks (
 stock_symbol VARCHAR(20) NOT NULL PRIMARY KEY,
 stock_name VARCHAR(64) NOT NULL,
 last_updated_time_date DATETIME NOT NULL,
 last_updated_price DOUBLE NOT NULL,
 sector VARCHAR(64) NOT NULL,
 market VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS portfolios (
 portfolio_id INT NOT NULL PRIMARY KEY,
 portfolio_name VARCHAR(64) NOT NULL,
 description VARCHAR(255) NOT NULL,
 capital DOUBLE NOT NULL,
 user_id INT NOT NULL,
 CONSTRAINT portfolios_fk1 FOREIGN KEY(user_id) references users(user_id)
);

CREATE TABLE IF NOT EXISTS trades (
 purchase_date_time DATETIME NOT NULL,
 purchase_price DOUBLE NOT NULL,
 purchase_quantity INT NOT NULL,
 portfolio_id INT NOT NULL,
 stock_symbol VARCHAR(20) NOT NULL,
 CONSTRAINT trades_pk PRIMARY KEY(purchase_date_time, portfolio_id, stock_symbol),
 CONSTRAINT trades_fk1 FOREIGN KEY(portfolio_id) references portfolios(portfolio_id)
);

