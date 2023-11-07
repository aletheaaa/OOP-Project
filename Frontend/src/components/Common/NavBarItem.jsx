import React from "react";
import { Link } from "react-router-dom";

function NavBarItem({ link, icon, text }) {
  return (
    <Link to={link} className="nav-link active ps-0 align-middle">
      <i className={"fs-4 " + icon}></i>{" "}
      <span className="ms-1 d-none d-sm-inline">{text}</span>
    </Link>
  );
}

export default NavBarItem;
