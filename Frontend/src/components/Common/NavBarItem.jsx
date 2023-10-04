import React, { useState, useEffect } from "react";

function NavBarItem({link, icon, text, bsToggle}) {
    return (
        <a href={link} className="nav-link active ps-0 align-middle" data-bs-toggle={bsToggle} >
            <i className={"fs-4 " + icon}></i>{" "}
            <span className="ms-1 d-none d-sm-inline">{text}</span>
        </a>
    );
}

export default NavBarItem;