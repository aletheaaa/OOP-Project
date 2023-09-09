import React, { useState, useEffect } from "react";

export default function SideNavBar() {
  return (
    <div
      class="sidebar d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 bg-dark min-vh-100 text-white fw-bold"
      style={{ width: "200px" }}
    >
      <a
        href="/"
        class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none"
      >
        <span class="fs-4 d-none d-sm-inline">
          <i class="bi-cash-coin"></i>
        </span>
        <span class="fs-5 d-none d-sm-inline ps-3">Goldman Sachs</span>
      </a>
      <nav
        class="nav flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start"
        id="menu"
      >
        <a href="#" class="nav-link active px-0 align-middle">
          <i class="fs-4 bi-speedometer2"></i>{" "}
          <span class="ms-1 d-none d-sm-inline">Dashboard</span>
        </a>
        <a
          href="#submenu1"
          data-bs-toggle="collapse"
          class="nav-link px-0 align-middle"
        >
          <i class="fs-4 bi-bootstrap"></i>{" "}
          <span class="ms-1 d-none d-sm-inline">Bootstrap</span>
        </a>
        <ul class="collapse flex-column" id="submenu1" data-bs-parent="#menu">
          <li>
            <a href="#" class="nav-link px-0">
              {" "}
              <span class="d-none d-sm-inline">Item</span> 1
            </a>
          </li>
          <li>
            <a href="#" class="nav-link px-0">
              {" "}
              <span class="d-none d-sm-inline">Item</span> 2
            </a>
          </li>
        </ul>
        <a
          href="#submenu2"
          data-bs-toggle="collapse"
          class="nav-link px-0 align-middle"
        >
          <i class="fs-4 bi-grid"></i>{" "}
          <span class="ms-1 d-none d-sm-inline">Products</span>
        </a>
        <div class="collapse list-group" id="submenu2" data-bs-parent="#menu">
          <a
            href="#"
            class="list-group-item list-group-item-action active"
            aria-current="true"
          >
            Product 1
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            Product 2
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            Product 3
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            Product 4
          </a>
        </div>
        <a href="#" class="nav-link px-0 align-middle">
          <i class="fs-4 bi-people"></i>{" "}
          <span class="ms-1 d-none d-sm-inline">Customers</span>
        </a>
      </nav>
      <hr />
      <div class="dropdown pb-4">
        <a
          href="#"
          class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
          id="dropdownUser1"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <img
            src="https://github.com/mdo.png"
            alt="hugenerd"
            width="30"
            height="30"
            class="rounded-circle"
          />
          <span class="d-none d-sm-inline mx-1">User</span>
        </a>
        <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
          <li>
            <a class="dropdown-item" href="#">
              New project...
            </a>
          </li>
          <li>
            <a class="dropdown-item" href="#">
              Settings
            </a>
          </li>
          <li>
            <a class="dropdown-item" href="#">
              Profile
            </a>
          </li>
          <li>
            <hr class="dropdown-divider" />
          </li>
          <li>
            <a class="dropdown-item" href="#">
              Sign out
            </a>
          </li>
        </ul>
      </div>
    </div>
  );
}
