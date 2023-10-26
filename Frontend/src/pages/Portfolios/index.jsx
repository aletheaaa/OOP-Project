import React, { useEffect, useState } from "react";
import PortfolioNavBar from "../../components/Portfolios/PortfolioNavBar";
import BarChart from "../../components/Common/BarChart";
import { generateDoughnutColors } from "../../utils/chartUtils";
import {
  getAssetAllocationAPI,
  getPortfolioDetailsAPI,
} from "../../api/portfolio";
import StockPerformanceTable from "../../components/Portfolios/StockPerformance";
import { useParams } from "react-router-dom";
import PortfolioDoughnutChart from "../../components/Portfolios/PortfolioDoughnutChart";
import PortfolioPerformanceSummary from "../../components/Portfolios/PortfolioPerformanceSummary";
import CreatePortfolioButton from "../../components/Portfolios/CreatePortfolioButton";
import PortfolioGrowthLineGraph from "../../components/Portfolios/PortfolioGrowthLineGraph";
import PortfolioReturnsBarChart from "../../components/Portfolios/PortfolioReturnsBarChart";

export default function Portfolios() {
  const [currentBalance, setCurrentBalance] = useState(0);
  const [chosenPortfolio, setChosenPortfolio] = useState(0);
  const [assetAllocationBySector, setAssetAllocationBySector] = useState({});
  const [
    asssetAllocationByIndividualStock,
    setAssetAllocationByIndividualStock,
  ] = useState({});

  const { portfolioId } = useParams(); // get portfolioID from url

  useEffect(() => {
    if (!portfolioId) return;
    // TODO: get the user's portfolio from backend
    // getting the user's portfolio
    const getPortfolioDetails = async () => {
      const portfolioDetails = await getPortfolioDetailsAPI(portfolioId);
      setChosenPortfolio(portfolioDetails);
    };
    getPortfolioDetails();

    const getAssetAllocation = async () => {
      let assetAllocationFromServer = await getAssetAllocationAPI(portfolioId);
      if (assetAllocationFromServer.status != 200) {
        console.log("error getting asset allocation by sector");
        return;
      }
      assetAllocationFromServer = assetAllocationFromServer.data.assets;

      // getting asset allocation by sector
      const assetAllocationBySector = () => {
        // Number of data points
        const numberOfDataPoints = Object.keys(
          assetAllocationFromServer
        ).length;
        // Generate dynamic colors
        const [dynamicBackgroundColors, dynamicBorderColors] =
          generateDoughnutColors(numberOfDataPoints);
        const sectorDoughnutData = {
          labels: Object.keys(assetAllocationFromServer),
          datasets: [
            {
              label: "% of Capital Allocated to Different Sector",
              data: Object.keys(assetAllocationFromServer).map(
                (element) => assetAllocationFromServer[element].value
              ),
              backgroundColor: dynamicBackgroundColors,
              borderColor: dynamicBorderColors,
              borderWidth: 1,
            },
          ],
        };
        setAssetAllocationBySector(sectorDoughnutData);
      };
      assetAllocationBySector();

      // getting asset allocation by indiv stocks
      const assetAllocationByIndividualStock = () => {
        const stockLabel = [];
        const stockData = [];
        Object.keys(assetAllocationFromServer).forEach((element) => {
          assetAllocationFromServer[element].stocks.forEach((stock) => {
            stockLabel.push(stock.symbol);
            stockData.push(stock.allocation);
          });
        });

        // Number of data points
        const numberOfDataPoints = stockLabel.length;
        // Generate dynamic colors
        const [doughnutBackgroundColors, doughnutBorderColors] =
          generateDoughnutColors(numberOfDataPoints);

        const individualStockDoughnutData = {
          labels: stockLabel,
          datasets: [
            {
              label: "% of Capital Allocated to Different Stocks",
              data: stockData,
              backgroundColor: doughnutBackgroundColors,
              borderColor: doughnutBorderColors,
              borderWidth: 1,
            },
          ],
        };
        setAssetAllocationByIndividualStock(individualStockDoughnutData);
      };
      assetAllocationByIndividualStock();
    };
    getAssetAllocation();
  }, [portfolioId]);

  const handleGetCurrentBalance = (balance) => {
    setCurrentBalance(balance);
  };

  return (
    <>
      <div className="container-fluid my-2 px-4 pt-2">
        <div className="d-flex justify-content-end">
          <CreatePortfolioButton />
        </div>
        <div className="row mb-3 mt-3">
          <div className="col">
            <h3>{chosenPortfolio.name}</h3>
            <div>{chosenPortfolio.description}</div>
          </div>
          <div className="col text-end fw-bold">
            Current Portfolio Value:
            <h4>${currentBalance}</h4>
          </div>
        </div>
        <div className="position-static mb-5 bg-body rounded pb-3">
          <PortfolioNavBar name={chosenPortfolio.name} />
          {/* Dashboard Cards */}
          <div className="row py-2 px-4">
            <PortfolioPerformanceSummary
              portfolioId={portfolioId}
              setCurrentBalanceParent={handleGetCurrentBalance}
            />
          </div>
          <div className="row px-5">
            {/* Allocation by Sector */}
            <div className="col-6">
              <PortfolioDoughnutChart
                asssetAllocation={assetAllocationBySector}
                title="% Allocation by Sector"
                tableHeaders={["Sector", "% Allocation"]}
              />
            </div>
            {/* Allocation by Individual Stocks */}
            <div className="col-6">
              <PortfolioDoughnutChart
                asssetAllocation={asssetAllocationByIndividualStock}
                title="% Allocation by Individual Stocks"
                tableHeaders={["Stock", "% Allocation"]}
              />
            </div>
          </div>
          {/* Trades table */}
          <StockPerformanceTable />
          {/* Performance Summary */}
          {/* <PerformanceSummaryTable /> */}
          {/* Portfolio Growth Line Graph */}
          <div className="row mt-5 px-5">
            <div className="col">
              <PortfolioGrowthLineGraph
                portfolioId={portfolioId}
                portfolioName={chosenPortfolio.name}
              />
            </div>
          </div>
          {/* Returns Bar Chart */}
          <div className="row mt-3 px-5">
            <div className="col">
              <PortfolioReturnsBarChart
                portfolioId={portfolioId}
                portfolioName={chosenPortfolio.name}
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
