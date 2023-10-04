

-- Insert Portfolio for User 1
INSERT INTO portfolios (portfolio_id, portfolio_name, description, capital, user_id)
VALUES (1, 'John Portfolio', 'Portfolio for John Doe', 10000.00, 1);

-- Insert Portfolio for User 2
-- INSERT INTO portfolio (portfolio_id, portfolio_name, description, capital, user_id)
-- VALUES (2, 'Jane Portfolio', 'Portfolio for Jane Smith', 8000.00, 2);



-- Insert Stock Data
INSERT INTO stocks (stock_symbol, last_updated_time, last_updated_price)
VALUES
  ('AAPL', '2023-10-03', 145.67),
  ('GOOGL', '2023-10-03', 2750.12);
  -- Add more stocks as needed
  ;


-- Insert Trade for User 1's Portfolio
INSERT INTO trades (purchase_date_time, purchase_price, purchase_quantity, portfolio_id, stock_symbol)
VALUES ('2023-10-03 09:00:00', 50.00, 100, 1, 'AAPL');

-- Insert another Trade for User 1's Portfolio
INSERT INTO trades (purchase_date_time, purchase_price, purchase_quantity, portfolio_id, stock_symbol)
VALUES ('2023-10-04 10:30:00', 55.00, 75, 1, 'GOOGL');

-- Insert Test Portfolios for User 1
INSERT INTO portfolios (portfolio_id, portfolio_name, description, capital, user_id) 
VALUES (1, 'Tech Stocks', 'Investment in technology sector', 50000.00, 1);


INSERT INTO portfolios (portfolio_id, portfolio_name, description, capital, user_id) 
VALUES (2, 'Real Estate', 'Real estate investments', 75000.00, 1);

-- Insert Test Portfolios for User 2
INSERT INTO portfolios (portfolio_id, portfolio_name, description, capital, user_id) 
VALUES (3, 'Med Stocks', 'Investment in medical sector', 90000.00, 2);

