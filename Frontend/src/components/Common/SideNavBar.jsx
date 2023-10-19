import React from "react";
import { getPortfolios, getProfile } from "../../api/user";
import NavBarItem from "./NavBarItem";
import User from "../User/User";

// Get User information from profile
const profile = await getProfile();
const email = profile.email;
const userName = email ? email.slice(0, email.indexOf("@")) : "";
const portfolios = await getPortfolios();

function SideNavBar() {
  return (
    <div className="sidebar d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 bg-dark text-white fw-bold">
      <a href="/" className="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none">
        <span className="fs-4 d-none d-sm-inline">
          <i className="bi-cash-coin"></i>
        </span>
        <span className="fs-5 d-none d-sm-inline ps-3">Goldman Sachs</span>
      </a>
      <nav className="nav flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
        {
          portfolios.map((portfolio, index) => {
            console.log(portfolio);
            return <NavBarItem key={index} link={`/portfolios/${portfolio.portfolioId}`} text={portfolio.name} />
          })
        }
        {
          /*
          <NavBarItem link="/" icon="bi-speedometer2" text="Dashboard" />
          <NavBarItem link="/portfolios/1" icon="bi-person-lines-fill" text="Portfolios" />
          <NavBarItem link="/stocks" icon="bi-graph-up-arrow" text="Stocks" />
          */
        }
      </nav>
      <hr />
      <User userName={userName} />
    </div>
  );
}

export default SideNavBar;