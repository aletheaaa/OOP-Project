import React, { useEffect } from "react";
import { Outlet } from "react-router-dom";
import SideNavBar from "../components/Common/SideNavBar";
import CreateOrEditPortfolioModal from "../components/Portfolios/CreateOrEditPortfolioModal";
import ChangePasswordModal from "../components/User/ChangePasswordModal";
import goldmanlogo from "../assets/goldmanlogo.png";

export default function Root() {
  const [width, setWidth] = React.useState(0);
  const [portfolios, setPortfolios] = React.useState([]);
  const [viewportWidth, setViewportWidth] = React.useState(0);
  useEffect(() => {
    const sidebarElement = document.getElementById("sidebar");
    if (sidebarElement) {
      const sidebarWidth = sidebarElement.offsetWidth;
      setWidth(sidebarWidth);
      console.log("sidebarWidth", sidebarWidth);
    }
    // getting viewport width
    const width = Math.max(
      document.documentElement.clientWidth,
      window.innerWidth || 0
    );
    setViewportWidth(width);
  }, [portfolios]);

  return (
    <div className="App">
      <div className="App-header py-3" id="topNavBar">
        <div style={{ width: "1000px" }}>
          <img
            src={goldmanlogo}
            style={{ height: "40px", width: "70px", display: "inline" }}
            className="img-fluid mx-3"
          />{" "}
          <a
            href="/"
            className="border-white border-start py-3 px-3 me-5"
            style={{ letterSpacing: ".2rem" }}
          >
            Portfolio Management
          </a>
          <a
            href="/"
            className="py-3 px-3 me-5"
            style={{ letterSpacing: ".1rem", width: "25px" }}
          >
            Portfolio Comparison
          </a>
          <a href="/" className="py-3 px-3 me-5">
            About Us
          </a>
        </div>
      </div>

      <div className="container-fluid">
        <div className="row">
          <div className="px-0">
            <SideNavBar setPortfoliosFromParent={(val) => setPortfolios(val)} />
          </div>
          <div
            className="App-body px-auto"
            style={{ marginLeft: width + "px", width: viewportWidth - width }}
          >
            <ChangePasswordModal />
            <Outlet />
          </div>
        </div>
      </div>
    </div>
  );
}