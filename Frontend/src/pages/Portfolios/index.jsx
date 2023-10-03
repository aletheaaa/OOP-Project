import React, { useEffect, useState } from "react";
import AreaChart from "../../components/Common/AreaChart";
import DashboardCard from "../../components/Common/DashboardCard";
import AddStockButton from "../../components/Stocks/AddStockButton";
import CreatePortfolioButton from "../../components/Portfolios/CreatePortfolioButton";

export default function Portfolios() {
  const [stocks, setStocks] = useState([]);

  useEffect(() => {
    const getStocks = async () => {
      // const stocksFromServer = await fetchStocks();
      const stocksFromServer = [
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
      setStocks(stocksFromServer);
    };
    getStocks();
  }, []);

  return (
    <>
      <div className="container-fluid my-2 px-4 pt-2">
        <div className="d-flex flex-row-reverse">
          <div className="w-25 d-flex justify-content-end">
            <CreatePortfolioButton />
          </div>
        </div>
        <div className="row mb-3 mt-5">
          <div className="col">
            <h3>Portfolio Name</h3>
            <div>description strategy etc</div>
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
              <div className="col-5">NASDAQ</div>
              <div className="col">PRICE</div>
              <div className="col">QTY</div>
              <div className="col">CAPITAL GAINS</div>
              <div className="col">RETURNS</div>
            </div>
            {stocks.map((element) => {
              return (
                <div className="row p-1">
                  <div className="col-5">{element.name}</div>
                  <div className="col">{element.price}</div>
                  <div className="col">{element.quantity}</div>
                  <div className="col">{element.capitalGain}</div>
                  <div className="col">{element.returns}</div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </>
  );
}
