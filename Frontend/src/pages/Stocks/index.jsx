import React from "react";
import StockNavBar from "../../components/Stocks/StockNavBar";
import AreaChart from "../../components/Common/AreaChart";
import DashboardCard from "../../components/Common/DashboardCard";
import AddStockButton from "../../components/Stocks/AddStockButton";

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
          <div className="col-12 col-lg-6">
            <div className="container">
              <div className="row">
                <div className="col-12 mb-4">
                  <DashboardCard
                    title="Price at Close"
                    value="170.69"
                    iconClassName="bi-cash-coin"
                    colorClassName="primary"
                  />
                </div>
                <div className="col-md-6 mb-4">
                  <DashboardCard
                    title="Previous Close"
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
        <div className="row">
          <div className="col-12">
            <AddStockButton stockPrice="170.69" />
          </div>
        </div>
      </div>
    </>
  );
};