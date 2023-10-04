import React, { useState, useEffect } from "react";
import NavBarItem from "./NavBarItem";
import User from "./User";

// DEV NOTES:
// - Need to fix sub-menu options for small viewports

export default function SideNavBar() {
  return (
    <div className="sidebar d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 bg-dark text-white fw-bold">
      <a href="/" className="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none">
        <span className="fs-4 d-none d-sm-inline">
          <i className="bi-cash-coin"></i>
        </span>
        <span className="fs-5 d-none d-sm-inline ps-3">Goldman Sachs</span>
      </a>
      <nav className="nav flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
        <NavBarItem link="/" icon="bi-speedometer2" text="Dashboard" />
        <NavBarItem link="/portfolios" icon="bi-person-lines-fill" text="Portfolios" />
        <NavBarItem link="/stocks" icon="bi-graph-up-arrow" text="Stocks" />
      </nav>
      <hr />
      <User />
    </div>
  );
}
