import React from "react";
import { useNavigate } from "react-router-dom";
import { logout } from "../../api/authenticate";
import ChangePasswordButton from "./ChangePasswordButton";
import CreateOrEditPortfolioButton from "../Portfolios/CreateOrEditPortfolioButton";

function User(props) {
  const navigate = useNavigate();

  const handleLogout = (event) => {
    event.preventDefault();
    logout();
  };

  const handleSettingsClick = (event) => {
    event.preventDefault();
    navigate("/settings");
  };

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
        <span className="d-none d-sm-inline mx-1">{props.userName}</span>
      </a>
      <ul className="dropdown-menu dropdown-menu-dark text-small shadow">
        <li>
          <ChangePasswordButton className={"dropdown-item"} />
        </li>
        <li>
          <button className="dropdown-item" onClick={handleSettingsClick}>
            Settings
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
