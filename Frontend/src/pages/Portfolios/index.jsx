import React, { useEffect, useState } from "react";
import PortfolioNavBar from "../../components/Portfolios/PortfolioNavBar";
import { generateDoughnutColors } from "../../utils/chartUtils";
import {
  getAssetAllocationAPI,
  getPortfolioDetailsAPI,
} from "../../api/portfolio";
import { useParams } from "react-router-dom";
import PortfolioDoughnutChart from "../../components/Portfolios/PortfolioDoughnutChart";
import PortfolioPerformanceSummary from "../../components/Portfolios/PortfolioPerformanceSummary";
import CreateOrEditPortfolioButton from "../../components/Portfolios/CreateOrEditPortfolioButton";
import PortfolioGrowthLineGraph from "../../components/Portfolios/PortfolioGrowthLineGraph";
import PortfolioReturnsBarChart from "../../components/Portfolios/PortfolioReturnsBarChart";
import DeletePortfolioButton from "../../components/Portfolios/DeletePortfolioButton";
import CreateOrEditPortfolioModal from "../../components/Portfolios/CreateOrEditPortfolioModal";

export default function Portfolios() {
  const [currentBalance, setCurrentBalance] = useState(0);
  const [chosenPortfolio, setChosenPortfolio] = useState({
    portfolio_name: "Loading...",
    description: "Loading...",
    capital: 0,
    start_date: "Loading...",
  });
  const [assetAllocationBySector, setAssetAllocationBySector] = useState({});
  const [
    asssetAllocationByIndividualStock,
    setAssetAllocationByIndividualStock,
  ] = useState({});
  const [assetListForEditPortfolio, setAssetListForEditPortfolio] = useState([
    { Symbol: "", Allocation: 0, id: 0 },
  ]);

  const { portfolioId } = useParams(); // get portfolioID from url
  const [modeOfModal, setModeOfModal] = useState("Create");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (!portfolioId) return;
    // getting the user's portfolio
    const getPortfolioDetails = async () => {
      const response = await getPortfolioDetailsAPI(portfolioId);
      if (response.status != 200) {
        console.log("error getting portfolio details");
        setErrorMessage(response.data);
        return;
      }
      setChosenPortfolio(response.data);
    };
    getPortfolioDetails();

    const getAssetAllocation = async () => {
      let assetAllocationFromServer = await getAssetAllocationAPI(portfolioId);
      if (assetAllocationFromServer.status != 200) {
        console.log(
          "error getting asset allocation: ",
          assetAllocationFromServer.data
        );
        setErrorMessage(assetAllocationFromServer.data);
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
              label: "% of Capital Allocated",
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
        const editPortfolioDetailsModal = [];
        let counter = 0;
        Object.keys(assetAllocationFromServer).forEach((element) => {
          // data formatting for doughnut chart
          assetAllocationFromServer[element].stocks.forEach((stock) => {
            stockLabel.push(stock.symbol);
            stockData.push(stock.allocation);
          });
          // data formatting for edit portfolio modal
          assetAllocationFromServer[element].stocks.forEach((stock) => {
            editPortfolioDetailsModal.push({
              Symbol: stock.symbol,
              Allocation: stock.allocation,
              id: counter,
            });
            counter++;
          });
        });
        setAssetListForEditPortfolio(editPortfolioDetailsModal);

        // Number of data points
        const numberOfDataPoints = stockLabel.length;
        // Generate dynamic colors
        const [doughnutBackgroundColors, doughnutBorderColors] =
          generateDoughnutColors(numberOfDataPoints);

        const individualStockDoughnutData = {
          labels: stockLabel,
          datasets: [
            {
              label: "% of Capital Allocated",
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
        {errorMessage && (
          <div className="position-static alert alert-danger" role="alert">
            {errorMessage}
          </div>
        )}
        <div className="d-flex justify-content-end">
          <CreateOrEditPortfolioButton
            target="#portfolioIndexPageModal"
            mode="Edit"
            setModeOfModal={(val) => {
              setModeOfModal(val);
            }}
          />
          <CreateOrEditPortfolioModal
            id="portfolioIndexPageModal"
            mode={modeOfModal}
            originalPortfolioDetails={chosenPortfolio}
            assetList={assetListForEditPortfolio}
          />
          <div style={{ marginLeft: "10px" }}></div>
          <CreateOrEditPortfolioButton
            mode="Create"
            target="#portfolioIndexPageModal"
            setModeOfModal={(val) => {
              setModeOfModal(val);
            }}
          />
          <CreateOrEditPortfolioModal
            id="portfolioIndexPageModal"
            mode={modeOfModal}
            originalPortfolioDetails={chosenPortfolio}
            assetList={assetListForEditPortfolio}
          />
          <div style={{ marginLeft: "10px" }}></div>
          <DeletePortfolioButton />
        </div>
        <div className="row mb-3 mt-3">
          <div className="col">
            <h3>{chosenPortfolio.portfolio_name}</h3>
            <div>{chosenPortfolio.description}</div>
          </div>
          <div className="col text-end fw-bold">
            Current Portfolio Value:
            <h4>${currentBalance}</h4>
          </div>
        </div>
        <div className="position-static mb-5 bg-body rounded pb-3">
          <PortfolioNavBar name={chosenPortfolio.portfolio_name} />
          {/* Dashboard Cards */}
          <div className="row py-2 px-4 mt-2">
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
          {/* Portfolio Growth Line Graph */}
          <div className="row mt-3 px-5">
            <div className="col">
              <PortfolioGrowthLineGraph
                portfolioId={portfolioId}
                portfolioName={chosenPortfolio.portfolio_name}
                startDate={chosenPortfolio.start_date}
              />
            </div>
          </div>
          {/* Returns Bar Chart */}
          <div className="row mt-3 px-5">
            <div className="col">
              <PortfolioReturnsBarChart
                portfolioId={portfolioId}
                portfolioName={chosenPortfolio.portfolio_name}
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
