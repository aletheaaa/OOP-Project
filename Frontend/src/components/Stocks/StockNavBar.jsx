import React from 'react';
import NavBarItem from '../Common/NavBarItem';
import StockSearchButton from './StockSearchButton';

function StockNavBar(stockSymbol) {
  return (
    <>
      <nav className="navbar navbar-expand-lg">
        <div className="container-fluid">
          <a className="navbar-brand fw-bold" href="/stocks">{ stockSymbol.stockSymbol }</a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <div className="navbar-nav me-auto mb-2 mb-lg-0">
              <NavBarItem link="/stocks" icon="bi bi-house-door" text="Summary" />
              <NavBarItem link="/stocks/chart" icon="bi bi-bar-chart" text="Chart" />
              <NavBarItem link="/stocks/history" icon="bi bi-clock-history" text="History" />
              <NavBarItem link="/stocks/profile" icon="bi bi-person-circle" text="Profile" />
              <NavBarItem link="/stocks/financials" icon="bi bi-cash-coin" text="Financials" />
              <NavBarItem link="/stocks/analytics" icon="bi bi-clipboard-data" text="Analytics" />
            </div>
            <div className="d-flex">
              <input type="text" className="form-control me-2" id="stockSymbol" placeholder="Search for Symbol" />
              <StockSearchButton />
            </div>
          </div>
        </div>
      </nav>
    </>
  )
};

export default StockNavBar;