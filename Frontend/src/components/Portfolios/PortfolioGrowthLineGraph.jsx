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
  portfolio2Id,
  startDate2,
  portfolioName2,
}) {
  const [chartData, setChartData] = useState({});
  const [lineGraphConfig, setLineGraphConfig] = useState("annual");
  const [chartOptions, setChartOptions] = useState({});
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    console.log(
      "this is protfolioId",
      portfolioId,
      portfolio2Id,
      startDate,
      startDate2
    );
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
        let portfolio2response;
        if (portfolio2Id) {
          portfolio2response = await getPortfolioGrowthByYearAPI(
            portfolio2Id,
            startDate2.split("-")[0]
          );
          if (portfolio2response.status != 200) {
            console.log(
              "Error in fetching portfolio growth",
              portfolio2response.data
            );
            setErrorMessage(portfolio2response.data);
            setChartData({});
            setChartOptions({});
            return;
          }
          //  get a combination of the keys in portoflio2response and response and remove duplicates
          labels = [
            ...new Set([
              ...Object.keys(response.data),
              ...Object.keys(portfolio2response.data),
            ]),
          ];
          // sorting the labels
          labels.sort((a, b) => {
            return parseInt(a) - parseInt(b);
          });
          // adding 0 to the data points that are not present in the response
          for (let i = 0; i < labels.length; i++) {
            if (!response.data[labels[i]]) {
              response.data[labels[i]] = null;
            }
            if (!portfolio2response.data[labels[i]]) {
              portfolio2response.data[labels[i]] = null;
            }
          }
          datasets = [
            {
              label: portfolioName,
              data: Object.values(response.data),
              borderColor: "rgb(255, 99, 132)",
              backgroundColor: "rgba(255, 99, 132, 0.5)",
            },
            {
              label: portfolioName2,
              data: Object.values(portfolio2response.data),
              borderColor: "rgb(132, 132, 255)",
              backgroundColor: "rgba(132, 132, 255, 0.5)",
            },
          ];
        } else {
          labels = Object.keys(response.data);
          datasets = [
            {
              label: portfolioName,
              data: Object.values(response.data),
              borderColor: "rgb(255, 99, 132)",
              backgroundColor: "rgba(255, 99, 132, 0.5)",
            },
          ];
        }
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
        let datasets2 = [];
        let data = {};
        if (portfolio2Id) {
          let portfolio2response = await getPortfolioGrowthByMonthAPI(
            portfolio2Id,
            startDate2.split("-")[0],
            startDate2.split("-")[1]
          );
          if (portfolio2response.status != 200) {
            console.log(
              "Error in fetching portfolio growth",
              portfolio2response.data
            );
            setErrorMessage(portfolio2response.data);
            setChartData({});
            setChartOptions({});
            return;
          }
          //  get a combination of the keys in portoflio2response and response and remove duplicates
          for (let year of Object.keys(response.data)) {
            for (let month of Object.keys(response.data[year])) {
              labels.push(month + ", " + year);
              datasets.push(response.data[year][month]);
            }
          }
          // adding response 2 labels if not present in response 1
          for (let year of Object.keys(portfolio2response.data)) {
            for (let month of Object.keys(portfolio2response.data[year])) {
              if (!labels.includes(month + ", " + year)) {
                labels.push(month + ", " + year);
              }
              datasets2.push(portfolio2response.data[year][month]);
            }
          }
          // sorting the labels based on year and month
          let monthMapping = {
            January: "1",
            February: "2",
            March: "3",
            April: "4",
            May: "5",
            June: "6",
            July: "7",
            August: "8",
            September: "9",
            October: "10",
            November: "11",
            December: "12",
          };
          labels.sort((a, b) => {
            let [month1, year1] = a.split(", ");
            let [month2, year2] = b.split(", ");
            if (year1 == year2) {
              return (
                parseInt(monthMapping[month1]) - parseInt(monthMapping[month2])
              );
            } else {
              return parseInt(year1) - parseInt(year2);
            }
          });
          console.log(response.data);
          console.log(portfolio2response.data);
          console.log(labels);
          // adding 0 to the data points that are not present in the response into dataset & dataset2
          for (let i = 0; i < labels.length; i++) {
            const [month, year] = labels[i].split(", ");
            if (!response.data[year] || !response.data[year][month]) {
              datasets.splice(i, 0, null);
            }
            if (
              !portfolio2response.data[year] ||
              !portfolio2response.data[year][month]
            ) {
              datasets2.splice(i, 0, null);
            }
          }

          data = {
            labels: labels,
            datasets: [
              {
                label: portfolioName,
                data: datasets,
                borderColor: "rgb(255, 99, 132)",
                backgroundColor: "rgba(255, 99, 132, 0.5)",
              },
              {
                label: portfolioName2,
                data: datasets2,
                borderColor: "rgb(132, 132, 255)",
                backgroundColor: "rgba(132, 132, 255, 0.5)",
              },
            ],
          };
        } else {
          for (let year of Object.keys(response.data)) {
            for (let month of Object.keys(response.data[year])) {
              labels.push(month + ", " + year);
              datasets.push(response.data[year][month]);
            }
          }
          data = {
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
        }

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
