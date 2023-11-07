import React, { useEffect, useState } from "react";
import { getPortfolioAnnualReturnsAPI } from "../../api/portfolio";
import BarChart from "../Common/BarChart";

export default function PortfolioReturnsBarChart({
  portfolioId,
  portfolioName,
  startDate,
  portfolio2Id,
  portfolio2Name,
  startDate2,
}) {
  const [barChartDataByYear, setBarChartDataByYear] = useState({});
  const [barChartOptions, setBarChartOptions] = useState({});
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (!portfolioId) return;
    if (!portfolioName) return;
    if (!startDate) return;
    const getPortfolioReturns = async () => {
      let portfolioReturns = await getPortfolioAnnualReturnsAPI(
        portfolioId,
        startDate.split("-")[0]
      );
      if (portfolioReturns.status != 200) {
        console.log("error getting portfolio returns", portfolioReturns.data);
        setErrorMessage(portfolioReturns.data);
        return;
      }
      let labels = [];
      let data;
      if (portfolio2Id) {
        let portfolioReturns2 = await getPortfolioAnnualReturnsAPI(
          portfolio2Id,
          startDate2.split("-")[0]
        );
        if (portfolioReturns2.status != 200) {
          console.log(
            "error getting portfolio returns",
            portfolioReturns2.data
          );
          setErrorMessage(portfolioReturns2.data);
          return;
        }
        portfolioReturns2 = portfolioReturns2.data;
        portfolioReturns = portfolioReturns.data;
        // getting unique labels
        labels = [
          ...new Set([
            ...Object.keys(portfolioReturns),
            ...Object.keys(portfolioReturns2),
          ]),
        ];
        // sorting the labels based on year
        labels.sort((a, b) => a - b);
        data = {
          labels,
          datasets: [
            {
              label: portfolioName,
              data: labels.map((label) => portfolioReturns[label] || 0),
              backgroundColor: "rgba(255, 99, 132, 0.5)",
            },
            {
              label: portfolio2Name,
              data: labels.map((label) => portfolioReturns2[label] || 0),
              backgroundColor: "rgba(54, 162, 235, 0.5)",
            },
          ],
        };
      } else {
        labels = Object.keys(portfolioReturns.data);
        data = {
          labels,
          datasets: [
            {
              label: portfolioName,
              data: Object.values(portfolioReturns.data),
              backgroundColor: "rgba(255, 99, 132, 0.5)",
            },
          ],
        };
      }
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

      setBarChartDataByYear(data);
      setBarChartOptions(options);
    };
    getPortfolioReturns();
  }, [
    portfolioId,
    portfolioName,
    startDate,
    portfolio2Id,
    startDate2,
    portfolio2Name,
  ]);

  return (
    <div className="card position-static shadow mb-4">
      <div className="card-header py-3 d-flex justify-content-between">
        <h6 className="m-0 font-weight-bold text-primary">Portfolio Returns</h6>
      </div>
      <div className="card-body row">
        <div className="chart-area">
          {errorMessage && (
            <div className="alert text-danger" role="alert">
              {errorMessage}
            </div>
          )}
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
