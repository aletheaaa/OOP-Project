import React from "react";
import { Link } from "react-router-dom";

function NavBarItem({ link, icon, text }) {
  return (
    <Link
      to={link}
      className="nav-link active ps-0 align-middle"
      style={{ color: "white" }}
    >
      <i className={"fs-4 " + icon}></i>{" "}
      <span
        className="text-white ps-1"
        style={{ position: "relative", bottom: "5px" }}
      >
        {text}
      </span>
    </Link>
  );
}

export default NavBarItem;
