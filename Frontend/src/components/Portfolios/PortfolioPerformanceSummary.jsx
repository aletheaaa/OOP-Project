import React, { useEffect, useState } from "react";
import DashboardCard from "../../components/Common/DashboardCard";
import { getPortfolioPerformanceSummaryAPI } from "../../api/portfolio";

function PortfolioPerformanceSummary({ portfolioId, setCurrentBalanceParent }) {
  const [InitialBalance, setInitialBalance] = useState([]);
  const [FinalBalance, setFinalBalance] = useState([]);
  const [netProfit, setNetProfit] = useState([]);
  const [CAGR, setCAGR] = useState([]);
  const [SharpeRatio, setSharpeRatio] = useState([]);
  const [SortinoRatio, setSortinoRatio] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const getPortfolioDetails = async () => {
      let portfolioDetails = await getPortfolioPerformanceSummaryAPI(
        portfolioId
      );
      console.log("portfolioDetails: ", portfolioDetails);
      if (portfolioDetails.status != 200) {
        console.log("Error in fetching portfolio details");
        setErrorMessage(
          `Error in fetching portfolio details: ${portfolioDetails.data}`
        );
        return;
      }
      portfolioDetails = portfolioDetails.data;
      setInitialBalance(portfolioDetails.InitialBalance);
      setFinalBalance(portfolioDetails.FinalBalance);
      setCurrentBalanceParent(portfolioDetails.FinalBalance);
      setNetProfit(portfolioDetails.NetProfit);
      setCAGR(portfolioDetails.CAGR);
      setSharpeRatio(portfolioDetails.SharpeRatio);
      setSortinoRatio(portfolioDetails.SortinoRatio);
    };
    getPortfolioDetails();
  }, []);

  return (
    <>
      {errorMessage ? (
        <div className="position-static alert alert-danger" role="alert">
          {errorMessage}
        </div>
      ) : (
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
          <div className="col-6 col-lg-3 mb-4">
            <DashboardCard
              title="Sortino Ratio"
              value={SortinoRatio}
              iconClassName="bi-cash-coin"
              colorClassName="info"
            />
          </div>
        </>
      )}
    </>
  );
}

export default PortfolioPerformanceSummary;
