import React, { useEffect, useState } from "react";
import AreaChart from "../../components/Common/AreaChart";
import DoughnutChart from "../../components/Common/DoughnutChart";
import DashboardCard from "../../components/Common/DashboardCard";

export default function Dashboard() {
  const [areaChartData, setAreaChartData] = useState([]);
  const [doughnutChartData, setDoughnutChartData] = useState([]);

  useEffect(() => {
    const getAreaChartData = () => {
      const labels = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ];
      const data = {
        labels,
        datasets: [
          {
            label: "Actual Performance",
            data: [8, 7, 6, 7, 9, 7, 9, 6, 9, 7, 8, 9],
            borderColor: "rgb(255, 99, 132)",
            backgroundColor: "rgba(255, 99, 132, 0.5)",
          },
          {
            label: "Index",
            data: [2, 3, 5, 2, 3, 4, 1, 5, 1, 3, 2, 7],
            borderColor: "rgb(53, 162, 235)",
            backgroundColor: "rgba(53, 162, 235, 0.5)",
          },
        ],
      };
      setAreaChartData(data);
    };
    const getDoughnutChartData = () => {
      const data = {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [
          {
            label: "% of Capital Allocated to Different Stocks",
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
              "rgba(255, 99, 132, 0.2)",
              "rgba(54, 162, 235, 0.2)",
              "rgba(255, 206, 86, 0.2)",
              "rgba(75, 192, 192, 0.2)",
              "rgba(153, 102, 255, 0.2)",
              "rgba(255, 159, 64, 0.2)",
            ],
            borderColor: [
              "rgba(255, 99, 132, 1)",
              "rgba(54, 162, 235, 1)",
              "rgba(255, 206, 86, 1)",
              "rgba(75, 192, 192, 1)",
              "rgba(153, 102, 255, 1)",
              "rgba(255, 159, 64, 1)",
            ],
            borderWidth: 1,
          },
        ],
      };
      setDoughnutChartData(data);
    };
    getAreaChartData();
    getDoughnutChartData();
  }, []);

  return (
    <>
      <div className="container my-3">
        <div className="row">
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Earnings (Monthly)"
              value="$40,000"
              iconClassName="bi-coin"
              colorClassName="primary"
            />
          </div>
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Earnings (Annual)"
              value="$360,000"
              iconClassName="bi-coin"
              colorClassName="success"
            />
          </div>
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Task Completion Status"
              value="50%"
              iconClassName="bi-clipboard"
              colorClassName="secondary"
            />
          </div>
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Pending Requests"
              value="18"
              iconClassName="bi-chat"
              colorClassName="warning"
            />
          </div>
        </div>
        <div className="row">
          <div className="col-xl-8 col-lg-7">
            <div class="card shadow mb-4">
              <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Returns</h6>
              </div>
              <div class="card-body">
                <div class="chart-area">
                  {areaChartData &&
                    areaChartData.datasets &&
                    areaChartData.datasets.length > 0 && (
                      <AreaChart data={areaChartData} />
                    )}
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-4 col-lg-5">
            <div class="card shadow mb-4">
              <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">
                  % of Capital Allocated to Different Stocks
                </h6>
              </div>
              <div class="card-body">
                <div class="chart-pie pt-4">
                  {doughnutChartData &&
                    doughnutChartData.datasets &&
                    doughnutChartData.datasets.length > 0 && (
                      <DoughnutChart data={doughnutChartData} />
                    )}
                </div>
              </div>
            </div>
          </div>
          {/* <div className="col-6 ml-3 d-flex justify-content-center">
        </div> */}
        </div>
      </div>
    </>
  );
}
