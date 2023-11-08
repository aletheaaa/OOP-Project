import React, { useEffect, useState } from "react";
import { generateDoughnutColors } from "../../utils/chartUtils";
import {
  getAssetAllocationAPI,
  getAssetAllocationByIndustryAPI,
  getPortfolioDetailsAPI,
  getPortfolioPerformanceSummaryAPI,
  getPortfolioDifferenceAPI,
  getAssetAllocationByCountryAPI,
} from "../../api/portfolio";
import { useParams } from "react-router-dom";
import PortfolioDoughnutChart from "../../components/Portfolios/PortfolioDoughnutChart";
import PortfolioPerformanceSummary from "../../components/Portfolios/PortfolioPerformanceSummary";
import CreateOrEditPortfolioButton from "../../components/Portfolios/CreateOrEditPortfolioButton";
import PortfolioGrowthLineGraph from "../../components/Portfolios/PortfolioGrowthLineGraph";
import PortfolioReturnsBarChart from "../../components/Portfolios/PortfolioReturnsBarChart";
import { useLocation } from "react-router-dom";
import DashboardCard from "../../components/Common/DashboardCard";

export default function ComparePortfolios() {
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

  const [portfolio1PerformanceSummary, setPortfolio1PerformanceSummary] =
    useState({
      InitialBalance: 0,
      CurrentBalance: 0,
      NetProfit: 0,
      CAGR: 0.0,
      SharpeRatio: 0.0,
    });
  const [portfolio2PerformanceSummary, setPortfolio2PerformanceSummary] =
    useState({
      InitialBalance: 0,
      CurrentBalance: 0,
      NetProfit: 0,
      CAGR: 0.0,
      SharpeRatio: 0.0,
    });

  const [errorMessage, setErrorMessage] = useState("");
  const [differenceInProfits, setDifferenceInProfits] = useState(0);

  const location = useLocation();

  useEffect(() => {
    // Use URLSearchParams to parse query parameters
    const queryParams = new URLSearchParams(location.search);

    // Get the values of the query parameters
    const portfolio1 = queryParams.get("portfolio1");
    const portfolio2 = queryParams.get("portfolio2");

    if (!portfolio1 || !portfolio2) return;

    // getting the user's portfolio
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
      if (portfolioId === portfolio1) {
        setPortfolio1Details(portfolioDetails);
      } else {
        setPortfolio2Details(portfolioDetails);
      }
    };
    getPortfolioDetails(portfolio1);
    getPortfolioDetails(portfolio2);

    const getPerformanceSummary = async (portfolioId) => {
      let response = await getPortfolioPerformanceSummaryAPI(portfolioId);
      if (response.status != 200) {
        console.log(
          "error getting portfolio performance summary: ",
          response.data
        );
        setErrorMessage(response.data);
        return;
      }
      // console.log("performance summary");
      // console.log(response.data);
      if (portfolioId === portfolio1) {
        setPortfolio1PerformanceSummary(response.data);
      } else {
        setPortfolio2PerformanceSummary(response.data);
      }
    };
    getPerformanceSummary(portfolio1);
    getPerformanceSummary(portfolio2);

    const getDifferenceInProfits = async () => {
      let response = await getPortfolioDifferenceAPI(portfolio1, portfolio2);
      if (response.status != 200) {
        console.log("error getting difference in profits: ", response.data);
        setErrorMessage(response.data);
        return;
      }
      setDifferenceInProfits(response.data);
    };
    getDifferenceInProfits();
  }, [location.search]);

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
          <div className="row py-2 px-5 mt-2">
            {portfolio1Id && portfolio2Id && (
              <>
                <div className="col">
                  <DashboardCard
                    title="Difference in Profits"
                    value={differenceInProfits.toFixed(2) + "%"}
                    colorClassName="success"
                  />
                </div>
                <h4 className="pt-3">
                  Portfolio 1: {portfolio1Details.portfolio_name}
                </h4>
                <PortfolioPerformanceSummary
                  performanceSummary={portfolio1PerformanceSummary}
                />
                <h4>Portfolio 2: {portfolio2Details.portfolio_name}</h4>
                <PortfolioPerformanceSummary
                  performanceSummary={portfolio2PerformanceSummary}
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
                  portfolio2Name={portfolio2Details.portfolio_name}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
