import React, { useState, useEffect } from "react";
import { getPortfolios } from "../../api/user";
import { Link } from "react-router-dom";
import DashboardCard from "../../components/Common/DashboardCard";
import CreateOrEditPortfolioButton from "../../components/Portfolios/CreateOrEditPortfolioButton";
import ChangePasswordModal from "../../components/User/ChangePasswordModal";
import ComparePortfolios from "../../components/Portfolios/ComparePortfolios";
import CreateOrEditPortfolioModal from "../../components/Portfolios/CreateOrEditPortfolioModal";

export default function Dashboard() {
  const [portfolios, setPortfolios] = useState([]);
  const [userName, setUserName] = useState("");
  const [bodyHeight, setBodyHeight] = useState(0);

  useEffect(() => {
    const getUserInfo = async () => {
      const portfolios = await getPortfolios();
      if (portfolios.status != 200) {
        console.log("error getting user portfolios");
        return;
      }
      setUserName(userName);
      if (portfolios === null) {
        return;
      }
      setPortfolios(portfolios.data.userPortfolios);
    };
    getUserInfo();

    const getBodyHeight = () => {
      const topbarElement = document.getElementById("topNavBar");
      let topBarHeight;
      if (topbarElement) {
        topBarHeight = topbarElement.offsetHeight;
      }
      // getting viewport height
      const height = Math.max(
        document.documentElement.clientHeight,
        window.innerHeight || 0
      );
      setBodyHeight(height - topBarHeight);
    };
    getBodyHeight();
  }, []);

  return portfolios && portfolios.length != 0 ? (
    <div
      className="container"
      style={{ height: bodyHeight }}
    >
      <div className="row row-cols-2">
        <div className="col">
          <h2 className="mt-5 lh-base">
            Select portfolio
            or create one
            <ChangePasswordModal />
          </h2>
          <div className="border-bottom border-dark border-3 w-75 mb-2"></div>
        </div>
        <div className="col"></div>
        
          {portfolios &&
          portfolios.length &&
          portfolios.map((portfolio) => (
            <div className="col mb-3">
            <Link to={`/portfolios/${portfolio.portfolioId}`}>
            <DashboardCard
              title={portfolio.portfolioId}
              value={portfolio.portfolioName}
              iconClassName="bi-cash-coin"
              colorClassName="info"
            />
            </Link>
            </div>
          ))}
      </div>
      <div className="text-center">
          <ComparePortfolios portfolios={portfolios} />
          <CreateOrEditPortfolioButton
            mode="Create"
            className="btn btn-secondary ms-3"
          />
          <CreateOrEditPortfolioModal />
        </div>
    </div>
  ) : (
    <div
      className="p-3 px-5 d-flex align-items-center"
      style={{ height: bodyHeight }}
    >
      <div className="col-6">
        <h2
          className="mt-5 lh-base"
          style={{ fontWeight: 800, letterSpacing: "1px" }}
        >
          Start analyzing your <br />
          portfolio with precision.
        </h2>
        <div className="border-bottom border-dark border-3 w-75 mb-2"></div>
        <span>No portfolios found. Create a portfolio to get started. </span>
        <br />
        <CreateOrEditPortfolioButton
          mode="Create"
          className="btn btn-secondary mt-3"
        />
        <CreateOrEditPortfolioModal />
      </div>
      <div className="col-6 h-50 w-50">
        <div
          style={{
            height: "75%",
            width: "55%",
            backgroundColor: "lightgrey",
            position: "relative",
            zIndex: 1,
          }}
        >
          <div
            style={{
              height: "100%",
              width: "100%",
              backgroundColor: "#031868",
              position: "relative",
              left: "50%",
              zIndex: -12,
              top: "50%",
            }}
          ></div>
        </div>
      </div>
    </div>
  );
}
