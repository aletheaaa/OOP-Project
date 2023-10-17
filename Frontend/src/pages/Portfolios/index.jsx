import React, { useEffect, useState } from "react";
import AreaChart from "../../components/Common/AreaChart";
import DashboardCard from "../../components/Common/DashboardCard";
import CreatePortfolioButton from "../../components/Portfolios/CreatePortfolioButton";
import DoughnutChart from "../../components/Common/DoughnutChart";

export default function Portfolios() {
  const [trades, setTrades] = useState([]);
  const [userPortfolios, setUserPortfolios] = useState([]);
  const [chosenPortfolio, setChosenPortfolio] = useState(0);
  const [chartData, setChartData] = useState([]);
  const [assetAllocationBySector, setAssetAllocationBySector] = useState({});
  const [
    asssetAllocationByIndividualStock,
    setAssetAllocationByIndividualStock,
  ] = useState({});

  // Generate random colors
  function randomColor(usedColors) {
    const maxAttempts = 1000;
    for (let attempt = 0; attempt < maxAttempts; attempt++) {
      const r = Math.floor(Math.random() * 256);
      const g = Math.floor(Math.random() * 256);
      const b = Math.floor(Math.random() * 256);
      const a = 0.2; // Adjust the alpha (transparency) as needed
      const color = `rgba(${r}, ${g}, ${b}, ${a}`;
      if (!usedColors.has(color)) {
        usedColors.add(color);
        return color;
      }
    }
    return "rgba(0, 0, 0, 0.2)"; // Return a default color if maxAttempts is exceeded
  }

  // Generate dynamic colors for doughnut chart
  function generateDoughnutColors(numDataPoints) {
    const usedColors = new Set();
    const dynamicBackgroundColors = [];
    const dynamicBorderColors = [];

    for (let i = 0; i < numDataPoints; i++) {
      const bgColor = randomColor(usedColors);
      dynamicBackgroundColors.push(bgColor);
      dynamicBorderColors.push(bgColor.replace("0.2", "1"));
    }

    return [dynamicBackgroundColors, dynamicBorderColors];
  }

  useEffect(() => {
    // TODO: get the user's trades from backend
    const getUserTrades = async () => {
      // const tradesFromServer = await fetchTrades();
      const tradesFromServer = [
        {
          name: "AAPL",
          price: 100,
          quantity: 1,
          capitalGain: 100,
          returns: 100,
        },
        {
          name: "TSLA",
          price: 200,
          quantity: 2,
          capitalGain: 200,
          returns: 200,
        },
      ];
      setTrades(tradesFromServer);
    };
    getUserTrades();

    // TODO: get the user's portfolio from backend
    // getting the user's portfolio
    setUserPortfolios([
      {
        portfolioId: "1",
        name: "Retirement",
        description: "retirement plan",
        totalCapital: 1000,
        Trades: [],
      },
      {
        portfolioId: "2",
        name: "Retirement 2",
        description: "retirement plan2",
        totalCapital: 1000,
        Trades: [],
      },
      {
        portfolioId: "3",
        name: "Retirement 3",
        description: "retirement plan3",
        totalCapital: 1000,
        Trades: [],
      },
    ]);
    setChosenPortfolio({
      portfolioId: "1",
      name: "Retirement",
      description: "retirement plan",
      totalCapital: 1000,
      Trades: [],
    });

    // TODO: get the user's portfolio from backend
    // getting the area line chart data
    const labels = [
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
      "November",
      "December",
    ];
    const data = {
      labels,
      datasets: [
        {
          label: "Actual Performance",
          data: [8, 7, 6, 7, 9, 7, 9, 6, 9, 7, 8, 9],
          borderColor: "rgb(255, 99, 132)",
          backgroundColor: "rgba(255, 99, 132, 0.5)",
        },
        {
          label: "Index",
          data: [2, 3, 5, 2, 3, 4, 1, 5, 1, 3, 2, 7],
          borderColor: "rgb(53, 162, 235)",
          backgroundColor: "rgba(53, 162, 235, 0.5)",
        },
      ],
    };
    setChartData(data);

    // getting the doughnut chart data - sector allocation
    // let doughnutBackgroundColors = [
    //   "rgba(255, 99, 132, 0.2)",
    //   "rgba(54, 162, 235, 0.2)",
    //   "rgba(255, 206, 86, 0.2)",
    //   "rgba(75, 192, 192, 0.2)",
    //   "rgba(153, 102, 255, 0.2)",
    //   "rgba(255, 159, 64, 0.2)",
    // ];
    // let doughnutBorderColors = [
    //   "rgba(255, 99, 132, 1)",
    //   "rgba(54, 162, 235, 1)",
    //   "rgba(255, 206, 86, 1)",
    //   "rgba(75, 192, 192, 1)",
    //   "rgba(153, 102, 255, 1)",
    //   "rgba(255, 159, 64, 1)",
    // ];

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
            // TODO: make the graph colors dynamic
            backgroundColor: dynamicBackgroundColors,
            borderColor: dynamicBorderColors,
            borderWidth: 1,
          },
        ],
      };
      setAssetAllocationBySector(sectorDoughnutData);
    };
    getAssetAllocationBySector();

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
      console.log(
        "this is allocation by indiv stock",
        individualStockDoughnutData
      );
      setAssetAllocationByIndividualStock(individualStockDoughnutData);
    };
    getAssetAllocationByIndividualStock();
  }, []);

  return (
    <>
      <div className="container-fluid my-2 px-4 pt-2">
        <div className="row d-flex justify-content-between">
          <select
            className="col-2 rounded"
            onChange={(e) =>
              setChosenPortfolio(
                userPortfolios.find(
                  (portfolio) => portfolio.portfolioId === e.target.value
                )
              )
            }
          >
            {userPortfolios.map((portfolio, index) => {
              return (
                <option value={portfolio.portfolioId} key={index}>
                  {portfolio.name}
                </option>
              );
            })}
          </select>
          <div className="col-2 d-flex justify-content-end">
            <CreatePortfolioButton />
          </div>
        </div>
        <div className="row mb-3 mt-5">
          <div className="col">
            <h3>{chosenPortfolio.name}</h3>
            <div>{chosenPortfolio.description}</div>
          </div>
          <div className="col text-end fw-bold">
            Current Portfolio Value:
            <h4>$1000</h4>
          </div>
        </div>
        <div className="shadow mb-5 bg-body rounded pb-3">
          <div
            className="p-3 rounded d-flex justify-content-end"
            style={{ backgroundColor: "lightgrey" }}
          >
            <select
              className="form-select w-25"
              aria-label="Default select example"
            >
              <option value="quarter">Quarter Returns</option>
              <option value="annual">Annual Returns</option>
            </select>
          </div>
          <div className="row py-2 px-4">
            <div className="col-12 col-lg-6">
              <div className="container">
                <div className="row">
                  <div className="col-12 mb-4">
                    <DashboardCard
                      title="Returns"
                      value="170.69"
                      iconClassName="bi-cash-coin"
                      colorClassName="primary"
                    />
                  </div>
                  <div className="col-md-6 mb-4">
                    <DashboardCard
                      title="Loss"
                      value="170.43"
                      iconClassName="bi-cash"
                      colorClassName="secondary"
                    />
                  </div>
                  <div className="col-md-6 mb-4">
                    <DashboardCard
                      title="Open"
                      value="169.34"
                      iconClassName="bi-coin"
                      colorClassName="secondary"
                    />
                  </div>
                  <div className="col-md-6 mb-4">
                    <DashboardCard
                      title="Placeholder"
                      value="100,000"
                      iconClassName="bi-cup-straw"
                      colorClassName="info"
                    />
                  </div>
                  <div className="col-md-6 mb-4">
                    <DashboardCard
                      title="Placeholder"
                      value="100,000"
                      iconClassName="bi-cup-hot"
                      colorClassName="info"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div className="col-12 col-lg-6">
              {chartData &&
                chartData.datasets &&
                chartData.datasets.length > 0 && <AreaChart data={chartData} />}
            </div>
          </div>
          <div className="row px-5">
            {/* Allocation by Sector */}
            <div className="col-6">
              <div class="card shadow mb-4">
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
              <div class="card shadow mb-4">
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
          <div className="px-5">
            <div
              className="row p-1 fw-medium"
              style={{ backgroundColor: "lightgray" }}
            >
              <div className="col-3">NASDAQ</div>
              <div className="col">PRICE</div>
              <div className="col">QTY</div>
              <div className="col">CAPITAL GAINS</div>
              <div className="col">RETURNS</div>
              <div className="col">CAPITAL ALLOCATION</div>
            </div>
            {trades.map((element, index) => {
              return (
                <div className="row p-1" key={index}>
                  <div className="col-3">{element.name}</div>
                  <div className="col">{element.price}</div>
                  <div className="col">{element.quantity}</div>
                  <div className="col">{element.capitalGain}</div>
                  <div className="col">{element.returns}</div>
                  <div className="col">100%</div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </>
  );
}
