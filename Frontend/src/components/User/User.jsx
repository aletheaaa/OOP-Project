import React from "react";
import { getProfile } from "../../api/user";

function User() {
  const profile = getProfile();
  
  const handleLogout = (event) => {
    event.preventDefault();
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("id");
    window.location.href = "/";
  }

  return (
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
        <span className="d-none d-sm-inline mx-1">{  }</span>
      </a>
      <ul className="dropdown-menu dropdown-menu-dark text-small shadow">
        <li>
          <a className="dropdown-item" href="#">
            New project...
          </a>
        </li>
        <li>
          <button className="dropdown-item" onClick={console.log("Settings")}>
            Settings
          </button>
        </li>
        <li>
        <button className="dropdown-item" onClick={console.log("Profile")}>
            Profile
          </button>
        </li>
        <li>
          <hr className="dropdown-divider" />
        </li>
        <li>
          <button className="dropdown-item" onClick={handleLogout}>
            Sign out  
          </button>
        </li>
      </ul>
    </div>
  );
}

export default User;