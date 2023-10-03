
-- Insert User 1
INSERT INTO users (user_id, first_name, last_name, email, password, role)
VALUES (1, 'John', 'Doe', 'johndoe@example.com', 'hashed_password_1', 'USER');

-- Insert User 2
INSERT INTO users (user_id, first_name, last_name, email, password, role)
VALUES (2, 'Jane', 'Smith', 'janesmith@example.com', 'hashed_password_2', 'USER');


-- Insert Portfolio for User 1
INSERT INTO portfolio (portfolio_id, portfolio_name, description, capital, user_id)
VALUES (1, "John's Portfolio", 'Portfolio for John Doe', 10000.00, 1);

-- Insert Portfolio for User 2
INSERT INTO portfolio (portfolio_id, portfolio_name, description, capital, user_id)
VALUES (2, "Jane's Portfolio", 'Portfolio for Jane Smith', 8000.00, 2);



-- Insert Stock Data
INSERT INTO stocks (stock_symbol, stock_name, last_updated_time_date, last_updated_price, sector, market)
VALUES
  ('AAPL', 'Apple Inc.', '2023-10-03 09:30:00', 145.67, 'Technology', 'NASDAQ'),
  ('GOOGL', 'Alphabet Inc.', '2023-10-03 09:30:00', 2750.12, 'Technology', 'NASDAQ');
  -- Add more stocks as needed
  ;


-- Insert Trade for User 1's Portfolio
INSERT INTO trades (purchase_date_time, purchase_price, purchase_quantity, portfolio_id, stock_symbol)
VALUES ('2023-10-03 09:00:00', 50.00, 100, 1, 'AAPL');

-- Insert another Trade for User 1's Portfolio
INSERT INTO trades (purchase_date_time, purchase_price, purchase_quantity, portfolio_id, stock_symbol)
VALUES ('2023-10-04 10:30:00', 55.00, 75, 1, 'GOOGL');
