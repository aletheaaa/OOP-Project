import React, { useEffect, useState } from "react";
import { getPortfolios, getProfile } from "../../api/user";
import NavBarItem from "./NavBarItem";
import User from "../User/User";

const iconList = [
  "bi-cash-coin",
  "bi-speedometer2",
  "bi-person-lines-fill",
  "bi-graph-up-arrow",
  "bi-graph-down-arrow",
];

function SideNavBar() {
  const [portfolios, setPortfolios] = useState([]);
  const [userName, setUserName] = useState("");
  useEffect(() => {
    const getUserInfo = async () => {
      // Get User information from profile
      const profile = await getProfile();
      const email = profile.email;
      const userName = email ? email.slice(0, email.indexOf("@")) : "";
      const portfolios = await getPortfolios();
      if (portfolios.status != 200) {
        console.log("error getting portfolios: ", portfolios.data);
        return;
      }
      setUserName(userName);
      if (portfolios === null) {
        return;
      }
      setPortfolios(portfolios.data.userPortfolios);
    };
    getUserInfo();
  }, []);

  return (
    <div
      id="sidebar"
      className="sidebar d-flex flex-column align-items-center align-items-sm-start px-3 pt-4 bg-dark text-white fw-bold"
    >
      <a
        href="/"
        className="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none"
      >
        <span
          className="fs-5 d-none d-sm-inline"
          style={{ letterSpacing: ".2rem" }}
        >
          Goldman Sachs
        </span>
      </a>
      <nav
        className="nav flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start"
        id="menu"
      >
        {portfolios &&
          portfolios.length != 0 &&
          portfolios.map((portfolio, index) => {
            return (
              <NavBarItem
                key={index}
                link={`/portfolios/${portfolio.portfolioId}`}
                text={portfolio.portfolioName}
                icon={iconList[index % iconList.length]}
              />
            );
          })}
        {portfolios == null && <div>No portfolios</div>}
      </nav>
      <hr />
      <User userName={userName} />
    </div>
  );
}

export default SideNavBar;
