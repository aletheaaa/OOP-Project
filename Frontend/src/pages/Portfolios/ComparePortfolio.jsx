import React, { useEffect, useState } from "react";
import { generateDoughnutColors } from "../../utils/chartUtils";
import {
  getAssetAllocationAPI,
  getAssetAllocationByIndustryAPI,
  getPortfolioDetailsAPI,
  getAssetAllocationByCountryAPI,
} from "../../api/portfolio";
import { useParams } from "react-router-dom";
import PortfolioDoughnutChart from "../../components/Portfolios/PortfolioDoughnutChart";
import PortfolioPerformanceSummary from "../../components/Portfolios/PortfolioPerformanceSummary";
import CreateOrEditPortfolioButton from "../../components/Portfolios/CreateOrEditPortfolioButton";
import PortfolioGrowthLineGraph from "../../components/Portfolios/PortfolioGrowthLineGraph";
import PortfolioReturnsBarChart from "../../components/Portfolios/PortfolioReturnsBarChart";
import { useLocation } from "react-router-dom";

export default function Portfolios() {
  const [portfolio1Id, setPortfolio1Id] = useState();
  const [portfolio2Id, setPortfolio2Id] = useState();
  const [portfolio1Details, setPortfolio1Details] = useState({
    portfolio_name: "Loading...",
    description: "Loading...",
    capital: 0,
    start_date: "Loading...",
  });
  const [portfolio2Details, setPortfolio2Details] = useState({
    portfolio_name: "Loading...",
    description: "Loading...",
    capital: 0,
    start_date: "Loading...",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const [portfolio1CurrentBalance, setPortfolio1CurrentBalance] = useState(0);
  const [portfolio2CurrentBalance, setPortfolio2CurrentBalance] = useState(0);

  const location = useLocation();

  useEffect(() => {
    // Use URLSearchParams to parse query parameters
    const queryParams = new URLSearchParams(location.search);

    // Get the values of the query parameters
    const portfolio1 = queryParams.get("portfolio1");
    const portfolio2 = queryParams.get("portfolio2");

    if (!portfolio1 || !portfolio2) return;
    // getting the user's portfolio
    console.log("this si portfolio1", portfolio1, portfolio2);
    setPortfolio1Id(portfolio1);
    setPortfolio2Id(portfolio2);
    const getPortfolioDetails = async (portfolioId) => {
      const response = await getPortfolioDetailsAPI(portfolioId);
      if (response.status != 200) {
        console.log("error getting portfolio details");
        setErrorMessage(response.data);
        return;
      }
      let portfolioDetails = { ...response.data, portfolioId: portfolioId };
      if (portfolioId == portfolio1) {
        setPortfolio1Details(portfolioDetails);
      } else {
        setPortfolio2Details(portfolioDetails);
      }
    };
    getPortfolioDetails(portfolio1);
    getPortfolioDetails(portfolio2);
  }, [location.search]);

  useEffect(() => {
    console.log("this si protfolios details ", portfolio1Details);
  }, [portfolio1Details]);

  return (
    <>
      <div className="container-fluid my-2 px-4 pt-5">
        {errorMessage && (
          <div className="position-static alert alert-danger" role="alert">
            {errorMessage}
          </div>
        )}
        <div className="position-static mb-5 bg-body rounded pb-3">
          {/* Dashboard Cards */}
          <div className="row py-2 px-4 mt-2">
            {portfolio1Id && portfolio2Id && (
              <>
                <PortfolioPerformanceSummary
                  portfolioId={portfolio1Id}
                  setCurrentBalanceParent={(val) =>
                    setPortfolio1CurrentBalance(val)
                  }
                />
                <PortfolioPerformanceSummary
                  portfolioId={portfolio2Id}
                  setCurrentBalanceParent={(val) =>
                    setPortfolio2CurrentBalance(val)
                  }
                />
              </>
            )}
          </div>
          <div className="row px-5">
            {/* Allocation by Sector */}
            {/* <div className="col-6">
              <PortfolioDoughnutChart
                asssetAllocation={assetAllocationBySector}
                title="% Allocation by Sector"
                tableHeaders={["Sector", "% Allocation"]}
              />
            </div> */}
            {/* Allocation by Individual Stocks */}
            {/* <div className="col-6">
              <PortfolioDoughnutChart
                asssetAllocation={asssetAllocationByIndividualStock}
                title="% Allocation by Individual Stocks"
                tableHeaders={["Stock", "% Allocation"]}
              />
            </div> */}
          </div>
          {/* <div className="row px-5">
            <div className="col-6">
              <PortfolioDoughnutChart
                asssetAllocation={assetAllocationByIndustry}
                title="% Allocation by Industry"
                tableHeaders={["Industry", "% Allocation"]}
              />
            </div>
            <div className="col-6">
              <PortfolioDoughnutChart
                asssetAllocation={assetAllocationByCountry}
                title="% Allocation by Country"
                tableHeaders={["Country", "% Allocation"]}
              />
            </div>
          </div> */}
          {/* Portfolio Growth Line Graph */}
          <div className="row mt-3 px-5">
            <div className="col">
              {portfolio1Details && portfolio2Details && (
                <PortfolioGrowthLineGraph
                  portfolioId={portfolio1Id}
                  portfolioName={portfolio1Details.portfolio_name}
                  startDate={portfolio1Details.start_date}
                  portfolio2Id={portfolio2Id}
                  startDate2={portfolio2Details.start_date}
                  portfolioName2={portfolio2Details.portfolio_name}
                />
              )}
            </div>
          </div>
          {/* Returns Bar Chart */}
          <div className="row mt-3 px-5">
            <div className="col">
              {portfolio1Details && portfolio2Details && (
                <PortfolioReturnsBarChart
                  portfolioId={portfolio1Id}
                  portfolioName={portfolio1Details.portfolio_name}
                  startDate={portfolio1Details.start_date}
                  portfolio2Id={portfolio2Id}
                  startDate2={portfolio2Details.start_date}
                  portfolioName2={portfolio2Details.portfolio_name}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
