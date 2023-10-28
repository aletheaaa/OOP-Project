import React, { useEffect, useState } from "react";
import AreaChart from "../../components/Common/AreaChart";
import DashboardCard from "../../components/Common/DashboardCard";
import {
  getPortfolioGrowthByYearAPI,
  getPortfolioGrowthByMonthAPI,
} from "../../api/portfolio";

export default function PortfolioGrowthLineGraph({
  portfolioId,
  portfolioName,
  startDate,
}) {
  const [chartData, setChartData] = useState({});
  const [lineGraphConfig, setLineGraphConfig] = useState("annual");
  const [chartOptions, setChartOptions] = useState({});
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (portfolioId == null || portfolioId == null) return;
    const getPortfolioGrowth = async () => {
      setErrorMessage("");
      let labels = [];
      let datasets = [];
      if (lineGraphConfig == "annual") {
        const response = await getPortfolioGrowthByYearAPI(
          portfolioId,
          startDate.split("-")[0]
        );
        if (response.status != 200) {
          console.log("Error in fetching portfolio growth", response.data);
          setChartData({});
          setChartOptions({});
          setErrorMessage(response.data);
          return;
        }
        labels = Object.keys(response.data);
        datasets = [
          {
            label: portfolioName,
            data: Object.values(response.data),
            borderColor: "rgb(255, 99, 132)",
            backgroundColor: "rgba(255, 99, 132, 0.5)",
          },
        ];
        const data = {
          labels,
          datasets: datasets,
        };
        const options = {
          responsive: true,
          plugins: {
            legend: {
              position: "top",
            },
            title: {
              display: true,
              text: "Returns",
            },
          },
          scales: {
            x: {
              ticks: {
                callback: function (value, index, ticks) {
                  return this.getLabelForValue(value);
                },
              },
            },
          },
        };
        setChartData(data);
        setChartOptions(options);
      } else if (lineGraphConfig == "monthly") {
        const response = await getPortfolioGrowthByMonthAPI(
          portfolioId,
          startDate.split("-")[0],
          startDate.split("-")[1]
        );
        if (response.status != 200) {
          console.log("Error in fetching portfolio growth", response.data);
          setErrorMessage(response.data);
          setChartData({});
          setChartOptions({});
          return;
        }
        let labels = [];
        let datasets = [];
        for (let year of Object.keys(response.data)) {
          for (let month of Object.keys(response.data[year])) {
            labels.push(month + ", " + year);
            datasets.push(response.data[year][month]);
          }
        }
        const data = {
          labels: labels,
          datasets: [
            {
              label: portfolioName,
              data: datasets,
              borderColor: "blue",
              fill: false,
            },
          ],
        };

        const options = {
          scales: {
            x: {
              ticks: {
                callback: function (value, index, ticks) {
                  let dataPoint = this.getLabelForValue(value).split(",");
                  if (dataPoint[0] == "January") {
                    return dataPoint[1];
                  } else {
                    return "";
                  }
                },
              },
            },
          },
        };
        setChartOptions(options);
        setChartData(data);
      }
    };
    getPortfolioGrowth();
  }, [lineGraphConfig, portfolioId, portfolioName, startDate]);

  return (
    <div className="card position-static shadow mb-4">
      <div className="card-header py-3 d-flex justify-content-between">
        <h6 className="m-0 font-weight-bold text-primary">Portfolio Growth</h6>
        <select
          className="form-select w-25"
          aria-label="Default select example"
          onChange={(e) => {
            setLineGraphConfig(e.target.value);
          }}
        >
          <option value="annual">Annual Growth</option>
          <option value="monthly">Monthly Growth</option>
        </select>
      </div>
      <div className="card-body row">
        {errorMessage && (
          <div className="position-static text-danger" role="alert">
            {errorMessage}
          </div>
        )}
        <div className="chart-area">
          {chartData &&
            chartOptions &&
            chartData.datasets &&
            chartData.datasets.length > 0 &&
            Object.keys(chartOptions).length !== 0 && (
              <AreaChart data={chartData} options={chartOptions} />
            )}
        </div>
      </div>
    </div>
  );
}
