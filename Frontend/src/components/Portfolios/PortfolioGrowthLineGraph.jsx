import React, { useEffect, useState } from "react";
import AreaChart from "../../components/Common/AreaChart";
import DashboardCard from "../../components/Common/DashboardCard";
import { getPortfolioGrowthByYearAPI } from "../../api/portfolio";

export default function PortfolioGrowthLineGraph({
  portfolioId,
  portfolioName,
}) {
  const [chartData, setChartData] = useState({});
  const [lineGraphConfig, setLineGraphConfig] = useState("annual");

  useEffect(() => {
    const getPortfolioGrowth = async () => {
      if (lineGraphConfig == "annual") {
        const response = await getPortfolioGrowthByYearAPI(portfolioId, "2021");
        if (response.status != 200) {
          console.log("Error in fetching portfolio growth");
          return;
        }

        const labels = Object.keys(response.data);
        const datasets = [
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
        setChartData(data);
      }
    };
    getPortfolioGrowth();
  }, []);

  return (
    <div className="card position-static shadow mb-4">
      <div className="card-header py-3 d-flex justify-content-between">
        <h6 className="m-0 font-weight-bold text-primary">Portfolio Growth</h6>
        <select
          className="form-select w-25"
          aria-label="Default select example"
          onChange={(e) => {
            console.log("e", e.target.value);
            setLineGraphConfig(e.target.value);
          }}
        >
          <option value="annual">Annual Returns</option>
          <option value="quarter">Quarter Returns</option>
        </select>
      </div>
      <div className="card-body row">
        <div className="chart-area">
          {chartData && chartData.datasets && chartData.datasets.length > 0 && (
            <AreaChart data={chartData} />
          )}
        </div>
      </div>
    </div>
  );
}
