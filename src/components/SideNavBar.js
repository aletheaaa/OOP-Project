import React, { useState, useEffect } from "react";
import NavBarItem from "./NavBarItem";

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
        <NavBarItem link="#" icon="bi-speedometer2" text="Dashboard" />
        <NavBarItem link="#orders" icon="bi-table" text="Orders" bsToggle="collapse" />
        <ul className="collapse flex-column" id="orders" data-bs-parent="#menu">
          <li>
            <a href="#" className="nav-link px-0">
              {" "}
              <span className="d-none d-sm-inline">Item</span> 1
            </a>
          </li>
          <li>
            <a href="#" className="nav-link px-0">
              {" "}
              <span className="d-none d-sm-inline">Item</span> 2
            </a>
          </li>
        </ul>
        <NavBarItem link="#products" icon="bi-grid" text="Products" bsToggle="collapse" />
        <div className="collapse list-group" id="products" data-bs-parent="#menu">
          <a
            href="#"
            className="list-group-item list-group-item-action active"
            aria-current="true"
          >
            Product 1
          </a>
          <a href="#" className="list-group-item list-group-item-action">
            Product 2
          </a>
          <a href="#" className="list-group-item list-group-item-action">
            Product 3
          </a>
          <a href="#" className="list-group-item list-group-item-action">
            Product 4
          </a>
        </div>
        <NavBarItem link="#customers" icon="bi-people" text="Customers" />
      </nav>
      <hr />
      <div className="dropdown pb-4">
        <a
          href="#"
          className="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
          id="dropdownUser1"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <img
            src="https://github.com/mdo.png"
            alt="hugenerd"
            width="30"
            height="30"
            className="rounded-circle"
          />
          <span className="d-none d-sm-inline mx-1">User</span>
        </a>
        <ul className="dropdown-menu dropdown-menu-dark text-small shadow">
          <li>
            <a className="dropdown-item" href="#">
              New project...
            </a>
          </li>
          <li>
            <a className="dropdown-item" href="#">
              Settings
            </a>
          </li>
          <li>
            <a className="dropdown-item" href="#">
              Profile
            </a>
          </li>
          <li>
            <hr className="dropdown-divider" />
          </li>
          <li>
            <a className="dropdown-item" href="#">
              Sign out
            </a>
          </li>
        </ul>
      </div>
    </div>
  );
}
