import React, { useEffect, useState } from "react";
import AreaChart from "../../components/Common/AreaChart";
import DashboardCard from "../../components/Common/DashboardCard";
import PortfolioNavBar from "../../components/Portfolios/PortfolioNavBar";
import DoughnutChart from "../../components/Common/DoughnutChart";
import BarChart from "../../components/Common/BarChart";
import { generateDoughnutColors } from "../../utils/chartUtils";
import { getPortfolioDetailsAPI } from "../../api/portfolio";
import StockPerformanceTable from "../../components/Portfolios/StockPerformance";
import { useParams } from "react-router-dom";
import CreatePortfolioButton from "../../components/Portfolios/CreatePortfolioButton";

export default function Portfolios() {
  const [trades, setTrades] = useState([]);
  const [chosenPortfolio, setChosenPortfolio] = useState(0);
  const [chartData, setChartData] = useState([]);
  const [assetAllocationBySector, setAssetAllocationBySector] = useState({});
  const [
    asssetAllocationByIndividualStock,
    setAssetAllocationByIndividualStock,
  ] = useState({});
  const [barChartConfig, setBarChartConfig] = useState("annual");
  const [barChartDataByYear, setBarChartDataByYear] = useState({});
  const [barChartDataByQuarter, setBarChartDataByQuarter] = useState({});

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

    // TODO: get the user's portfolio from backend
    // getting the area line chart data
    const labels = [
      "November",
      "December",
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
    ];
    const data = {
      labels,
      datasets: [
        {
          label: "TSLA",
          data: [
            227.82, 194.7, 108.1, 181.41, 202.77, 194.77, 161.83, 207.52,
            179.82, 261.07, 245.01, 251.6,
          ],
          borderColor: "rgb(255, 99, 132)",
          backgroundColor: "rgba(255, 99, 132, 0.5)",
        },
        {
          label: "Index",
          data: [200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200],
          borderColor: "rgb(53, 162, 235)",
          backgroundColor: "rgba(53, 162, 235, 0.5)",
        },
      ],
    };
    setChartData(data);

    // TODO: to connect to backend
    const getAssetAllocationBySector = async () => {
      // return data from backend
      let assetAllocationFromServer = {
        Technology: {
          value: 0.6,
          stocks: [
            {
              symbol: "TSLA",
              allocation: 0.4,
            },
            {
              symbol: "AAPL",
              allocation: 0.2,
            },
          ],
        },
        Healthcare: {
          value: 0.4,
          stocks: [
            {
              symbol: "PFE",
              allocation: 0.4,
            },
          ],
        },
      };

      // Number of data points
      const numberOfDataPoints = Object.keys(assetAllocationFromServer).length;
      // Generate dynamic colors
      const [dynamicBackgroundColors, dynamicBorderColors] =
        generateDoughnutColors(numberOfDataPoints);
      const sectorDoughnutData = {
        labels: Object.keys(assetAllocationFromServer),
        datasets: [
          {
            label: "% of Capital Allocated to Different Sector",
            data: Object.values(assetAllocationFromServer).map(
              (element) => element.value * 100
            ),
            backgroundColor: dynamicBackgroundColors,
            borderColor: dynamicBorderColors,
            borderWidth: 1,
          },
        ],
      };
      setAssetAllocationBySector(sectorDoughnutData);
    };
    getAssetAllocationBySector();

    // TODO: to connect to backend
    const getAssetAllocationByIndividualStock = async () => {
      // return data from backend
      let assetAllocationFromServer = {
        Technology: {
          value: 0.6,
          stocks: [
            {
              symbol: "TSLA",
              allocation: 0.4,
            },
            {
              symbol: "AAPL",
              allocation: 0.2,
            },
          ],
        },
        Healthcare: {
          value: 0.4,
          stocks: [
            {
              symbol: "PFE",
              allocation: 0.4,
            },
          ],
        },
      };
      // getting asset allocation by indiv stocks
      const stockLabel = [];
      const stockData = [];
      Object.values(assetAllocationFromServer).forEach((element) => {
        element.stocks.forEach((stock) => {
          stockLabel.push(stock.symbol);
          stockData.push(stock.allocation * 100);
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
    getAssetAllocationByIndividualStock();
  }, [portfolioId]);

  // TODO: to connect to backend
  // get data for bar chart
  useEffect(() => {
    const getPortfolioReturns = async () => {
      let options = {
        responsive: true,
        plugins: {
          legend: {
            position: "top",
          },
          title: {
            display: true,
            text: "Chart.js Bar Chart",
          },
        },
      };

      const labels = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
      ];

      let data = {
        labels,
        datasets: [
          {
            label: chosenPortfolio.name,
            data: labels.map(() => Math.floor(Math.random() * 1001) + 0),
            backgroundColor: "rgba(255, 99, 132, 0.5)",
          },
        ],
      };
      setBarChartDataByYear({
        options: options,
        data: data,
      });
      let data2 = {
        labels,
        datasets: [
          {
            label: chosenPortfolio.name,
            data: labels.map(() => Math.floor(Math.random() * 500) + 0),
            backgroundColor: "rgba(255, 99, 132, 0.5)",
          },
        ],
      };
      setBarChartDataByQuarter({
        options: options,
        data: data2,
      });
    };
    getPortfolioReturns();
  }, [chosenPortfolio]);

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
            <h4>$1000</h4>
          </div>
        </div>
        <div className="position-static mb-5 bg-body rounded pb-3">
          <PortfolioNavBar name={chosenPortfolio.name} />
          {/* Dashboard Cards */}
          <div className="row py-2 px-4">
            <div className="col-6 col-lg-3 mb-4 ">
              <DashboardCard
                title="Returns"
                value="170.69"
                iconClassName="bi-cash-coin"
                colorClassName="primary"
              />
            </div>
            <div className="col-6 col-lg-3 mb-4">
              <DashboardCard
                title="Loss"
                value="170.43"
                iconClassName="bi-cash"
                colorClassName="secondary"
              />
            </div>
            <div className="col-6 col-lg-3 mb-4">
              <DashboardCard
                title="Open"
                value="169.34"
                iconClassName="bi-coin"
                colorClassName="info"
              />
            </div>
            <div className="col-6 col-lg-3 mb-4">
              <DashboardCard
                title="Close"
                value="170.69"
                iconClassName="bi-cash-coin"
                colorClassName="info"
              />
            </div>
          </div>
          <div className="row px-5">
            {/* Allocation by Sector */}
            <div className="col-6">
              <div class="card position-static shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">
                    % Allocation by Sector
                  </h6>
                </div>
                <div class="card-body row">
                  <div class="chart-area w-50">
                    {assetAllocationBySector &&
                      Object.keys(assetAllocationBySector).length > 0 && (
                        <DoughnutChart data={assetAllocationBySector} />
                      )}
                  </div>
                  <div className="w-50 d-flex align-items-center">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                          <th scope="col">Sector</th>
                          <th scope="col">% Allocation</th>
                        </tr>
                      </thead>
                      <tbody>
                        {assetAllocationBySector &&
                          assetAllocationBySector.labels &&
                          assetAllocationBySector.labels.length > 0 &&
                          assetAllocationBySector.labels.map(
                            (element, index) => {
                              return (
                                <tr key={index}>
                                  <td>{element}</td>
                                  <td>
                                    {
                                      assetAllocationBySector.datasets[0].data[
                                        index
                                      ]
                                    }
                                    %
                                  </td>
                                </tr>
                              );
                            }
                          )}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            {/* Allocation by Individual Stocks */}
            <div className="col-6">
              <div class="card shadow position-static mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">
                    % Allocation by Individual Stocks
                  </h6>
                </div>
                <div class="card-body row">
                  <div class="chart-area w-50">
                    {asssetAllocationByIndividualStock &&
                      Object.keys(asssetAllocationByIndividualStock).length >
                        0 && (
                        <DoughnutChart
                          data={asssetAllocationByIndividualStock}
                        />
                      )}
                  </div>
                  <div className="w-50 d-flex align-items-center">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                          <th scope="col">Sector</th>
                          <th scope="col">% Allocation</th>
                        </tr>
                      </thead>
                      <tbody>
                        {asssetAllocationByIndividualStock &&
                          asssetAllocationByIndividualStock.labels &&
                          asssetAllocationByIndividualStock.labels.length > 0 &&
                          asssetAllocationByIndividualStock.labels.map(
                            (element, index) => {
                              return (
                                <tr key={index}>
                                  <td>{element}</td>
                                  <td>
                                    {
                                      asssetAllocationByIndividualStock
                                        .datasets[0].data[index]
                                    }
                                    %
                                  </td>
                                </tr>
                              );
                            }
                          )}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
          {/* Trades table */}
          <StockPerformanceTable />
          {/* Portfolio Growth Line Graph */}
          <div className="row mt-5 px-5">
            <div className="col">
              <div className="card position-static shadow mb-4">
                <div class="card-header py-3 d-flex justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">
                    Portfolio Growth
                  </h6>
                  <select
                    className="form-select w-25"
                    aria-label="Default select example"
                    onChange={(e) => {
                      console.log("e", e.target.value);
                      setBarChartConfig(e.target.value);
                    }}
                  >
                    <option value="annual">Annual Returns</option>
                    <option value="quarter">Quarter Returns</option>
                  </select>
                </div>
                <div class="card-body row">
                  <div class="chart-area">
                    {chartData &&
                      chartData.datasets &&
                      chartData.datasets.length > 0 && (
                        <AreaChart data={chartData} />
                      )}
                  </div>
                </div>
              </div>
            </div>
          </div>
          {/* Returns Bar Chart */}
          <div className="row mt-3 px-5">
            <div className="col">
              <div className="card position-static shadow mb-4">
                <div class="card-header py-3 d-flex justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">
                    Portfolio Returns
                  </h6>
                  <select
                    className="form-select w-25"
                    aria-label="Default select example"
                    onChange={(e) => {
                      console.log("e", e.target.value);
                      setBarChartConfig(e.target.value);
                    }}
                  >
                    <option value="annual">Annual Returns</option>
                    <option value="quarter">Quarter Returns</option>
                  </select>
                </div>
                <div class="card-body row">
                  <div class="chart-area">
                    {barChartConfig == "annual"
                      ? barChartDataByYear &&
                        barChartDataByYear.data &&
                        barChartDataByYear.data.datasets &&
                        barChartDataByYear.data.datasets.length > 0 && (
                          <BarChart data={barChartDataByYear.data} />
                        )
                      : barChartDataByQuarter &&
                        barChartDataByQuarter.data &&
                        barChartDataByQuarter.data.datasets &&
                        barChartDataByQuarter.data.datasets.length > 0 && (
                          <BarChart data={barChartDataByQuarter.data} />
                        )}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
