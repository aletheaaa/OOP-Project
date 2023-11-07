import React, { useEffect, useState } from "react";
import DashboardCard from "../../components/Common/DashboardCard";

function PortfolioPerformanceSummary(props) {
  // console.log(props.performanceSummary);
  let InitialBalance = Number(props.performanceSummary.InitialBalance).toFixed(2);
  let NetProfit = Number(props.performanceSummary.NetProfit).toFixed(2);
  let CAGR = Number(props.performanceSummary.CAGR).toFixed(2);
  let SharpeRatio = Number(props.performanceSummary.SharpeRatio).toFixed(2);

  return (
    <>
      <div className="col-6 col-lg-3 mb-4">
        <DashboardCard
          title="Initial Balance"
          value={InitialBalance}
          iconClassName="bi-cash-coin"
          colorClassName="info"
        />
      </div>
      <div className="col-6 col-lg-3 mb-4 ">
        <DashboardCard
          title="Net Profit"
          value={NetProfit}
          iconClassName="bi-cash-coin"
          colorClassName="primary"
        />
      </div>
      <div className="col-6 col-lg-3 mb-4">
        <DashboardCard
          title="CAGR"
          value={Number.parseFloat(CAGR).toFixed(2) + "%"}
          iconClassName="bi-cash"
          colorClassName="secondary"
        />
      </div>
      <div className="col-6 col-lg-3 mb-4">
        <DashboardCard
          title="Sharpe Ratio"
          value={SharpeRatio}
          iconClassName="bi-coin"
          colorClassName="info"
        />
      </div>
    </>
  );
}

export default PortfolioPerformanceSummary;
