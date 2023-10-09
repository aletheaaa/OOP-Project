select * from users;

-- Insert Portfolio for User 1
INSERT INTO portfolios (portfolio_name, description, capital, start_date, time_period, user_id)
VALUES ('JohnPortfolio', 'Portfolio for John Doe', 10000.00, '2021-01', 'Monthly', 1);

-- Insert Portfolio for User 2
-- INSERT INTO portfolio (portfolio_id, portfolio_name, description, capital, user_id)
-- VALUES (2, 'Jane Portfolio', 'Portfolio for Jane Smith', 8000.00, 2);





-- Insert Asset for User 1's Portfolio
INSERT INTO assets (purchased_price, portfolio_name, stock_symbol, allocation, sector, monthly_performance)
VALUES (50.00, 'JohnPortfolio', 'AAPL', 66.67, 'TECHNOLOGY', '2021-Jan-1110');


-- Insert another Asset for User 1's Portfolio
INSERT INTO assets (purchased_price,portfolio_name,stock_symbol,allocation,sector,monthly_performance)
VALUES (50.00, 'JohnPortfolio','PFA',66.67,'HEALTHCARE','2022-Feb-1110' );

-- Insert Test Portfolios for User 1
INSERT INTO portfolios (portfolio_id, portfolio_name, description, capital, user_id) 
VALUES (1, 'Tech Stocks', 'Investment in technology sector', 50000.00, 1);


INSERT INTO portfolios (portfolio_id, portfolio_name, description, capital, user_id) 
VALUES (2, 'Real Estate', 'Real estate investments', 75000.00, 54);

-- Insert Test Portfolios for User 2
INSERT INTO portfolios (portfolio_id, portfolio_name, description, capital, user_id) 
VALUES (3, 'Med Stocks', 'Investment in medical sector', 90000.00, 54);

select * from portfolios;

