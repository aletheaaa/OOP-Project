import React, { useEffect } from "react";
import { Outlet } from "react-router-dom";
import SideNavBar from "../components/Common/SideNavBar";
import CreatePortfolioModal from "../components/Portfolios/CreatePortfolioModal";
import ChangePasswordModal from "../components/User/ChangePasswordModal";

function Root() {
  const [width, setWidth] = React.useState(0);
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
  }, []);

  return (
    <div className="App">
      <div className="App-header">Portfolio Management</div>
      <div className="container-fluid">
        <div className="row">
          <div className="px-0">
            <SideNavBar />
          </div>
          <div
            className="App-body px-auto"
            style={{ marginLeft: width + "px", width: viewportWidth - width }}
          >
            <CreatePortfolioModal />
            <ChangePasswordModal />
            <Outlet />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Root;
