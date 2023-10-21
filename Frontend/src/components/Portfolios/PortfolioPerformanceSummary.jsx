import React, { useEffect, useState } from "react";
import DashboardCard from "../../components/Common/DashboardCard";
import { getPortfolioPerformanceSummaryAPI } from "../../api/portfolio";

function PortfolioPerformanceSummaryCards(portfolioId, setCurrentBalance) {
  const [InitialBalance, setInitialBalance] = useState([]);
  const [FinalBalance, setFinalBalance] = useState([]);
  const [netProfit, setNetProfit] = useState([]);
  const [CAGR, setCAGR] = useState([]);
  const [SharpeRatio, setSharpeRatio] = useState([]);
  const [SortinoRatio, setSortinoRatio] = useState([]);

  useEffect(() => {
    const getPortfolioDetails = async () => {
      let portfolioDetails = await getPortfolioPerformanceSummaryAPI(
        portfolioId
      );
      portfolioDetails = portfolioDetails.data;
      setInitialBalance(portfolioDetails.InitialBalance);
      setFinalBalance(portfolioDetails.FinalBalance);
      setCurrentBalance(portfolioDetails.FinalBalance);
      setNetProfit(portfolioDetails.NetProfit);
      setCAGR(portfolioDetails.CAGR);
      setSharpeRatio(portfolioDetails.SharpeRatio);
      setSortinoRatio(portfolioDetails.SortinoRatio);
    };
    getPortfolioDetails();
  }, []);

  return (
    <>
      <div className="col-6 col-lg-3 mb-4 ">
        <DashboardCard
          title="Net Profit"
          value={netProfit}
          iconClassName="bi-cash-coin"
          colorClassName="primary"
        />
      </div>
      <div className="col-6 col-lg-3 mb-4">
        <DashboardCard
          title="CAGR"
          value={CAGR}
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
      <div className="col-6 col-lg-3 mb-4">
        <DashboardCard
          title="Sortino Ratio"
          value={SortinoRatio}
          iconClassName="bi-cash-coin"
          colorClassName="info"
        />
      </div>
    </>
  );
}

export default PortfolioPerformanceSummaryCards;
