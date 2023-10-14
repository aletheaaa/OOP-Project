import React, { useEffect, useState } from "react";
import AreaChart from "../../components/Common/AreaChart";
import DashboardCard from "../../components/Common/DashboardCard";
import CreatePortfolioButton from "../../components/Portfolios/CreatePortfolioButton";

export default function Portfolios() {
  const [trades, setTrades] = useState([]);
  const [userPortfolios, setUserPortfolios] = useState([]);
  const [chosenPortfolio, setChosenPortfolio] = useState(0);

  useEffect(() => {
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
            {userPortfolios.map((portfolio) => {
              return (
                <option value={portfolio.portfolioId}>{portfolio.name}</option>
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
              <option value="quarter" selected>
                Quarter Returns
              </option>
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
              <AreaChart />
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
            {trades.map((element) => {
              return (
                <div className="row p-1">
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
