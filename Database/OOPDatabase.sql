CREATE SCHEMA IF NOT EXISTS OOP;
USE OOP;

DROP TABLE IF EXISTS assets;
DROP TABLE IF EXISTS portfolios;
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


CREATE TABLE IF NOT EXISTS portfolios (
 portfolio_name VARCHAR(64) NOT NULL PRIMARY KEY,
 description VARCHAR(255) NOT NULL,
 capital DOUBLE NOT NULL,
 start_date VARCHAR(255) NOT NULL,
 time_period VARCHAR(255) NOT NULL,
 user_id INT NOT NULL,
 CONSTRAINT portfolios_fk1 FOREIGN KEY(user_id) references users(user_id)
);

CREATE TABLE IF NOT EXISTS assets (
 purchased_price DOUBLE NOT NULL,
 portfolio_name VARCHAR(64) NOT NULL,
 stock_symbol VARCHAR(20) NOT NULL,
 allocation DOUBLE NOT NULL,
 sector VARCHAR(64) NOT NULL,
 monthly_performance VARCHAR(64) NOT NULL,
 CONSTRAINT assets_pk PRIMARY KEY(portfolio_name, stock_symbol),
 CONSTRAINT assets_fk1 FOREIGN KEY(portfolio_name) references portfolios(portfolio_name)
--  CONSTRAINT trades_fk2 FOREIGN KEY(stock_symbol) references stocks(stock_symbol)
);