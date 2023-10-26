import React, { useEffect, useState } from "react";
import { getPortfolioAnnualReturnsAPI } from "../../api/portfolio";
import BarChart from "../Common/BarChart";

export default function PortfolioReturnsBarChart({
  portfolioId,
  portfolioName,
}) {
  const [barChartDataByYear, setBarChartDataByYear] = useState({});
  const [barChartOptions, setBarChartOptions] = useState({});

  useEffect(() => {
    if (!portfolioId) return;
    if (!portfolioName) return;
    const getPortfolioReturns = async () => {
      let portfolioReturns = await getPortfolioAnnualReturnsAPI(
        portfolioId,
        "2021"
      );
      if (portfolioReturns.status != 200) {
        console.log("error getting portfolio returns");
        return;
      }
      console.log("portfolio returns", portfolioReturns);
      let options = {
        responsive: true,
        plugins: {
          legend: {
            position: "top",
          },
          title: {
            display: true,
            text: "% returns by year",
          },
        },
      };

      const labels = Object.keys(portfolioReturns.data);

      let data = {
        labels,
        datasets: [
          {
            label: portfolioName,
            data: Object.values(portfolioReturns.data),
            backgroundColor: "rgba(255, 99, 132, 0.5)",
          },
        ],
      };
      setBarChartDataByYear(data);
      setBarChartOptions(options);
    };
    getPortfolioReturns();
  }, [portfolioId, portfolioName]);

  return (
    <div className="card position-static shadow mb-4">
      <div className="card-header py-3 d-flex justify-content-between">
        <h6 className="m-0 font-weight-bold text-primary">Portfolio Returns</h6>
      </div>
      <div className="card-body row">
        <div className="chart-area">
          {barChartDataByYear &&
            barChartDataByYear.datasets &&
            barChartDataByYear.datasets.length > 0 && (
              <BarChart data={barChartDataByYear} options={barChartOptions} />
            )}
        </div>
      </div>
    </div>
  );
}
