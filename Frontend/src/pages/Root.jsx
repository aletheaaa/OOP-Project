import React from "react";
import { Outlet } from "react-router-dom";
import SideNavBar from "../components/Common/SideNavBar";
import CreatePortfolioModal from "../components/Portfolios/CreatePortfolioModal";

function Root() {
  return (
    <div className="App">
      <div className="App-header">Portfolio Management</div>
      <div className="container-fluid">
        <div className="row">
          <div className="col-1 col-sm-3 px-0">
            <SideNavBar />
          </div>
          <div className="App-body col px-0 mx-0">
            <CreatePortfolioModal />
            <Outlet />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Root;
