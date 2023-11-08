import React, { useEffect, useState } from "react";
import { getPortfolios } from "../../api/user";
import { getEmail } from "../../api/authenticate";
import NavBarItem from "./NavBarItem";
import User from "../User/User";

const iconList = [
  "bi-cash-coin",
  "bi-speedometer2",
  "bi-person-lines-fill",
  "bi-graph-up-arrow",
  "bi-airplane",
  "bi-briefcase",
  "bi-award",
  "bi-backpack",
  "bi-basket",
];

function SideNavBar({ setPortfoliosFromParent }) {
  const [portfolios, setPortfolios] = useState([]);
  const [userName, setUserName] = useState("");
  useEffect(() => {
    const getUserInfo = async () => {
      // Get User information from profile
      const email = getEmail();
      console.log(email);
      let slicedEmail = email ? email.slice(0, email.indexOf("@")) : "";
      const portfolios = await getPortfolios();
      if (portfolios.status != 200) {
        console.log("error getting portfolios: ", portfolios.data);
        return;
      }
      setUserName(slicedEmail);
      if (portfolios === null) {
        return;
      }
      setPortfolios(portfolios.data.userPortfolios);
      setPortfoliosFromParent(portfolios.data.userPortfolios);
    };
    getUserInfo();
  }, []);

  return (
    <div
      id="sidebar"
      className="sidebar d-flex flex-column align-items-center align-items-sm-start px-3 bg-dark text-white fw-bold"
      style={{ paddingTop: "100px" }}
    >
      <nav
        className="nav flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start"
        id="menu"
      >
        {portfolios && <span>Portfolios</span>}
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
