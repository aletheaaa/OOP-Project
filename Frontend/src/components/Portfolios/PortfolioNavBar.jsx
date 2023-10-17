import React from "react";
import NavBarItem from "../Common/NavBarItem";

function PortfolioNavBar(props) {
  return (
    <>
      <nav
        className="navbar navbar-expand-lg rounded"
        style={{ backgroundColor: "lightgrey" }}
      >
        <div className="container-fluid">
          <a className="navbar-brand fw-bold" href="/portfolios/1">
            {props.name}
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <div className="navbar-nav me-auto mb-2 mb-lg-0">
              <NavBarItem
                link="/portfolios/1"
                icon="bi bi-house-door"
                text="Summary"
              />
              <NavBarItem
                link="/portfolios/1/chart"
                icon="bi bi-bar-chart"
                text="Chart"
              />
              <NavBarItem
                link="/portfolios/1/history"
                icon="bi bi-clock-history"
                text="History"
              />
              <NavBarItem
                link="/portfolios/1/profile"
                icon="bi bi-person-circle"
                text="Profile"
              />
              <NavBarItem
                link="/portfolios/1/financials"
                icon="bi bi-cash-coin"
                text="Financials"
              />
              <NavBarItem
                link="/portfolios/1/analytics"
                icon="bi bi-clipboard-data"
                text="Analytics"
              />
            </div>
          </div>
        </div>
      </nav>
    </>
  );
}

export default PortfolioNavBar;
