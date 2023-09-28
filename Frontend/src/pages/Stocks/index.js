import React from "react";
import StockNavBar from "../../components/Stocks/StockNavBar";
import AreaChart from "../../components/AreaChart";

export default function Stocks() {
  return (
    <>
      <div className="container-fluid my-2">
        <div className="row">
          <div className="col-12">
            <StockNavBar />
          </div>
        </div>
        <div className="row">
          <div className="col-12">
            <AreaChart />
          </div>
        </div>
      </div>
    </>
  );
};